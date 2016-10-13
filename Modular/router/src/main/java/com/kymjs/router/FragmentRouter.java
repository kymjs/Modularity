package com.kymjs.router;

import android.support.v4.app.Fragment;

/**
 * Created by ZhangTao on 10/12/16.
 */

public class FragmentRouter {

    public static Fragment getFragment(String name) {
        Fragment fragment;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fragment;
    }
}
