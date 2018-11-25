package com.example.jage.sqliteapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jage.sqliteapp.db.CompanyOperations;
import com.example.jage.sqliteapp.model.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewAllCompanies extends AppCompatActivity {

    private CompanyOperations companyOps;
    List<Company> companies;
    private StableArrayAdapter adapter;
    private ArrayList<String> list;
    private SharedPreferences mPrefs;
    private Button backButton;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_companies);
        listview = (ListView) findViewById(R.id.list);
        refreshList();
    }
    @Override
    public void onRestart() {
        super.onRestart();
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;
        }
        return false;
    }

    public void refreshList(){
        list = new ArrayList<String>();

        backButton = (Button)findViewById(R.id.button_back_main);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewAllCompanies.this,MainActivity.class);
                startActivity(i);
            }
        });
        companyOps = new CompanyOperations(this);
        companyOps.open();
        companies = companyOps.getAllCompanies();
        companyOps.close();
        for (Company comp : companies){
            list.add(comp.getCompanyID()+". "+comp.getCompanyName());
        }
        adapter = new StableArrayAdapter(this,
                R.xml.company_item, R.id.firstLine, list);
        listview.setAdapter(adapter);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                if(item != null) {
                    String[] parts = item.split("\\."); // String array, each element is text between dots
                    String beforeFirstDot = parts[0];    // Text before the first dot

                    SharedPreferences.Editor ed = mPrefs.edit();
                    ed.putLong("company_id", Long.valueOf(beforeFirstDot));
                    ed.commit();
                    startActivityForResult(new Intent(ViewAllCompanies.this, viewCompany.class), 0);
                    list.clear();
                }
            }

        });
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  int textViewResourceId2,
                                  List<String> objects) {
            super(context, textViewResourceId, textViewResourceId2, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
