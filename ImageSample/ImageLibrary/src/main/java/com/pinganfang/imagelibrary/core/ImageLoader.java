package com.pinganfang.imagelibrary.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.Map;

/**
 * 图片加载工具
 * Created by LIUYONGKUI726 on 2016-08-25.
 */
public class ImageLoader {

    private Context mContext;
    private Picasso mPicasso;
    private int mErrorResId;

    /**
     * 初始化ImageLoader， size为了兼容之前的构造方法，等待使用成熟后取出
     * @param context
     * @param size
     */
    public ImageLoader(Context context, float size) {
        // TODo
        this.mContext = context;
        // Generate the global default Picasso instance.
        this.mPicasso = Picasso.with(context);
    }

    /**
     * An error drawable to be used if the request image could not be loaded.
     *
     * @param errorResId
     */
    public void setErrorResId(int errorResId) {
        this.mErrorResId = errorResId;
    }

    /**
     * <br>
     * In most cases, you should use this when you are dealing with a custom View or view holder
     * which should implement the Target interface.
     * <p/>
     * <p/>
     * Implementing on a View: <br>
     * {@code
     * public class ProfileView extends FrameLayout implements Target
     *
     * @param filePathOrUrl
     * @param target
     * @Override public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
     * setBackgroundDrawable(new BitmapDrawable(bitmap));
     * }
     * @Override public void onBitmapFailed() {
     * setBackgroundResource(R.drawable.profile_error);
     * }
     * @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
     * frame.setBackgroundDrawable(placeHolderDrawable);
     * }
     * }
     * }
     * </p>
     * <p/>
     * <p/>
     * Implementing on a view holder object for use inside of an adapter: <br>
     * {@code
     * public class ViewHolder implements Target {
     * public FrameLayout frame;
     * public TextView name;
     * @Override public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
     * frame.setBackgroundDrawable(new BitmapDrawable(bitmap));
     * }
     * @Override public void onBitmapFailed() {
     * frame.setBackgroundResource(R.drawable.profile_error);
     * }
     * @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
     * frame.setBackgroundDrawable(placeHolderDrawable);
     * }
     * }
     * }
     * </p>
     */
    public void fetchImage(String filePathOrUrl, Target target) {
        mPicasso.load(filePathOrUrl).into(target);
    }

    /**
     * 通过文件路径或者Url加载图片
     *
     * @param imageView     Asynchronously fulfills the request into the specified ImageView.
     * @param filePathOrUrl Start an image request using the specified path.
     */
    public void loadImage(final ImageView imageView, String filePathOrUrl) {
        loadImage(imageView, filePathOrUrl, 0);
    }

