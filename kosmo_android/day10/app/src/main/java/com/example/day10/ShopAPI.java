package com.example.day10;

import static com.example.day10.RemoteService.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopAPI {

    public static RemoteService call(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RemoteService service = retrofit.create(RemoteService.class);
        return service;
    }
}
