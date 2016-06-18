package mainpagers.cpackage.t23DesignMode.builder;

/**
 * Created by LiuShao on 2016/5/19.
 * 统一组装过程
 */
public class Director {
    Builder mBuilder = null;

    public Director(Builder builder){
        mBuilder = builder;
    }

    public void construct(String board,String display){
        mBuilder.BuildBoard(board);
        mBuilder.BuildDisplay(display);
        mBuilder.BuildOS();
    }



}
