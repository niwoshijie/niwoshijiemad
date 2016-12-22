package mainpagers.cpackage.hardware;

import android.view.KeyEvent;
import android.view.View;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import base.BaseActivity;
import liushaobo.mad.R;

@ContentView(R.layout.c_pager_hard_ware_demo)
public class HardWareDemo extends BaseActivity {

    private BulbLight bulbLight;

    @Override
    public void initView() {
        x.view().inject(this);
    }

    @Override
    public void initValue() {}

    @Override
    protected void initData() {
        bulbLight = new BulbLight();
    }

    @Event(value = {R.id.btn_bulb_open,R.id.btn_bulb_close,R.id.btn_call_cramer}, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bulb_close:
                bulbLight.setFlashlightEnabled(false);
                break;
            case R.id.btn_bulb_open:
                bulbLight.setFlashlightEnabled(true);
                break;
            case R.id.btn_call_cramer:
                startNextActivity(HardWareDemo.this,CramerActivity.class);
                break;
        }
    }

    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
        switch (kCode) {
            case KeyEvent.KEYCODE_BACK:
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                return true;
        }
        return super.onKeyDown(kCode, kEvent);
    }

}
