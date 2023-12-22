package com.example.day10;

import java.util.HashMap;

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

public interface RemoteService {
    public static final String BASE_URL="http://192.168.0.196:8080";

    @GET("/shop/list")
    Call<HashMap<String,Object>> list(@Query("page") int page,@Query("size") int size);

    @GET("/shop/read/{pid}")
    Call<ShopVO> read( @Path("pid") int pid);

    @POST("/shop/update")
    Call< Void> update(@Body ShopVO vo);

    @Multipart
    @POST("/shop/upload/image")
    Call<Void> upload(@Part("pid") RequestBody id, @Part MultipartBody.Part file);

}
