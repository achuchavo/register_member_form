package com.example.kiosk_munjin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageResult extends AppCompatActivity {

    ImageView img_result;
    String thefilename;
    Button btn_print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_image_result);

        img_result = findViewById(R.id.img_result);
        btn_print = findViewById(R.id.btn_print);

        Intent intent = getIntent();
        thefilename = intent.getStringExtra("afilename");
        loadImageFromStorage(thefilename);
    }

    private void loadImageFromStorage(String afile)
    {

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            String path = directory.getAbsolutePath();
            // edt_locker.setText(path);
            File f=new File(path, afile);
//            edt_locker.setText( f.getAbsoluteFile().toString());

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img_result.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void printResult(View view)
    {

        img_result.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) img_result.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
//                Bitmap bitmap = screenShot(getWindow().getDecorView().getRootView());
        PrintHelper photoPrinter = new PrintHelper(ImageResult.this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("Munjin Join", bitmap);
    }


    public void closePage(View view)
    {
        finish();
    }
}