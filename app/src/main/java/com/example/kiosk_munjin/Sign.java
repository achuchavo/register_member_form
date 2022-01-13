package com.example.kiosk_munjin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Sign extends AppCompatActivity {
    PaintView paintView;
    ConstraintLayout lay_paint;
    Button btn_save;
    String thefilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        setTitle("사인");


        paintView = new PaintView (this);
        lay_paint = (ConstraintLayout) findViewById(R.id.lay_paint);
        lay_paint.addView(paintView);
        btn_save = findViewById(R.id.btn_save);


        Intent intent = getIntent();
        thefilename = intent.getStringExtra("adate");

        btn_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(thefilename))
                {
                    Toast.makeText(Sign.this, "ENTER PHONE NUMBER AND TRY AGAIN!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {

                }
                Bitmap bitmap = screenShot(lay_paint);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // FileOutputStream streams = this.openFileOutput(afilename, Context.MODE_PRIVATE);
                String a_filename = "test.png";
                String a_apth = saveToInternalStorage(bitmap,a_filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent data_intent = new Intent();
                data_intent.putExtra("image",byteArray);
                data_intent.putExtra("apath", a_apth);
                setResult(RESULT_OK, data_intent);
                finish();

            }
        });
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

    public Bitmap screenShot(View view)
    {
//        View view = getWindow().getDecorView().getRootView();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}