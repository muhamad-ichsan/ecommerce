package com.backend.ecommerce.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FormatUtil {

	public static Date convertToLocalDateTime(String dateString) throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
	public static String convertDateToString(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
}
