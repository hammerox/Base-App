package com.mcustodio.baseapp.view.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcustodio.baseapp.R
import com.mcustodio.baseapp.model.example.Example
import com.mcustodio.baseapp.utils.switchVisibility
import com.mcustodio.baseapp.utils.toString
import kotlinx.android.synthetic.main.item_counter.view.*



class MainAdapter : RecyclerView.Adapter<MainAdapter.CounterViewHolder>() {

    var onItemClick: ((Example) -> Unit)? = null
    var onItemLongClick: ((Example) -> Unit)? = null

    var data : List<Example> = listOf()
        set(value) {
            field = value.sortedByDescending { it.date }
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_counter, parent, false)
        return CounterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        val resistance = data[position]
        holder.setView(resistance)
        holder.setClickListener(onItemClick, resistance)
        holder.setLongClickListener(onItemLongClick, resistance)
    }



    class CounterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val card = view.card_counteritem
        private val date = view.text_counteritem_date
        private val description = view.text_counteritem_description

        fun setView(example: Example) {
            date.text = example.date?.toString("HH:mm (dd/MM/yyyy)") ?: ""
            description.text = example.description
            description.switchVisibility(!example.description.isNullOrBlank())
        }

        fun setClickListener(onItemClick: ((Example) -> Unit)?, example: Example) {
            card.setOnClickListener { onItemClick?.invoke(example) }
        }

        fun setLongClickListener(onItemLongClick: ((Example) -> Unit)?, example: Example) {
            card.setOnLongClickListener {
                onItemLongClick?.invoke(example)
                true
            }
        }
    }
}