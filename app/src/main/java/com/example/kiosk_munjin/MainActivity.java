package com.example.kiosk_munjin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;

    TextView txt_title;
    TextView txt_date;
    TextView txt_signName;
    EditText edt_phone;
    EditText edt_name;
    EditText edt_addr;
    TextView edt_condition;
    TextView txt_update_condition;
    EditText edt_gaurantee;
    Button btn_sign;
    Button btn_search;
    Button btn_save;
    String thedate;
    ImageView img_sign;
    ConstraintLayout lay_parent;
    RadioGroup grp_addr;
    RadioGroup grp_period;
    RadioGroup grp_lesson;
    RadioGroup grp_coupon;
    String temp_conditon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);
//        final HideKeyBoard hd_kb = (HideKeyBoard) getApplicationContext();
        HideKeyBoard hideKeyBoard = new HideKeyBoard();
        hideKeyBoard.setupUI(findViewById(R.id.lay_parent),MainActivity.this);

        txt_title = findViewById(R.id.txt_title);
        txt_signName = findViewById(R.id.txt_signName);
        txt_update_condition = findViewById(R.id.txt_update_condition);
        txt_update_condition.setPaintFlags(txt_update_condition.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG); //underline text
        grp_addr = findViewById(R.id.grp_addr);
        grp_period = findViewById(R.id.grp_period);
        grp_lesson = findViewById(R.id.grp_lesson);
        grp_coupon = findViewById(R.id.grp_coupon);
        txt_date = findViewById(R.id.txt_date);
        edt_phone = findViewById(R.id.edt_phone);
        edt_addr = findViewById(R.id.edt_addr);
        edt_condition = findViewById(R.id.edt_condition);
        edt_gaurantee = findViewById(R.id.edt_gaurantee);
        edt_name = findViewById(R.id.edt_name);
        btn_sign = findViewById(R.id.btn_sign);
        btn_search = findViewById(R.id.btn_search);
        btn_save = findViewById(R.id.btn_save);
        img_sign = findViewById(R.id.img_sign);
        lay_parent = (ConstraintLayout) findViewById(R.id.lay_parent);
//        edt_condition.setFocusable(false);
//        edt_condition.setInputType(InputType.TYPE_NULL);

        dbManager = new DBManager(this);
        dbManager.open();
//        dbManager.deletename("아츄");

        String s = "1. 회원카드는 타인(가족포함)에게 양도, 대여 할 수 없습니다."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "2. 락커키 분실등 회원님의 부주의로 인한 피해는 보상하지 않습니다."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "3. 유효기간은 연장이 불가능하며, 유효기간 경과후 회원자격은 상실됩니다."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "4. 계약해지는 유효기간 내에만 가능하며 이용금액은 위약금, 세금, 카드수수료 등을 포함한 비회원 요금으로 정산합니다."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "5. 락커이용기간 종료후 발생하는 분실사고는 책임지지 않습니다."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "6. 타인에게 불편을 주거나 이용수칙을 준수하지 않는 회원은 강제탈퇴 등의 불이익을 받을 수 있습니다."
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + System.getProperty("line.separator");
        edt_condition.setText(s);
        getCondition();
