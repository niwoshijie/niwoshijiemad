package mainpagers.cpackage.t23DesignMode.prototype;

/**
 * Created by LiuShao on 2016/6/13.
 */
public class LoginIml implements LoginInterface{

    @Override
    public void login() {
        User user = new User();
        user.age = 22;
        user.address= new Adress("aa","bb","cc");
        user.phoneNum = "111111";
        LoginSession.setLoginedUser(user);
    }


}
