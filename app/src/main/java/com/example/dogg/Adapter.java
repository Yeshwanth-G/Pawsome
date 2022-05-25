package com.example.dogg;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter extends RecyclerView.Adapter<Adapter.veiwholder>{
    private static final String TAG ="jk" ;
    ArrayList<helperclass>a;
Context context;
    public Adapter(Context context, ArrayList<helperclass> a) {
        this.a = a;
        this.context=context;
    }

    @Override
    public veiwholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        veiwholder vh=new veiwholder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter.veiwholder holder, int position) {
            helperclass h=a.get(position);
            if(h.breeds!=null&&h.breeds.size()>0)holder.getDogname().setText(h.getBreeds().get(0).getName());
            else holder.getDogname().setVisibility(View.INVISIBLE);
            Log.w(TAG,"Please bro:"+h.url);
            Log.d("TAG","reyyyyy....");
            Glide.with(context).load(h.url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(holder.getImageView());

            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit rt=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/")
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    Retro_interface rti=rt.create(Retro_interface.class);
                    favclass fv=new favclass(h.getId(),"1");
                    Call<response> call=rti.postfav(fv,"dcf55151-0743-47d4-b745-8953f6feaf3b");
                    call.clone().enqueue(new Callback<response>() {
                        @Override
                        public void onResponse(Call<response> call, Response<response> response) {
                            if(response!=null) {
                                Toast.makeText(context, "Sucess:" + response.body().messege + "-" + h.getId() + "okk:" + response.body().id, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<response> call, Throwable t) {

                        }
                    });
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it=new Intent(context,details.class);
                    it.putExtra("container",h);
                    context.startActivity(it);
                }
            });
    }
    @Override
    public int getItemCount() {
        return a.size();
    }
    public class veiwholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView dogname;
        ImageButton imageButton;

        public TextView getDogname() {
            return dogname;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public veiwholder(View itemView) {
            super(itemView);
            imageButton=(ImageButton)itemView.findViewById(R.id.like);
            imageView=itemView.findViewById(R.id.image);
            dogname=itemView.findViewById(R.id.dogname);
        }
    }

}
