package mainpagers.cpackage.t23DesignMode.factory;

/**
 * Created by LiuShao on 2016/6/13.
 * 工厂方法模拟，对数据的处理
 */

public abstract class IOHandler {

    /*添加一条信息*/
    public abstract void add(String id,String name);

    /*删除一条信息*/
    public abstract void delete(String id);

    /*更新一条信息*/
    public abstract void update(String id,String name);

    /*查询某条信息*/
    public abstract String check(String id);

}
