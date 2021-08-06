package com.example.dogg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity<stringRequest> extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
ImageView imageView;
    TextView tst,t;
    int i,j;
    ConstraintLayout l;
    long a;
    ProgressBar p;
    ImageButton isb;
    String last,name,imperialweight,imperialheight,life_span,bred_for,bred_group,temperament,st;
    String url1=null;
    String url,temp,selectedbreed;
    ArrayList<String> ar;
    ArrayList<String> backarray=new ArrayList<>();
    ArrayList<String>tempar;
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        i=0;
        p=(ProgressBar)findViewById(R.id.progressBar1);
        tempar=getIntent().getStringArrayListExtra("favarray");
         s = (Spinner) findViewById(R.id.spinner);
        tst = (TextView) findViewById(R.id.textView);
        t = (TextView) findViewById(R.id.textView3);
        l=(ConstraintLayout)findViewById(R.id.layout1);
        imageView = (ImageView) findViewById(R.id.imageView);
        isb=(ImageButton)findViewById(R.id.favourite) ;
     ar=new ArrayList<String>();//= load();
        p.setVisibility(View.INVISIBLE);
        ar=load();
        ar.add("Choose a Bred");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_spinner_item,ar) ;//ArrayAdapter.createFromResource(this,R.array.level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
       s.setOnItemSelectedListener(this);
        isb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempar.add(backarray.get(i));
            }
        });
       // i=0;
        l.setOnTouchListener(new OnSwipeTouchListener(SearchActivity.this){
            @Override
            public void onSwipeLeft() {

                super.onSwipeLeft();
                p.setVisibility(View.VISIBLE);
                tst.setText("");

                if(i==backarray.size()-1)
               {display(a);
               i++;}
               else
               { i++;
                   func(backarray.get(i));}
            }
            @Override
            public void onSwipeRight() {

                super.onSwipeRight();
                tst.setText("");
                if(i>0)
                { p.setVisibility(View.VISIBLE);
                    i--;
                    func(backarray.get(i));
                }
            }
            @Override
            public void onDoubleClick() {
                super.onDoubleClick();
                if(j==0)
                {
                    t.setText("Double tap to disable");
                    tst.setText(last);
                    j++;}
                else
                { tst.setText("");
                    t.setText("Double Click for info\nSwipe left for more doggies");

                    j=0;
                }

            }

        });
    }
    @Override
    public void onBackPressed(){
        Intent it=new Intent(SearchActivity.this,MainActivity.class);
        it.putStringArrayListExtra("favarray",tempar);
        startActivity(it);
    }

    public ArrayList<String> load()
    {
        ArrayList<String> ar=new ArrayList<String>();
        RequestQueue queue = Volley.newRequestQueue(this);
       url= "https://api.thedogapi.com/v1/breeds";

        // Request a string response from the provided URL.
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<=264;i++)
                            { JSONObject temp=response.getJSONObject(i);
                            st=temp.getString("name");
                            ar.add(st);
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // textView.setText("That didn't work!");
            }
        }
        );
        queue.add(stringRequest);


        return ar;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //((TextView)view).setTextColor(Color.BLACK);
selectedbreed=parent.getItemAtPosition(position).toString();
a=parent.getItemIdAtPosition(position);
        i=0;
        backarray.removeAll(backarray);
display(a);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void display(long id) {
    if(id!=0)   p.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
       url="https://api.thedogapi.com/v1/images/search?breed_id="+id;

        // Request a string response from the provided URL.
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject temp=response.getJSONObject(0);
                            url1= temp.getString("url");
                            backarray.add(url1);
                            JSONArray j=(temp.getJSONArray("breeds"));
                            JSONObject j1=j.getJSONObject(0);
                            JSONObject j2= j1.getJSONObject("weight");
                           JSONObject j3= j1.getJSONObject("height");
                             name= j1.getString("name");
                            life_span= j1.getString("life_span");
                            temperament= j1.getString("temperament");
                             imperialweight= j2.getString("imperial");
                            imperialheight= j3.getString("imperial");
                           last="Name:"+name+"\nWeight:"+imperialweight+"\nHieght:"+imperialheight+"\nLife Span:"+life_span+"\nTemparament:"+temperament;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            last="No info availiable";
                        }
                       func(url1);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        queue.add(stringRequest);
    }

    public void func(String s) {
        Glide.with(SearchActivity.this).load(s).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                p.setVisibility(View.INVISIBLE);
                t.setText("Double Click for info\nSwipe left for more doggies");
                return false;
            }
        }).into(imageView);
    }
}