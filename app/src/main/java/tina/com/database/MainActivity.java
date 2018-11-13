package tina.com.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        Toast.makeText(this, "执行成功", Toast.LENGTH_SHORT).show();
    }


}
