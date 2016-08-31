package com.pinganfang.imagelibrary.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;


/**
 *图片ImagePresenter
 * Created by LIUYONGKUI726 on 2016-08-26.
 */
public class PaImagePresenter {

    private static final String TAG = "PaImageLoader";
    private Context context;
    private static PaImagePresenter sInstance;
    private ImageBridge imageBridge;

    /**
     * 获取单例对象
     *
     * @param aContext context
     * @return 返回 PaStaticsManager
     */
    public static synchronized PaImagePresenter getInstance(Context aContext) {
        if (sInstance == null) {
            sInstance = new PaImagePresenter(aContext, new ImageBridgeImpl(aContext));
        }
        return sInstance;
    }

    /**
     * constructor
     * @param context context
     */
    private PaImagePresenter(Context context, ImageBridge imageBridge) {
        this.context = context;
        this.imageBridge = imageBridge;
    }


    public void init(Context context, float size) {

         imageBridge.init();
    }

    public void fetchImage(String filePathOrUrl, Target target) {
        imageBridge.fetchImage(filePathOrUrl, target);
    }

    public void loadImage(final ImageView imageView, int resourceId, Callback callback) {
        imageBridge.loadImage(imageView, resourceId, callback);
    }

    public void loadImage(final ImageView imageView, File file, int placeholderResId, Callback callback) {
        imageBridge.loadImage(imageView, file, placeholderResId, callback);


    }

    public void loadImage(final ImageView imageView, File file, int placeholderResId, int width, int height,
                          boolean isNeedCut, Callback callback) {

    }

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId, int width, int height,
                          boolean isNeedCut, Callback callback) {}

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId,
                          Callback callback) {
        imageBridge.loadImage(imageView, filePathOrUrl, placeholderResId);
    }

    protected void cancelRequest(ImageView view){
        imageBridge.cancelRequest(view);
    }

    public void clearCache(ImageView view){
        imageBridge.cancelRequest(view);
    }


    protected void clearMemoryCache(String url, ImageView image) {
        imageBridge.clearMemoryCache(url, image);
    }

    @Deprecated
    public void removeMemoryCache(String key) {
        // TODO nothing 兼容之前，之后测试正常后删除
        imageBridge.removeMemoryCache(key);
    }

    /**
     * Invalidate all memory cached images for the specified {@code path}. You can also pass a
     * {@linkplain RequestCreator#stableKey stable key}.
     */

    protected void clearCache(String path) {
        imageBridge.clearCache(null, null, path);
    }

    /**
     * Invalidate all memory cached images for the specified {@code uri}.
     */
    protected void clearCache(Uri uri) {
        imageBridge.clearCache(uri);
    }



}
