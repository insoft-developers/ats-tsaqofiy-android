package com.insoft.attsaqofiy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.insoft.attsaqofiy.adapter.JadwalAdapter;
import com.insoft.attsaqofiy.adapter.KeluarAdapter;
import com.insoft.attsaqofiy.json.JadwalRequestJson;
import com.insoft.attsaqofiy.json.JadwalResponseJson;
import com.insoft.attsaqofiy.json.KeluarRequestJson;
import com.insoft.attsaqofiy.json.KeluarResponseJson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class KeluarActivity extends AppCompatActivity {
    private RecyclerView rv_keluar;
    private RegisterAPI registerAPI;
    SessionManager sessionManager;
    String idsession, kodesession, gurusession, usrsession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluar);
        rv_keluar = findViewById(R.id.rv_keluar);
        registerAPI = UtilsAPI.getApiService();
        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getSessionData();
        idsession = user.get(sessionManager.ID);
        kodesession = user.get(sessionManager.KODE);
        gurusession = user.get(sessionManager.GURU);
        usrsession = user.get(sessionManager.USERNAME);

        LinearLayoutManager layoutManager = new LinearLayoutManager(KeluarActivity.this);
        rv_keluar.setLayoutManager(layoutManager);
        fetch_data();
    }

    private void fetch_data() {
        KeluarRequestJson param = new KeluarRequestJson();
        param.setIdguru(Integer.parseInt(idsession));
        registerAPI = UtilsAPI.getApiService();
        registerAPI.listkeluar(param).enqueue(new Callback<KeluarResponseJson>() {
            @Override
            public void onResponse(Call<KeluarResponseJson> call, retrofit2.Response<KeluarResponseJson> response) {
                if (response.isSuccessful()) {
                    KeluarAdapter adapter = new KeluarAdapter(KeluarActivity.this, response.body().getData());
                    adapter.notifyDataSetChanged();
                    rv_keluar.setAdapter(adapter);
                } else {
                    Log.d("REs", response.toString());
                }
            }

            @Override
            public void onFailure(Call<KeluarResponseJson> call, Throwable t) {
                Toast.makeText(KeluarActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}