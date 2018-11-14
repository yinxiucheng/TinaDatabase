package tina.com.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import tina.com.database.bean.Person;
import tina.com.database.bean.User;
import tina.com.db.BaseDao;
import tina.com.db.BaseDaoFactory;
import tina.com.db.BaseDaoNewImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insertObject(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(BaseDaoNewImpl.class, User.class);
        baseDao.insert(new User(19, "Tina1", "123456"));
        baseDao.insert(new User(1, "Tina", "123456"));
        baseDao.insert(new User(2, "Tina", "123456"));

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
        where.setId(19);
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


}
