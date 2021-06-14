package com.example.android.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.a7minutesworkout.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFinishActivity)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarFinishActivity.setOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }
        addDateToDatabase()

    }

    private fun addDateToDatabase(){
        val calendar=Calendar.getInstance()
        val dateTime=calendar.time
        Log.i("date",""+dateTime)

        val sdf=SimpleDateFormat("dd MMM yyyy  HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)
        val dbHandler=SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("date","added")
    }


}