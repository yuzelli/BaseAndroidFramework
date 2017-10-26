package com.example.vpubao.baseandroidframework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title：SAFEYE@
 * @Description：
 * @date 2015-4-14 下午1:17:47
 */

public abstract class BaseFragment extends RxFragment {


    private View rootView;
    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
            initListenter(rootView);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initListenter(View view);

    protected abstract void initData();


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            this.onTransformPause();
        else
            this.onTransformResume();
    }

    /**
     * fragment隐藏时回调
     */
    protected void onTransformPause() {}

    /**
     * fragment显示时回调
     */
    protected void onTransformResume() {

    }


}
