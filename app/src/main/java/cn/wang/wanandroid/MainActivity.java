package cn.wang.wanandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.listview);
        adapter = new MyAdapter(this,null);
        listView.setAdapter(adapter);
    }

    private void initData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/article/list/1/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("kwwl","获取数据成功了");
                    Gson gson = new Gson();
                    ArticleBean articleBean = gson.fromJson(response.body().string(), ArticleBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setDatas(articleBean.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        }
                    });
//                    for (ArticleBean.DataBean.DatasBean bean:articleBean.getData().getDatas()) {
//                        Log.d("kwwl",bean.getTitle());
//                    }
                }
            }
        });
    }
}
