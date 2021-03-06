package com.example.jage.sqliteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jage.sqliteapp.db.CompanyOperations;
import com.example.jage.sqliteapp.model.Company;

public class viewCompany extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton consultRadioButton,softRadioButton,fabRadioButton;
    private EditText firstNameEditText;
    private EditText emailEditText;
    private EditText websiteEditText;
    private EditText phoneEditText;
    private EditText productsEditText;
    private Button editCompanyButton;
    private Button goBackButton;

    private Company newCompany;
    private Company oldCompany;
    private CompanyOperations employeeData;
    private long empId;
    private SharedPreferences mPrefs;

    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);
        refreshView();

    }

    @Override
    public void onRestart() {
        super.onRestart();
        refreshView();
    }

    public void refreshView(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        newCompany = new Company();
        oldCompany = new Company();
        firstNameEditText = (EditText)findViewById(R.id.edit_text_name);
        emailEditText = (EditText)findViewById(R.id.edit_text_email);
        phoneEditText = (EditText) findViewById(R.id.edit_text_phone);
        radioGroup = (RadioGroup) findViewById(R.id.radio_type);
        consultRadioButton = (RadioButton) findViewById(R.id.radio_1);
        softRadioButton = (RadioButton) findViewById(R.id.radio_2);
        fabRadioButton = (RadioButton) findViewById(R.id.radio_3);
        websiteEditText = (EditText)findViewById(R.id.edit_text_website);
        productsEditText = (EditText)findViewById(R.id.edit_text_products);
        editCompanyButton = (Button)findViewById(R.id.button_add_update_employee);
        goBackButton = (Button)findViewById(R.id.button_back_to_list);
        employeeData = new CompanyOperations(this);
        employeeData.open();
        editCompanyButton.setText("Edit Company");
        empId = mPrefs.getLong("company_id", 0);
        initializeCompany(empId);

        editCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmpIdAndUpdateEmp();
            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(viewCompany.this,ViewAllCompanies.class);
                startActivity(i);
            }
        });
    }

    private void initializeCompany(long empId) {
        oldCompany = employeeData.getCompany(empId);
        firstNameEditText.setText(oldCompany.getCompanyName());
        firstNameEditText.setEnabled(false);
        emailEditText.setText(oldCompany.getEmail());
        emailEditText.setEnabled(false);
        phoneEditText.setText(oldCompany.getPhone());
        phoneEditText.setEnabled(false);
        productsEditText.setText(oldCompany.getProductsAndServices());
        productsEditText.setEnabled(false);
        if(oldCompany.getType() != null){
        if(oldCompany.getType().equals(Company.CompanyType.CONSULTORIA.toString())){
            radioGroup.check(R.id.radio_1);
        } else if(oldCompany.getType().equals(Company.CompanyType.DESAROLLO_A_LA_MEDIDA.toString())){
            radioGroup.check(R.id.radio_2);
        } else if (oldCompany.getType().equals(Company.CompanyType.FABRICA_DE_SOFTWARE.toString())){
            radioGroup.check(R.id.radio_3);
        }
        }
        radioGroup.getChildAt(0).setEnabled(false);
        radioGroup.getChildAt(1).setEnabled(false);
        radioGroup.getChildAt(2).setEnabled(false);

        websiteEditText.setText(oldCompany.getWebPage());
        websiteEditText.setEnabled(false);
    }

    public void getEmpIdAndUpdateEmp(){

        Intent i = new Intent(viewCompany.this,AddUpdateCompany.class);
        i.putExtra(EXTRA_ADD_UPDATE, "Update");
        i.putExtra(EXTRA_EMP_ID,empId);
        startActivity(i);

    }
}
