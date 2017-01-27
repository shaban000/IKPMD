package shaban.jama.eindopdracht.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by sangam on 09/01/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;
    public static final String dbName = "ikpmdV2.db";
    public static final int dbVersion = 8;

    public DatabaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    public static synchronized DatabaseHelper getHelper (Context ctx){
        if (mInstance == null){
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseInfo.databaseTabels.leerdoel + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.Columns.LEERDOEL_NAME+ " TEXT NOT NULL UNIQUE);"

        );
        db.execSQL("CREATE TABLE " + DatabaseInfo.databaseTabels.subdoel + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.Columns.SUBDOEL_NAME+ " TEXT," +
                DatabaseInfo.Columns.WEEK+ " TEXT," +
                DatabaseInfo.Columns.VOLDAAN + " BOOLEAN," +
                DatabaseInfo.Columns.FK_ID_LEERDOEL + " INTEGER," +
                " FOREIGN KEY (id_leerdoel) REFERENCES "+DatabaseInfo.databaseTabels.leerdoel+" (_id));"
        );

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.databaseTabels.leerdoel);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.databaseTabels.subdoel);
        onCreate(db);

    }
    public void insert(String table, String nullColumnHack, ContentValues values){
        mSQLDB.insert(table, nullColumnHack, values);
        Log.d("entry","entry is toegevoegd");
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }
    public Integer countTabel(String tabel){
        String count = "SELECT count(*) FROM "+ tabel;
        Cursor mcursor = mSQLDB.rawQuery(count, null);
        mcursor.moveToFirst();
        return mcursor.getInt(0);
    }
    public void update(String table, ContentValues values, String where, String[]whereArgs){
        mSQLDB.update(table,values,where,whereArgs);
    }
}