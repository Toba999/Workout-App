package com.example.android.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)

        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="History"
        }

        binding.toolbarHistoryActivity.setOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allCompletedDateList= dbHandler.getAllCompletedDataList()

        if(allCompletedDateList.size>0){
            binding.tvHistory.visibility=View.VISIBLE
            binding.rvHistory.visibility=View.VISIBLE
            binding.tvNoDataAvailable.visibility=View.GONE

            binding.rvHistory.layoutManager=LinearLayoutManager(this)
            val historyAdapter=HistoryAdapter(this,allCompletedDateList)
            binding.rvHistory.adapter=historyAdapter
        }else{
            binding.tvHistory.visibility=View.GONE
            binding.rvHistory.visibility=View.GONE
            binding.tvNoDataAvailable.visibility=View.VISIBLE

        }
    }
}