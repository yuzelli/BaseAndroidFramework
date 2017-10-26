package com.example.vpubao.baseandroidframework.utils;


import com.example.vpubao.baseandroidframework.widget.customview.BaiduLoading;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * Created by a on 2016/5/6.
 */
public class RxHelper<T> {
    //子线程运行，主线程回调
    public Observable.Transformer<T, T> io_main(final RxFragmentActivity context) {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {

                Observable<T> tObservable1 = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                //   ProgressDialogUtil.showProgress(context, "正在加载，请稍候");
                                BaiduLoading.onBeiginDialog(context);
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.bindUntilEvent(context.lifecycle(), ActivityEvent.STOP));

                return tObservable1;

            }
        };
    }

    //子线程运行，主线程回调
    public Observable.Transformer<T, T> io_main_no(final RxFragmentActivity context) {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {

                Observable<T> tObservable1 = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
//                                ProgressDialogUtil.showProgress(context, "正在加载，请稍候");
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.bindUntilEvent(context.lifecycle(), ActivityEvent.STOP));

                return tObservable1;

            }
        };
    }

}


