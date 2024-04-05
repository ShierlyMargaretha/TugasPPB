package com.example.aplikasiteman

import android.annotation.SuppressLint
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (private val DataTeman: ArrayList<data_teman>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> (){
    private val context: Context

    //view holder digunakan untuk menyimpan referensi daro view-view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Nama: TextView
        val Alamat: TextView
        val NoHP: TextView
        val ListItem: LinearLayout

        //meninisialisasi view yang terpasang pada layout RecyclerView
        init {
            Nama = itemView.findViewById(R.id.namax)
            Alamat = itemView.findViewById(R.id.alamatx)
            NoHP = itemView.findViewById(R.id.no_hpx)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    //membuat view untuk menyiapkan dan memasang layout yg digunakan pada RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.view_design, parent, false)
        return ViewHolder(V)
    }

    @SuppressLint("SetTextI18n")
    //mengambil nilai atau value pada RC berdasarkan posisi tertentu
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama: String? = DataTeman.get(position).nama
        val Alamat: String? = DataTeman.get(position).alamat
        val NoHP: String? = DataTeman.get(position).no_hp

    //masukkan nilai atau value le dalam view
        holder.Nama.text = "Nama: $Nama"
        holder.Alamat.text = "Alamat: $Alamat"
        holder.NoHP.text = "No Handphone: $NoHP"
        holder.ListItem.setOnLongClickListener(
            object :View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    //materi mingdep
                    return true
                }
            })
        }
    //menghitung Ukuran/jumlah Data yng akan ditampilkan pada RC
    override fun getItemCount(): Int {
        return DataTeman.size
    }

    //membuat konstruktor, untuk menerima input dr databse
    init {
        this.context = context
    }
    }