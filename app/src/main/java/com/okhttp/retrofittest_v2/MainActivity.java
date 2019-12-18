package com.okhttp.retrofittest_v2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.okhttp.retrofittest_v2.bean.BeanAndroidContent;
import com.okhttp.retrofittest_v2.bean.BeanContent;
import com.okhttp.retrofittest_v2.bean.BeanData;
import com.okhttp.retrofittest_v2.bean.Translation;
import com.okhttp.retrofittest_v2.login.User;
import com.okhttp.retrofittest_v2.retrofit.IBeanService;
import com.okhttp.retrofittest_v2.retrofit.Retrofit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "hahanihao";

    private EditText mEmail;
    private EditText mPassword;
    private TextView mLoginInfo;
    private Button mLogin;

    private IBeanService service;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

        retrofit=Retrofit.getRetrofit();
        service= retrofit.getService();

//        testRxJava();
//        UniteRetrofitAndRxJava();
//        processUrlsIsIpByMap();
        processUrlsIsIpByOneFlatMap();
    }

    public Observer getObserver(){
        //创建观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onCompleted");
            }

            //onSubscribe()方法是最先调用的
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"subscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        };
        return observer;
    }

    private void UniteRetrofitAndRxJava(){
        //步骤4：创建Retrofit对象
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 步骤5：创建 网络请求接口 的实例
        IBeanService request = retrofit.create(IBeanService.class);

        // 步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = request.getCall();

        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())            // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 步骤8：对返回的数据进行处理
                        result.show() ;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });
    }
    private void testRxJava(){

        //创建被观察者
        //Observable是被观察者，创建后传入一个OnSubscribe对象，
        //当Observeable（观察者）调用subscribe进行注册观察者时，OnSubscribe的Call方法就会触发。
        //ObservableEmitter：Emitter是发射器的意思，它可以发出三种类型的事件，与之对应的。
        //Observer有三个回调方法：
        //onNext：接受到一个事件，
        //onCompleted:接受完事件后调用，只会调用一次
        //onError：发生错误时调用，并停止接受事件，调用一次
        //注：onCompleted和onError不会同时调用，只会调用其中之一。
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                emitter.onNext("我是");
                emitter.onNext("RxJava");
                emitter.onNext("简单示例");
                emitter.onError(new Throwable("出错了"));
                emitter.onComplete();
//                emitter.onNext(getResponse());
            }
            //通过map操作符对数据进行中间处理
        }).map(new Function<String, String>() {
            @Override
            public String apply(String response) throws Exception {
                return response + " hahanihao ";
            }
        });

        //创建观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onCompleted");
            }

            //onSubscribe()方法是最先调用的
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"subscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        };
        //注册，将观察者和被观察者关联，将会触发OnSubscribe.call方法
        observable.subscribe(observer);

        //创建观察者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String mResponse) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                mLoginInfo.setText(mResponse);
            }
        };
        //subscribeOn()指定的是发送事件的线程，observeOn()指定的是接受事件的线程
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer);
        //在RxJava中，已经内置了很多线程选项供我们选择，例如有
        //Schedulers.io()代表io操作的线程，通常用于网络、读写文件等IO密集型的操作
        //Schedulers.computation()代表CPU计算密集型的操作，例如需要大量计算的操作
        //Schedulers.newThread()代表一个常规的新线程
        //AndroidSchedulers.mainThread()表示Android的主线程
        //这些内置的Scheduler已经足够满足我们开发的需求，因而我们应该使用内置的这些选项，
        //在RxJav内部使用的是线程池来维护这些线程，所有效率也比较高。
    }
    private String getResponse() {
        String url = "http://v.juhe.cn/weather/index?cityname=%E6%9D%AD%E5%B7%9E&dtype=&format=&key=7970495dbf33839562c9d496156e13cc";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Response response;

        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "error";
        }
    }

    private void processUrlsIsIpByMap(){
        Observable.just("https://www.baidu.com/", "https://www.google.com/", "https://www.bing.com/")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s){
                        try{
                            return s + " : " + getIPByUrl(s);
                        }catch (MalformedURLException e){
                            e.printStackTrace();
                        }catch (UnknownHostException e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }
    private String getIPByUrl(String str) throws MalformedURLException, UnknownHostException{
        URL urls = new URL(str);
        String host = urls.getHost();
        String address = InetAddress.getByName(host).toString();
        int b = address.indexOf("/");
        return address.substring(b + 1);
    }

    private void processUrlsIsIpByOneFlatMap(){
        Observable.just("https://www.baidu.com/", "https://www.google.com/", "https://www.bing.com/")
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        return createIpObservable(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> createIpObservable(final String url){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    String ip = getIPByUrl(url);
                    emitter.onNext(ip);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                    emitter.onNext(null);
                }catch (UnknownHostException e){
                    e.printStackTrace();
                    emitter.onNext(null);
                }
                emitter.onComplete();
            }
            }).subscribeOn(Schedulers.io());
    }

    public void initView(){
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mLoginInfo = (TextView) findViewById(R.id.login_info);
    }

    public void initListener(){
        mLogin.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                login();
                break;
        }
    }

    private void login(){
        mLoginInfo.setText("");
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(MainActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> userinfo = new HashMap<String, String>();
        userinfo.put("username", email);
        userinfo.put("password", password);

        retrofit2.Call<BeanData> call = service.getInfo("https://www.wanandroid.com/user/login/", userinfo);
        call.enqueue(new Callback<BeanData>() {
            @Override
            public void onResponse(retrofit2.Call<BeanData> call, Response<BeanData> response) {
                //判断是否请求成功
                if (response.isSuccessful()) {
                   BeanData result = response.body();//关键
                    //判断result数据是否为空
                    if (result != null) {
                        mLoginInfo.setText("email="+email+"\npassword="+password+"\nLoginInfo=" +
                                result.getData() + "\t" + result.getErrorCode() + "\t" + result.getErrorMsg());
                        Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("isSuccessful", "false");
                }
            }

            @Override
            public void onFailure(Call<BeanData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

