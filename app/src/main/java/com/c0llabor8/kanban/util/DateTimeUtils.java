package com.c0llabor8.kanban.util;

import android.text.format.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

  static private String dateFormat = "MM/dd/yy";
  static private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());

  /**
   * Converts dates from long format to String format
   *
   * @param millis time in milliseconds
   * @return Formatted date string
   */
  public static String getDateString(long millis) {
    Date date = new Date(millis);
    sdf.setTimeZone(TimeZone.getDefault());
    return sdf.format(date);
  }

  public static String getRelativeTime(long millis) {
    return DateUtils.getRelativeTimeSpanString(
        millis,
        System.currentTimeMillis(),
        DateUtils.SECOND_IN_MILLIS
    ).toString();
  }

}
