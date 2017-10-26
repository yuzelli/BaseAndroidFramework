package com.example.vpubao.baseandroidframework.utils;


import com.example.vpubao.baseandroidframework.base.App;
import com.example.vpubao.baseandroidframework.widget.customview.BaiduLoading;

import rx.Subscriber;

/**
 * Created by a on 2016/5/6.
 */
public abstract class RxSubscriber<T> extends Subscriber<T>{
    @Override
    public void onCompleted() {
        //ProgressDialogUtil.dismiss();
        BaiduLoading.onStopDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        BaiduLoading.onStopDialog();
        _onError(e.getMessage());
       // ProgressDialogUtil.dismiss();
        if (!NetUtils.isConnected(App.getAppContext())) {
//            MYToast.show("请求失败，请检查网络!");
            return;
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
