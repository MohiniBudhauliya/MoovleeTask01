package mb.com.moovleelogin.UserRelatedClasses;

/**
 * Created by Anshul on 04-11-17.
 */

public class LoginUserDetails {
    String name,email,logintime,app_name,url;

    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setLogintime(String logintime)
    {
        this.logintime=logintime;
    }
    public String getLogintime()
    {
        return this.logintime;
    }
    public void setAppname(String app_name)
    {
        this.app_name=app_name;
    }
    public String getAppname()
    {
        return  this.app_name;
    }
    public void setPic_url(String url)
    {
        this.url=url;
    }
    public String getPic_url()
    {
        return this.url;
    }


}
