package com.pinganfang.imagelibrary.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.Map;

/**
 * 图片基础加载库和处理库
 *
 * Created by LIUYONGKUI726 on 2016-08-25.
 */
public class PaImageLoader {

    private static Context mContext;


    public PaImageLoader() {
    }

    /**
     * init imaglaoder
     * init
     * @param aContext
     */
    public static void initialize(Context aContext) {
        initialize(aContext, 0.1f);
    }

    /**
     * init imaglaoder
     * @param context
     * @param size
     */
    public static void initialize(Context context, float size) {
        mContext = context;
        PaImagePresenter.getInstance(context).init(context, size);
    }

    public void fetchImage(String filePathOrUrl, Target target) {
      PaImagePresenter.getInstance(mContext).fetchImage(filePathOrUrl, target);
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
    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId,
                          Callback callback) {
        PaImagePresenter.getInstance(mContext).loadImage(imageView, filePathOrUrl, placeholderResId, callback);

    }

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId,
                          int width, int height) {
        loadImage(imageView, filePathOrUrl, placeholderResId, width, height, false);
    }

    public void loadImage(final ImageView imageView, String filePathOrUrl,
                          int placeholderResId, int width, int height, boolean isNeedCut) {
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
    public void loadImage(final ImageView imageView, File file, int placeholderResId, Callback callback) {

        PaImagePresenter.getInstance(mContext).loadImage(imageView, file, placeholderResId, callback);
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
     * Note: The Callback param is a strong reference and will prevent your Activity
     * or Fragment from being garbage collected.
     * If you use this method, it is strongly recommended you invoke an adjacent
     * Picasso.cancelRequest(android.widget.ImageView) call to prevent temporary leaking.
     *
     * @param imageView  Asynchronously fulfills the request into the specified ImageView.
     * @param resourceId Start an image request using the specified drawable resource ID.
     * @param callback
     */
    public void loadImage(final ImageView imageView, int resourceId, Callback callback) {
        PaImagePresenter.getInstance(mContext).loadImage(imageView, resourceId, callback);
    }

    public void loadImage(final ImageView imageView, File file, int placeholderResId, int width,
                          int height, boolean isNeedCut, Callback callback) {
        PaImagePresenter.getInstance(mContext).loadImage(imageView, file, placeholderResId, width, height,
         isNeedCut, callback);
    }

    public void loadImage(final ImageView imageView, String filePathOrUrl, int placeholderResId,
                          int width, int height, boolean isNeedCut, Callback callback) {
        PaImagePresenter.getInstance(mContext).loadImage(imageView, filePathOrUrl, placeholderResId,
                width, height, isNeedCut, callback);
    }
    /**
     * cancelRequest
     * @param view
     */
    public void cancelRequest(ImageView view) {
        PaImagePresenter.getInstance(mContext).cancelRequest(view);
    }

    @Deprecated
    public void clearMemoryCache(ImageView view) {
        // TODO nothing 兼容之前，之后测试正常后删除
        PaImagePresenter.getInstance(mContext).clearCache(view);

    }

    @Deprecated
    public void clearMemoryCache() {
        // TODO nothing 兼容之前，之后测试正常后删除
       clearMemoryCache(null);
    }

    /**
     * / key只能以文件路径或url为主 其他自定义无发兼容以前版本
     * @param key
     * @return
     */
    @Deprecated
    public Bitmap removeMemoryCache(String key) {
        // TODO nothing 兼容之前，之后测试正常后删除
        PaImagePresenter.getInstance(mContext).removeMemoryCache(key);
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
