package com.example.courtday

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var tvName: EditText
    private lateinit var btn_cont: Button
    private val sp_name = "savename"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sp = this.getSharedPreferences(sp_name, Context.MODE_PRIVATE)

        val saved_name = sp.getString("name", "")


        tvName = findViewById(R.id.tv_name)
        tvName.setText(saved_name)

        btn_cont = findViewById(R.id.btn_continue)
        btn_cont.setOnClickListener {
            val changePage = Intent(this, Home::class.java)
            val name = tvName.text.toString()

            val sp_editor = sp.edit()
            sp_editor.putString("name", name)
            sp_editor.apply()
            changePage.putExtra("NAME", name)
            startActivity(changePage)
        }
    }
}
