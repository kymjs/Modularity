package debug;

import android.app.Application;

import com.kymjs.crash.CustomActivityOnCrash;
import com.kymjs.app.explorer.BuildConfig;
import com.kymjs.router.App;
import com.kymjs.router.LogUtils;

/**
 * Created by ZhangTao on 10/12/16.
 */
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setEnable(BuildConfig.DEBUG);
        CustomActivityOnCrash.install(App.INSTANCE);
    }
}
