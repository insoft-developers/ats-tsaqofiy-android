package com.insoft.attsaqofiy.model;

public class Jadwal {
    private int id;
    private int id_kelas;
    private int id_studi;
    private int id_guru;
    private String hari;
    private String jam_masuk;
    private String jam_keluar;
    private String bidang_studi;
    private String kelas;
    private String guru;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(int id_kelas) {
        this.id_kelas = id_kelas;
    }

    public int getId_studi() {
        return id_studi;
    }

    public void setId_studi(int id_studi) {
        this.id_studi = id_studi;
    }

    public int getId_guru() {
        return id_guru;
    }

    public void setId_guru(int id_guru) {
        this.id_guru = id_guru;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public String getJam_keluar() {
        return jam_keluar;
    }

    public void setJam_keluar(String jam_keluar) {
        this.jam_keluar = jam_keluar;
    }

    public String getBidang_studi() {
        return bidang_studi;
    }

    public void setBidang_studi(String bidang_studi) {
        this.bidang_studi = bidang_studi;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getGuru() {
        return guru;
    }

    public void setGuru(String guru) {
        this.guru = guru;
    }
}
