package com.example.aplikasiteman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasiteman.databinding.ActivityMyListDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyListData : AppCompatActivity() {
    //deklarasikan variabel ubtuk RC
    private var recyclerView : RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    //deklarasi variabel DB Refrence & Arraylist dengan parameter ClassModel
    val database = FirebaseDatabase.getInstance()
    private var DataTeman = ArrayList<data_teman>()
    private var auth: FirebaseAuth? = null

    private lateinit var binding: ActivityMyListDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.datalist)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar!!.title = "Data Teman"
        auth = FirebaseAuth.getInstance()
        MyRecyclerView()
        GetData()
    }

    //kode untuk mengambil data databse & menamplikan ke dalam adapter
    private fun GetData(){
        Toast.makeText(applicationContext, "Mohon tunggu sebentar...", Toast.LENGTH_LONG).show()
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        getReference.child("Admin").child(getUserID).child("DataTeman")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (snapshot in dataSnapshot.children) {
                        //mapping data pada DataSnapshot ke dalam objek DataTeman
                        val teman = snapshot.getValue(data_teman::class.java)
                        //mengambil Primaary Key untuk proses update/delete
                        teman?.key = snapshot.key
                        DataTeman.add(teman!!)
                    }
                    //Inisialisasi adapter dan data teman dalam bentuk array
                    adapter = RecyclerViewAdapter(DataTeman, this@MyListData)
                    //memasang adapter pada RC
                    recyclerView?.adapter = adapter
                    (adapter as RecyclerViewAdapter).notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Data berhasil dimuat", Toast.LENGTH_LONG).show()
                }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //kode ini dijalankan ketika error, disimpan ke logcat
                    Toast.makeText(applicationContext, "Data Gagal dimuat", Toast.LENGTH_LONG).show()
                    Log.e("MyListActivity", databaseError.details +" "+ databaseError.message)
                }
            })
    }
    //baris kode untuk mengatur RC
    private fun MyRecyclerView(){
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        //buat garis bawah setiap item data
        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.line)!!)
        recyclerView?.addItemDecoration(itemDecoration)
    }
}