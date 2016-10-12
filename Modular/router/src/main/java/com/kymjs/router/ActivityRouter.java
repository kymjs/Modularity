package com.kymjs.router;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ZhangTao on 10/12/16.
 */

public class ActivityRouter {

    public static void startActivity(Context context, String action) {
        context.startActivity(new Intent(action));
    }
}
