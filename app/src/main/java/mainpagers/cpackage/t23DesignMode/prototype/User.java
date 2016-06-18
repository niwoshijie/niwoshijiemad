package mainpagers.cpackage.t23DesignMode.prototype;

/**
 * Created by LiuShao on 2016/6/13.
 * 用户实体类
 */
public class User implements Cloneable{
    public int age;
    public String phoneNum;
    public Adress address;

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", phoneNum='" + phoneNum + '\'' +
                ", address=" + address +
                '}';
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        User user = null;

        try {
            user = (User) super.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


        return user;
    }
}
