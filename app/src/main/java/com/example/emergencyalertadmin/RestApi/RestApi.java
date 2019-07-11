package com.example.emergencyalertadmin.RestApi;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST(Url.Notification)
    Call<JsonElement> Send(@Field("title") String title, @Field("body") String body, @Field("category") String category);


}
