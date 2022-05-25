package com.example.dogg;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class details extends AppCompatActivity {
ImageView dogimage;
TextView name,breed,weight,temperament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent it=getIntent();

        helperclass h=(helperclass) it.getExtras().getSerializable("container");
        dogimage=(ImageView)findViewById(R.id.dogimage);
        name=(TextView)findViewById(R.id.name);
        breed=(TextView)findViewById(R.id.breed);
        weight=(TextView)findViewById(R.id.weight);
        temperament=(TextView)findViewById(R.id.temperament);
        String url=h.getUrl();
        GlideApp.with(this).load(url).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(dogimage);
        if(h.breeds!=null&&h.breeds.size()>0){
        name.setText("My Name is: "+h.getBreeds().get(0).getName());
        breed.setText("Breed: "+h.getBreeds().get(0).breed_group);
        weight.setText("My Life Span: "+h.getBreeds().get(0).getLife_span());
        temperament.setText("Temperamnet: "+h.getBreeds().get(0).temperament);
        }
    }
}