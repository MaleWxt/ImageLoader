package com.pinganfang.sample.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.text.DecimalFormat;


public class RotateActivity extends AppCompatActivity implements OnSeekbarChangeListener, View.OnClickListener {
    private ImageView rotate_image;
    private CrystalSeekbar range_seekbar;
    private TextView now_rotate;
    private Button rotate_button, check_button;
    private DecimalFormat df;
    private File actualImage;
    private Bitmap tempBitmap;
    private static final int PICK_IMAGE_REQUEST = 1;

    private float rotate_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        init();
        setListener();
    }

    private void init() {
        rotate_image = (ImageView) findViewById(R.id.rotate_image);
        range_seekbar = (CrystalSeekbar) findViewById(R.id.range_seekbar);
        now_rotate = (TextView) findViewById(R.id.now_rotate);
        rotate_button = (Button) findViewById(R.id.rotate_button);
        check_button = (Button) findViewById(R.id.check_button);
        df = new DecimalFormat();
        String style = "0.0";//定义要显示的数字的格式
        df.applyPattern(style);// 将格式应用于格式化器
    }

    private void setListener() {
        range_seekbar.setOnSeekbarChangeListener(this);
        rotate_button.setOnClickListener(this);
        check_button.setOnClickListener(this);
    }

    @Override
    public void valueChanged(Number value) {
        rotate_int = value.floatValue() * 3.6f;
        now_rotate.setText("现在的旋转角度 : " + String.valueOf(df.format(rotate_int)));
    }

    @Override
    public void onClick(View view) {
        //设置旋转并传递到ImageView按钮
        if (view.getId() == R.id.rotate_button) {
            if(checkFileExit()){
                tempBitmap = ImageCompressorTools.rotate(tempBitmap, (int) rotate_int);
                rotate_image.setImageBitmap(tempBitmap);
                tempBitmap = BitmapFactory.decodeFile(actualImage.getAbsolutePath());
            }
        }
        //选择图片按钮
        if (view.getId() == R.id.check_button) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    actualImage = FileUtil.from(this, data.getData());
                    tempBitmap = BitmapFactory.decodeFile(actualImage.getAbsolutePath());
                    rotate_image.setImageBitmap(tempBitmap);
                    Log.d("--->", "返回来的图片大小 :" + (actualImage.length() / 1024) + "k");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkFileExit() {
        if (actualImage == null || tempBitmap == null) {
            Toast.makeText(RotateActivity.this, "原始图片没有选择,可能引发空指针", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        tempBitmap = null;
    }
}
