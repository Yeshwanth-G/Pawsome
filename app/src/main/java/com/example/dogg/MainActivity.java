package com.example.dogg;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
public class MainActivity<stringRequest> extends AppCompatActivity {
    ImageView imageView;
    ConstraintLayout l;
    Context context;
    Button b, favb;
    ProgressBar p;
    TextView t, ts;
     static int i=0;
    int i1,j, o, c, loadcount;
    ImageButton ib;
    String last, name, imperialweight, imperialheight, life_span, bred_for, bred_group, temperament;
    String url1,srp;
    String url, temp, spr, sp;
    ArrayList<String> fav = new ArrayList<String>();
    ArrayList<String> lastarray = new ArrayList<String>();
    ArrayList<String> backarray = new ArrayList<String>();
    ArrayList<String> backarray1 = new ArrayList<String>();
    ArrayList<String> lastarray1 = new ArrayList<String>();
String[] as;
    String[] bs;
    String[] cs;
    ArrayList<String> fav1 = new ArrayList<String>();
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fav = getIntent().getStringArrayListExtra("favarray");
        if ((fav == null))
            fav = new ArrayList<String>();
        l = (ConstraintLayout) findViewById(R.id.layout);
        imageView = (ImageView) findViewById(R.id.imageView);
        p = (ProgressBar) findViewById(R.id.progressBar);
        b = (Button) findViewById(R.id.button);
        favb = (Button) findViewById(R.id.favouriteb);
        t = (TextView) findViewById(R.id.textView);
        ts = (TextView) findViewById(R.id.textView3);
        ib = (ImageButton) findViewById(R.id.favourite);
        t.setText("");
        load();

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav.add(backarray.get(i));
            }
        });
        favb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it = new Intent(MainActivity.this, FavActivity.class);
                                        it.putStringArrayListExtra("favarray", fav);
                                        startActivity(it);
                                    }
                                });
    l.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
        @Override
        public void onSwipeLeft() {

            super.onSwipeLeft();
            if (i == backarray.size() - 1) {
                load();
                i++;
            } else {
                i++;
                func(backarray.get(i));
            }
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();
            t.setText("");
            if (i > 0) {
                i--;
                func(backarray.get(i));
            }
        }

        @Override
        public void onDoubleClick() {
            super.onDoubleClick();
            if ((j == 0)) {
                t.setText(lastarray.get(i));//GETTING INFO AT PARTICULAR INDEX IN A ARRAYLIST....
                ts.setText("Double tap to disable");
                j++;
            } else {
                ts.setText("Double Click to get Info\nSwipe left for more doggies");
                t.setText("");
                j = 0;
            }
        }

    });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SearchActivity.class);
                it.putStringArrayListExtra("favarray", fav);
                startActivity(it);
            }
        });

    }
    public void func(String s) {
        GlideApp.with(MainActivity.this).load(s).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        p.setVisibility(View.INVISIBLE);
                        ts.setText("Double Click to get Info\nSwipe left for more doggies");
                        return false;
                    }
                }).into(imageView);
    }

public void load()
{
p.setVisibility(View.VISIBLE);
ts.setText("");
    RequestQueue queue = Volley.newRequestQueue(this);
   url="https://api.thedogapi.com/v1/images/search";
    JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET,url,null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                         JSONObject temp=response.getJSONObject(0);
                        url1= temp.getString("url");
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
                    if(srp!=null)
                    { url1=srp;
                        i--;}
                    else{
                        backarray.add(url1);
                        lastarray.add(last);}
srp=null;
                    func(url1);
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // textView.setText("That didn't work!");
        }
    }
    );
queue.add(stringRequest);
}
    @Override
    public void onBackPressed(){
       finishAffinity();
    }
}