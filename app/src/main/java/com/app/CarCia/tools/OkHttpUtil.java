package com.app.CarCia.tools;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.app.CarCia.impl.JsonType;
import com.app.CarCia.impl.OkHttpResponseListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChanZeeBm on 2015/12/3.
 */
public class OkHttpUtil<T> implements Callback {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpResponseListener<T> okHttpResponseListener;
    private Class<T> clz;

    private final static int OBJECT = 0x001;
    private final static int ARRAY = 0x002;

    static {
        //初始化超时时间 15s
        okHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
    }

    public OkHttpUtil GET(String url) {
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(this);
        return this;
    }

    public OkHttpUtil POST(String url, RequestBody requestBody) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(this);
        return this;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        AppTools.dismissLoadingDialog();
        e.printStackTrace();
        if (okHttpResponseListener != null) {
            okHttpResponseListener.onError("ErrorCode:" + request.toString());
        }
    }

    @Override
    public void onResponse(Response response) throws IOException {
        AppTools.dismissLoadingDialog();
        if (response.isSuccessful()) {
            String jsonStr = response.body().string();
            String json = null;
            if (!jsonStr.substring(0, 1).equals("{") || !jsonStr.substring(0, 1).equals("[")) {
                for (int i = 0; i < jsonStr.length(); i++) {
                    if (jsonStr.substring(i, i + 1).equals("{") || jsonStr.substring(i, i + 1)
                            .equals
                                    ("[")) {
                        json = jsonStr.substring(i, jsonStr.length());
                        break;
                    }
                }
            }
            LogTools.e(jsonStr);
            LogTools.json(json != null ? json : jsonStr);
            parseJson(json != null ? json : jsonStr);
        } else {
            if (okHttpResponseListener != null) {
                okHttpResponseListener.onError(response.toString());
            }
        }
    }

    private void parseJson(String jsonStr) {
        Message msg = Message.obtain();
        if (okHttpResponseListener != null) {
            JsonType type = type(jsonStr);
            if (type != null)
                switch (type) {
                    case JSON_OBJECT:
                        try {
                            T t = new Gson().fromJson(jsonStr, clz);
                            msg.obj = t;
                            msg.what = OBJECT;
                            handler.sendMessage(msg);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        break;
                    case JSON_ARRAY:
                        try {
                            List<T> tList = new Gson().fromJson(jsonStr, new TypeToken<List<T>>() {
                            }.getType());
                            msg.obj = tList;
                            msg.what = ARRAY;
                            handler.sendMessage(msg);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        throw new IllegalArgumentException("response not jsonObject either " +
                                "JsonArray");
                }
        }
    }


    public OkHttpUtil setClz(Class clz) {
        this.clz = (Class<T>) clz;
        return this;
    }

    private JsonType type(String response) {
        String charFirst = response.substring(0, 1);
        if (TextUtils.equals("{", charFirst)) {
            return JsonType.JSON_OBJECT;
        } else if (TextUtils.equals("[", charFirst)) {
            return JsonType.JSON_ARRAY;
        }
        return null;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OBJECT:
                    T t = (T) msg.obj;
                    okHttpResponseListener.onJsonObjectResponse(t);
                    break;
                case ARRAY:
                    List<T> list = (List<T>) msg.obj;
                    okHttpResponseListener.onJsonArrayResponse(list);
                    break;
            }
        }
    };

    public void setOkHttpResponseListener(OkHttpResponseListener<T> okHttpResponseListener) {
        this.okHttpResponseListener = okHttpResponseListener;
    }
}
