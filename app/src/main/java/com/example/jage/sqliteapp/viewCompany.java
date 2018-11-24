package com.example.jage.sqliteapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Button addUpdateButton;
    private Company newCompany;
    private Company oldCompany;
    private CompanyOperations employeeData;
    private long empId;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);
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
        addUpdateButton = (Button)findViewById(R.id.button_add_update_employee);
        employeeData = new CompanyOperations(this);
        employeeData.open();
        addUpdateButton.setText("Add service or product");
        empId = mPrefs.getLong("company_id", 0);

        initializeCompany(empId);
    }

    private void initializeCompany(long empId) {
        oldCompany = employeeData.getCompany(empId);
        firstNameEditText.setText(oldCompany.getCompanyName());
        emailEditText.setText(oldCompany.getEmail());
        phoneEditText.setText(oldCompany.getPhone());
        if(oldCompany.getType().equals(Company.CompanyType.CONSULTORIA.toString())){
            radioGroup.check(R.id.radio_1);
        } else if(oldCompany.getType().equals(Company.CompanyType.DESAROLLO_A_LA_MEDIDA.toString())){
            radioGroup.check(R.id.radio_2);
        } else if (oldCompany.getType().equals(Company.CompanyType.FABRICA_DE_SOFTWARE.toString())){
            radioGroup.check(R.id.radio_3);
        }


        websiteEditText.setText(oldCompany.getWebPage());
    }
}
