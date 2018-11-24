package com.example.jage.sqliteapp;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.jage.sqliteapp.db.CompanyOperations;
import com.example.jage.sqliteapp.model.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewAllCompanies extends ListActivity {

    private CompanyOperations companyOps;
    List<Company> companies;
    private StableArrayAdapter adapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_companies);
        list = new ArrayList<String>();
        companyOps = new CompanyOperations(this);
        companyOps.open();
        companies = companyOps.getAllCompanies();
        companyOps.close();
        for (Company comp : companies){
            list.add(comp.getCompanyName());
        }
        adapter = new StableArrayAdapter(this,
                R.xml.company_item, R.id.firstLine, list);
        setListAdapter(adapter);
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
