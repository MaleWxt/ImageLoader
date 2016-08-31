package com.pinganfang.sample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pinganfang.sample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListener();
    }

    private void init() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
    }

    private void setListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                jump(RotateActivity.class);
                break;
            case R.id.btn2:
                jump(ScaleActivity.class);
                break;
            case R.id.btn3:
                jump(CompressActivity.class);
                break;
            default:
                break;
        }
    }

    private void jump(Class c) {
        Intent i = new Intent(MainActivity.this, c);
        startActivity(i);
    }
}
