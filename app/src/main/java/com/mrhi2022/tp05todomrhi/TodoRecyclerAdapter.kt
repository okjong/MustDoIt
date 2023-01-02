package com.mrhi2022.tp05todomrhi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrhi2022.tp05todomrhi.databinding.RecyclerviewItemTodoBinding

class TodoRecyclerAdapter constructor(val context:Context, var todoItems:MutableList<TodoItem>) : RecyclerView.Adapter<TodoRecyclerAdapter.VH>() {

    val categoryIcons:Array<Int> = arrayOf(
        R.drawable.profle,
        R.drawable.ic_baseline_laptop_chromebook_24,
        R.drawable.ic_baseline_menu_book_24,
        R.drawable.ic_baseline_self_improvement_24,
        R.drawable.ic_baseline_color_lens_24,
        R.drawable.ic_baseline_groups_24,
        R.drawable.ic_baseline_bubble_chart_24
    )

    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val binding: RecyclerviewItemTodoBinding = RecyclerviewItemTodoBinding.bind(itemView)
        init {
            binding.root.setOnClickListener {

                (context as TodoActivity).showBottomSheet(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView:View = layoutInflater.inflate(R.layout.recyclerview_item_todo, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.todoItemTvTitle.text= todoItems.get(position).title
        holder.binding.todoItemTvDate.text= todoItems[position].date
        holder.binding.todoItemIvCategory.setImageResource(categoryIcons[ todoItems[position].category ])
    }


    override fun getItemCount(): Int = todoItems.size


}