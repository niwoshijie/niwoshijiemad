package mainpagers.cpackage.t23DesignMode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import liushaobo.mad.R;
import mainpagers.cpackage.t23DesignMode.listenercallback.Mode;
import mainpagers.cpackage.t23DesignMode.listenercallback.TestBean;
import mainpagers.cpackage.t23DesignMode.listenercallback.TestBean2;

/**
 * 23中设计模式
 */
public class T23DesignMode extends Activity implements View.OnClickListener {

    private Button btn_1;
    private Button btn_2;
    private Mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t23_design_mode);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_1:

                TestBean testBean = new TestBean();
                mode = testBean;
                mode.onClick(v);
                break;
            case R.id.btn_2:
                TestBean2 testBean2 = new TestBean2();
                mode = testBean2;
                mode.onClick(v);
                break;
        }
    }

}
