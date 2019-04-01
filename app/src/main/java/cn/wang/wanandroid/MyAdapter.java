package cn.wang.wanandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleBean.DataBean.DatasBean> datas;

    public MyAdapter(Context context, List<ArticleBean.DataBean.DatasBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public void setDatas(List<ArticleBean.DataBean.DatasBean> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /*
    如下代码需要优化，参考：
    http://www.runoob.com/w3cnote/android-tutorial-baseadapter.html
    提高篇：打造万能的ListView GridView 适配器
    https://blog.csdn.net/lmj623565791/article/details/38902805/
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        TextView tv_title = convertView.findViewById(R.id.tv_title);
        TextView tv_author = convertView.findViewById(R.id.tv_author);
        TextView tv_chapter = convertView.findViewById(R.id.tv_chapter);
        TextView tv_date = convertView.findViewById(R.id.tv_date);
        ArticleBean.DataBean.DatasBean bean = datas.get(position);
        tv_title.setText(bean.getTitle());
        tv_author.setText("作者："+ bean.getAuthor());
        tv_chapter.setText("分类："+ bean.getSuperChapterName() + "/" + bean.getChapterName());
        tv_date.setText("时间："+ bean.getNiceDate());
        return convertView;
    }
}
