package com.dashingqi.news.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dashingqi.news.R;
import com.dashingqi.news.bean.NewsDataBean;
/**
 * Created by zhangqi on 2017/4/21.
 */

public class NewsDetailDataAdapter extends BaseQuickAdapter<NewsDataBean.ResultBean.DataBean,BaseViewHolder> {
    public NewsDetailDataAdapter() {
        super(R.layout.item_news_detail);
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, NewsDataBean.ResultBean.DataBean dataBean) {
        viewHolder.setText(R.id.tv_news_title,dataBean.getTitle())
                .setText(R.id.tv_name,dataBean.getAuthor_name())
                .setText(R.id.tv_data,dataBean.getDate());
        viewHolder.addOnClickListener(R.id.ll_news_detail);
        Glide.with(mContext).
                load(dataBean.getThumbnail_pic_s()).
                placeholder(R.drawable.ic_error).
                error(R.drawable.ic_error).
                crossFade().
                centerCrop().
                into((ImageView) viewHolder.getView(R.id.iv_news_pic));
    }
}
