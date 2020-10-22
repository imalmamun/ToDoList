package com.almamun.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.almamun.todolist.Modelclass.TaskModelClass;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WORKDATABASE";
    public static final String TABLE_NAME = "WORKTODO";
    public static final String ID = "ID";
    public static final String TASK_TEXT = "TASKTEXT";
    public static final String STATUS = "STATUS";

    public DatabaseHelperClass (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STATUS + " BOOL, " + TASK_TEXT + " TEXT)";


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
//        onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
    }

//    add to database function
    public boolean addTask(TaskModelClass taskModelClass){
        SQLiteDatabase sqLiteDatabase;

        sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(STATUS, taskModelClass.getStatus());
        contentValues.put(TASK_TEXT, taskModelClass.getTasktext());

        long insert = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(insert == -1){

            return false;
        }else{
            return true;
        }

    }

    public void deleteTask(int id){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        db.execSQL(sql);
        db.close();
    }
    public void updateValue(TaskModelClass taskModelClass){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID,taskModelClass.getId());
        cv.put(STATUS,taskModelClass.getStatus());
        cv.put(TASK_TEXT,taskModelClass.getTasktext());
        db.update(TABLE_NAME,cv, "ID = ?",new String[] {String.valueOf(taskModelClass.getId())});


//        String sql = "UPDATE " + TABLE_NAME + " SET " + TASK_TEXT + " = "+  + " WHERE " + ID + " = " + id;
//        db.execSQL(sql);
        db.close();

    }


    //      retrieve from database function
    public List<TaskModelClass> getTaskList(){
        SQLiteDatabase sqLiteDatabase;

        String sql ="SELECT * FROM " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<TaskModelClass> alltask = new ArrayList<>();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                boolean status = cursor.getInt(1) == 1 ? true: false;
                String tasktext = cursor.getString(2);
                alltask.add(new TaskModelClass(id,status,tasktext));



            }while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return alltask;
    }

    public void updateCheck(TaskModelClass taskModelClass){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        if(taskModelClass.getStatus() == false){
            String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS + " = 1 WHERE " + ID + " = " +taskModelClass.getId();
            db.execSQL(sql);
        }else{
            String sql = "UPDATE " + TABLE_NAME + " SET " + STATUS + " = 0 WHERE " + ID + " = " +taskModelClass.getId();
            db.execSQL(sql);

        }

        db.close();
    }


}
