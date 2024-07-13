package com.example.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText item;
Button add;
ListView list;
ArrayList<String> itemList = new ArrayList<>();
ArrayAdapter<String> arrayAdapter;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item=findViewById(R.id.editText1);
        add=findViewById(R.id.btn1);
        list=findViewById(R.id.list);

         try {
             itemList=FileHelper.readData(this);
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,itemList);
        list.setAdapter(arrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = item.getText().toString();
                itemList.add(itemName);
                item.setText("");
                try {
                    FileHelper.writeData(itemList,getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
             arrayAdapter.notifyDataSetChanged();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert =new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you Want to Delete This Task");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });


                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                itemList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                        try {
                            FileHelper.writeData(itemList,getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                AlertDialog alertDialog= alert.create();
                alertDialog.show();
            }
        });
    }
}