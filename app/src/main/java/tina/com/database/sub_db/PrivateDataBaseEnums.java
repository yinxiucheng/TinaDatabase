package tina.com.database.sub_db;

import java.io.File;

import tina.com.database.bean.User;
import tina.com.database.db.BaseDaoFactory;

/**
 * @author yxc
 * @date 2018/11/14
 */
enum PrivateDataBaseEnums {
    database("");
    private String value;
    PrivateDataBaseEnums(String value){

    }
    //用于产生路径
    public String getValue(){
        UserDao userDao=BaseDaoFactory.getInstance().getBaseDao(UserDao.class,User.class);
        if(userDao!=null){
            User currentUser=userDao.getCurrentUser();
            if(currentUser!=null){
                File file=new File("/data/data/tina.com.database");
                if(!file.exists()){
                    file.mkdirs();
                }
                ///data/data/com.example.a48608.ls4_databaseframework_20180307/n0003_login.db
                return file.getAbsolutePath()+"/"+currentUser.getId()+"_login.db";
            }
        }
        return null;
    };
}