//        edt_condition.setEnabled(false);
//        edt_condition.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus)
//                {
//                    temp_conditon = edt_condition.getText().toString();
//                }
//                if (!hasFocus)
//                {
//                    String new_condition = edt_condition.getText().toString();
//                    if ( new_condition.equalsIgnoreCase(temp_conditon))
//                    {
////                        Toast.makeText(MainActivity.this, "SAME SAME", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        DateFormat dateFormat = new SimpleDateFormat("MM");
//                        DateFormat dateFormatd = new SimpleDateFormat("dd");
//                        DateFormat dateFormaty = new SimpleDateFormat("yyyy");
//                        DateFormat dateFormath = new SimpleDateFormat("HH");
//                        DateFormat dateFormatm = new SimpleDateFormat("mm");
//                        DateFormat dateFormats = new SimpleDateFormat("ss");
//                        Date date = new Date();
//                        String adate = dateFormaty.format(date).toString() +  dateFormat.format(date).toString() +
//                                dateFormatd.format(date).toString() +  dateFormath.format(date).toString() +
//                                dateFormatm.format(date).toString() + dateFormats.format(date).toString();
//                        String acondition = edt_condition.getText().toString();
//                        if( dbManager.insert_condition(acondition, adate) == true)
//                        {
//                            Toast.makeText(MainActivity.this, "업데이트 완료!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//
//                }
//
//            }
//        });




       clearfields();
        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txt_signName.setText("회원명 : " + edt_name.getText().toString());

            }
        });
        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final int[] keyDel = new int[1];
                edt_phone.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {


                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel[0] = 1;
                        return false;
                    }
                });

                if (keyDel[0] == 0) {
                    int len = edt_phone.getText().length();
                    if(len == 3 || len == 8) {
                        edt_phone.setText(edt_phone.getText() + "-");
                        edt_phone.setSelection(edt_phone.getText().length());
                    }
                } else {
                    keyDel[0] = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt_title.setText("회원가입신청서");
//        edt_phone.setText("010-6254-6053");
    }




