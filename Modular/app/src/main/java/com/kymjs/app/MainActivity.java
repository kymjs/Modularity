package com.kymjs.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.kymjs.app.base_res.utils.AnimationUtil;
import com.kymjs.router.ActivityRouter;
import com.kymjs.router.FragmentRouter;
import com.kymjs.router.RouterList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main,
                        FragmentRouter.getFragment(RouterList.FRAGMENT_MEMORYBOX_MAIN))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            ActivityRouter.startActivity(this, RouterList.ACTIVITY_MEMORYBOX_MAIN);
        } else if (id == R.id.nav_send) {
            throw new RuntimeException("throw exception");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        TextView view = (TextView) findViewById(com.kymjs.app.memory.R.id.titlebar_text_exittip);
        view.setVisibility(View.VISIBLE);
        Animation a = AnimationUtil.getTranslateAnimation(0f, 0f, -getToolbar().getHeight(), 0f, 300);
        view.startAnimation(a);
    }

    /**
     * 取消退出
     */
    public void cancleExit() {
        final TextView view = (TextView) findViewById(com.kymjs.app.memory.R.id.titlebar_text_exittip);
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
