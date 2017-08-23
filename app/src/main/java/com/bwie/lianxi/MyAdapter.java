package com.bwie.lianxi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andy.library.ChannelBean;
import com.bwie.lianxi.Frgment.CaiJingFragment;
import com.bwie.lianxi.Frgment.GuoNeiFragment;
import com.bwie.lianxi.Frgment.JunShiFragment;
import com.bwie.lianxi.Frgment.KeJiFragment;
import com.bwie.lianxi.Frgment.SheHuiFragment;
import com.bwie.lianxi.Frgment.ShiShangFragment;
import com.bwie.lianxi.Frgment.TiYuFragment;
import com.bwie.lianxi.Frgment.TuiJianFragment;
import com.bwie.lianxi.Frgment.YuLeFragment;

import java.util.List;

/**
 * Created by 北城 on 2017/8/3.
 */


public class MyAdapter extends FragmentPagerAdapter {
    //private String[] title = {"推荐","国内","体育","时尚","社会","娱乐","科技","财经","军事"};
    private FragmentManager mFragmentManager;
    List<ChannelBean> mChannelBeanList;
    public MyAdapter(FragmentManager fm, List<ChannelBean> channelBeanList) {
        super(fm);
        mFragmentManager = fm;
        mChannelBeanList = channelBeanList;
    }

    @Override
    public Fragment getItem(int position) {
        String name = mChannelBeanList.get(position).getName();
        switch (name){

            case "推荐":
                return new TuiJianFragment();
            case "国内":
                return new GuoNeiFragment();
            case "体育":
                return new TiYuFragment();
            case "时尚":
                return new ShiShangFragment();
            case "社会":
                return new SheHuiFragment();
            case "娱乐":
                return new YuLeFragment();
            case "科技":
                return new KeJiFragment();
            case "财经":
                return new CaiJingFragment();
            case "军事":
                return new JunShiFragment();
        }
        return  null;
    }

    @Override
    public int getCount() {
        return mChannelBeanList == null ? 0 : mChannelBeanList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannelBeanList.get(position).getName();
    }
}
