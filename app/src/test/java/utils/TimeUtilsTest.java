package utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by feige on 2016/12/27.
 */
public class TimeUtilsTest {

    private   SimpleDateFormat sFormatToday = new SimpleDateFormat("今天 HH:mm");
    private   SimpleDateFormat sFormatThisYear = new SimpleDateFormat("MM/dd HH:mm");
    private   SimpleDateFormat sFormatOtherYear = new SimpleDateFormat("yy/MM/dd HH:mm");
    private SimpleDateFormat sFormatMessageToday = new SimpleDateFormat("今天");
    private SimpleDateFormat sFormatMessageThisYear = new SimpleDateFormat("MM/dd");
    private SimpleDateFormat sFormatMessageOtherYear = new SimpleDateFormat("yy/MM/dd");

    @Test
    public void testString(){
        String aaa = "2016-12-27 15:38:10";
        String bb = StringDayToNow(aaa);
        System.out.println(bb);

    }

    public  String StringDayToNow(String times){
        String returnTime = times;
        try {
            returnTime = dayToNow(longFromDay(times));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnTime;
    }

    /**
     *
     * @param day
     * @return
     * @throws ParseException
     */
    public static long longFromDay(String day) throws ParseException {
        final String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.parse(day).getTime();
    }

    public  String dayToNow(long time) {
        return dayToNow(time, true);
    }

    public  String dayToNow(long time, boolean displayHour) {
        long nowMill = System.currentTimeMillis();

        long minute = (nowMill - time) / 60000;
        if (minute < 60) {
            if (minute <= 0) {
                return Math.max((nowMill - time) / 1000, 1) + "秒前"; // 由于手机时间的原因，有时候会为负，这时候显示1秒前
            } else {
                return minute + "分钟前";
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(nowMill);
        Long timeObject = new Long(time);
        if (calendar.get(GregorianCalendar.YEAR) != year) { // 不是今年
            SimpleDateFormat formatOtherYear = displayHour ? sFormatOtherYear : sFormatMessageOtherYear;
            return formatOtherYear.format(timeObject);
        } else if (calendar.get(GregorianCalendar.MONTH) != month
                || calendar.get(GregorianCalendar.DAY_OF_MONTH) != day) { // 今年
            SimpleDateFormat formatOtherYear = displayHour ? sFormatThisYear : sFormatMessageThisYear;
            return formatOtherYear.format(timeObject);
        } else { // 今天
            SimpleDateFormat formatToday = displayHour ? sFormatToday : sFormatMessageToday;
            return formatToday.format(timeObject);
        }
    }

}