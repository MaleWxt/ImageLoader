package com.pinganfang.imagelibrary.core;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.pinganfang.imagelibrary.util.NullUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.Map;

/**
 * 图片加载框架桥接桥梁，为后面快速实现替换更新第三方框架而实现
 * Created by LIUYONGKUI726 on 2016-08-25.
 */
public abstract class ImageBridge {

    abstract void init();

    abstract void loadImage(final ImageView imageView, RequestCreator requestCreator,
              int placeholderResId, Callback callback);

    abstract void loadImage(final ImageView imageView, int resourceId, Callback callback);


    public abstract void fetchImage(String filePathOrUrl, Target target);

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
     * Note: The Callback param is a strong reference and will prevent your Activity or Fragment
     * from being garbage collected.
     * If you use this method, it is strongly recommended you invoke an adjacent P
     * icasso.cancelRequest(android.widget.ImageView) call to prevent temporary leaking.
     *
     * @param imageView        Asynchronously fulfills the request into the specified ImageView.
     * @param filePathOrUrl    Start an image request using the specified path.
     * @param placeholderResId A placeholder drawable to be used while the image is being loaded.
     * @param callback
     * by liz 加了一个 filePathOrUrl为空的判断
     */
    public abstract void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId,
                          Callback callback);



    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId,
                          int width, int height) {
        loadImage(imageView, filePathOrUrl, placeholderResId, width, height, false);
    }

    public void loadImage(final ImageView imageView, String filePathOrUrl,
                          int placeholderResId, int width, int height, boolean isNeedCut) {
        loadImage(imageView, filePathOrUrl, null, placeholderResId, width, height, isNeedCut, null);
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
     * Note: The Callback param is a strong reference and will prevent your Activity or Fragment
     * from being garbage collected.
     * If you use this method, it is strongly recommended you invoke an adjacent
     * Picasso.cancelRequest(android.widget.ImageView) call to prevent temporary leaking.
     *
     * @param imageView        Asynchronously fulfills the request into the specified ImageView.
     * @param file             Start an image request using the specified image file.
     * @param placeholderResId A placeholder drawable to be used while the image is being loaded.
     * @param callback
     */
    abstract void loadImage(final ImageView imageView, File file, int placeholderResId, Callback callback);

    /**
     * 通过资源文件加载图片
     *
     * @param imageView  Asynchronously fulfills the request into the specified ImageView.
     * @param resourceId Start an image request using the specified drawable resource ID.
     */
    public void loadImage(final ImageView imageView, int resourceId) {
        loadImage(imageView, resourceId, null);
    }

    public void loadImage(final ImageView imageView, File file, int placeholderResId, int width,
                          int height, boolean isNeedCut, Callback callback) {
        loadImage(imageView, null, file, placeholderResId, width, height, isNeedCut, callback);
    }

    abstract void loadImage(final ImageView imageView, String filePathOrUrl, File file ,int placeholderResId,
                          int width, int height, boolean isNeedCut, Callback callback);

    abstract void loadImage(final ImageView imageView, RequestCreator requestCreator, int placeholderResId,
                          int width, int height, boolean isNeedCut, Callback callback);


    /**
     * cancelRequest
     * @param image
     */
    abstract void cancelRequest(ImageView image);

    abstract void clearMemoryCache(String url, ImageView image);

    /**
     * Invalidate all memory cached images for the specified {@code path}. You can also pass a
     * {@linkplain RequestCreator#stableKey stable key}.
     */

    protected void clearCache(String path) {
        clearCache(null, null, path);
    }

    /**
     * Invalidate all memory cached images for the specified {@code uri}.
     */
    protected void clearCache(Uri uri) {
        clearCache(uri, null, null);

    }

    /**
     * Invalidate all memory cached images for the specified {@code uri}.
     */
    protected void clearCache(File file) {
        clearCache(null, file, null);
    }

    abstract void clearCache(Uri uri, File file, String path);

    @Deprecated
    public Bitmap removeMemoryCache(String key) {
        // TODO nothing 兼容之前，之后测试正常后删除
        // key 只能以文件路径或url为主 其他自定义无发兼容以前版本
        clearCache(key);
        return null;
    }

    @Deprecated
    public Map<String, Bitmap> snapshot() {
        // TODO nothing 兼容之前，之后测试正常后删除
        return null;
    }

    @Deprecated
    public void setMemCacheSize(int maxSize) {
        // TODO nothing 兼容之前，之后测试正常后删除
    }

    @Deprecated
    public void cancleTask() {
        // TODO nothing 兼容之前，之后测试正常后删除
    }
    @Deprecated
    public int getMemCacheSize() {
        // TODO nothing 兼容之前，之后测试正常后删除
        return 0;
    }


}
