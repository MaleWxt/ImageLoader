package com.pinganfang.imagelibrary.core;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.pinganfang.imagelibrary.util.NullUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * 框架集成具体实现类，抽离业务和具体实现
 * Created by LIUYONGKUI726 on 2016-08-25.
 */
public class ImageBridgeImpl extends ImageBridge {

    private static final String TAG = "PaImageLoaderImpl";
    private Context context;
    private Picasso mPicasso;

    public ImageBridgeImpl(Context context) {
        this.context = context;
    }

    @Override
    void init() {
        // Generate the global default Picasso instance.
        this.mPicasso = Picasso.with(context);

    }

    @Override
    void loadImage(ImageView imageView, RequestCreator requestCreator, int placeholderResId, Callback callback) {
        if (placeholderResId != 0) {
            requestCreator.placeholder(placeholderResId);
        }
        requestCreator.into(imageView, callback);

    }


    @Override
    void loadImage(ImageView imageView, int resourceId, Callback callback) {

        NullUtils.checkNotNull(mPicasso, "Picasso == Null, you must init PaImageloder: call PaImageloder.inti()");
        loadImage(imageView, mPicasso.load(resourceId), 0, callback);

    }

    @Override
    public void fetchImage(String filePathOrUrl, Target target) {
        NullUtils.checkNotNull(mPicasso, "Picasso == Null, you must init PaImageloder: call PaImageloder.inti()");
        mPicasso.load(filePathOrUrl).into(target);
    }

    @Override
    public void loadImage(ImageView imageView, String filePathOrUrl, int placeholderResId, Callback callback) {
        if (null == filePathOrUrl || TextUtils.isEmpty(filePathOrUrl.trim())) {
            loadImage(imageView, placeholderResId);
        } else {
            loadImage(imageView, getRequestCreator(filePathOrUrl), placeholderResId, callback);
        }
    }

    @Override
    void loadImage(ImageView imageView, File file, int placeholderResId, Callback callback) {
        if (file == null || !file.exists()) {
            Log.v(TAG, "===== File is null or not exists, load place holder resource. =====");
            loadImage(imageView, placeholderResId);
        } else {
            Log.v(TAG, "===== Load image file path : " + file.getPath() + " =====");
            NullUtils.checkNotNull(mPicasso, "Picasso == Null, you must init PaImageloder: call PaImageloder.inti()");
            loadImage(imageView, mPicasso.load(file), placeholderResId, callback);
        }
    }

    @Override
    void loadImage(ImageView imageView, String filePathOrUrl, File file, int placeholderResId, int width, int height, boolean isNeedCut, Callback callback) {
        if (TextUtils.isEmpty(filePathOrUrl.trim()) && file == null) {
//            DevUtil.v("Picasso", "===== File path or url is empty, load place holder resource. =====");
            loadImage(imageView, placeholderResId);
            return;
        }

        RequestCreator creator = null;

        if (!TextUtils.isEmpty(filePathOrUrl.trim())) {
            creator = getRequestCreator(filePathOrUrl);
        }

        if (file != null) {
           creator = mPicasso.load(file);
        }

        if (placeholderResId != 0) {
            creator.placeholder(placeholderResId);
        }

        if (width != 0 && height != 0) {
            creator.resize(width, height);
        }

        if (isNeedCut) {
            creator.centerCrop();
        } else {
            creator.centerInside();
        }
        creator.into(imageView, callback);
    }

    @Override
    void loadImage(ImageView imageView, RequestCreator requestCreator, int placeholderResId, int width, int height, boolean isNeedCut, Callback callback) {
        if (placeholderResId != 0) {
            requestCreator.placeholder(placeholderResId);
        }

        // resize貌似会引起闪
//        requestCreator.resize(width, height);、
        requestCreator.into(imageView, callback);
    }

    @Override
    void cancelRequest(ImageView view) {
        NullUtils.checkNotNull(mPicasso, "Picasso == Null, Are you  init PaImageloder?: call PaImageloder.inti()");
        mPicasso.cancelRequest(view);
    }

    @Override
    void clearMemoryCache(String url, ImageView view) {
        mPicasso.load(url).memoryPolicy(MemoryPolicy.NO_CACHE).into(view);

    }

    @Override
    void clearCache(Uri uri, File file, String path) {
        if (TextUtils.isEmpty(uri.toString())){
            mPicasso.invalidate(uri);
            return;
        }
        if (NullUtils.isNull(file)) {
            mPicasso.invalidate(file);
            return;
        }
        if(TextUtils.isEmpty(path)) {
            mPicasso.invalidate(path);
        }
    }


    public void cancelRequest(Target target) {
        mPicasso.cancelTag(target);
    }

    public RequestCreator getRequestCreator(String filePathOrUrl) {
        File file = new File(filePathOrUrl);
        if (file != null && file.exists()) {
            return mPicasso.load(Uri.fromFile(file));
        }
        return mPicasso.load(filePathOrUrl);
    }




}