//get condition
    public void getCondition()
    {

        Cursor cursor = dbManager.fetchone_condition("");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String acondition = cursor.getString(cursor.getColumnIndex("usr_condition"));
                edt_condition.setText(acondition);
            };
        }
    }

    //open sign activity
    public void openSign(View view){
        String the_phone = edt_phone.getText().toString();
        Intent intent = new Intent(this, Sign.class);
        String the_name = edt_name.getText().toString();
        // check if Name and Number have been filled
        if ( the_name.matches(""))
        {
            Toast.makeText(MainActivity.this, "이름랑 랑 핸드폰번호 입력해야해요!", Toast.LENGTH_LONG).show();;
            return;
        }
        if ( the_phone.matches(""))
        {
            Toast.makeText(MainActivity.this, "이름랑 랑 핸드폰번호 입력해야해요!", Toast.LENGTH_LONG).show();;
            return;
        }
        intent.putExtra("aname", the_name);
        intent.putExtra("aphone", the_phone);
        intent.putExtra("adate", thedate);
//        intent.putExtra("newfile", thedate);
        startActivityForResult(intent,2);
    }


    //save to database and take screenshot of page
    public void saveInfo(View view){


        String the_mobile = edt_phone.getText().toString();
        String the_name = edt_name.getText().toString();
        if ( the_name.matches(""))
        {
            Toast.makeText(MainActivity.this, "이름랑 랑 핸드폰번호 입력해야해요!", Toast.LENGTH_LONG).show();;
            return;
        }
        if( dbManager.insert(the_name, the_mobile, the_mobile,edt_addr.getText().toString(),
                "","","","",thedate) == true)
        {
            saveResultImg();
            thedate = "";
            clearfields();
            Toast.makeText(MainActivity.this, "저장 완료!", Toast.LENGTH_LONG).show();
        }
        else
        {Toast.makeText(MainActivity.this, "저장 실페!", Toast.LENGTH_LONG).show();};
    }

    //open activity to manage user Conditions
    public void openconditions(View view){
        String the_condition = edt_condition.getText().toString();
        Intent intent = new Intent(this, UpdateConditions.class);
        intent.putExtra("aconditions", the_condition);
        startActivityForResult(intent,3);
    }


    //open ImageResult activity
    public void openImageResult(View view){

        searchUser(view);
    }


    //open new activity
    public void  searchUser(View view) {
        // EditText edt_name = findViewById(R.id.edt_name);
        String the_name = edt_name.getText().toString();
        String the_mobile = edt_phone.getText().toString();
        Intent intent = new Intent(this, SearchUser.class);
        String message = edt_name.getText().toString();
        intent.putExtra("ausername", the_name);
        intent.putExtra("amobile", the_mobile);
        startActivityForResult(intent,1);
    }

    public void setDate()
    {

        DateFormat dateFormat = new SimpleDateFormat("MM");
        DateFormat dateFormatd = new SimpleDateFormat("dd");
        DateFormat dateFormaty = new SimpleDateFormat("yyyy");
        DateFormat dateFormath = new SimpleDateFormat("HH");
        DateFormat dateFormatm = new SimpleDateFormat("mm");
        DateFormat dateFormats = new SimpleDateFormat("ss");
        Date date = new Date();
        thedate = dateFormaty.format(date).toString() +  dateFormat.format(date).toString() +
                dateFormatd.format(date).toString() +  dateFormath.format(date).toString() +
                dateFormatm.format(date).toString() + dateFormats.format(date).toString();
        txt_date.setText(dateFormaty.format(date).toString() + "년 " +dateFormat.format(date).toString() + "월 " +
                dateFormatd.format(date).toString() + "일");
        //2021년 12월 18일
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        super.onActivityResult(requestCode, resultCode, data_intent);
        if (requestCode == 2) //  from Sign activity
        {
            if(resultCode == RESULT_OK)
            {
                String apath = data_intent.getStringExtra("apath");
                byte[] byteArray = data_intent.getByteArrayExtra("image");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String str_name = edt_name.getText().toString();
                img_sign.setImageBitmap(bmp);
            }
        }
        if (requestCode == 3) //  Result from UpdateConditions
        {
            if(resultCode == RESULT_OK)
            {
                String acondtitions = data_intent.getStringExtra("aconditions");
                edt_condition.setText(acondtitions);
            }
        }
    }

    public void saveResultImg ()
    {
        String the_name = edt_name.getText().toString();
        String a_filename = the_name +"_"+ thedate +".png";
        btn_save.setVisibility(View.GONE);
        btn_search.setVisibility(View.GONE);
        btn_sign.setVisibility(View.GONE);
        txt_update_condition.setText("");

        Bitmap bitmap = screenShot(lay_parent);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        String a_filename = "parent.png";
        String a_apth = saveToInternalStorage(bitmap,a_filename);
        btn_save.setVisibility(View.VISIBLE);
        btn_search.setVisibility(View.VISIBLE);
        btn_sign.setVisibility(View.VISIBLE);
        txt_update_condition.setText(">>> 역관 수정");

    }

    public Bitmap screenShot(View view)
    {
//        View view = getWindow().getDecorView().getRootView();
        edt_addr.setCursorVisible(false);
        edt_name.setCursorVisible(false);
        edt_phone.setCursorVisible(false);
        edt_condition.setCursorVisible(false);
        edt_gaurantee.setCursorVisible(false);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        edt_addr.setCursorVisible(true);
        edt_name.setCursorVisible(true);
        edt_phone.setCursorVisible(true);
        edt_condition.setCursorVisible(true);
        edt_gaurantee.setCursorVisible(true);
        return bitmap;

    }
    public void clearfields()
     {
         edt_phone.setText("010-");
         edt_name.setText("");
         edt_addr.setText("");
         edt_gaurantee.setText("");
         txt_signName.setText("회원명 : ");
         uncheckGroup(grp_addr);
         uncheckGroup(grp_period);
         uncheckGroup(grp_lesson);
         uncheckGroup(grp_coupon);
         setDate();
         img_sign.setImageResource(0);
     }

     public void uncheckGroup(View view)
     {

         int count = ((RadioGroup)view).getChildCount();
         ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();
         for (int i=0;i<count;i++)
         {
             View o = ((RadioGroup)view).getChildAt(i);
             if (o instanceof RadioButton) {
                 RadioButton selectedAnswer =(RadioButton)o;
                 selectedAnswer.setChecked(false);
             }
         }

     }

    private String saveToInternalStorage(Bitmap bitmapImage,String afilename){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,afilename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


}