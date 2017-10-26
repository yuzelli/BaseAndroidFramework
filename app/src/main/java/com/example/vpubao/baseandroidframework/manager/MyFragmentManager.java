package com.example.vpubao.baseandroidframework.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by DeepRolling on 2017/10/25.
 */

public class MyFragmentManager {
    private static MyFragmentManager myFragmentManager;

    public static MyFragmentManager getInstance() {
        if (myFragmentManager == null) {
            myFragmentManager = new MyFragmentManager();
        }
        return myFragmentManager;
    }


    public void switchFragment(Fragment from, Fragment to,
                                         boolean ischildFragment, int layoutId) {
        FragmentManager myFragmentManager;
        if (ischildFragment) {
            myFragmentManager = from.getChildFragmentManager();
        } else {
            myFragmentManager = from.getFragmentManager();
        }
        if (myFragmentManager == null || layoutId <= 0 || from == to || to == null || from == null) {
            return;
        }
        FragmentTransaction transaction = myFragmentManager.beginTransaction();
        transaction.hide(from);
        if (!to.isAdded()) {
            transaction.add(layoutId, to, to.getClass().getSimpleName());
        } else {
            transaction.show(to);
        }
        transaction.commit();
    }

}
