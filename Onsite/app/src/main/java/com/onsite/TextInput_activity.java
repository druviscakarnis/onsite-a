package com.onsite;

import android.content.ContextWrapper;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextInput_activity extends AppCompatActivity {
    File addressFile;
    ArrayList<String> array;
    EditText addressInput;
    RecyclerView addressList;
    Button submit;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Onsite";
    String address = "";

    void createAddressFile(){
        try{
            addressFile = new File("address_list.txt");
            if(addressFile.createNewFile()){
                System.out.println("Text file created");
            }else{
                System.out.println("Text file exists");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    void writeAddress(){
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(path+"/"+addressFile, true));
            writer.write(address+"\n");
            writer.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    void readAddresses(){

            Scanner reader = new Scanner(path + "/" + addressFile);
            while(reader.hasNextLine()){
                String addressLine = reader.nextLine();
                array.add(addressLine);
            }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_activity);

        File dir = new File(path);
        dir.mkdirs();

        addressInput = (EditText) findViewById(R.id.addAddress);
        submit = (Button) findViewById(R.id.submit);

        addressList = (RecyclerView) findViewById(R.id.addressList);
        addressList.setLayoutManager(new LinearLayoutManager(this));
        addressList.setAdapter(new AddressAdapter(array));

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                createAddressFile();
                address= addressInput.getText().toString();
                System.out.println(address);
                writeAddress();
                Toast.makeText(getApplicationContext(), "Adrese saglabāta", Toast.LENGTH_LONG).show();
                address = "";
                addressInput.setText("");
            }
        });

    }
}