package com.example.dogg;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Listview extends AppCompatActivity {
    RecyclerView rc;
    Adapter ad;
    boolean bl=false;
    Retrofit retrofit,retrofit1;
    LinearLayoutManager lm;
    ArrayList<helperclass>arr;
    ConstraintLayout cl;
    ArrayList<String>breed_names;
    FloatingActionButton favbtn,viewbtn,uploadbtn;
    ProgressBar pg;
    Spinner s;
    long id;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        cl=(ConstraintLayout)findViewById(R.id.layout);
        rc=(RecyclerView)findViewById(R.id.recyclerView);
        pg=(ProgressBar)findViewById(R.id.pgbar);
        favbtn=(FloatingActionButton)findViewById(R.id.favbtn);
        viewbtn=(FloatingActionButton)findViewById(R.id.veiwbtn);
        uploadbtn=(FloatingActionButton)findViewById(R.id.uploadbtn);
        rc.setHasFixedSize(true);
        arr=new ArrayList<>();
        breed_names=new ArrayList<>();

        breed_names=load_names();
        breed_names.add("Choose a bread");
        s=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String >(Listview.this,android.R.layout.simple_spinner_item,breed_names);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad=new Adapter(this,arr);
        lm=new LinearLayoutManager(this);
        rc.setLayoutManager(lm);
        rc.setAdapter(ad);
        s.setAdapter(arrayAdapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            id=parent.getItemIdAtPosition(position);
            arr.clear();
            String sp="https://api.thedogapi.com/v1/images/";
            retrofit1=new Retrofit.Builder().baseUrl(sp).addConverterFactory(GsonConverterFactory.create()).build();
            Retro_interface rt=retrofit1.create(Retro_interface.class);
            Call<List<helperclass>> call=rt.get_breed_images(""+id);
            for(int i=0;i<10;i++)
                load(call);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bl==false){
                uploadbtn.setVisibility(View.VISIBLE);
                favbtn.setVisibility(View.VISIBLE);
                viewbtn.setImageResource(R.drawable.ic_baseline_clear_24);
                bl=true;
                }
                else{
                    uploadbtn.setVisibility(View.INVISIBLE);
                    favbtn.setVisibility(View.INVISIBLE);
                    viewbtn.setImageResource(R.drawable.ic_baseline_add_24);
                    bl=false;
                }

            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Listview.this,upload.class);
                startActivity(it);
            }
        });
        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Listview.this,temporary.class);
                startActivity(it);
            }
        });
        retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        Retro_interface rt=retrofit.create(Retro_interface.class);
        Call<List<helperclass>> call=rt.getimages();
        for(int i=0;i<10;i++)
            load(call);
    }

    public ArrayList<String> load_names() {
        ArrayList<String>arr=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        Retro_interface rt=retrofit.create(Retro_interface.class);
        Call<List<helperclass>> call=rt.getbreeds();
        call.clone().enqueue(new Callback<List<helperclass>>() {
            @Override
            public void onResponse(Call<List<helperclass>> call, Response<List<helperclass>> response) {

                if(response.isSuccessful()){
                    List<helperclass>lt=response.body();
                    for(helperclass h:lt){
                        arr.add(h.name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<helperclass>> call, Throwable t) {

            }
        });
        return arr;
    }
    public void load(Call<List<helperclass>> call)
    {

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

            }
        });
    }
}