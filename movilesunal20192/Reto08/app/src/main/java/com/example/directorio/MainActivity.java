package com.example.directorio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addEnterpriseButton;
    private Button editEnterpriseButton;
    private Button deleteEnterpriseButton;
    private Button viewAllEnterpriseButton;
    private EnterpriseOperations enterpriseOps;
    private static final String EXTRA_EMP_ID = "id";
    private static final String EXTRA_ADD_UPDATE = "add_update";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        enterpriseOps = new EnterpriseOperations(MainActivity.this);

        addEnterpriseButton = (Button) findViewById(R.id.buttonCreate);
        editEnterpriseButton = (Button) findViewById(R.id.buttonUpdate);
        deleteEnterpriseButton = (Button) findViewById(R.id.buttonDelete);
        viewAllEnterpriseButton = (Button)findViewById(R.id.buttonViewAll);

        addEnterpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddUpdateEnterprise.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });
        editEnterpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEntIdAndUpdateEnt();
            }
        });
        deleteEnterpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEntIdAndRemoveEnt();
            }
        });
        viewAllEnterpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllEnterprises.class);
                startActivity(i);
            }
        });

    }

    public void getEntIdAndUpdateEnt(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEntIdView = li.inflate(R.layout.dialog_get_ent_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEntIdView);

        final EditText userInput = (EditText) getEntIdView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        String idEnt = userInput.getText().toString();
                        if (idEnt.matches("[0-9]+") && idEnt.length() > 0) {
                            if (enterpriseOps.getEnterprise(Long.parseLong(idEnt)) == null) {
                                dialog.dismiss();
                                Toast t = Toast.makeText(MainActivity.this, "La empresa " + idEnt + " no existe, intenta otra", Toast.LENGTH_LONG);
                                t.show();
                            } else {
                                Intent i = new Intent(MainActivity.this, AddUpdateEnterprise.class);
                                i.putExtra(EXTRA_ADD_UPDATE, "Update");
                                i.putExtra(EXTRA_EMP_ID, Long.parseLong(userInput.getText().toString()));
                                startActivity(i);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Solo se admiten números",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create()
                .show();

    }


    public void getEntIdAndRemoveEnt(){

        LayoutInflater li = LayoutInflater.from(this);
        View getEmpIdView = li.inflate(R.layout.dialog_get_ent_id, null);
        final View confirmDeleteView = li.inflate(R.layout.dialog_confirm_delete, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set dialog_get_emp_id.xml to alertdialog builder
        alertDialogBuilder.setView(getEmpIdView);

        final EditText userInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogUserInput);
        final TextView text = (TextView) confirmDeleteView.findViewById(R.id.text_view_delete);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        final String idEnt = userInput.getText().toString();
                        if (idEnt.matches("[0-9]+") && idEnt.length() > 0) {
                            if (enterpriseOps.getEnterprise(Long.parseLong(idEnt)) == null) {
                                dialog.dismiss();
                                Toast t = Toast.makeText(MainActivity.this, "La empresa " + idEnt + " no existe, intenta otra", Toast.LENGTH_LONG);
                                t.show();
                            } else {

                                text.setText("¿Deseas eliminar la empresa " + idEnt + " ?");
                                alertDialogBuilder.setView(confirmDeleteView);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                enterpriseOps.removeEnterprise(enterpriseOps.getEnterprise(Long.parseLong(idEnt)));
                                                Toast t = Toast.makeText(MainActivity.this, "Empresa " + idEnt + " eliminada correctamente!", Toast.LENGTH_SHORT);
                                                t.show();
                                            }
                                        })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();

                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Solo se admiten números",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create()
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        enterpriseOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        enterpriseOps.close();

    }
}
