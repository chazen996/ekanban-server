package chazen.ekanban.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class SysUser {

    private int id;
    private String username;
    private String password;
    /* roles暂时不起作用，本项目用户无权限之分，这里保存下来的作用是为了适配JWT的各种
     * 拦截器和权限配置的需要，在findUserByUsername方法中直接赋值为ROLE_USER（仅当
     * 此方法被调用时才会生成JwtUser,此时赋上值即可）*/
    @JsonIgnore
    private List<String> roles;

    /* JsonFormat注解把Date类型的属性（时间戳）转换为可读的指定格式；
     * 这里的lastPasswordResetDate用来控制token在修改密码后无效需重新获取；
      * 关于数据库和实体类时间属性的转换，数据库中时间可直接使用timestamp，并设置
      * 默认为CURRENT_TIMESTAMP，勾上根据当前时间戳更新选项（更改密码时可自动更新），
      * 实体类的属性直接使用java.util.Date即好，但注意应当在save之前手动为其赋值
      * 设置当前系统时间。同时检查sql语句的书写，要把此条数据插入数据库*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastPasswordResetDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
}
