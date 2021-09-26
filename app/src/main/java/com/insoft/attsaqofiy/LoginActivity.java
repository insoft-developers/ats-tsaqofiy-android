package com.insoft.attsaqofiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.insoft.attsaqofiy.json.LoginRequestJson;
import com.insoft.attsaqofiy.json.LoginResponseJson;
import com.insoft.attsaqofiy.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText etusername, etpassword;
    private ProgressBar loading;
    private Button btn_login;
    private RegisterAPI registerAPI;
    private List<User> user;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerAPI = UtilsAPI.getApiService();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        etusername = findViewById(R.id.et_username);
        etpassword = findViewById(R.id.et_password);
        loading = findViewById(R.id.loading);
        btn_login = findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                if(username.isEmpty()) {
                    etusername.setError("Username Tidak Boleh Kosong...");
                }
                else if(password.isEmpty()) {
                    etpassword.setError("Password Tidak Boleh Kosong...");
                }
                else {
                    proses_login(username, password);
                }
            }
        });
    }

    private void proses_login(String username, String password) {
        loading.setVisibility(View.VISIBLE);
        LoginRequestJson param = new LoginRequestJson();
        param.setUsername_guru(username);
        param.setPassword_guru(password);

        registerAPI = UtilsAPI.getApiService();
        registerAPI.loginguru(param).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(Call<LoginResponseJson> call, retrofit2.Response<LoginResponseJson> response) {
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    if(status.equalsIgnoreCase("1")) {
                        user = response.body().getData();
                        String id = String.valueOf(user.get(0).getId());
                        String kode = user.get(0).getKode();
                        String guru = user.get(0).getGuru();
                        String usrname = user.get(0).getUsername();
                        sessionManager.createSession(id, kode, guru, usrname);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Gagal Periksa Kembali Username dan Password Anda...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("REs", response.toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponseJson> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}