package com.backend.ecommerce.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.ecommerce.dto.TransactionDto;
import com.backend.ecommerce.model.Transaction;
import com.backend.ecommerce.model.TransactionDetail;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.repository.TransactionDetailRepository;
import com.backend.ecommerce.repository.TransactionRepository;
import com.backend.ecommerce.util.FormatUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionDetailRepository transactionDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	 @Value("${spring.kafka.template.default-topic}")
	 private String defaultTopic;

	 @Transactional
	 public Transaction save(TransactionDto transactionData) {
	     try {
	         Date date = FormatUtil.convertToLocalDateTime(transactionData.getDate());
	         Transaction newTransaction = transactionRepository.save(new Transaction(date, BigDecimal.ZERO));
	         boolean allProductsInStock = transactionData.getDetail().stream()
	                 .allMatch(detail -> processTransactionDetail(newTransaction, detail));

	         if (!allProductsInStock) {
	             throw new RuntimeException("Failed to make a transaction because there is a product out of stock");
	         }
	         return newTransaction;
	     } catch (ParseException e) {
	         throw new RuntimeException("Failed to parse date", e);
	     }
	 }

	private boolean processTransactionDetail(Transaction newTransaction, TransactionDetail transactionDetail) {
		return productRepository.findById(transactionDetail.getProduct().getId()).map(productExisting -> {
			if (productExisting.getStock() >= transactionDetail.getQty()) {
				productExisting.setStock(productExisting.getStock() - transactionDetail.getQty());
				productRepository.save(productExisting);
				
				BigDecimal subtotal = productExisting.getPrice().multiply(BigDecimal.valueOf(transactionDetail.getQty()));
                newTransaction.setAmount(newTransaction.getAmount().add(subtotal));

				transactionDetail.setProduct(productExisting);
				transactionDetail.setTransaction(newTransaction);
				transactionDetailRepository.save(transactionDetail);
				return true;
			} else {
				return false;
			}
		}).orElse(false);
	}

	public TransactionDto findById(Long id) {
		Transaction transaction = transactionRepository.findById(id).orElse(null);
		if (transaction == null) {
	        throw new EntityNotFoundException("Transaction with ID " + id + " not found");
	    }
		List<TransactionDetail> listTransactionDetail = transactionDetailRepository.findByTransactionId(transaction.getId());
		TransactionDto dto = new TransactionDto();
		dto.setId(transaction.getId());
		try {
			dto.setDate(FormatUtil.convertDateToString(transaction.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dto.setAmount(transaction.getAmount());
		dto.setDetail(listTransactionDetail);

		return dto;
	}

	public Iterable<TransactionDto> findAll() {
	    Sort ascendingSort = Sort.by(Sort.Order.asc("id"));
	    List<TransactionDto> listDtos = new ArrayList<>();
	    Iterable<Transaction> transactions = transactionRepository.findAll(ascendingSort);
	    if (!transactions.iterator().hasNext()) {
	        throw new EntityNotFoundException("No transactions found");
	    }
	    transactions.forEach(transaction -> {
	        List<TransactionDetail> listDetailDtos = new ArrayList<>();
	        transactionDetailRepository.findByTransactionId(transaction.getId()).forEach(transactionDetail -> {
	            listDetailDtos.add(transactionDetail);
	        });

	        TransactionDto transactionDto = new TransactionDto();
	        transactionDto.setId(transaction.getId());
	        try {
	            transactionDto.setDate(FormatUtil.convertDateToString(transaction.getDate()));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        transactionDto.setAmount(transaction.getAmount());
	        transactionDto.setDetail(listDetailDtos);
	        listDtos.add(transactionDto);
	    });

	    return listDtos;
	}

	public void generateInvoice(Long id) {
		TransactionDto dto = findById(id);
		if (dto != null) {
			sendMessage(defaultTopic, dto);
		}
	}

	public void sendMessage(String topic, TransactionDto dto) {
		try {
			String data = objectMapper.writeValueAsString(dto);
			kafkaTemplate.send(topic, data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
