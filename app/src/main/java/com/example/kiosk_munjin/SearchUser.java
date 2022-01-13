package com.example.kiosk_munjin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchUser extends AppCompatActivity {
    private MainActivity mainActivity;
    private DBManager dbManager;

    ArrayAdapter adapter;
    private ArrayList<String> listItem;

    private SimpleCursorAdapter adapters;
    final String[] from = new String[] { DatabaseHelper.USRNAME, DatabaseHelper.USRMOBILE,DatabaseHelper.USRDATE };

//    final int[] to = new int[] { R.id.id, R.id.usr_name, R.id.usr_mobile,R.id.usr_addr,R.id.usr_addrtype,R.id.usr_term,R.id.usr_lesson,R.id.usr_coupon,R.id.usr_date};
    final int[] to = new int[] { R.id.usr_name, R.id.usr_mobile,R.id.usr_date};

//    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        setTitle("조회 결과");

        Button btn_print_test = findViewById(R.id.btn_print_test);
        ListView lst_user = findViewById(R.id.lst_user);

        lst_user.setEmptyView(findViewById(R.id.empty));

        listItem = new ArrayList<>();

        dbManager = new DBManager(this);
        dbManager.open();
        //viewData();
        viewDatas();

        lst_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView usr_name = (TextView) view.findViewById(R.id.usr_name);
                TextView usr_mobile = (TextView) view.findViewById(R.id.usr_mobile);
                TextView usr_date = (TextView) view.findViewById(R.id.usr_date);


                String str_name = usr_name.getText().toString();
                String str_mobile = usr_mobile.getText().toString();
                String str_date = usr_date.getText().toString();

                String thefilename = str_name + "_" + str_date + ".png";
                openImageResults(thefilename,str_date);


//                Intent data_intent = new Intent();
//                data_intent.putExtra("usr_name", str_name);
//                data_intent.putExtra("usr_mobile", str_mobile);
//                data_intent.putExtra("id", id);
//                data_intent.putExtra("usr_date", str_date);
//                setResult(RESULT_OK, data_intent);
//                finish();

            }
        });






        btn_print_test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }





    private void viewDatas(){
        ListView lst_user = findViewById(R.id.lst_user);
        Intent intent = getIntent();
        String thename = intent.getStringExtra("ausername");
        String themobile = intent.getStringExtra("amobile");
        Cursor cursor = dbManager.fetchone(thename,themobile);

        adapters = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapters.notifyDataSetChanged();

        lst_user.setAdapter(adapters);
    }

    public void openImageResults(String afilename,String adate)
    {
        Intent intent = new Intent(this, ImageResult.class);
        intent.putExtra("afilename", afilename);
        intent.putExtra("adate", adate);
        startActivityForResult(intent,3);
    }

}