package com.insoft.attsaqofiy;

import com.insoft.attsaqofiy.json.CekinRequestJson;
import com.insoft.attsaqofiy.json.CekinResponseJson;
import com.insoft.attsaqofiy.json.CekoutRequestJson;
import com.insoft.attsaqofiy.json.CekoutResponseJson;
import com.insoft.attsaqofiy.json.JadwalRequestJson;
import com.insoft.attsaqofiy.json.JadwalResponseJson;
import com.insoft.attsaqofiy.json.KeluarRequestJson;
import com.insoft.attsaqofiy.json.KeluarResponseJson;
import com.insoft.attsaqofiy.json.LoginRequestJson;
import com.insoft.attsaqofiy.json.LoginResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterAPI {
    @Headers("Content-Type: application/json")

    @POST("loginguru")
    Call<LoginResponseJson> loginguru(@Body LoginRequestJson param);

    @POST("liststudi")
    Call<JadwalResponseJson> liststudi(@Body JadwalRequestJson param);

    @POST("listmasuk")
    Call<KeluarResponseJson> listkeluar(@Body KeluarRequestJson param);

    @POST("masuk")
    Call<CekinResponseJson> cekin(@Body CekinRequestJson param);

    @POST("keluar")
    Call<CekoutResponseJson> cekout(@Body CekoutRequestJson param);
}
