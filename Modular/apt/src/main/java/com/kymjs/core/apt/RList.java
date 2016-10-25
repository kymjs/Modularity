package com.kymjs.core.apt;

/**
 * Created by ZhangTao on 10/24/16.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface RList {
    String name();
}
