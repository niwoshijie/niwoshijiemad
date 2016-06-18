package mainpagers.cpackage.t23DesignMode.factory;

/**
 * Created by LiuShao on 2016/6/13.
 */
public class IOTest {

    public void iotest(){
        IOHandler ioHandler = IOFactory.getIOHandler(FileHandler.class);
        ioHandler.add("","");
        String id = ioHandler.check("");
    }
}
