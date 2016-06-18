package mainpagers.cpackage.t23DesignMode.abstractfactory;

/**
 * Created by LiuShao on 2016/6/17.
 */
public class AbstractFactoryTest {

    public void test(){
        CarFactory factory = new QQFactory();
        factory.createBrake().brake();
        factory.createEngine().engine();
        factory.createItire().tire();

        CarFactory factory1 = new SUVFactory();
        factory1.createEngine().engine();

    }

}
