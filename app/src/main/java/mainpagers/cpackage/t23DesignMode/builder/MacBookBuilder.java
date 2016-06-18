package mainpagers.cpackage.t23DesignMode.builder;

/**
 * Created by LiuShao on 2016/5/19.
 * 具体的Builder类，MacBookBuilder类
 */
public class MacBookBuilder extends Builder{

    private Computer mComputer = new MacBook();

    @Override
    public void BuildBoard(String board) {
        mComputer.setmBoard(board);
    }

    @Override
    public void BuildDisplay(String display) {
        mComputer.setmDisplay(display);
    }

    @Override
    public void BuildOS() {
        mComputer.setmOS();
    }

    @Override
    public Computer create() {
        return mComputer;
    }



}
