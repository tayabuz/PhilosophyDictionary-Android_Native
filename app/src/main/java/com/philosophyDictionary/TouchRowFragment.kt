package com.philosophyDictionary

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.philosophyDictionary.ContentManager.position
import com.philosophyDictionary.databinding.FragmentTouchRowBinding


class TouchRowFragment : Fragment() {

    private lateinit var viewModel: TermViewModel
    private lateinit var binding: FragmentTouchRowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = MainActivity.viewModel
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_touch_row, container, false)
        binding.term = viewModel.getLiveData().value!![position]

        (activity as AppCompatActivity).supportActionBar!!.title = binding.term!!.Definition
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu_touch_row, menu)
        val foundTermInFavourite = viewModel.isFavourite(binding.term!!)
        menu.findItem(R.id.term_favourite).setIcon(if (foundTermInFavourite) R.drawable.ic_favourite_24dp else R.drawable.ic_unfavourite_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.term_favourite) {
            viewModel.switchFavourite(binding.term!!)
            val foundTermInFavourite = viewModel.isFavourite(binding.term!!)
            item.setIcon(if (foundTermInFavourite) R.drawable.ic_favourite_24dp else R.drawable.ic_unfavourite_24dp)
            true
        } else super.onOptionsItemSelected(item)
    }
}
