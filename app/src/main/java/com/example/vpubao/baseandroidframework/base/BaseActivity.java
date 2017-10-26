package com.example.vpubao.baseandroidframework.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bugtags.library.Bugtags;
import com.example.vpubao.baseandroidframework.manager.ActivityStackManager;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author DeepCoding 2017.10.25
 * @version 1.0
 */
public abstract class BaseActivity extends RxFragmentActivity {

    Unbinder mUnbinder;

    protected Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        // 隐藏软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // 隐藏actionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityStackManager.getInstance().addActivity(this);

        View inflate = View.inflate(this, getLayoutId(), null);
        setContentView(inflate);
        initView(inflate);
        initListenter(inflate);
        mUnbinder = ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
		Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
		Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //bugtags记录按键操作步骤
		Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().finishActivity(this);
        mUnbinder.unbind();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initListenter(View view);

    protected abstract void initData();

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 1000) {
            exitTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getInstance().AppExit(this);
            System.exit(0);
        }
    }



}
