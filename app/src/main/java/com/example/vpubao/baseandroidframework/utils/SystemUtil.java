package com.example.vpubao.baseandroidframework.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by DeepRolling on 2017/10/25.
 */

public class SystemUtil {

    private static SystemUtil systemUtil;

    public static SystemUtil getInstance() {
        if (systemUtil == null) {
            systemUtil = new SystemUtil();
        }
        return systemUtil;
    }

    public boolean isPad(Context context) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() == null) {
            return (contextWeakReference.get().getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } else {
            return (context.getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        }
    }


    public boolean isDebug(Context context) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() == null) {
            return (contextWeakReference.get().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } else {
            return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }


    public long getUseMemory(Context context) {
        long MEM_UNUSED;
        // 得到ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 创建ActivityManager.MemoryInfo对象
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // 取得剩余的内存空间
        MEM_UNUSED = mi.availMem / 1048576;
        return MEM_UNUSED;
    }

    // 获得总内存
    public long getAllMemory() {
        long mTotal;
        // /proc/meminfo读出的内核信息进行解释
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        // 截取字符串信息
        content = content.substring(begin + 1, end).trim();
        mTotal = Integer.parseInt(content);
        return mTotal;
    }

}
