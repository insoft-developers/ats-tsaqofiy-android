package com.insoft.attsaqofiy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.Result;
import com.insoft.attsaqofiy.adapter.JadwalAdapter;
import com.insoft.attsaqofiy.json.CekinRequestJson;
import com.insoft.attsaqofiy.json.CekinResponseJson;
import com.insoft.attsaqofiy.json.JadwalRequestJson;
import com.insoft.attsaqofiy.json.JadwalResponseJson;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;

public class CekinActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private FusedLocationProviderClient client;
    private String lati , longi;
    SessionManager sessionManager;
    private RegisterAPI registerAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        sessionManager = new SessionManager(this);
        registerAPI = UtilsAPI.getApiService();

        if (ActivityCompat.checkSelfPermission(CekinActivity.this, CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekinActivity.this,
                    new String[]{Manifest.permission.CAMERA},1);
        }

        if (ActivityCompat.checkSelfPermission(CekinActivity.this, ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekinActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        client = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void handleResult(Result result) {
        if (ActivityCompat.checkSelfPermission(CekinActivity.this, CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekinActivity.this,
                    new String[]{Manifest.permission.CAMERA},1);
        }

        if (ActivityCompat.checkSelfPermission(CekinActivity.this, ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekinActivity.this,
                    new String[]{ACCESS_FINE_LOCATION},1);
        }

        client.getLastLocation().addOnSuccessListener(CekinActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null){
                    lati = String.valueOf(location.getLatitude());
                    longi = String.valueOf(location.getLongitude());

                }else{
                    lati =  "10";
                    longi = "10";
                }

                HashMap<String, String> user = sessionManager.getSessionData();

                String idguru = user.get(sessionManager.ID);
                String kodeguru = user.get(sessionManager.KODE);
                String namaguru = user.get(sessionManager.GURU);
                String usrguru = user.get(sessionManager.USERNAME);
                String kodekelas = result.getText();
                int idstudi = getIntent().getIntExtra("id_studi", 0);
                int idkelas = getIntent().getIntExtra("id_kelas", 0);
                prosescekin(idguru, kodekelas, idstudi, idkelas, lati, longi );

//                Toast.makeText(CekinActivity.this, "Data: "+kodeguru+"-"+kodekelas+"-"+lati+"-"+longi, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void prosescekin(String idguru, String kodekelas, int idstudi, int idkelas, String lati, String longi) {
        CekinRequestJson param = new CekinRequestJson();
        param.setIdguru(Integer.parseInt(idguru));
        param.setKodekelas(kodekelas);
        param.setIdstudi(idstudi);
        param.setIdkelas(idkelas);
        param.setLatitude(lati);
        param.setLongitude(longi);

        registerAPI = UtilsAPI.getApiService();
        registerAPI.cekin(param).enqueue(new Callback<CekinResponseJson>() {
            @Override
            public void onResponse(Call<CekinResponseJson> call, retrofit2.Response<CekinResponseJson> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    String pesan = response.body().getMessage();
                    if(status.equalsIgnoreCase("1")) {
                        mScannerView.stopCamera();
                        Intent intent = new Intent(CekinActivity.this, SuksesActivity.class);
                        intent.putExtra("keterangancekin", pesan);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        mScannerView.stopCamera();
                        Toast.makeText(CekinActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("REs", response.toString());
                    Toast.makeText(CekinActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CekinResponseJson> call, Throwable t) {
                Toast.makeText(CekinActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


}