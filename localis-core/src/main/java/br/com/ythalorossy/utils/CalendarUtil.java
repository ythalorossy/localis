package br.com.ythalorossy.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	public static Calendar convert(Date date) {

		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		
		return calendar;
	}
	
	public static Date convert(Calendar calendar) {

		return calendar.getTime();
	}

}
