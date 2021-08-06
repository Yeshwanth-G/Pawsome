package com.example.dogg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavActivity extends AppCompatActivity {
int i=0;
String s;
ImageView iv;
ProgressBar p;
TextView t;
    ArrayList<String>pp=new ArrayList<>();
    ConstraintLayout l;
    String fav;
    FloatingActionButton share;
    ArrayList<String> pfav;
    StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        StrictMode.setVmPolicy(builder.build());
        p=(ProgressBar)findViewById(R.id.progressBar2);
        t=(TextView)findViewById(R.id.textView2);
        l=(ConstraintLayout)findViewById(R.id.layout);
        iv=(ImageView)findViewById(R.id.imageView2) ;
        share=(FloatingActionButton)findViewById(R.id.sharebutton) ;
        loaddata();
        pp=getIntent().getStringArrayListExtra("favarray");
        Collections.reverse(pp);
        pp.addAll(pfav);
        if(!(pp.isEmpty()))
        { loads(i);
        store();}
        else{
          t.setText("Nothing to show");
        p.setVisibility(View.INVISIBLE);}
     share.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             share();
         }
     });
        l.setOnTouchListener(new OnSwipeTouchListener(FavActivity.this){
            @Override
            public void onSwipeLeft() {

                super.onSwipeLeft();
                if(i<(pp.size()-1))
                {i++;
                loads(i);
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

       if(i>0){
i--;
loads(i);}
            }
            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                pp.remove(i);
                if(i==pp.size())
                    i--;
                if(!(pp.isEmpty())){
                loads(i);
                store();}
                else
                { iv.setImageDrawable(null);
                    pp.removeAll(pp);
                    pfav.removeAll(pfav);
                    store();
                    p.setVisibility(View.INVISIBLE);
                    t.setText("Nothing to show here");}

            }

        });
    }

    public void loads(int i) {
        Glide.with(FavActivity.this).load(pp.get(i)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                p.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(iv);
    }

    private void store() {
        SharedPreferences sharedPreferences=getSharedPreferences("storeds",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(pp);
        editor.putString("storeds",json);
        editor.apply();
    }
    private  void loaddata(){
        SharedPreferences sharedPreferences=getSharedPreferences("storeds",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("storeds",null);
        Type type=new TypeToken<ArrayList<String>>() {}.getType();
        pfav=gson.fromJson(json,type);
        if(pfav==null)
            pfav= new ArrayList<String>();
    }
    public void share() {
        try {

            Bitmap b=getBitmapFromView(iv);
            File file=new File(this.getExternalCacheDir(),"doggie.webp");
            FileOutputStream f=new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.WEBP,100,f);
                f.flush();
                f.close();
                file.setReadable(true,false);
            final Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
            intent.setType("image/webp");
            startActivity(Intent.createChooser(intent,"Share via..."));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Bitmap getBitmapFromView(View view){
        Bitmap b=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(b);
        Drawable bd=view.getBackground();
        if(bd!=null) bd.draw(canvas);
        else canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return b;
    }
    @Override
    public void onBackPressed(){
        Intent it=new Intent(FavActivity.this,MainActivity.class);
        startActivity(it);
    }

}