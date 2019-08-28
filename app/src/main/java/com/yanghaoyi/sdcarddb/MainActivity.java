package com.yanghaoyi.sdcarddb;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yanghaoyi.sdcarddb.model.Student;
import com.yanghaoyi.sdcarddb.model.db.DaoMaster;
import com.yanghaoyi.sdcarddb.model.db.DaoSession;
import com.yanghaoyi.sdcarddb.model.db.GreenDaoContext;

import java.util.List;

import javax.xml.transform.Templates;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DaoSession daoSession;
    private TextView tvAdd;
    private TextView tvQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGreenDao();
        initView();
        initEvent();
    }

    private void initView(){
        tvAdd = findViewById(R.id.tvAdd);
        tvQuery = findViewById(R.id.tvQuery);
    }

    private void initEvent(){
        tvAdd.setOnClickListener(this);
        tvQuery.setOnClickListener(this);
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "base.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public List queryAll(){
        List<Student> students = daoSession.loadAll(Student.class);
        Log.w("Student", "Students size is: "+students.size()+"Students is: "+students.toString());
        return students;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAdd:
                Student student = new Student();
                student.setStudentNo(1);
                int age = 10;
                student.setAge(age);
                student.setTelPhone("11111");
                student.setName("test");
                student.setSex("男");
                student.setAddress("address");
                student.setGrade(String.valueOf(age % 10) + "年纪");
                student.setSchoolName("school");
                daoSession.insert(student);
                break;
            case R.id.tvQuery:
                queryAll();
                break;
            default:
                break;
        }
    }
}
