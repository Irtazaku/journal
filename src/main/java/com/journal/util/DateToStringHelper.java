package com.journal.util;

import com.journal.util.ConstantsAndEnums.GlobalConstants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateToStringHelper {

	public static String dateString = GlobalConstants.DF_DEFAULT_DATE_STRING;
	
	public String dateToStringWithMonthCode(Date date){
		if(date != null){
			DateFormat df = new SimpleDateFormat(GlobalConstants.DF_WITH_MONTH_CODE_ONE);
			dateString = df.format(date);
		}
		return dateString;
	}
	
	public String summaryDateFullMonth(Date date){
		if(date != null){
			DateFormat df = new SimpleDateFormat(GlobalConstants.DF_WITH_FULL_MONTH);
			dateString = df.format(date).toUpperCase();
		}
		return dateString;
	}
	
	public String summaryDate(Date date){
		if(EntityHelper.isNotNull(date)){
			DateFormat df = new SimpleDateFormat(GlobalConstants.DF_WITH_MONTH_CODE_TWO);
			dateString = df.format(date).toUpperCase();
		}
		return dateString;
	}

	public String datePeriodString(Date startDate, Date endDate){
		if(EntityHelper.isNotNull(startDate, endDate)){
			DateFormat df = new SimpleDateFormat(GlobalConstants.DF_PERIOD);
			String startDateString = df.format(startDate);
			String endDateString = df.format(endDate);
			dateString = startDateString + " to " + endDateString;
		}
		return dateString;
	}

	public String dateString(Date date){
		if(EntityHelper.isNotNull(date)){
			DateFormat df = new SimpleDateFormat(GlobalConstants.DF_PERIOD);
			dateString = df.format(date);;
		}
		return dateString;
	}
	
	public String timeToString(Date date){
		if(EntityHelper.isNotNull(date)){
			DateFormat df = new SimpleDateFormat(GlobalConstants.DF_TIME);
			dateString = df.format(date);
		}
		return dateString;
	}

	public static String toIso(Date date){
		if(date == null){
			return null;
		}
		return ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).print(new DateTime(date));
	}
}
