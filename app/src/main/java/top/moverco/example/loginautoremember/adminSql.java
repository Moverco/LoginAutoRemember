package top.moverco.example.loginautoremember;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by UPC on 2016/8/23.
 */
public class adminSql extends SQLiteOpenHelper {
    private static final String CREATE_TABLE = "create table userInfo ( id integer primary key autoincrement, adminName text not null, password text not null, userName text )";
    private Context mContext;
    adminSql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
