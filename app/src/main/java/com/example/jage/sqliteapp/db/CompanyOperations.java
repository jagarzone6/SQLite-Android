package com.example.jage.sqliteapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jage.sqliteapp.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyOperations {

    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            CompanyDBHandler.COLUMN_ID,
            CompanyDBHandler.COLUMN_NAME,
            CompanyDBHandler.COLUMN_WEB_PAGE,
            CompanyDBHandler.COLUMN_PHONE,
            CompanyDBHandler.COLUMN_EMAIL,
            CompanyDBHandler.COLUMN_TYPE

    };

    public CompanyOperations(Context context){
        dbhandler = new CompanyDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Company addCompany(Company company){
        ContentValues values  = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_NAME,company.getCompanyName());
        values.put(CompanyDBHandler.COLUMN_WEB_PAGE,company.getWebPage());
        values.put(CompanyDBHandler.COLUMN_PHONE, company.getPhone());
        values.put(CompanyDBHandler.COLUMN_EMAIL, company.getEmail());
        values.put(CompanyDBHandler.COLUMN_TYPE, company.getType());
        long insertid = database.insert(CompanyDBHandler.TABLE_COMPANY,null,values);
        company.setCompanyID(insertid);
        return company;

    }

    // Getting single Employee
    public Company getCompany(long id) {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANY,allColumns,CompanyDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Company e = new Company(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return Employee
        return e;
    }

    public List<Company> getAllCompanies() {

        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANY,allColumns,null,null,null, null, null);

        List<Company> companies = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Company company = new Company();
                company.setCompanyID(cursor.getLong(cursor.getColumnIndex(CompanyDBHandler.COLUMN_ID)));
                company.setCompanyName(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_NAME)));
                company.setEmail(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_EMAIL)));
                company.setPhone(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_PHONE)));
                company.setType(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_TYPE)));
                company.setWebPage(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_WEB_PAGE)));
                companies.add(company);
            }
        }
        // return All Employees
        return companies;
    }




    // Updating Employee
    public int updateCompany(Company company) {

        ContentValues values = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_NAME, company.getCompanyName());
        values.put(CompanyDBHandler.COLUMN_EMAIL, company.getEmail());
        values.put(CompanyDBHandler.COLUMN_PHONE, company.getPhone());
        values.put(CompanyDBHandler.COLUMN_WEB_PAGE, company.getWebPage());
        values.put(CompanyDBHandler.COLUMN_TYPE, company.getType().toString());

        // updating row
        return database.update(CompanyDBHandler.TABLE_COMPANY, values,
                CompanyDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(company.getCompanyID())});
    }

    // Deleting Employee
    public void removeCompany(Company company) {

        database.delete(CompanyDBHandler.TABLE_COMPANY, CompanyDBHandler.COLUMN_ID + "=" + company.getCompanyID(), null);
    }

}
