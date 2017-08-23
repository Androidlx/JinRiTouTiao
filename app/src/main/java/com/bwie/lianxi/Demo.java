package com.bwie.lianxi;

import android.content.Context;

/**
 * Created by 北城 on 2017/8/10.
 */

public class Demo {
    private static final String DATU = "最佳效果";
    private static final String ZHONGTU = "较省流量";
    private static final String XIAOTU = "极省流量";
    private String BASE_URL=DATU;
    private static Demo demo;
    private boolean wang = true;
    public static final String SP_NAME = "SP_NAME";
    public static final String PICTURE_LOAD_MODE_KEY = "PICTURE_LOAD_MODE_KEY";

    private Demo(){

    }
    public static Demo getInstance() {
        if (demo == null) {
            synchronized (Utils.class) {
                if (demo == null) {
                    demo = new Demo();
                }
            }
        }
        return demo;
    }
    public String getBASE_URL(){
        if(wang){
            int mode = AppApplication.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getInt(PICTURE_LOAD_MODE_KEY, 0);
            switch (mode){
                case 0:{
                    BASE_URL = DATU;
                }
                break;
                case 1:{
                    BASE_URL = ZHONGTU;
                }
                break;
                case 2:{
                    BASE_URL = XIAOTU;
                }
                break;
            }
        }else {
            BASE_URL = DATU;
        }
        return BASE_URL;
    }
    public void changeNetState(boolean wang) {
        this.wang = wang;
    }
}