    /**
     * 通过文件路径或者Url加载图片，未加载完成前显示占位资源
     *
     * @param imageView        Asynchronously fulfills the request into the specified ImageView.
     * @param filePathOrUrl    Start an image request using the specified path.
     * @param placeholderResId A placeholder drawable to be used while the image is being loaded.
     */
    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId) {
        loadImage(imageView, filePathOrUrl, placeholderResId, null);
    }

    public void loadImage(final ImageView imageView, String filePathOrUrl, Callback callback) {
        loadImage(imageView, filePathOrUrl, 0, callback);
    }

    /**
     * 通过文件路径或者Url加载图片，未加载完成前显示占位资源，占位资源不能为0.<br>
     * <br>
     * Note: The Callback param is a strong reference and will prevent your Activity or Fragment from being garbage collected.
     * If you use this method, it is strongly recommended you invoke an adjacent Picasso.cancelRequest(android.widget.ImageView) call to prevent temporary leaking.
     *
     * @param imageView        Asynchronously fulfills the request into the specified ImageView.
     * @param filePathOrUrl    Start an image request using the specified path.
     * @param placeholderResId A placeholder drawable to be used while the image is being loaded.
     * @param callback
     * by liz 加了一个 filePathOrUrl为空的判断
     */
    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId, Callback callback) {
        if (null == filePathOrUrl || TextUtils.isEmpty(filePathOrUrl.trim())) {
//            DevUtil.v("Picasso", "===== File path or url is empty, load place holder resource. =====");
            loadImage(imageView, placeholderResId);
        } else {
//            DevUtil.v("Picasso", "===== Load image path : " + filePathOrUrl + " =====");
            loadImage(imageView, getRequestCreator(filePathOrUrl), placeholderResId, callback);
        }
    }

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId, int width, int height) {
        loadImage(imageView, filePathOrUrl, placeholderResId, width, height, false);
    }

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId, int width, int height, boolean isNeedCut) {
        loadImage(imageView, filePathOrUrl, placeholderResId, width, height, isNeedCut, null);
    }

    /**
     * 通过文件加载图片
     *
     * @param imageView Asynchronously fulfills the request into the specified ImageView.
     * @param file      Start an image request using the specified image file.
     */
    public void loadImage(final ImageView imageView, File file) {
        loadImage(imageView, file, 0);
    }

    /**
     * 通过文件加载图片，未加载完成前显示占位资源
     *
     * @param imageView        Asynchronously fulfills the request into the specified ImageView.
     * @param file             Start an image request using the specified image file.
     * @param placeholderResId A placeholder drawable to be used while the image is being loaded.
     */
    public void loadImage(final ImageView imageView, File file, int placeholderResId) {
        loadImage(imageView, file, placeholderResId, null);
    }

    /**
     * 通过文件加载图片，未加载完成前显示占位资源，无占位资源id为0.<br>
     * <br>
     * Note: The Callback param is a strong reference and will prevent your Activity or Fragment from being garbage collected.
     * If you use this method, it is strongly recommended you invoke an adjacent Picasso.cancelRequest(android.widget.ImageView) call to prevent temporary leaking.
     *
     * @param imageView        Asynchronously fulfills the request into the specified ImageView.
     * @param file             Start an image request using the specified image file.
     * @param placeholderResId A placeholder drawable to be used while the image is being loaded.
     * @param callback
     */
    public void loadImage(final ImageView imageView, File file, int placeholderResId, Callback callback) {
        if (file == null || !file.exists()) {
//            DevUtil.v("Picasso", "===== File is null or not exists, load place holder resource. =====");
            loadImage(imageView, placeholderResId);
        } else {
//            DevUtil.v("Picasso", "===== Load image file path : " + file.getPath() + " =====");
            loadImage(imageView, mPicasso.load(file), placeholderResId, callback);
        }
    }

    /**
     * 通过资源文件加载图片
     *
     * @param imageView  Asynchronously fulfills the request into the specified ImageView.
     * @param resourceId Start an image request using the specified drawable resource ID.
     */
    public void loadImage(final ImageView imageView, int resourceId) {
        loadImage(imageView, resourceId, null);
    }

    /**
     * 通过资源文件加载图片，未加载完成前显示占位资源，无占位资源id为0.<br>
     * <br>
     * Note: The Callback param is a strong reference and will prevent your Activity or Fragment from being garbage collected.
     * If you use this method, it is strongly recommended you invoke an adjacent Picasso.cancelRequest(android.widget.ImageView) call to prevent temporary leaking.
     *
     * @param imageView  Asynchronously fulfills the request into the specified ImageView.
     * @param resourceId Start an image request using the specified drawable resource ID.
     * @param callback
     */
    public void loadImage(final ImageView imageView, int resourceId, Callback callback) {
        if (resourceId <= 0) {
//            DevUtil.v("Picasso", "===== Resource ID is zero, cannot load resource image. =====");
            return;
        }
        loadImage(imageView, mPicasso.load(resourceId), 0, callback);
    }

    public void loadImage(final ImageView imageView, RequestCreator requestCreator, int placeholderResId, Callback callback) {
        if (placeholderResId != 0) {
            requestCreator.placeholder(placeholderResId);
        }
        requestCreator.into(imageView, callback);
    }

    public void loadImage(final ImageView imageView, File file, int placeholderResId, int width, int height,
                          boolean isNeedCut, Callback callback) {
        RequestCreator creator = mPicasso.load(file);
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

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId, int width, int height,
                          boolean isNeedCut, Callback callback) {
        if (TextUtils.isEmpty(filePathOrUrl.trim())) {
//            DevUtil.v("Picasso", "===== File path or url is empty, load place holder resource. =====");
            loadImage(imageView, placeholderResId);
            return;
        }

        RequestCreator creator = getRequestCreator(filePathOrUrl);
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

    public void loadImage(final ImageView imageView, RequestCreator requestCreator, int placeholderResId,
                          int width, int height, boolean isNeedCut, Callback callback) {
        if (placeholderResId != 0) {
            requestCreator.placeholder(placeholderResId);
        }

        // resize貌似会引起闪
//        requestCreator.resize(width, height);

        requestCreator.into(imageView, callback);
    }

    public RequestCreator getRequestCreator(String filePathOrUrl) {
        File file = new File(filePathOrUrl);

        // why load Uri - https://github.com/square/picasso/issues/1069
        if (file != null && file.exists()) {
            return mPicasso.load(Uri.fromFile(file));
        }

//        DevUtil.v("Eva", "isNotFile = " + filePathOrUrl);
        return mPicasso.load(filePathOrUrl);
    }

    public void cancelRequest(Target target) {

    }

    public void cancelRequest(ImageView view) {


        mPicasso.cancelRequest(view);
    }

    public void clearMemoryCache() {
        // TODO nothing 兼容之前，之后测试正常后删除


    }

    public Bitmap removeMemoryCache(String key) {
        // TODO nothing 兼容之前，之后测试正常后删除
        return null;
    }

    public Map<String, Bitmap> snapshot() {
        // TODO nothing 兼容之前，之后测试正常后删除
        return null;
    }

    public void setMemCacheSize(int maxSize) {
        // TODO nothing 兼容之前，之后测试正常后删除
    }

    public void cancleTask() {
        // TODO nothing 兼容之前，之后测试正常后删除
    }

    public int getMemCacheSize() {
        // TODO nothing 兼容之前，之后测试正常后删除
        return 0;
    }
}
