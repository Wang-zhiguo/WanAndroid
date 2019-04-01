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
        //1.创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //2.创建Request对象，设置一个url地址。
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/article/list/0/json")
                .build();
        //3.创建一个call对象,参数就是Request请求对象，并异步执行
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    //使用Gson解析
                    Gson gson = new Gson();
                    ArticleBean articleBean = gson.fromJson(response.body().string(), ArticleBean.class);
                    //注意：目前执行在新线程中，若操作主线程View，需要runOnUiThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //数据送给适配器
                            adapter.setDatas(articleBean.getData().getDatas());
                            //刷新
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
