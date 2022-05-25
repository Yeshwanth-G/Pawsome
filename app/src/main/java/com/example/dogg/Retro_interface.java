package com.example.dogg;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retro_interface {

    @GET("images/search")
    Call<List<helperclass>> getimages();
    @GET("breeds")
    Call<List<helperclass>>getbreeds();
    @GET("images/{image_id}")
    Call<helperclass>getimageid(@Path("image_id")String image_id, @Query("api_key")String api_key);
    @GET("favourites")
    Call<List<helperclass>>getfav(@Query("api_key")String api_key);
    @GET("search?")
    Call<List<helperclass>>get_breed_images(@Query("breed_id")String id);
    @POST("favourites")
     Call<response>postfav(@Body favclass f,@Query("api_key")String api_key);
    @GET("images")
    Call<List<helperclass>>getuploads(@Query("api_key")String api_key,@Query("limit")String limit);
    @Multipart
    @POST("images/upload")
    Call<uploadresponse>upload(@Part("file\";filename=\"dog.png\" ") RequestBody file, @Part("sub_id")RequestBody sub_id, @Query("api_key")String api_key);
}
