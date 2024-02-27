package com.backend.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4536717740941266199L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")    
	private Long id;

    @NotEmpty(message = "Name is required")
    @Column(name = "product_name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "product_description", length = 500)
    private String description;

    @NotNull(message = "Price is required")
    @Column(name = "product_price")
    private BigDecimal price;
    
    @NotNull(message = "Stock is required")
    @Column(name = "product_stock")
    private Integer stock;

    @ManyToOne
    @NotNull(message = "Category is required")
    private Category category;

    public Product() {
    }

    public Product(Long id, String name, String description, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
