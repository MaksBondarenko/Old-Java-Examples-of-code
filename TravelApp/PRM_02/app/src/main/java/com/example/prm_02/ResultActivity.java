package com.example.prm_02;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_act);
        if(getIntent()!=null) {
            if (getIntent().hasExtra("path")) {
                ImageView naglowek = (ImageView) findViewById(R.id.image);
                Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("path"));
                Bitmap bitmap1 = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                bitmap1.setHeight(1200);
                naglowek.setImageBitmap(bitmap1);
            }
        }
    }
    public void  goBack(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
