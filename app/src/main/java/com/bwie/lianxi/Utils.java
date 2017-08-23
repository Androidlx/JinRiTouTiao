package com.bwie.lianxi;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by 北城 on 2017/8/8.
 */

public class Utils {
    public static int theme = 0;
    public static final int DAY_THEME = 0;
    public static final int NIGHT_THEME = 1;

    public static void zhuti(Activity activity){
        switch (theme){
            case DAY_THEME:
                activity.setTheme(R.style.day_Theme);
                break;
            case NIGHT_THEME:
                activity.setTheme(R.style.night_Theme);
                break;
        }

    }
    public static void gaibian(Activity activity){
        switch (theme){
            case DAY_THEME:
                theme = NIGHT_THEME;
                break;
            case NIGHT_THEME:
                theme = DAY_THEME;
                break;
        }
        activity.finish();
        activity.overridePendingTransition(R.anim.in,R.anim.out);
        activity.startActivity(new Intent(activity,activity.getClass()));
    }
}
