package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import liushaobo.mad.R;
import utils.Speaker;
import utils.TextToSpeechAll;

/**
 * Created by LiuShao on 2016/1/31.
 */
public class WeiTextToSpeechPager extends BaseWeiChatPager{


    @ViewInject(R.id.tv_speech1)
    private Button button1;
    @ViewInject(R.id.tv_speech2)
    private Button button2;
    @ViewInject(R.id.tv_speech3)
    private Button button3;

    public WeiTextToSpeechPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.weichat_text_tospeech,null);
        x.view().inject(this, view);
        return view;
    }


    private Speaker speaker;
    

    @Override
    public void initData(String msg) {

        
        setButtonText(button1,"殷孝桥是不是糊");
        setButtonText(button2,"殷孝桥是个好孩子");
        setButtonText(button3,"吴冲冲是个大帅哥");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("speek","button1");
                TextToSpeechAll.getTextToSpeechAll().speakVoice("殷孝桥是不是糊");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("speek","button2");
                TextToSpeechAll.getTextToSpeechAll().speakVoice("殷孝桥是个好孩子");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("speek","button3");
                TextToSpeechAll.getTextToSpeechAll().speakVoice("吴冲冲是个大帅哥");
            }
        });
    }

    void setButtonText(Button button,String texts){
        button.setText(texts);
    }

}
