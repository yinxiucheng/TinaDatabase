package tina.com.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import tina.com.database.bean.Person;
import tina.com.database.bean.User;
import tina.com.db.BaseDao;
import tina.com.db.BaseDaoFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insertObject(View view) {

        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        baseDao.insert(new User(19, "Tina", "123456"));
        baseDao.insert(new User(1, "Tina", "123456"));
        baseDao.insert(new User(2, "Tina", "123456"));

        BaseDao personDao = BaseDaoFactory.getInstance().getBaseDao(Person.class);
        personDao.insert(new Person("Tina", 18));

        Toast.makeText(this, "执行成功", Toast.LENGTH_SHORT).show();
    }

    public void updateObject(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        User user = new User();
        user.setName("Tina");

        User where = new User();
        where.setPassword("111111");
        baseDao.update(user, where);

        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }


    public void deleteObject(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        User where = new User();
        where.setId(19);
        baseDao.delete(where);

        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }



    public void queryObject(View view) {

    }


}
