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
    private Integer id;
    private String name;

    private String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

}