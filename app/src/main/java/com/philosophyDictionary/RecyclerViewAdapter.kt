package com.philosophyDictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.philosophyDictionary.databinding.RecyclerviewRowBinding


class RecyclerViewAdapter
internal constructor(private val activity: MainActivity, private var viewModel: TermViewModel) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(activity)
    private var mClickListener: ItemClickListener? = null
    init{
        viewModel.getLiveData().observe(this.activity, Observer {
            notifyDataSetChanged()
        })
        viewModel.getFavouriteLiveData().observe(this.activity, Observer {
            notifyDataSetChanged()
        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerviewRowBinding = DataBindingUtil.inflate(
            mInflater, R.layout.recyclerview_row, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val terms = viewModel.getLiveData().value
        val term = terms!![position]
        holder.bind(term)
    }

    override fun getItemCount(): Int {
        return viewModel.getLiveData().value?.size!!
    }

    public fun isDataExist():Boolean{
        return itemCount != 0
    }

    inner class ViewHolder(private var binding: RecyclerviewRowBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener, View.OnLongClickListener {
        override fun onLongClick(p0: View?): Boolean {
            viewModel.switchFavourite(viewModel.getLiveData().value!![adapterPosition])
            return true
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        fun bind(item: Term) {
            binding.term = item
            binding.viewModel = viewModel
            binding.notifyChange()
            binding.executePendingBindings()
        }
    }

    internal fun getItem(id: Int): Term? {
        return viewModel.getLiveData().value?.get(id)
    }

    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}