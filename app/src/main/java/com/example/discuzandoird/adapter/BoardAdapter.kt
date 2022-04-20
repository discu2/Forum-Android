package com.example.discuzandoird.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.contains
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.discuzandoird.R
import com.example.discuzandoird.bean.BoardBean
import com.example.discuzandoird.databinding.BoardCellBinding
import com.example.discuzandoird.databinding.FragmentHomeBinding

class BoardAdapter : ListAdapter<BoardBean, BoardAdapter.BoardViewHolder>(DIFFCALLBACK) {

    class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val holder = BoardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.board_cell,
                parent, false
            )
        )


        return holder
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {

        val button = holder.itemView.findViewById<Button>(R.id.boardCellButton)
        button.text = getItem(position).name
        button.setOnClickListener {

            println("pypy")
        }

        holder.itemView.setOnClickListener {
            println("bbb")
        }

    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<BoardBean>() {
        override fun areItemsTheSame(oldItem: BoardBean, newItem: BoardBean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BoardBean, newItem: BoardBean): Boolean {
            return oldItem.id == newItem.id
        }
    }

}