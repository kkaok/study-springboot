package eblo.study.springboot.web.servlet.constant;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class DateFormatConstant {

    public final static String DATE_FORMAT_STR = "yyyy-MM-dd";
    public final static String DATETIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR);
    public final static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(DATETIME_FORMAT_STR);
    
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_STR);
    public final static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT_STR);

}
