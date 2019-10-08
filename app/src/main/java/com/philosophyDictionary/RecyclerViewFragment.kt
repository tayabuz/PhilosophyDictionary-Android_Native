package com.philosophyDictionary

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.philosophyDictionary.databinding.FragmentRecycleviewBinding


class RecyclerViewFragment : Fragment() {
    companion object{
        lateinit var adapter: RecyclerViewAdapter
    }
    private lateinit var binding: FragmentRecycleviewBinding
    private lateinit var viewModel: TermViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainActivity.viewModel
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycleview, container, false)

        val recyclerView = binding.rvTerms

        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getLiveData().value
        adapter = RecyclerViewAdapter(activity!! as MainActivity, viewModel)
        recyclerView.adapter = adapter
        adapter.setClickListener(
            MyClick(activity)
        )
        binding.adapter = adapter
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_recycleview, menu)
        val menuView = menu.findItem(R.id.term_search)
        val searchView = menuView.actionView as SearchView
        searchView.onActionViewExpanded()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchFilter(query)
                binding.adapter = adapter
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.searchFilter(query)
                binding.adapter = adapter
                return true
            }
        })
    }

    private var isFavouriteEnabled: Boolean = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.show_favourite) {
            if(!isFavouriteEnabled){
                viewModel.favouriteFilter()
                item.title = getString(R.string.show_all)
                isFavouriteEnabled = true
            }
            else{
                item.title = getString(R.string.favourite)
                viewModel.resetFilter()
                isFavouriteEnabled = false
            }
            true
        } else super.onOptionsItemSelected(item)
    }

    class MyClick(private val fractivity: FragmentActivity?) :
        RecyclerViewAdapter.ItemClickListener {

        override fun onItemClick(view: View, position: Int) {
            if(fractivity != null){
                ContentManager.position = position
                val navController = Navigation.findNavController(fractivity, R.id.nav_host_fragment)
                navController.navigate(R.id.touchRowFragment)
            }
        }
    }
}
