package com.example.android.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(val context: Context, private val items:ArrayList<String>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(binding: ItemHistoryRowBinding)
        : RecyclerView.ViewHolder(binding.root){
        val tvPosition=binding.tvPosition
        val tvItem=binding.tvItem
        val llHistoryMainItem=binding.llHistoryItemMain
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryRowBinding.inflate
            (LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date:String=items[position]
        holder.tvPosition.text=(position+1).toString()
        holder.tvItem.text=date

        if (position % 2 == 0){
            holder.llHistoryMainItem.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        }else{
            holder.llHistoryMainItem.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


}