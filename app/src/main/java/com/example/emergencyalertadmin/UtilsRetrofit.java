package com.example.emergencyalertadmin;

import android.content.Context;
import android.widget.Toast;

import com.example.emergencyalertadmin.Activities.Main2Activity;
import com.example.emergencyalertadmin.RestApi.ManagerAll;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilsRetrofit {

    private static UtilsRetrofit ourInstance = new UtilsRetrofit();

    public static synchronized UtilsRetrofit getOurInstance() {
        return ourInstance;
    }

    public void sendNotification(NotificationModel notificationModel, final Context context) {
            Call<JsonElement> call = ManagerAll.getOurInstance().send_notif(notificationModel);
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    Toast.makeText(context,"Başarılı",Toast.LENGTH_LONG).show();
                    Main2Activity activity = (Main2Activity) context;
                  }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    System.out.println(t.toString());
                }
            });

    }
}

