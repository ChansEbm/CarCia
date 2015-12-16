package com.app.CarCia.ui.Activity;

import android.support.v7.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
//    private VideoLayout videoLayout;
//    private SurfaceView surfaceView;
//    private int m_iLogID = -1;                // return by NET_DVR_Login_v30
//    private int m_iPlayID = -1;                // return by NET_DVR_RealPlay_V30
//
//    private int m_iPort = -1;                // play port
//    private int m_iStartChan = 0;                // start channel no
//    private int m_iChanNum = 0;                //channel number
//    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        videoLayout = (VideoLayout) viewDataBinding;
//    }
//
//    @Override
//    protected void initViews() {
//        surfaceView = videoLayout.surface;
//        initeSdk();
//    }
//
//    @Override
//    protected void initEvents() {
//        surfaceView.getHolder().addCallback(this);
//        initLogin();
//        play();
//    }
//
//    private void play() {
//        if (m_iPlayID < 0) {
//            startSinglePreview();
//        }
//    }
//
//    private void startSinglePreview() {
//
//        RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
//        if (fRealDataCallBack == null) {
//            return;
//        }
//
//        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
//        previewInfo.lChannel = m_iStartChan;
//        previewInfo.dwStreamType = 1; //substream
//        previewInfo.bBlocked = 1;
//        // HCNetSDK start preview
//        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID, previewInfo,
//                fRealDataCallBack);
//        if (m_iPlayID < 0) {
//            return;
//        }
//
//        LogTools.i("NetSdk Play sucess ***********************3***************************");
//
//    }
//
//    private RealPlayCallBack getRealPlayerCbf() {
//        RealPlayCallBack cbf = new RealPlayCallBack() {
//            public void fRealDataCallBack(int iRealHandle, int iDataType, byte[] pDataBuffer, int
//                    iDataSize) {
//                // player channel 1
//                VideoActivity.this.processRealData(1, iDataType, pDataBuffer, iDataSize, Player
//                        .STREAM_REALTIME);
//            }
//        };
//        return cbf;
//    }
//
//    public void processRealData(int iPlayViewNo, int iDataType, byte[] pDataBuffer, int
//            iDataSize, int iStreamMode) {
//
//        if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
//            if (m_iPort >= 0) {
//                return;
//            }
//            m_iPort = Player.getInstance().getPort();
//            if (m_iPort == -1) {
//                return;
//            }
//            LogTools.i("getPort succ with: " + m_iPort);
//            if (iDataSize > 0) {
//                if (!Player.getInstance().setStreamOpenMode(m_iPort, iStreamMode))  //set stream
//                // mode
//                {
//                    LogTools.e("setStreamOpenMode failed");
//                    return;
//                }
//                if (!Player.getInstance().openStream(m_iPort, pDataBuffer, iDataSize, 2 * 1024 *
//                        1024)) //open stream
//                {
//                    LogTools.e("openStream failed");
//                    return;
//                }
//                if (!Player.getInstance().play(m_iPort, surfaceView.getHolder())) {
//                    LogTools.e("play failed");
//                    return;
//                }
//                if (!Player.getInstance().playSound(m_iPort)) {
//                    LogTools.e("playSound failed with error code:" + Player.getInstance()
//                            .getLastError(m_iPort));
//                    return;
//                }
//            }
//        } else {
//            if (!Player.getInstance().inputData(m_iPort, pDataBuffer, iDataSize)) {
//                LogTools.e(Player.getInstance().getLastError(m_iPort));
//            }
//
//        }
//
//    }
//
//    private void initLogin() {
//        try {
//            if (m_iLogID < 0) {
//                // login on the device
//                m_iLogID = loginDevice();
//                if (m_iLogID < 0) {
//                    return;
//                }
//                // get instance of exception callback and set
//                ExceptionCallBack oexceptionCbf = getExceptiongCbf();
//                if (oexceptionCbf == null) {
//                    return;
//                }
//
//                if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(oexceptionCbf)) {
//                    return;
//                }
//                LogTools.i("log success");
//            } else {
//                // whether we have logout
//                if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
//                    return;
//                }
//                m_iLogID = -1;
//            }
//        } catch (Exception err) {
//        }
//
//    }
//
//    public void cleanup() {
//        // release player resource
//
//        Player.getInstance().freePort(m_iPort);
//        m_iPort = -1;
//
//        // release net SDK resource
//        HCNetSDK.getInstance().NET_DVR_Cleanup();
//    }
//
//    private void stopSinglePreview() {
//        if (m_iPlayID < 0) {
//            return;
//        }
//
//        //  net sdk stop preview
//        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
//            return;
//        }
//
//        m_iPlayID = -1;
//        stopSinglePlayer();
//    }
//
//    private void stopSinglePlayer() {
//        Player.getInstance().stopSound();
//        // player stop play
//        if (!Player.getInstance().stop(m_iPort)) {
//            return;
//        }
//
//        if (!Player.getInstance().closeStream(m_iPort)) {
//            return;
//        }
//        if (!Player.getInstance().freePort(m_iPort)) {
//            return;
//        }
//        m_iPort = -1;
//    }
//
//    private ExceptionCallBack getExceptiongCbf() {
//        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
//            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
//                System.out.println("recv exception, type:" + iType);
//            }
//        };
//        return oExceptionCbf;
//    }
//
//    private int loginDevice() {
//        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
//        if (null == m_oNetDvrDeviceInfoV30) {
//            return -1;
//        }
//        String strIP = "58.252.110.19";
//        int nPort = 8003;
//        String strUser = "carcia1";
//        String strPsd = "carcia11";
//        // call NET_DVR_Login_v30 to login on, port 8000 as default
//        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd,
//                m_oNetDvrDeviceInfoV30);
//        if (iLogID < 0) {
//            return -1;
//        }
//        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
//            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
//            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
//        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
//            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
//            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum + m_oNetDvrDeviceInfoV30
//                    .byHighDChanNum * 256;
//        }
//        return iLogID;
//    }
//
//    private boolean initeSdk() {
//        //init net sdk
//        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
//            LogTools.e("HCNetSDK init is failed!");
//            return false;
//        }
//        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, AppTools.getVideoCacheDir(), true);
//        return true;
//    }
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_video;
//    }
//
//    @Override
//    protected void onClick(int id, View view) {
//
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        if (-1 == m_iPort) {
//            return;
//        }
//        Surface surface = holder.getSurface();
//        if (true == surface.isValid()) {
//            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, holder)) {
//                LogTools.e("Player setVideoWindow failed!");
//            }
//        }
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (-1 == m_iPort) {
//            return;
//        }
//        if (true == holder.getSurface().isValid()) {
//            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
//                LogTools.e("Player setVideoWindow failed!");
//            }
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        stopSinglePreview();
//        cleanup();
//    }
}
