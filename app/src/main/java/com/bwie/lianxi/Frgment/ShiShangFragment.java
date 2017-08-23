package com.bwie.lianxi.Frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.lianxi.Bean;
import com.bwie.lianxi.MyBase;
import com.bwie.lianxi.R;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * Created by 北城 on 2017/8/3.
 */

public class ShiShangFragment extends Fragment implements XListView.IXListViewListener {


    private XListView xlistview;
    private MyBase adapter;
    private boolean flag;
    private int index = 1;
    private List<Bean.ResultBean.DataBean> data;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        xlistview = (XListView) view.findViewById(R.id.xlistview);
        xlistview.setPullLoadEnable(true);
        xlistview.setXListViewListener(this);
        qingqiu();

    }

    private void qingqiu() {
        String url = "http://v.juhe.cn/toutiao/index";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("key", "5b6258c74f4346147b12fe38490a12b2");
        params.addBodyParameter("type","shishang");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                data = bean.getResult().getData();
                if (adapter == null) {
                    adapter = new MyBase(getActivity(),data,xlistview);
                    xlistview.setAdapter(adapter);
                } else {
                    adapter.more(data, flag);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRefresh() {
        ++index;
        qingqiu();
        flag = true;
        xlistview.stopRefresh(true);

    }

    @Override
    public void onLoadMore() {
        ++index;
        qingqiu();
        flag = false;
        xlistview.stopLoadMore();

    }

}
