package com.example.kiosk_munjin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateConditions extends AppCompatActivity {
    private DBManager dbManager;


    EditText edt_condition;
    Button btn_close;
    Button btn_set_conditions;
    String theconditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_conditions);

        dbManager = new DBManager(this);
        dbManager.open();

        edt_condition = findViewById(R.id.edt_condition);
        btn_close = findViewById(R.id.btn_close);
        btn_set_conditions = findViewById(R.id.btn_set_conditions);

        Intent intent = getIntent();
        theconditions = intent.getStringExtra("aconditions");
        edt_condition.setText(theconditions);
    }

    public void closePage(View view)
    {
        Intent data_intent = new Intent();
        data_intent.putExtra("aconditions",theconditions);
        setResult(RESULT_OK, data_intent);
        finish();
    }

    public void setConditions(View view)
    {
        String new_condtitions = edt_condition.getText().toString();
        //add new condition to database
        DateFormat dateFormat = new SimpleDateFormat("MM");
        DateFormat dateFormatd = new SimpleDateFormat("dd");
        DateFormat dateFormaty = new SimpleDateFormat("yyyy");
        DateFormat dateFormath = new SimpleDateFormat("HH");
        DateFormat dateFormatm = new SimpleDateFormat("mm");
        DateFormat dateFormats = new SimpleDateFormat("ss");
        Date date = new Date();
        String adate = dateFormaty.format(date).toString() +  dateFormat.format(date).toString() +
                dateFormatd.format(date).toString() +  dateFormath.format(date).toString() +
                dateFormatm.format(date).toString() + dateFormats.format(date).toString();
        if( dbManager.insert_condition(new_condtitions, adate) == true)
        {
            //send added condition to main form
            Intent data_intent = new Intent();
            data_intent.putExtra("aconditions",new_condtitions);
            setResult(RESULT_OK, data_intent);
            finish();
        }
        else
        {
            Toast.makeText(UpdateConditions.this, "업데이트 실패!", Toast.LENGTH_LONG).show();
        }
    }
}