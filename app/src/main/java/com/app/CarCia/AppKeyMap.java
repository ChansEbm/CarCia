package com.app.CarCia;

import com.app.CarCia.tools.AppTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public class AppKeyMap {
    //手机正则
    public final static String PHONE_REGEX = "[1][3578]\\d{9}";
    //身份证正则
    public final static String CITIZEN_ID_REGEX = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
    //Action Key
    public final static String ACTION_KEY = "ACTION_KEY";

    public final static String ACTION_JUMP = "android.action.jump";
    public final static String ACTION_INTO_BRAND = "android.action.into.brand";

    //默认动画执行时间 300ms
    public final static int DEFAULT_DURATION = 300;

    public final static int FROYO = 0x0000001f;
    public final static int GINGERBREAD = 0x0000002f;

    public final static String HEAD_URL = "http://diy.appbaba.com/garcia/api.php?op=app&type=";
    public final static String HEAD_URL1 = "http://st.zhundu" +
            ".cc/users/garcianetcn/api/garcia_app/Order/";

    public final static String CONTENT_JPG = "image/jpg";
    public final static String CONTENT_PNG = "image/png";
    public final static String CONTENT_TXT = "text/plain";
    public final static String CONTENT_MP3 = "audio/mp3";
    public final static String CONTENT_OCT = "application/octet-stream";

    public static String UserId = "";

}
