package com.example.jage.sqliteapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jage.sqliteapp.db.CompanyOperations;
import com.example.jage.sqliteapp.model.Company;

import java.util.Date;

public class AddUpdateCompany  extends AppCompatActivity {

    private static final String EXTRA_EMP_ID = "com.androidtutorialpoint.empId";
    private static final String EXTRA_ADD_UPDATE = "com.androidtutorialpoint.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private RadioGroup radioGroup;
    private RadioButton consultRadioButton,softRadioButton,fabRadioButton;
    private EditText firstNameEditText;
    private EditText emailEditText;
    private EditText websiteEditText;
    private EditText phoneEditText;
    private Button addUpdateButton;
    private Company newCompany;
    private Company oldCompany;
    private String mode;
    private long empId;
    private CompanyOperations employeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_employee);
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


        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if(mode.equals("Update")){

            addUpdateButton.setText("Update Company");
            empId = getIntent().getLongExtra(EXTRA_EMP_ID,0);

            initializeCompany(empId);

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radio_1) {
                    newCompany.setType(Company.CompanyType.CONSULTORIA.toString());
                    if(mode.equals("Update")){
                        oldCompany.setType(Company.CompanyType.CONSULTORIA.toString());
                    }
                } else if (checkedId == R.id.radio_2) {
                    newCompany.setType(Company.CompanyType.DESAROLLO_A_LA_MEDIDA.toString());
                    if(mode.equals("Update")){
                        oldCompany.setType(Company.CompanyType.DESAROLLO_A_LA_MEDIDA.toString());
                    }
                } else if (checkedId == R.id.radio_3) {
                    newCompany.setType(Company.CompanyType.FABRICA_DE_SOFTWARE.toString());
                    if(mode.equals("Update")){
                        oldCompany.setType(Company.CompanyType.FABRICA_DE_SOFTWARE.toString());
                    }
                }
            }

        });

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equals("Add")) {
                    newCompany.setCompanyName(firstNameEditText.getText().toString());
                    newCompany.setEmail(emailEditText.getText().toString());
                    newCompany.setPhone(phoneEditText.getText().toString());
                    newCompany.setWebPage(websiteEditText.getText().toString());
                    employeeData.addCompany(newCompany);
                    Toast t = Toast.makeText(AddUpdateCompany.this, "Company "+ newCompany.getCompanyName() + "has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateCompany.this,MainActivity.class);
                    startActivity(i);
                }else {
                    oldCompany.setCompanyName(firstNameEditText.getText().toString());
                    oldCompany.setEmail(emailEditText.getText().toString());
                    oldCompany.setPhone(phoneEditText.getText().toString());
                    oldCompany.setWebPage(websiteEditText.getText().toString());
                    employeeData.updateCompany(oldCompany);
                    Toast t = Toast.makeText(AddUpdateCompany.this, "Company "+ oldCompany.getCompanyName() + " has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateCompany.this,viewCompany.class);
                    startActivity(i);

                }


            }
        });


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
