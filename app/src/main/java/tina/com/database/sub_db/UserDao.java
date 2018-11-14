package tina.com.database.sub_db;

import android.util.Log;

import java.util.List;

import tina.com.database.bean.User;
import tina.com.database.db.BaseDao;

/**
 * @author yxc
 * @date 2018/11/14
 */
//用于维护共有数据
public class UserDao extends BaseDao<User> {

    @Override
    public long insert(User entity) {
        List<User> list = query(new User());
        User where = null;
        for (User user:list){
            where = new User();
            where.setId(user.getId());
            user.setStatus(0);
            Log.i("tina","用户"+user.getName()+"更改为未登录状态");
            update(user, where);
        }
        Log.i("tina","用户"+entity.getName()+"登录");
        entity.setStatus(1);
        return super.insert(entity);
    }

    /**
     * 得到当前登录的User
     */
    public User getCurrentUser(){
        User user=new User();
        user.setStatus(1);
        List<User> list=query(user);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

}
