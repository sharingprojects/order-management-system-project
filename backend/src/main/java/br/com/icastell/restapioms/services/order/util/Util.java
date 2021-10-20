package br.com.icastell.restapioms.services.order.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {
	
	public static Date addDaysToDate(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
			
		return c.getTime();
	}
	
	public static String convertDateToString(Date date) {

		SimpleDateFormat sDate;

		String country = System.getProperty("user.country");

		if ("BR".equals(country))
			sDate = new SimpleDateFormat("dd-MMM-yy");

		else
			sDate = new SimpleDateFormat("MM/dd/yyyy");

		return sDate.format(date);
	}
	
	public static NumberFormat findCurrencyInstance() {
		
		NumberFormat nf = null;
		String country = System.getProperty("user.country");

		if ("BR".equals(country))
		    nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		
		return nf;
	}
	
	

}
