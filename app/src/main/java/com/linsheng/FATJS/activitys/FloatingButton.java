package com.linsheng.FATJS.activitys;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.linsheng.FATJS.AccUtils;
import com.linsheng.FATJS.bean.Variable;
import com.linsheng.FATJS.rpa.wechatSendMsg.WechatSendMsgService;
import com.linsheng.FATJS.utils.ExitException;

import java.util.List;

public class FloatingButton extends Service {
    private static final String TAG = "FATJS";

    private WindowManager wm;
    private LinearLayout ll;

    private int offset_y = -730;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 定义面板
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Variable.btnTextView = new TextView(Variable.context);
        ll = new LinearLayout(Variable.context);

        ViewGroup.LayoutParams txtParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Variable.btnTextView.setText("打开");
        Variable.btnTextView.setTextSize(13);
        Variable.btnTextView.setTextColor(Color.argb(200,10,250,0));
        Variable.btnTextView.setPadding(5,2,5,5);
        Variable.btnTextView.setLayoutParams(txtParameters);

        // LinearLayout 容器
        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(180,0,0,0));
        ll.setPadding(5, 2, 5, 5);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(llParameters);

        // 设置面板
        WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(90, 60, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x = 20;
        parameters.y = offset_y;
        parameters.gravity = Gravity.LEFT | Gravity.CENTER;
        parameters.setTitle("FATJS");

        // 添加元素到面板
        ll.addView(Variable.btnTextView);
        wm.addView(ll, parameters);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 找色测试
//                findColorTest();


                // 点击处理
                btnClick();
            }
        });
    }

    private void btnClick() {
        if ("打开".contentEquals(Variable.btnTextView.getText())) {
            Log.i(TAG, "onClick: 打开 --> 全屏");
            Variable.btnTextView.setText("全屏");

            // 展开悬浮窗
            Intent intent = new Intent();
            intent.setAction("com.msg");
            intent.putExtra("msg", "show_max");
            Variable.context.sendBroadcast(intent);
        }else if("隐藏".contentEquals(Variable.btnTextView.getText())){
            Log.i(TAG, "onClick: 隐藏 --> 打开");
            Variable.btnTextView.setText("打开");

            // 隐藏悬浮窗
            Intent intent = new Intent();
            intent.setAction("com.msg");
            intent.putExtra("msg", "hide_mini");
            Variable.context.sendBroadcast(intent);
        }else if("全屏".contentEquals(Variable.btnTextView.getText())) {
            Log.i(TAG, "onClick: 全屏 --> 隐藏");
            Variable.btnTextView.setText("隐藏");

            // 隐藏悬浮窗
            Intent intent = new Intent();
            intent.setAction("com.msg");
            intent.putExtra("msg", "full_screen");
            Variable.context.sendBroadcast(intent);
        }
    }

    private void findColorTest() {
        // 移动悬浮窗
        try {
            AccUtils.moveFloatWindow("打开");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        List<String> allText = AccUtils.findAllText();
//        if (allText != null) {
//            try {
//                AccUtils.printLogMsg("allText => " + allText);
//            } catch (ExitException e) {
//                e.printStackTrace();
//            }
//        }

//        try {
//            WechatSendMsgService.readAddressBooks();
//        } catch (ExitException e) {
//            e.printStackTrace();
//        }

//        int[] color = ScreenLib.findColor( 0xfe2b54, "23|0|0xfe2b54,23|9|0xfe2b54,7|9|0xfe2b54,7|21|0xfe2b54,25|21|0xfe2b54,17|29|0xfe2b54,17|38|0xfe2b54", 90, 668, 210, 1066, 2218);
//        if (color != null) {
//            Log.i(TAG, "onClick: colorPicker => " + Arrays.toString(color));
//        }
    }
}