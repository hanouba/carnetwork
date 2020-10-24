package com.carnetwork.hansen.mvp.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 创建者 by ${HanSir} on 2018/11/21.
 * 版权所有  WELLTRANS.
 * 说明
 */
@Entity
public class LoginInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名
    @Unique
    private String username;
    //密码
    private String password;

    private String serverIp;
    private String serverPort;
    private String projectName;

    public LoginInfo(String username, String password ,String serverIp,String serverPort) {
        this.username = username;
        this.password = password;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public LoginInfo(String username, String password ,String serverIp,String serverPort, String projectName) {
        this.username = username;
        this.password = password;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.projectName = projectName;
    }

    @Generated(hash = 1298991920)
    public LoginInfo(Long id, String username, String password, String serverIp, String serverPort,
            String projectName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.projectName = projectName;
    }

    @Generated(hash = 1911824992)
    public LoginInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
