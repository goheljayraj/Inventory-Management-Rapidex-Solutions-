package com.rapidexsol.testapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class additemsactivity extends AppCompatActivity {
        Button viewallbutton,addbutton;
        EditText etname,etbarcode,etquantity,etmultiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additemsactivity);

        addbutton = (Button) findViewById(R.id.addbuttonid);
        etname = findViewById(R.id.itemnameid);
        etbarcode = findViewById(R.id.barcodeid);
        etquantity = findViewById(R.id.quantityid);
        etmultiplier = findViewById(R.id.multiplierid);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inventory invent;
                try{
                invent = new Inventory(-1,etname.getText().toString(),etbarcode.getText().toString(),Integer.parseInt(etquantity.getText().toString()),Integer.parseInt(etmultiplier.getText().toString()));
                Toast.makeText(additemsactivity.this, "Item Added", Toast.LENGTH_SHORT).show();}
                catch (Exception e){
                    Toast.makeText(additemsactivity.this, "Error Adding Item", Toast.LENGTH_SHORT).show();
                    invent = new Inventory(-1,"error", "error" ,0,0);

                }
                DataBase dataBase = new DataBase(additemsactivity.this);
                boolean success = dataBase.addOne(invent);
               // Toast.makeText(additemsactivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
            }
        });


        viewallbutton = (Button) findViewById(R.id.viewallbuttonid);
        viewallbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openviewallactivity();
            }
        });
    }
    private void openviewallactivity(){
        Intent intent3 = new Intent(this,viewallactivity.class);
        startActivity(intent3);
    }
}