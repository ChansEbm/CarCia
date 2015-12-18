package com.app.CarCia.tools;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.impl.OkHttpResponseListener;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ChanZeeBm on 12/3/2015.
 */
public class OkHttpBuilder {

    /**
     * Get Action
     */
    public static class GET {
        private String fullUrl = "";
        private Map<String, String> params = null;
        private Class entityClass;

        public GET url(String url) {
            this.fullUrl = AppKeyMap.HEAD_URL + url;
            return this;
        }

        public GET url1(String url) {
            this.fullUrl = AppKeyMap.HEAD_URL1 + url;
            return this;
        }

        public GET params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public GET entityClass(Class entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public void enqueue(@NonNull FragmentActivity fragmentActivity, OkHttpResponseListener
                okHttpResponseListener) {
            AppTools.showLoadingDialog(fragmentActivity);
            if (TextUtils.isEmpty(fullUrl) || entityClass == null)
                throw new NullPointerException("url is unavailable or not invoke entity");
            if (params != null) {
                fullUrl = attachHttpGetParam(fullUrl, params);
            }
            new OkHttpUtil().setClz(entityClass).GET(fullUrl).setOkHttpResponseListener
                    (okHttpResponseListener);
        }

    }

    /**
     * Post Action
     */
    public static class POST {
        private String fullUrl = "";
        private Map<String, String> params = null;
        private Class entityClass;
        private RequestBody requestBody = null;
        private MediaType mediaType = getContentType(com.app.CarCia.impl.MediaType.OCT);

        public POST url(String url) {
            this.fullUrl = AppKeyMap.HEAD_URL + url;
            return this;
        }

        public POST url1(String url) {
            this.fullUrl = AppKeyMap.HEAD_URL1 + url;
            return this;
        }

        public POST params(Map<String, String> params) {
            if (params == null)
                throw new NullPointerException("params is null,plz check");
            FormEncodingBuilder builder = new FormEncodingBuilder();
            Set<String> keys = params.keySet();//把参数添加到构建体
            for (String key : keys) {
                builder.add(key, params.get(key));
            }
            requestBody = builder.build();
            return this;
        }

        /**
         * 构造构建体(只能允许添加某1种类型的文件,同时最好设置MediaType)
         *
         * @param params  参数
         * @param files   要添加的文件路径
         * @param fileKey 跟后台约定的文件List的key
         * @return this
         * @see #mediaType(com.app.CarCia.impl.MediaType)
         */
        public POST params(Map<String, String> params, List<String> files, String fileKey) {
            MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
            Set<String> keys = params.keySet();//把参数添加到构建体
            for (String key : keys) {
                multipartBuilder.addFormDataPart(key, params.get(key));
            }
            for (String path : files) {
                multipartBuilder.addFormDataPart(fileKey, null, RequestBody.create(mediaType, new
                        File(path)));
            }
            requestBody = multipartBuilder.build();
            return this;
        }


        /**
         * 添加多文件构建体
         *
         * @param params   普通参数
         * @param files    不同文件类型的List
         * @param fileKeys 文件类型的key
         * @return this
         */
        public POST params(Map<String, String> params, @NonNull List<List<String>> files,
                           @NonNull String...
                                   fileKeys) {
            MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
            Set<String> keys = params.keySet();//把参数添加到构建体
            mediaType = MediaType.parse(AppKeyMap.CONTENT_OCT);
            for (String key : keys) {
                builder.addFormDataPart(key, params.get(key));
            }
            for (int i = 0; i < fileKeys.length; i++) {//循环key
                List<String> list = files.get(i);//根据key获得要添加的文件
                for (String filePath : list) {//遍历文件
                    int suffixIndex = filePath.lastIndexOf(".");
                    String fileSuffix = filePath.substring(suffixIndex, filePath.length());

                    LogTools.i("filePath:" + filePath);
                    LogTools.i("fileSuffix:" + fileSuffix);
                    builder.addFormDataPart(fileKeys[i], "i" + fileSuffix, RequestBody
                            .create(mediaType,
                                    new File(filePath)));//以添加的key为后台key,批量添加文件到后台
                }
            }
            requestBody = builder.build();
            return this;
        }


        public POST entityClass(Class entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public void enqueue(@NonNull FragmentActivity fragmentActivity, OkHttpResponseListener
                okHttpResponseListener) {
            AppTools.showLoadingDialog(fragmentActivity);
            LogTools.i(AppTools.loadingDialog.isShowing());
            if (TextUtils.isEmpty(fullUrl) || requestBody == null || entityClass == null) {
                throw new NullPointerException("url or map is unavailable, or not invoke entity");
            }
            new OkHttpUtil().setClz(entityClass).POST(fullUrl, requestBody)
                    .setOkHttpResponseListener
                            (okHttpResponseListener);
        }

        public POST mediaType(com.app.CarCia.impl.MediaType contentMediaType) {
            mediaType = getContentType(contentMediaType);
            return this;
        }
    }

    private static String attachHttpGetParam(String fullUrl, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(fullUrl);
        for (String key : params.keySet()) {
            stringBuilder.append("&").append(key).append("=").append(params.get(key
            ));
        }
        return stringBuilder.toString();
    }

    private static MediaType getContentType(com.app.CarCia.impl.MediaType mediaType) {
        switch (mediaType) {
            case JPG:
                return MediaType.parse(AppKeyMap.CONTENT_JPG);
            case MP3:
                return MediaType.parse(AppKeyMap.CONTENT_MP3);
            case OCT:
                return MediaType.parse(AppKeyMap.CONTENT_OCT);
            case PNG:
                return MediaType.parse(AppKeyMap.CONTENT_PNG);
            case TXT:
                return MediaType.parse(AppKeyMap.CONTENT_TXT);
            default:
                return MediaType.parse(AppKeyMap.CONTENT_OCT);
        }
    }

}
