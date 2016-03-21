package org.thinkadv.enxita.youtubereviewsapp.youtubeapi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.client.util.DateTime;

public class Utilities {
	private static DateFormat dateFormat = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");

	public static Date convertGoogleDate(DateTime dateTime)
			throws ParseException {
		Date date = null;

		if (dateTime != null) {
			date = new Date(dateTime.getValue());
		}

		return date;
	}

	public static String convertDateToString(Date date) {

		String text = dateFormat.format(date);

		return text;
	}

	public static Date convertStringToDate(String dateString)
			throws ParseException {
		Date date = null;
		date = dateFormat.parse(dateString);

		return date;
	}

}
