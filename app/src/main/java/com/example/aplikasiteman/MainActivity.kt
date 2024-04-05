package com.example.aplikasiteman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.aplikasiteman.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth:FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inisiasi ID (button)
        binding.logout.setOnClickListener(this)
        binding.save.setOnClickListener(this)
        binding.showData.setOnClickListener(this)

        //mendapatkan Instance Firebase Autentikasi
        auth = FirebaseAuth.getInstance()
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            R.id.save -> {
                //Mendapatkan UserID dari pengguna yang terauth
                val getUserID = auth!!.currentUser!!.uid

                //Mendapatkan instance dari Database
                val database = FirebaseDatabase.getInstance()

                //Mendapatkan Data yang diinputkan User ke dalam variabel
                val getNama: String = binding.nama.getText().toString()
                val getAlamat: String = binding.alamat.getText().toString()
                val getNoHP: String = binding.noHp.getText().toString()

                //Mendapatkan referensi dari database
                val getReference: DatabaseReference
                getReference = database.reference

                //mengecek apakah ada data yang kosong
                if (isEmpty(getNama) || isEmpty(getAlamat) || isEmpty(getNoHP)) {
                    //Jika ada, maka akan menampilkan pesan singkat seperti berikut
                    Toast.makeText(
                        this@MainActivity,
                        "Data tidak boleh ada yang kosong",
                        Toast.LENGTH_SHORT).show()
                } else {
                    //Jika tidak ada, maka menyimpan ke databse sesuai ID masing-masing akun
                    getReference.child("Admin").child(getUserID).child("DataTeman").push()
                        .setValue(data_teman(getNama, getAlamat, getNoHP))
                        .addOnCompleteListener(this) {
                            //bagian isi terjadi ketika user berhasil menyimpan data
                            binding.nama.setText("")
                            binding.alamat.setText("")
                            binding.noHp.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.logout -> {
                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    })
            }
            R.id.show_data -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}