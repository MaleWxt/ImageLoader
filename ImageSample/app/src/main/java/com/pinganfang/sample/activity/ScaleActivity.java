package com.pinganfang.sample.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.pinganfang.sample.tools.FileUtil;
import com.pinganfang.imagelibrary.compressor.ImageCompressorTools;
import com.pinganfang.sample.R;

import java.io.File;
import java.io.IOException;

public class ScaleActivity extends AppCompatActivity implements OnSeekbarChangeListener, View.OnClickListener {
    private TextView now_scale, scale_type;
    private CrystalSeekbar range_seekbar;
    private ImageView scale_image;
    private Button select_button, scale_button1, scale_button2, scale_button3;
    private static final int PICK_IMAGE_REQUEST = 1;
    private File actualImage;
    private Bitmap tempBitmap;
    private float scale_int, width_et, height_et;
    private EditText height_value, width_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        init();
        setListener();
    }

    private void init() {
        scale_type = (TextView) findViewById(R.id.scale_type);
        now_scale = (TextView) findViewById(R.id.now_scale);
        select_button = (Button) findViewById(R.id.select_button);
        scale_button1 = (Button) findViewById(R.id.scale_button1);
        scale_button2 = (Button) findViewById(R.id.scale_button2);
        scale_button3 = (Button) findViewById(R.id.scale_button3);
        range_seekbar = (CrystalSeekbar) findViewById(R.id.range_seekbar);
        scale_image = (ImageView) findViewById(R.id.scale_image);
        height_value = (EditText) findViewById(R.id.height_value);
        width_value = (EditText) findViewById(R.id.width_value);
    }

    private void setListener() {
        range_seekbar.setOnSeekbarChangeListener(this);
        scale_button1.setOnClickListener(this);
        scale_button2.setOnClickListener(this);
        scale_button3.setOnClickListener(this);
        select_button.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    actualImage = FileUtil.from(this, data.getData());
                    tempBitmap = BitmapFactory.decodeFile(actualImage.getAbsolutePath());
                    scale_image.setImageBitmap(tempBitmap);
                    Log.d("--->", "返回来的图片大小 :" + (actualImage.length() / 1024) + "k");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.select_button) {
            scale_type.setText("");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        if (view.getId() == R.id.scale_button1) {
            scale_type.setText("现在是原有储存*" + scale_int + "的大小");
            if (checkFileExit()) {
                tempBitmap = ImageCompressorTools.scale(tempBitmap, scale_int);
                scale_image.setImageBitmap(tempBitmap);
                tempBitmap = BitmapFactory.decodeFile(actualImage.getAbsolutePath());
            }

        }
        if (view.getId() == R.id.scale_button2) {
            scale_type.setText("不等比缩放图片");
            if (checkFileExit()) {
                if (width_value.getText().toString().length() > 0 && height_value.getText().toString().length() > 0) {
                    width_et = Float.valueOf(width_value.getText().toString().trim()) / 100;
                    height_et = Float.valueOf(height_value.getText().toString().trim()) / 100;
                    scale_type.setText("不等比缩放图片 宽比: " + width_et + " 高比: " + height_et);
                    tempBitmap = ImageCompressorTools.scale(tempBitmap, width_et, height_et);
                    scale_image.setImageBitmap(tempBitmap);
                    tempBitmap = BitmapFactory.decodeFile(actualImage.getAbsolutePath());
                } else {
                    Toast.makeText(ScaleActivity.this, "输入框内容不符合逻辑", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (view.getId() == R.id.scale_button3) {
            scale_type.setText("设置宽高缩放图片");
            if (checkFileExit()) {
                if (width_value.getText().toString().length() > 0 && height_value.getText().toString().length() > 0) {
                    int w = Integer.valueOf(width_value.getText().toString().trim());
                    int h = Integer.valueOf(height_value.getText().toString().trim());
                    scale_type.setText("不等比缩放图片 宽: " + w + " 高: " + h);
                    tempBitmap = ImageCompressorTools.zoom(tempBitmap, w, h);
                    scale_image.setImageBitmap(tempBitmap);
                    tempBitmap = BitmapFactory.decodeFile(actualImage.getAbsolutePath());
                } else {
                    Toast.makeText(ScaleActivity.this, "输入框内容不符合逻辑", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void valueChanged(Number value) {
        scale_int = value.floatValue() / 100;
        now_scale.setText("缩放比等于 " + value.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        tempBitmap = null;
        actualImage = null;
    }

    private boolean checkFileExit() {
        if (actualImage == null || tempBitmap == null) {
            Toast.makeText(ScaleActivity.this, "原始图片没有选择,可能引发空指针", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
