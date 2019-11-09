package com.okhttp.retrofittest_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.okhttp.retrofittest_v2.bean.BeanAndroidContent;
import com.okhttp.retrofittest_v2.bean.BeanContent;
import com.okhttp.retrofittest_v2.retrofit.IBeanService;
import com.okhttp.retrofittest_v2.retrofit.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText;
    private TextView mTextView;
    private Button mButton;

    private IBeanService service;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.et_input);
        mTextView = (TextView) findViewById(R.id.tv_2);
        mButton = (Button) findViewById(R.id.bt_query);

        Retrofit.address = "https://www.wanandroid.com/article/list/0/json/";
        retrofit=Retrofit.getRetrofit();
        service= retrofit.getService();

        mButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_query:
                query();
                break;
        }
    }

    private void query(){
        mTextView.setText("");
        String num=mEditText.getText().toString();

        if(num.isEmpty()){
            Toast.makeText(MainActivity.this, "请输入ID", Toast.LENGTH_SHORT).show();
            return;
        }
        retrofit2.Call<BeanContent> call = service.getMenuById(num);
        call.enqueue(new Callback<BeanContent>() {
            @Override
            public void onResponse(retrofit2.Call<BeanContent> call, Response<BeanContent> response) {
                //判断是否请求成功
                if (response.isSuccessful()) {
                    BeanContent result = response.body();//关键
                    //判断result数据是否为空
                    if (result != null) {
                        BeanAndroidContent data = result.getData();
                        String errorMsg = result.getErrorMsg();
                        mTextView.setText("data="+data.getTitle()+"\nniceDate="+errorMsg);
                    }else{
                        Log.d("result != null", "false");
                    }
                }else{
                    Log.d("isSuccessful", "false");
                }
            }

            @Override
            public void onFailure(Call<BeanContent> call, Throwable t) {

            }
        });
    }
}

