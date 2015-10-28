package com.ism.login;

import android.content.Context;
import android.content.Intent;

/**
 * Created by c162 on 07/10/15.
 */
public class Global {

    static Context context;

    public Global(Context context) {
        this.context = context;
    }

    public static String userId;
    public static String fullName;
    public static String password;

}
