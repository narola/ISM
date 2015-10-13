package com.ism.login;

import android.content.Context;
import android.content.Intent;

/**
 * Created by c162 on 07/10/15.
 */
public class Global {
   static  Context context;

    public Global(Context context) {
        this.context = context;
    }

    public static String userId;

    public static String password;

    public static Boolean isRemember;


    public static void myIntent(Context con,Class contextTo ){
        Intent intent=new Intent(con,contextTo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        con.startActivity(intent);

    }
}
