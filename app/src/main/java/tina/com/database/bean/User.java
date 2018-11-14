package tina.com.database.bean;

import tina.com.database.annotation.DbField;
import tina.com.database.annotation.DbTable;

/**
 * @author yxc
 * @date 2018/11/13
 */
@DbTable("tb_user")
public class User {

    @DbField("_id")
    private String id;
    private String name;

    private String password;
    private Integer status;

    public User(){}

    public User(String id, String name, String password, Integer status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}