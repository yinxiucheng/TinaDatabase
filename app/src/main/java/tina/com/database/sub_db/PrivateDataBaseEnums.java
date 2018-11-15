package tina.com.database.sub_db;

import android.os.Environment;

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
                File file=new File(Environment.getExternalStorageDirectory(), "update/" + currentUser.getId());
                if(!file.exists()){
                    file.mkdirs();
                }
                return file.getAbsolutePath()+"/login.db";
            }
        }
        return null;
    };
}
