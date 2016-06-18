package mainpagers.cpackage.t23DesignMode.prototype;

/**
 * Created by LiuShao on 2016/6/13.
 */
public class LoginSession {
    static LoginSession loginSession = null;
    private static User LoginedUser;

    public LoginSession() {
    }

    public static LoginSession getLoginSession() {
        if(loginSession == null){
            loginSession = new LoginSession();
        }
        return loginSession;
    }

    public static void setLoginSession(LoginSession loginSession) {
        LoginSession.loginSession = loginSession;
    }

    public static User getLoginedUser() {
        return LoginedUser;
//        return LoginedUser.clone();
    }

    public static void setLoginedUser(User loginedUser) {
        LoginedUser = loginedUser;
    }
}
