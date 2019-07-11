package com.example.emergencyalertadmin.RestApi;

import com.example.emergencyalertadmin.NotificationModel;
  import com.google.gson.JsonElement;

import retrofit2.Call;

public class ManagerAll extends BaseManager {
    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getOurInstance() {
        return ourInstance;
    }

    public Call<JsonElement> send_notif(NotificationModel mdl) {
       Call<JsonElement> call = getRestApiClient().Send(mdl.getTitle(),mdl.getBody(),mdl.getCategory());
         return call;
    }





}
