package com.example.certi_gen.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.certi_gen.Classes.Certificate;
import com.example.certi_gen.Classes.Folder;
import com.example.certi_gen.Classes.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperFolder extends SQLiteOpenHelper {
    private static final String dbName="FolderandCertificate";
    public static final String FoldersTable="Folders";
    public static final String colFolderName = "FolderName";
    public static final String colFolderSize = "FolderSize";
    public static final String colFolderCount = "FolderCount";
    public static final String colFolderDateCreated = "FolderDateCreated";
    public static final String colFolderDescription = "FolderDescription";
      public static final String colFolderTemplatePath="FolderTemplatePath";
    public static final String colFolderExcelPath = "FolderExcelPath";
    public static final String colFolderSignaturePath = "FolderSignaturePath";
    public static final String colFolderDateOnCerti = "FolderDateOnCerti";



    public static final String CertificatesTable = "Certificate";
    public static final String colCertiName = "CertiName";
    public static final String colCertiImageRef = "CertiImageRef";
    public static final String colCertiSize = "CertiSize";
    public static final String colCertiEmailId = "CertiEmailId";
    public static final String colCertiPosition = "CertiPosition";
    public static final String colCertiPhonenumber = "CertiPhoneNumber";
    public static final String colCertiRollNo = "CertiRollNo";
    public static final String colCertiFolderRelation = "CertiFolderRelation";//it is name of folder, name is unique




    public DatabaseHelperFolder(Context context) {
        super(context, dbName, null, 33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists " + FoldersTable+ "( " + colFolderName + " String, " + colFolderSize + " String, "
                + colFolderCount + " String, " + colFolderDateCreated + " String, "  +colFolderDescription + " String, " +
                colFolderTemplatePath + " String, "  + colFolderExcelPath + " String, " +
                colFolderSignaturePath + " String, " + colFolderDateOnCerti+ " String " +  ");");
        db.execSQL("Create table if not exists " + CertificatesTable+ "( " + colCertiName + " String, " + colCertiSize + " String, "
                + colCertiImageRef + " String, " + colCertiEmailId + " String, " + colCertiFolderRelation + " String, "
                + colCertiRollNo + " String, " + colCertiPhonenumber + " String, " + colCertiPosition+ " String " +  ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

    public long insertFolder(Folder folder){
        SQLiteDatabase DB = this.getWritableDatabase();
        String value = null;

        ContentValues values = new ContentValues();
        values.put(colFolderName, folder.getName());
        values.put(colFolderSize, folder.getSize());
        values.put(colFolderCount, folder.getNoOfItem());
        values.put(colFolderDescription,folder.getDescription());
        values.put(colFolderDateCreated,folder.getDateCreated());
        values.put(colFolderTemplatePath,folder.getTempPath());
        values.put(colFolderSignaturePath,folder.getSignPath());
        values.put(colFolderExcelPath,folder.getExcelPath());
        values.put(colFolderDateOnCerti,folder.getCertiDate());

        long ID = DB.insert(FoldersTable, null, values);

        DB.close();

        return ID;
    }

    public List<Folder> getAllFolder() {

        List<Folder> folder = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + FoldersTable ;

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do
            {
               Folder folder1 = new Folder();
                folder1.setName(cursor.getString(cursor.getColumnIndex(colFolderName)));
                folder1.setNoOfItem(cursor.getString(cursor.getColumnIndex(colFolderCount)));
                folder1.setCertiDate(cursor.getString(cursor.getColumnIndex(colFolderDateOnCerti)));
                folder1.setTempPath(cursor.getString(cursor.getColumnIndex(colFolderTemplatePath)));
                folder1.setExcelPath(cursor.getString(cursor.getColumnIndex(colFolderExcelPath)));
                folder1.setDescription(cursor.getString(cursor.getColumnIndex(colFolderDescription)));
                folder1.setDateCreated(cursor.getString(cursor.getColumnIndex(colFolderDateCreated)));
                folder1.setSignPath(cursor.getString(cursor.getColumnIndex(colFolderSignaturePath)));
                folder1.setSize(cursor.getString(cursor.getColumnIndex(colFolderSize)));

                folder.add(folder1);
            }
            while (cursor.moveToNext());
        }

        DB.close();
        cursor.close();
        return folder;
    }


    public long insertCerti(Certificate certificate){
        SQLiteDatabase DB = this.getWritableDatabase();
        String value = null;

        ContentValues values = new ContentValues();
        values.put(colCertiEmailId, certificate.getEmailId());
        values.put(colCertiSize, certificate.getSize());
        values.put(colCertiFolderRelation,certificate.getFolderRelation());
        values.put(colCertiPhonenumber, certificate.getPhoneNumber());
        values.put(colCertiPosition, certificate.getPosition());
        values.put(colCertiName, certificate.getName());
        values.put(colCertiRollNo, certificate.getRollNo());
        values.put(colCertiImageRef,certificate.getImageRef());

        long ID = DB.insert(CertificatesTable, null, values);

        DB.close();

        return ID;
    }

    public List<Certificate> getAllCertitificate(String folderRelation) {

        List<Certificate> certificates = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + CertificatesTable + " WHERE " + colCertiFolderRelation +" =?" + folderRelation ;

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do
            {
                Certificate certificate1 = new Certificate();
                certificate1.setName(cursor.getString(cursor.getColumnIndex(colCertiName)));
                certificate1.setEmailId(cursor.getString(cursor.getColumnIndex(colCertiEmailId)));
                certificate1.setFolderRelation(folderRelation);
                certificate1.setImageRef(cursor.getString(cursor.getColumnIndex(colCertiImageRef)));
                certificate1.setPhoneNumber(cursor.getString(cursor.getColumnIndex(colCertiPhonenumber)));
                certificate1.setRollNo(cursor.getString(cursor.getColumnIndex(colCertiRollNo)));
                certificate1.setSize(cursor.getString(cursor.getColumnIndex(colCertiSize)));
                certificate1.setPosition(cursor.getString(cursor.getColumnIndex(colCertiPosition)));
                certificates.add(certificate1);
            }
            while (cursor.moveToNext());
        }

        DB.close();
        cursor.close();
        return certificates;
    }

}
