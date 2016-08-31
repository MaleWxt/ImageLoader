package com.pinganfang.sample.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinganfang.sample.tools.FileUtil;
import com.pinganfang.imagelibrary.compressor.ImageCompressorTools;
import com.pinganfang.imagelibrary.compressor.OnCompressListener;
import com.pinganfang.sample.R;

import java.io.File;
import java.io.IOException;

public class CompressActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView compress_image;
    private Button compress_btn1, select_image;
    private static final int PICK_IMAGE_REQUEST = 1;
    private File actualImage;
    private Bitmap tempBitmap;
    private TextView new_image_size, old_image_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);
        init();
        setListener();
    }

    private void init() {
        compress_image = (ImageView) findViewById(R.id.compress_image);
        compress_btn1 = (Button) findViewById(R.id.compress_btn1);
        new_image_size = (TextView) findViewById(R.id.new_image_size);
        old_image_size = (TextView) findViewById(R.id.old_image_size);
        select_image = (Button) findViewById(R.id.select_image);
    }

    private void setListener() {
        select_image.setOnClickListener(this);
        compress_btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                break;
            case R.id.compress_btn1:
                if (checkFileExit()) {
                    final long[] l = new long[1];
                    ImageCompressorTools.Luban.get(this)
                            .load(actualImage)
                            .putGear(ImageCompressorTools.Luban.THIRD_GEAR)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    Toast.makeText(CompressActivity.this, "开始压缩", Toast.LENGTH_SHORT).show();
                                    l[0] = System.currentTimeMillis();
                                }

                                @Override
                                public void onSuccess(File file) {
                                    new_image_size.setText(file.length() / 1024 + "k");
                                    compress_image.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
                                    long l1 = System.currentTimeMillis();
                                    new_image_size.setText("图片尺寸 : " + ImageCompressorTools.Luban.get(getApplicationContext()).getImageSize(file.getPath())[0]
                                            + " * " + ImageCompressorTools.Luban.get(getApplicationContext()).getImageSize(file.getPath())[1]
                                            + " 内存大小 " + (actualImage.length() / 1024) + "k" + " 耗时 " + (l1 - l[0]));
                                }
                            }).launch();

                }
                break;
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
                    compress_image.setImageBitmap(tempBitmap);
                    old_image_size.setText("返回来的图片大小 :" + (actualImage.length() / 1024) + "k");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkFileExit() {
        if (actualImage == null || tempBitmap == null) {
            Toast.makeText(CompressActivity.this, "原始图片没有选择,可能引发空指针", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
