package top.moverco.example.loginautoremember;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by UPC on 2016/8/23.
 */
public class LogUp extends Activity {

    private EditText adminInfo,passwordInfo,confirmPasswordInfo,userNameInfo;
    private String adminStr,passwordStr,confirmStr,userNameStr;
    private Button logup,refresh;
    private adminSql adminsql;
    private Context mContext = this;
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.loguplayout);
        adminsql = new adminSql(this,"user.db",null,1);

        //初始化控件
        adminInfo = (EditText)findViewById(R.id.adminEdit);
        passwordInfo = (EditText)findViewById(R.id.passwordEdit);
        confirmPasswordInfo = (EditText)findViewById(R.id.competeEdit);
        userNameInfo = (EditText)findViewById(R.id.userNameEdit);
        logup = (Button)findViewById(R.id.confirm);
        refresh = (Button)findViewById(R.id.refresh);


    }
    public void logup(View view){
        switch (view.getId()){
            case R.id.confirm:
                if(adminInfo!=null&&passwordInfo.getText()==confirmPasswordInfo.getText()&&userNameInfo!=null) {
                    SQLiteDatabase db = adminsql.getWritableDatabase();
                    Cursor cursor = db.query("userInfo", null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            String str = cursor.getString(cursor.getColumnIndex("adminName"));
                            if (str == adminInfo.getText().toString())
                                Toast.makeText(mContext, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                            else{
                                ContentValues values = new ContentValues();
                                values.put("adminName",adminInfo.getText().toString());
                                values.put("password",passwordInfo.getText().toString());
                                values.put("userName",userNameInfo.getText().toString());
                                db.insert("userInfo",null,values);
                                finish();
                            }
                            break;
                        } while (cursor.moveToNext());
                    }
                }else {
                    Toast.makeText(mContext,"输入信息有误，请重新输入",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.refresh:
                adminInfo.setText(null);
                passwordInfo.setText(null);
                confirmPasswordInfo.setText(null);
                userNameInfo.setText(null);
                break;
            default:
                break;
        }
    }
}
