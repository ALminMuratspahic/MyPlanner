package com.almin.myplanner.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.almin.myplanner.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "TASK";

    public static final String COL_ID = "ID";
    public static final String COL_TASK_NAME = "TASK_NAME";
    public static final String COL_TASK_DATE = "TASK_DATE";
    public static final String COL_ACTIV_TASK = "ACTIV_TASK";
    List<Task> listOfTask;
    private final Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "myplaner.db", null, 1); //from doc.i saw factory can be null
        this.context = context;

    }

    @Override   //call on first time i want do access db OBJ
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //so create table here
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK_NAME + " TEXT NOT NULL, " + COL_TASK_DATE + " TEXT, " + COL_ACTIV_TASK + " BOOL)";

        sqLiteDatabase.execSQL(createTable);

    }

    @Override   //if databese version change
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //CRUD
    //POST Task to Database
    public boolean addTask(Task task) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //something like HasMap

        contentValues.put(COL_TASK_NAME, task.getTaskName());
        contentValues.put(COL_TASK_DATE, task.getDate());
        contentValues.put(COL_ACTIV_TASK, task.isActive());

        long insert = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return insert>-1?true:false;

    }

    //GET all data form Database
    public List<Task> findALl() {
        listOfTask = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null); //return type is cursor,but in documentation i haven't rawQuery() with only String

        if (cursor.moveToNext()) {
            do {
                int idTask = cursor.getInt(0); //i know that my first col. is id,type of int
                String taskName = cursor.getString(1);
                String taskDate = cursor.getString(2);
                int isActive = cursor.getInt(3); //i can like (3)==1?true:false;
                boolean isActiveTask;
                if (isActive == 1) { // convert result to boolean,because i need boolean for my OBJ
                    isActiveTask = true;
                } else {
                    isActiveTask = false;
                }
                Task task = new Task(idTask, taskName, taskDate, isActiveTask);
                listOfTask.add(task);
            } while (cursor.moveToNext());

        } else {
            //i need....nothing ..
        }
        cursor.close();
        sqLiteDatabase.close();

        return listOfTask;
    }
    //PUT date
    public void updateData(int id, String taskName, String taskDate, boolean isActive) {

        SQLiteDatabase myDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TASK_NAME, taskName);
        contentValues.put(COL_TASK_DATE, taskDate);
        contentValues.put(COL_ACTIV_TASK, isActive);

        long resultUpdate = myDataBase.update(TABLE_NAME, contentValues, "ID=?", new String[]{Integer.toString(id)});

        if (resultUpdate == -1) {
            Toast.makeText(context, "Faile Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTask(int id) {

        SQLiteDatabase myDatabase = this.getWritableDatabase();
        long result = myDatabase.delete(TABLE_NAME, "ID=?", new String[]{Integer.toString(id)});

        if (result == -1) {
            Toast.makeText(context, "Fail to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
        }

    }


}
