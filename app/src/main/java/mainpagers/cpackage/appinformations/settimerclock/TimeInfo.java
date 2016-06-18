package mainpagers.cpackage.appinformations.settimerclock;

import android.app.Activity;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by LiuShao on 2016/6/15.
 */
public class TimeInfo{

    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mDate;


    private Activity context;


    // On clicking Time picker
    public void setTime(View v){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                null,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(context.getFragmentManager(), "Timepickerdialog");
    }


    // On clicking Date picker
    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.getShowsDialog();
        dpd.show(context.getFragmentManager(), "Datepickerdialog");
    }


}
