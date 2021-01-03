package ru.vigivn.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vigivn.assignment.databinding.ItemViewBinding
import kotlin.random.Random

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val items: MutableList<Int> = ArrayList()

    private val pool = hashSetOf<Int>()
    private var lastId = 0

    private fun getId(): Int {
        if (pool.isNotEmpty()) {
            val id = pool.minOrNull()!!
            pool.remove(id)
            return id
        }

        return lastId++
    }

    fun setData(data: List<Int>) {
        items.clear()
        items.addAll(data)
        lastId = data.size
        notifyDataSetChanged()
    }

    fun newItem() {
        val index = Random.nextInt(items.size + 1)
        items.add(index, getId())
        notifyItemInserted(index)
    }

    fun removeItem(id: Int) {
        val pos = items.indexOf(id)
        if (pos >= 0) {
            pool.add(id)
            items.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val itemBinding =
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Int) {
            binding.label.text = id.toString()
            binding.btnClose.setOnClickListener {
                removeItem(id)
            }
        }
    }
}