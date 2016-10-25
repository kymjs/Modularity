package com.kymjs.app.memory.module.main;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.AnimationUtil;
import com.kymjs.app.memory.BuildConfig;
import com.kymjs.app.memory.R;
import com.kymjs.router.FragmentRouter;
import com.kymjs.router.RouterList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseres_activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.baseres_colorPrimary));
        }

        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.baseres_content_main,
                        FragmentRouter.getFragment(RouterList.MEMORY_FRAG_MAIN))
                .commit();
    }

    protected Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.baseres_toolbar);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (BuildConfig.ISAPP) {
            return handleExitTip(keyCode);
        }
        return super.onKeyDown(keyCode, event);
    }

    /////////////////////////// show exit tip ////////////////////////////////////

    private boolean isOnKeyBacking;
    private Handler mMainLoopHandler = new Handler(Looper.getMainLooper());
    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            cancleExit();
        }
    };

    private boolean handleExitTip(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isOnKeyBacking) {
                mMainLoopHandler.removeCallbacks(onBackTimeRunnable);
                isOnKeyBacking = false;
                finish();
            } else {
                isOnKeyBacking = true;
                showExitTip();
                mMainLoopHandler.postDelayed(onBackTimeRunnable, 2000);
            }
            return true;
        }
        return false;
    }

    /**
     * 显示Toolbar的退出tip
     */
    public void showExitTip() {
        TextView view = (TextView) findViewById(R.id.titlebar_text_exittip);
        view.setVisibility(View.VISIBLE);
        Animation a = AnimationUtil.getTranslateAnimation(0f, 0f, -getToolbar().getHeight(), 0f, 300);
        view.startAnimation(a);
    }

    /**
     * 取消退出
     */
    public void cancleExit() {
        final TextView view = (TextView) findViewById(R.id.titlebar_text_exittip);
        Animation a = AnimationUtil.getTranslateAnimation(0f, 0f, 0f, -getToolbar().getHeight(), 300);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(a);
    }
}
