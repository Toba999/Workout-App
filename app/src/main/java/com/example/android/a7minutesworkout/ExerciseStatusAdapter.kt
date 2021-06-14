package com.example.android.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.a7minutesworkout.databinding.ItemExerciseStatusBinding


class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>,val context:Context)
    :RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {



   class ViewHolder(val binding:ItemExerciseStatusBinding ) : RecyclerView.ViewHolder(binding.root){
       val tvItem=binding.tvItem
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate
            (LayoutInflater.from(parent.context),parent,false))
    }
    

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel=items[position]
        holder.binding.tvItem.text=model.getId().toString()

        if (model.getIsSelected()){
            holder.binding.tvItem.background=ContextCompat.
            getDrawable(context,R.drawable.item_circular_thin_color_accent_border)
            holder.binding.tvItem.setTextColor(Color.parseColor("#212121"))
        }else if (model.getIsCompleted()){
            holder.binding.tvItem.background=ContextCompat.
            getDrawable(context,R.drawable.item_circular_color_accent_background)
            holder.binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.binding.tvItem.background=ContextCompat.
            getDrawable(context,R.drawable.item_circular_color_gray_background)
            holder.binding.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int {
        return items.size

    }
}