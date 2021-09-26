package com.insoft.attsaqofiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuksesOutActivity extends AppCompatActivity {
    private Button btnok;
    private TextView keterangancekin;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_out);

        btnok = findViewById(R.id.btnok);
        keterangancekin = findViewById(R.id.keterangancekin);
        String ket = getIntent().getStringExtra("keterangancekin");
        keterangancekin.setText(ket);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuksesOutActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}