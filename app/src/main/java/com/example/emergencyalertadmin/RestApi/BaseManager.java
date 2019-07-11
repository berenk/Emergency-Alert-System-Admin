package com.example.emergencyalertadmin.RestApi;

public class BaseManager {
    protected RestApi getRestApiClient() {
        RestApiClient restApiClient = new RestApiClient(BaseUrl.main_URL);
        return restApiClient.getRestApi();
    }
}
