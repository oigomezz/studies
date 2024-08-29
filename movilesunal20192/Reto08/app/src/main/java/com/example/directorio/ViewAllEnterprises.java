package com.example.directorio;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

public class ViewAllEnterprises extends ListActivity {
    private EnterpriseOperations enterpriseOps;
    private List<Enterprise> enterprises;
    private ArrayAdapter<Enterprise> adapter;
    private EditText filter;
    private CheckBox filterConsuloria, filterDesarrollo, filterFabricacion;
    private String stringFilter;

    private static final String TAG = "ViewAllEnterprises";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_enterprises);

        filter = (EditText) findViewById(R.id.edit_text_filter_name);
        filterConsuloria = (CheckBox) findViewById(R.id.check_box_consultoria);
        filterDesarrollo = (CheckBox) findViewById(R.id.check_box_desarrollo);
        filterFabricacion = (CheckBox) findViewById(R.id.check_box_fabricacion);
        stringFilter ="";
        enterpriseOps = new EnterpriseOperations(this);
        enterpriseOps.open();
        enterprises = enterpriseOps.getAllEnterprises();
        enterpriseOps.close();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, enterprises);
        setListAdapter(adapter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ViewAllEnterprises.this).adapter.getFilter().filter(s);
                stringFilter =  s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.check_box_consultoria:
                if (checked) {
                    (ViewAllEnterprises.this).adapter.getFilter().filter("consultoria");
                    Log.d(TAG, "onCheckboxClicked: consultoria is checked");
                }
                else {
                    (ViewAllEnterprises.this).adapter.getFilter().filter("");
                    Log.d(TAG, "onCheckboxClicked: consultoria is unchecked");
                }
                // Remove the meat
                break;
            case R.id.check_box_desarrollo:
                if (checked)
                    (ViewAllEnterprises.this).adapter.getFilter().filter("desarrollo");
                else
                    (ViewAllEnterprises.this).adapter.getFilter().filter("");
                break;
            case R.id.check_box_fabricacion:
                if (checked)
                    (ViewAllEnterprises.this).adapter.getFilter().filter("fabricacion");
                else
                    (ViewAllEnterprises.this).adapter.getFilter().filter("");
                break;
        }
    }
}
