package com.insoft.attsaqofiy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.insoft.attsaqofiy.CekinActivity;
import com.insoft.attsaqofiy.CekoutActivity;
import com.insoft.attsaqofiy.R;
import com.insoft.attsaqofiy.model.Jadwal;
import com.insoft.attsaqofiy.model.Keluar;

import java.util.List;

public class KeluarAdapter extends RecyclerView.Adapter<KeluarAdapter.ViewHolder>  {

    private Context context;
    private List<Keluar> datajadwal;

    public KeluarAdapter(Context context, List<Keluar> datajadwal) {
        this.context = context;
        this.datajadwal = datajadwal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keluar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtkelas.setText(datajadwal.get(position).getKelas());
        holder.txtstudi.setText(datajadwal.get(position).getBidang_studi());
        holder.txtjam.setText(datajadwal.get(position).getJam_masuk()+"-"+datajadwal.get(position).getJam_keluar());
        holder.txtjamguru.setText(datajadwal.get(position).getGuru_masuk()+"-"+datajadwal.get(position).getGuru_keluar());
        holder.txtguru.setText(datajadwal.get(position).getGuru());
        holder.txtketerangan.setText(datajadwal.get(position).getKeterangan());

        holder.rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CekoutActivity.class);
                intent.putExtra("id_absensi", datajadwal.get(position).getId());
                intent.putExtra("id_studi", datajadwal.get(position).getId_studi());
                intent.putExtra("id_kelas", datajadwal.get(position).getId_kelas());
                intent.putExtra("kode_kelas", datajadwal.get(position).getKode_kelas());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datajadwal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txtkelas, txtstudi, txtjam, txtguru, txtjamguru, txtketerangan;
        private LinearLayout rootlayout;


        public ViewHolder(View itemView) {
            super(itemView);

            txtkelas = itemView.findViewById(R.id.txt_kelas);
            txtstudi = itemView.findViewById(R.id.txt_pelajaran);
            txtjam = itemView.findViewById(R.id.txt_jam);
            txtguru = itemView.findViewById(R.id.txt_guru);
            txtjamguru = itemView.findViewById(R.id.txt_jam_guru);
            txtketerangan = itemView.findViewById(R.id.txt_keterangan);
            rootlayout = itemView.findViewById(R.id.rootlayout);

        }
    }

}

