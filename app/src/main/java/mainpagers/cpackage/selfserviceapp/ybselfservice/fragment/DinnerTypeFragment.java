package mainpagers.cpackage.selfserviceapp.ybselfservice.fragment;

import android.os.Bundle;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.beans.TestObj;
import mainpagers.cpackage.selfserviceapp.ybselfservice.DetialActivity;

/**
 * Created by LiuShao on 2016/4/11.
 */
@ContentView(R.layout.fragment_dinner_type)
public class DinnerTypeFragment extends BaseFragment{

    private GoodDetialFragment goodDetialFragment;
    private Bundle bundle = new Bundle();
    private String DINNER_TYPE ="dinnerType";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            goodDetialFragment = (GoodDetialFragment) getActivity().getSupportFragmentManager().findFragmentByTag(GOODS_DETIAL_TAG);
        }
        if(goodDetialFragment == null){
            goodDetialFragment = new GoodDetialFragment();
        }
        testObj = new TestObj();
    }

    private TestObj testObj;
    @Event(value={R.id.iv_dinnertype_eatin,R.id.iv_dinnertype_packageing},type=View.OnClickListener.class)
    private void toNext(View view){
        switch (view.getId()){
            case R.id.iv_dinnertype_eatin:
                bundle.putString(DINNER_TYPE,"eatin");
                testObj.dinnerType= "eatin";
                break;
            case R.id.iv_dinnertype_packageing:
                bundle.putString(DINNER_TYPE,"packageing");
                testObj.dinnerType= "packageing";
                break;
        }


        ((DetialActivity)getActivity()).setObject(testObj);

        switchContent(this, goodDetialFragment, DINER_TYPE_TAG);
        bundle.remove(DINNER_TYPE);
    }

}
