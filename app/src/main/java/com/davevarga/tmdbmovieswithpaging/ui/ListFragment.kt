package com.davevarga.tmdbmovieswithpaging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbmovieswithpaging.R
import com.davevarga.tmdbmovieswithpaging.R.id.action_listFragment_to_filterFragment
import com.davevarga.tmdbmovieswithpaging.models.Movie
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), MovieClickListener {

    lateinit var swipeLayout: SwipeRefreshLayout
    val args: ListFragmentArgs by navArgs()

    private val viewModel by lazy { ViewModelProviders.of(
        requireActivity(),
        MovieViewModelFactory(requireActivity().application, args.minYear, args.maxYear)
    )
        .get(MovieViewModel::class.java) }

    private val viewModelAdapter = PagingAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.insert(args.minYear, args.maxYear)
        swipeLayout = swipeRefresh
        recycler_view.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(9)
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.getMoviesPaged().observe(viewLifecycleOwner) { pagedList ->
            viewModelAdapter.submitList(pagedList)
            recycler_view.adapter = viewModelAdapter

        }


        filterButton.setOnClickListener { view: View ->
            view.findNavController().navigate(action_listFragment_to_filterFragment)

        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }
//        observeMovieModel()

    }

//    private fun observeMovieModel() {
//        viewModel.pMovieList.observe(viewLifecycleOwner, Observer { items ->
//            items?.apply {
//                viewModelAdapter.items = items
//                recycler_view.adapter = viewModelAdapter
//            }
//        })
//
//    }


    override fun onItemClick(item: Movie, position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }

}