package com.example.dogg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class your_uploads extends AppCompatActivity {
    RecyclerView rc;
    Adapter ad;
    ArrayList<helperclass> arr;

    final String TAG="kjk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_uploads);
        rc=(RecyclerView)findViewById(R.id.recyclerView);
        rc.setHasFixedSize(true);
        arr=new ArrayList<>();
        getSupportActionBar().setTitle("Your Uploads");
        ad=new Adapter(this,arr);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(ad);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        Retro_interface rt=retrofit.create(Retro_interface.class);
        Call<List<helperclass>> call=rt.getuploads("dcf55151-0743-47d4-b745-8953f6feaf3b","10");
        load(call);
    }
    public void load(Call<List<helperclass>>call){
        call.clone().enqueue(new Callback<List<helperclass>>() {
            @Override
            public void onResponse(Call<List<helperclass>> call, Response<List<helperclass>> response) {
                if(response.isSuccessful()){
                    List<helperclass>lt=response.body();
                    for(helperclass h:lt){
                        arr.add(h);
                    }
                    ad.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<helperclass>> call, Throwable t) {
                Log.d(TAG,"Response Failed...."+t.getMessage());
            }
        });
    }
}