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
import com.insoft.attsaqofiy.json.CekoutRequestJson;
import com.insoft.attsaqofiy.json.CekoutResponseJson;
import com.insoft.attsaqofiy.json.JadwalRequestJson;
import com.insoft.attsaqofiy.json.JadwalResponseJson;
import com.insoft.attsaqofiy.json.KeluarRequestJson;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission_group.CAMERA;

public class CekoutActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
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

        if (ActivityCompat.checkSelfPermission(CekoutActivity.this, CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekoutActivity.this,
                    new String[]{Manifest.permission.CAMERA},1);
        }

        if (ActivityCompat.checkSelfPermission(CekoutActivity.this, ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekoutActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        client = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void handleResult(Result result) {
        if (ActivityCompat.checkSelfPermission(CekoutActivity.this, CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekoutActivity.this,
                    new String[]{Manifest.permission.CAMERA},1);
        }

        if (ActivityCompat.checkSelfPermission(CekoutActivity.this, ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(CekoutActivity.this,
                    new String[]{ACCESS_FINE_LOCATION},1);
        }

        client.getLastLocation().addOnSuccessListener(CekoutActivity.this, new OnSuccessListener<Location>() {
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
                String kodescan = result.getText();


                int idabsen = getIntent().getIntExtra("id_absensi", 0);
                int idstudi = getIntent().getIntExtra("id_studi", 0);
                int idkelas = getIntent().getIntExtra("id_kelas", 0);
                String kodekelas = getIntent().getStringExtra("kode_kelas");

                if(!kodekelas.equalsIgnoreCase(kodescan)) {
                    Toast.makeText(CekoutActivity.this, "Kelas Tidak Sesuai", Toast.LENGTH_SHORT).show();
                    mScannerView.stopCamera();
                } else {
                    prosescekout(idabsen, idguru, kodekelas, idstudi, idkelas, lati, longi);
                }

            }
        });
    }

    private void prosescekout(int idabsen, String idguru, String kodekelas, int idstudi, int idkelas, String lati, String longi) {
        CekoutRequestJson param = new CekoutRequestJson();
        param.setIdabsen(idabsen);
        param.setIdguru(Integer.parseInt(idguru));
        param.setKodekelas(kodekelas);
        param.setIdstudi(idstudi);
        param.setIdkelas(idkelas);
        param.setLatitude(lati);
        param.setLongitude(longi);

        registerAPI = UtilsAPI.getApiService();
        registerAPI.cekout(param).enqueue(new Callback<CekoutResponseJson>() {
            @Override
            public void onResponse(Call<CekoutResponseJson> call, retrofit2.Response<CekoutResponseJson> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    if(status.equalsIgnoreCase("1")) {
                        mScannerView.stopCamera();
                        Intent intent = new Intent(CekoutActivity.this, SuksesOutActivity.class);
                        intent.putExtra("keterangancekin", pesan);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {

                        Toast.makeText(CekoutActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        mScannerView.stopCamera();
                    }

                } else {
                    Log.d("REs", response.toString());
                    Toast.makeText(CekoutActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CekoutResponseJson> call, Throwable t) {
                Toast.makeText(CekoutActivity.this, "System error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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