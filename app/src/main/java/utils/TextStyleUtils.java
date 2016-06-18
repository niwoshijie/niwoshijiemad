package utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiuShao on 2016/3/15.
 */
public class TextStyleUtils {
    /**
     * 关键字高亮显示
     * @param
     */
    public static  SpannableString highlight(String message){
        message = message.replaceAll(";",",");
        SpannableString spannable = new SpannableString(message);
        CharacterStyle span = null;
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(message);
        while (m.find()) {
            span = new ForegroundColorSpan(Color.RED);//需要标红
            spannable.setSpan(span, m.start(),  m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RelativeSizeSpan(2.0f), m.start(),m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }


}
