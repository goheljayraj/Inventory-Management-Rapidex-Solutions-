package com.rapidexsol.testapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class viewallactivity extends AppCompatActivity {
    ListView ltview;
    Button exportbutton,resetbutton;
    ArrayAdapter itemArrayAdapter;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallactivity);

        dataBase = new DataBase(viewallactivity.this);


        ltview = findViewById(R.id.listviewid);

        resetbutton = findViewById(R.id.resetbbuttonid);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBase.clearAll();

            }
        });



        exportbutton = findViewById(R.id.exportbuttonid);
        exportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oncreateexportbutton();
            }
        });
        oncreateviewall();


    }


    private void oncreateexportbutton(){


        dataBase.exportDatabse();
//        boolean success =
//        if(success==true) {
//            Toast.makeText(viewallactivity.this, "true", Toast.LENGTH_SHORT).show();}
//        else{
//            Toast.makeText(viewallactivity.this, "false", Toast.LENGTH_SHORT).show();
//        }

    }
    public void oncreateviewall(){

        showCustomerOnListView(dataBase);
         //Toast.makeText(viewallactivity.this, "everyItem.toString()" , Toast.LENGTH_SHORT).show();

        ltview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Inventory clickeditem = (Inventory) parent.getItemAtPosition(pos);
                dataBase.deleteOne(clickeditem);
                showCustomerOnListView(dataBase);
                Toast.makeText(viewallactivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();



            }
        });

    }


    public void showCustomerOnListView(DataBase dataBase2){
        itemArrayAdapter = new ArrayAdapter<Inventory>(viewallactivity.this, android.R.layout.simple_list_item_1,dataBase.getEveryItem());
        ltview.setAdapter(itemArrayAdapter);
    }
}