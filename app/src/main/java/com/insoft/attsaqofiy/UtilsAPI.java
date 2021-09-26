package com.insoft.attsaqofiy;

public class UtilsAPI {

    public static final String BASE_ROOT_URL = "http://192.168.0.105/pesantren/index.php/api/Login/";

    public static RegisterAPI getApiService() {
        return RetrofitClient.getClient(BASE_ROOT_URL).create(RegisterAPI.class);
    }

}
