package com.example.certi_gen.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.certi_gen.Classes.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperUser extends SQLiteOpenHelper {
    private static final String dbName="User";
    public static final String UsersTable="Users";
    public static final String colUserName = "UserName";
    public  static  final String colUserEmail = "EmailId";
    public static final String colUserPicRef = "UserPicRef";
    public DatabaseHelperUser(Context context) {
        super(context, dbName, null, 33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists " + UsersTable+ "( " + colUserName + " String, " + colUserPicRef + " String, "+ colUserEmail+ " String " +  ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }
    public long insertUSer(User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colUserName, user.getName());
        values.put(colUserEmail, user.getEmailid());
        values.put(colUserPicRef, user.getImageRef());

        long ID = DB.insert(UsersTable, null, values);

        DB.close();

        return ID;
    }


    public void updateUser(String emailid, String newName,String newEmail,String newRef) {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(colUserPicRef,newRef);
        cvUpdate.put(colUserEmail,newEmail);
        cvUpdate.put(colUserName,newName);
        DB.update(DatabaseHelperUser.UsersTable, cvUpdate,
                "EmailId" + " =?" , new String[]{emailid});
    }

    public List<User> getAllUser() {

        List<User> user = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + UsersTable ;

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do
            {
                User userr = new User();
               userr.setName(cursor.getString(cursor.getColumnIndex(colUserName)));

                userr.setEmailid(cursor.getString(cursor.getColumnIndex(colUserEmail)));

                userr.setImageRef(cursor.getString(cursor.getColumnIndex(colUserPicRef)));

                user.add(userr);
            }
            while (cursor.moveToNext());
        }

        DB.close();

        cursor.close();
        return user;
    }

}
