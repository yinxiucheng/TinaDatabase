package tina.com.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import tina.com.database.bean.Person;
import tina.com.database.bean.Photo;
import tina.com.database.bean.User;
import tina.com.database.db.BaseDao;
import tina.com.database.db.BaseDaoFactory;
import tina.com.database.db.BaseDaoNewImpl;
import tina.com.database.sub_db.BaseDaoSubFactory;
import tina.com.database.sub_db.PhotoDao;
import tina.com.database.sub_db.UserDao;

public class MainActivity extends AppCompatActivity {

    private int i;
    BaseDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
    }

    public void insertObject(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(BaseDaoNewImpl.class, User.class);
        baseDao.insert(new User("NO" + 19, "Tina1", "123456", 0));
        baseDao.insert(new User("NO" +1, "Tina", "123456", 0));
        baseDao.insert(new User("NO" +2, "Tina", "123456", 0));

        BaseDao personDao = BaseDaoFactory.getInstance().getBaseDao(BaseDaoNewImpl.class,Person.class);
        personDao.insert(new Person("Tina", 18));

        Toast.makeText(this, "执行成功", Toast.LENGTH_SHORT).show();
    }

    public void updateObject(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(BaseDaoNewImpl.class, User.class);
        User user = new User();
        user.setName("Tina");

        User where = new User();
        where.setPassword("111111");
        baseDao.update(user, where);

        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }


    public void deleteObject(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(BaseDaoNewImpl.class, User.class);
        User where = new User();
        where.setId("NO" + 19);
        baseDao.delete(where);

        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }


    public void queryObject(View view) {
        BaseDao baseDao= BaseDaoFactory.getInstance().getBaseDao(BaseDaoNewImpl.class, User.class);
        User where=new User();
        where.setName("Tina1");
        List<User> list=baseDao.query(where);
        Log.i("Tina1","listsize="+list.size());
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i)+" i="+i);
        }
    }


    public void login(View view) {
        //服务器传回的信息
        User user=new User();
        user.setName("张三"+(++i));
        user.setPassword("123456");
        user.setId("N00"+i);
        //数据插入
        userDao.insert(user);
        Toast.makeText(this,"执行成功!",Toast.LENGTH_SHORT).show();
    }

    public void subInsert(View view) {
        Photo photo=new Photo();
        photo.setPath("data/data/my.jpg");
        photo.setTime(new Date().toString());

        PhotoDao photoDao= BaseDaoSubFactory.getInstance().getSubDao(PhotoDao.class,Photo.class);
        photoDao.insert(photo);
        Toast.makeText(this,"执行成功!",Toast.LENGTH_SHORT).show();
    }


}
