package com.bwie.lianxi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.limxing.xlistview.view.XListView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by 北城 on 2017/8/15.
 */

public class MyBase extends BaseAdapter {
    Context context;
    List<Bean.ResultBean.DataBean> data;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mpop;
    private TextView delete;
    private ImageView close;
    private XListView xListView;

    public MyBase(Context context, List<Bean.ResultBean.DataBean> data, XListView xListView){
        this.context = context;
        this.data = data;
        this.xListView = xListView;
        mLayoutInflater = LayoutInflater.from(context);
        initPopView();

    }
    ImageOptions options = new ImageOptions.Builder()
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setUseMemCache(true)
            .setSize(200, 200)
            .build();
    public void more(List<Bean.ResultBean.DataBean> datas, boolean flag) {
        for (Bean.ResultBean.DataBean bean : datas) {
            if (flag) {
                data.add(0, bean);
            } else {
                data.add(bean);
            }
        }

    }
    @Override
    public int getCount() {
        return data != null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item,null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.area = (TextView) convertView.findViewById(R.id.area);
            holder.more = (ImageView) convertView.findViewById(R.id.more);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position).getTitle());
        holder.type.setText(data.get(position).getDate());
        holder.area.setText(data.get(position).getAuthor_name());
        x.image().bind(holder.img, data.get(position).getThumbnail_pic_s(),options);
        holder.more.setOnClickListener(new PopAction(position));
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,XinWen.class);
                intent.putExtra("url",data.get(position-1).getUrl());
                context.startActivity(intent);
            }
        });



        return convertView;
    }
    class PopAction implements View.OnClickListener{
        private int position;


        public PopAction(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            int[] array = new int[2];
            v.getLocationOnScreen(array);
            int x = array[0];
            int y = array[1];
            showPop(v, position, x, y);
        }
    }
    private void initPopView(){
        View popwindowLayout = mLayoutInflater.inflate(R.layout.popwindow,null);
        mpop = new PopupWindow(popwindowLayout, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mpop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        mpop.setAnimationStyle(R.style.popWindowAnimation);
        delete = (TextView) popwindowLayout.findViewById(R.id.delete_tv);
        close = (ImageView) popwindowLayout.findViewById(R.id.close_iv);
    }
    private void showPop(View parent, final int position, int x, int y){
        mpop.showAtLocation(parent,0,x,y);
        mpop.setFocusable(true);
        mpop.setOutsideTouchable(true);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyDataSetChanged();
                if(mpop.isShowing()){
                    mpop.dismiss();
                }

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mpop.isShowing()){
                    mpop.dismiss();
                }
            }
        });

    }

    class ViewHolder {
        TextView name;
        TextView type;
        TextView area;
        ImageView img;
        ImageView more;
    }
}
