package top.moverco.example.loginautoremember;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button login,logup,cancel;
    private CheckBox checkBox;
    private EditText adminEdit,passwordEdit;
    SharedPreferences sharedPreferences;
    private Context mContext = this;
//    private String adminInfo,passwordInfo;
    private adminSql adminsql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.login);
        logup = (Button)findViewById(R.id.logUp);
        cancel = (Button)findViewById(R.id.cancel_action);
        adminEdit =(EditText)findViewById(R.id.adminEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);
        checkBox = (CheckBox)findViewById(R.id.checkbox);


        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        //初始化是否记住密码状态
        editor.putBoolean("isRemembered",false);
        editor.commit();
        if(sharedPreferences.getBoolean("isRemembered",true)&&sharedPreferences.getString("adminName",null)!=null){
            adminEdit.setText(sharedPreferences.getString("adminName",null));
        }
        adminsql = new adminSql(mContext,"user.db",null,1);




    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        super.onDestroy();
        if(checkBox.isChecked()==sharedPreferences.getBoolean("isRemembered",false)){

        }else {
           if (checkBox.isChecked()){
               editor.putBoolean("isRemembered",true);

           }else{
               editor.putBoolean("isRemembered",false);
           }
            editor.commit();
        }
    }

    public void doClick(View view){
        switch (view.getId()){
            case R.id.login:
                if(adminEdit.getText().toString()==null||passwordEdit.getText().toString()==null)
                {
                    Toast.makeText(mContext,"密码或账户名不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    SQLiteDatabase db = adminsql.getWritableDatabase();
                    Cursor cursor = db.query("userInfo", null, null, null, null, null, null);
//                db.execSQL("select password from userInfo, where adminName = "+ adminEdit.getText().toString());
                    if (cursor.moveToFirst()) {
                        do {
                            String adminFromSys = cursor.getString(cursor.getColumnIndex("adminName"));
                            if (adminFromSys == adminEdit.getText().toString()) {
                                if (cursor.getString(cursor.getColumnIndex("password")) == passwordEdit.getText().toString()) {
                                    Intent intent1 = new Intent(mContext, SActivity.class);
                                    startActivity(intent1);
                                    if (checkBox.isChecked()) {
                                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                        editor.putString("adminName", adminEdit.getText().toString());
                                        editor.commit();
                                    }
                                }
                            }
                        } while (cursor.moveToNext());
                    }
                }

                break;
            case R.id.cancel_action:
                finish();
                break;
            case R.id.logUp:
                Intent intent2 = new Intent(MainActivity.this,LogUp.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

}
