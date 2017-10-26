package com.example.vpubao.baseandroidframework.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 此类用于网络方面的相关操作（如判断网络是否连接等）
 */
public class NetUtils{

	public static boolean isConnected(Context context)
	{

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity)
		{

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected())
			{
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					return true;
				}
			}
		}
		return false;
	}


	public static boolean isWifi(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}


	public static void openSetting(Activity activity)
	{
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}

}
