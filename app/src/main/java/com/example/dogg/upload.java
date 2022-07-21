package com.example.dogg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class upload extends AppCompatActivity {
    ImageView imageView;
    MaterialButton materialButton,seeuploads;
    String Path;
    Uri uri;
    MultipartBody.Part body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageView=(ImageView)findViewById(R.id.uploadimg);
        getSupportActionBar().setTitle("Upload");
        materialButton=(MaterialButton)findViewById(R.id.uploadbtn);
        seeuploads=(MaterialButton)findViewById(R.id.seeuploads);
        seeuploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent it=new Intent(upload.this,your_uploads.class);
            startActivity(it);
            }
        });
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Intent it=new Intent();
                it.setType("image/png");
                it.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(it,10);
//                uploadimage(body);
                }
                else{
                    ActivityCompat.requestPermissions(upload.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10&&resultCode== Activity.RESULT_OK){
             uri=data.getData();
            Path =RealPathUtil.getRealPath(upload.this,uri);
            File file=new File(Path);
            imageView.setImageURI(uri);
            RequestBody requestBody= RequestBody.create(MediaType.parse(getContentResolver().getType(uri)),file);
            body=MultipartBody.Part.createFormData("file",file.getName(),requestBody);
            RequestBody subid=RequestBody.create(MediaType.parse("multipart/form-data"),"1");
            uploadimage(requestBody,subid);
        }
    }
    public void uploadimage(RequestBody body,RequestBody subid){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        Retro_interface rt=retrofit.create(Retro_interface.class);
        Call<uploadresponse> call=rt.upload(body,subid,"dcf55151-0743-47d4-b745-8953f6feaf3b");
        call.clone().enqueue(new Callback<uploadresponse>() {
            @Override
            public void onResponse(Call<uploadresponse> call, Response<uploadresponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(upload.this,"Successfully uploaded..",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<uploadresponse> call, Throwable t) {
                Toast.makeText(upload.this,"sorryy.."+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}