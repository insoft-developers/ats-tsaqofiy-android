package com.insoft.attsaqofiy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.insoft.attsaqofiy.adapter.JadwalAdapter;
import com.insoft.attsaqofiy.json.JadwalRequestJson;
import com.insoft.attsaqofiy.json.JadwalResponseJson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class MasukActivity extends AppCompatActivity {
    private RecyclerView rv_masuk;
    private RegisterAPI registerAPI;
    SessionManager sessionManager;
    String idsession, kodesession, gurusession, usrsession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        rv_masuk = findViewById(R.id.rv_masuk);
        registerAPI = UtilsAPI.getApiService();
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getSessionData();
        idsession = user.get(sessionManager.ID);
        kodesession = user.get(sessionManager.KODE);
        gurusession = user.get(sessionManager.GURU);
        usrsession = user.get(sessionManager.USERNAME);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MasukActivity.this);
        rv_masuk.setLayoutManager(layoutManager);
        fetch_data();
    }

    private void fetch_data() {
        JadwalRequestJson param = new JadwalRequestJson();
        param.setIdguru(Integer.parseInt(idsession));
        registerAPI = UtilsAPI.getApiService();
        registerAPI.liststudi(param).enqueue(new Callback<JadwalResponseJson>() {
            @Override
            public void onResponse(Call<JadwalResponseJson> call, retrofit2.Response<JadwalResponseJson> response) {
                if (response.isSuccessful()) {
                    JadwalAdapter adapter = new JadwalAdapter(MasukActivity.this, response.body().getData());
                    adapter.notifyDataSetChanged();
                    rv_masuk.setAdapter(adapter);
                } else {
                    Log.d("REs", response.toString());
                }
            }

            @Override
            public void onFailure(Call<JadwalResponseJson> call, Throwable t) {
                Toast.makeText(MasukActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}