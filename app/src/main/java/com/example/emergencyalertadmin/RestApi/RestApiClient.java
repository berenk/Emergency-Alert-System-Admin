package com.example.emergencyalertadmin.RestApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {
    private RestApi mRestApi;

    public RestApiClient(String restApiServiceUrl) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(restApiServiceUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mRestApi = retrofit.create(RestApi.class);
    }

    public RestApi getRestApi() {
        return mRestApi;
    }
}
