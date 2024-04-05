package com.example.aplikasiteman

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.LauncherActivity.ListItem
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition
import java.text.ParsePosition

class RecyclerViewAdapter (private val DataTeman: ArrayList<data_teman>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> () {
    private val context: Context

    //view holder digunakan untuk menyimpan referensi daro view-view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val V: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.view_design, parent, false)
        return ViewHolder(V)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama: String? = DataTeman.get(position).nama
        val Alamat: String? = DataTeman.get(position).alamat
        val NoHP: String? = DataTeman.get(position).no_hp


        //masukkan nilai atau value le dalam view
        holder.Nama.text = "Nama: $Nama"
        holder.Alamat.text = "Alamat: $Alamat"
        holder.NoHP.text = "No Handphone: $NoHP"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                //membuat fungsi edit dan delete
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when (i) {
                            0 -> {
                                //berpindah ke halaman update data untuk ambil data pada listdata
                                val bundle = Bundle()
                                bundle.putString("dataNama", DataTeman[position].nama)
                                bundle.putString("dataAlamat", DataTeman[position].alamat)
                                bundle.putString("dataNoHP", DataTeman[position].no_hp)
                                bundle.putString("getPrimaryKey", DataTeman[position].key)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1 -> {}
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
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