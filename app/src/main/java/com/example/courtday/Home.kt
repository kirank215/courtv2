package com.example.courtday

import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class Home : AppCompatActivity() {
    private lateinit var SearchName: TextView
    private lateinit var Detail1: TextView
    private lateinit var Detail2: TextView
    private lateinit var Detail3: TextView
    private lateinit var Detail4: TextView
    private lateinit var Detail5: TextView
    private lateinit var searchBtn: Button
    private lateinit var todayToggle: ToggleButton
    private lateinit var tomToggle: ToggleButton
    private var infoMap = mutableMapOf<String, Info>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        SearchName = findViewById(R.id.tv_searchname)
        Detail1 = findViewById(R.id.tv_detail1_1)
        Detail2 = findViewById(R.id.tv_detail2_1)
        Detail3 = findViewById(R.id.tv_detail3_1)
        Detail4 = findViewById(R.id.tv_detail4_1)
        Detail5 = findViewById(R.id.tv_detail5_1)

        searchBtn = findViewById(R.id.btn_search)
        todayToggle = findViewById(R.id.btn_today)
        tomToggle = findViewById(R.id.btn_tomorrow)
        val name = intent.getSerializableExtra("NAME") as String
        SearchName.text = name
        val todayDate = getTodaysDate()
        val info = Info("info1Today", "info2Today", "info3Today", "info4Today")



        todayToggle.setOnClickListener {
            if(todayToggle.isChecked) {
                todayToggle.setBackgroundResource(R.drawable.button_selected)
                tomToggle.isChecked = false
                tomToggle.callOnClick()
            }
            else
                todayToggle.setBackgroundResource(R.drawable.button2_bg)
        }

        tomToggle.setOnClickListener {
            if(tomToggle.isChecked) {
                tomToggle.setBackgroundResource(R.drawable.button_selected)
                todayToggle.isChecked = false
                todayToggle.callOnClick()
            }
            else
                tomToggle.setBackgroundResource(R.drawable.button2_bg)
        }


        searchBtn.setOnClickListener{
            val newinfo = getInfo(name)
            if(todayToggle.isChecked) {
                setDetails(info)
            }
            else if(tomToggle.isChecked)
            {
              setDetails(newinfo)
            }

        }
    }

    private fun getInfo(name: String): Info {

        val base_url = "https://www.mhc.tn.gov.in/judis/clists/clists-madras/views/a.php?result="
        val doc = "cause_11022022.xml"

        val date = "2022-02-11"

        val url = base_url + getBase64(doc) + "&cdate=" + getBase64(date) + "&ft=2" + "&fil=" + name


        // alternative: directly parse and search in xml file

        thread {
            val json = try { URL(url).readText() } catch (e: Exception) { return@thread }
            runOnUiThread {
                //parse info from json
            }
        }
        // parse html response
        // display case id + case name + link
        // add notifs
        val newinfo = Info("info1Tomo", "info2Tomo", "info3Tomo", "info4Tomo")
        return newinfo

    }
    fun getBase64(input: String): String? {
        return Base64.encodeToString(input.toByteArray(), Base64.NO_WRAP)
    }

    private fun setDetails(info: Info) {
        Detail1.text = info.component1()
        Detail2.text = info.component2()
        Detail3.text = info.component3()
        Detail4.text = info.component4()
    }

    private fun getTodaysDate(): String {
        val sdf = SimpleDateFormat.getDateInstance()
        return sdf.format(Calendar.getInstance().time)
    }
}