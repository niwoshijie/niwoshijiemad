package mainpagers.cpackage.t23DesignMode.builder;

/**
 * Created by LiuShao on 2016/5/19.
 */
public class BuilderSimpleTest {

    public void test(){
        Builder builder = new MacBookBuilder();
        Director pcDirector = new Director(builder);
        pcDirector.construct("英特尔主板", "我的显示器");
        String a = builder.create().toString();
    }

}
