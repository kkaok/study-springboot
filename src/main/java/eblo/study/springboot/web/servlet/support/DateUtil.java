package eblo.study.springboot.web.servlet.support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    
    public static final DateFormat getDateFormat() {
        return getDateFormat(DATE_FORMAT_STR);
    }
    
    public static final DateFormat getDateFormat(String dfs) {
        return new SimpleDateFormat(dfs);
    }
    
    public static final DateFormat getDateTimeFormat() {
        return getDateTimeFormat(DATETIME_FORMAT_STR);
    }
    
    public static final DateFormat getDateTimeFormat(String dfs) {
        return new SimpleDateFormat(dfs);
    }
    
    public static final DateTimeFormatter getDateFormatter() {
        return getDateFormatter(DATE_FORMAT_STR);
    }
    
    public static final DateTimeFormatter getDateFormatter(String dfs) {
        return DateTimeFormatter.ofPattern(dfs);
    }
    
    public static final DateTimeFormatter getDateTimeFormatter() {
        return getDateTimeFormatter(DATETIME_FORMAT_STR);
    }

    public static final DateTimeFormatter getDateTimeFormatter(String dfs) {
        return DateTimeFormatter.ofPattern(dfs);
    }
    
    public static String formatDate(Date date) {
        return formatDate(date, DATE_FORMAT_STR);
    }

    public static String formatDate(Date date, String dfs) {
        return getDateFormat(dfs).format(date);
    }
    
    public static Date parseDate(String dateString) {
        return parseDate(dateString, DATE_FORMAT_STR);
    }
    
    public static Date parseDate(String dateString, String dfs) {
        try {
            return getDateFormat(dfs).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } 
    }

}
