package com.example.prm_02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddDescActivity extends AppCompatActivity {
    EditText desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_desc);
        desc = (EditText) findViewById(R.id.description);
    }
    public void  saveDesc(View view){
            Intent intent = new Intent();
            intent.putExtra("desc", desc.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
    }
}
