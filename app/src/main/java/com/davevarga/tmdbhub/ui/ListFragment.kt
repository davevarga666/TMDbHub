package com.davevarga.tmdbhub.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.davevarga.tmdbhub.R
import com.davevarga.tmdbhub.databinding.FragmentListBinding
import com.davevarga.tmdbhub.models.Movie
import com.davevarga.tmdbhub.network.GetData
import com.davevarga.tmdbhub.network.ServiceBuilder
import com.davevarga.tmdbhub.repository.NetworkRepository
import io.reactivex.disposables.CompositeDisposable

class ListFragment : Fragment(), MovieClickListener {

    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var binding: FragmentListBinding
    private lateinit var networkRepository: NetworkRepository
    private val args: ListFragmentArgs by navArgs()
    private val compositeDisposable = CompositeDisposable()
    private val movieAdapter = MoviePagedlistAdapter(this)

    private val movieViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            MovieViewModelFactory(
                requireActivity().application, networkRepository, args.minYear, args.maxYear, args.genres
            )
        )
            .get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().setTitle("Home")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: GetData = ServiceBuilder.getNetworkClient(GetData::class.java)
        networkRepository = NetworkRepository(apiService)

        movieViewModel.refresh()
        movieViewModel.moviePagedList = networkRepository.fetchLiveMoviePagedList(compositeDisposable, args.minYear, args.maxYear, args.genres)
        hideKeyboard()
        swipeLayout = binding.swipeRefresh
        setupRecyclerView()
        observe()
        setBindings()

    }

    private fun setBindings() {
        binding.swipeRefresh.setOnRefreshListener {
            movieViewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observe() {
        movieViewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)

        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
            layoutManager = GridLayoutManager(
                context,
                2,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onItemClick(item: Movie, position: Int) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(item)
        findNavController().navigate(action)

    }


}