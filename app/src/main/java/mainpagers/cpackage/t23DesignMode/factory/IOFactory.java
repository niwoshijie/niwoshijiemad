package mainpagers.cpackage.t23DesignMode.factory;

/**
 * Created by LiuShao on 2016/6/13.
 * 简单工厂模式
 */
public abstract class IOFactory {
    public static <T extends IOHandler> T  getIOHandler(Class clz){
        IOHandler ioHandler= null;
        try {
            ioHandler = (IOHandler) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) ioHandler;
    }

}
