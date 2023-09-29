package com.example.regions_app.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.regions_app.App
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.databinding.FragmentRegionListBinding
import com.example.regions_app.ui.adapter.RegionsAdapter
import com.example.regions_app.utils.Status
import com.example.regions_app.viewmodel.RegionsListViewModel
import com.google.gson.Gson
import javax.inject.Inject


class RegionListFragment : Fragment() {

    private var _binding: FragmentRegionListBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: RegionsListViewModel

    private lateinit var adapter: RegionsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
        if (viewModel.isEmpty()) {
            viewModel.getRegions()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        binding.apply {
            adapter =
                RegionsAdapter({ region -> onRegionClick(region) }, mutableListOf(), mutableMapOf())
            rvMain.layoutManager = LinearLayoutManager(context)
            rvMain.adapter = adapter

            pullToRefresh.setOnRefreshListener {
                viewModel.getRegions()
            }
        }
    }

    private fun setupObserver() {
        viewModel.regionsList.observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.pullToRefresh.isRefreshing = true
                }

                Status.SUCCESS -> {
                    binding.pullToRefresh.isRefreshing = false
                    it.data?.let { it1 -> renderList(it1, viewModel.liked) }
                }

                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun onRegionClick(region: RegionModel) {
        val action = RegionListFragmentDirections.actionRegionsListFragmentToRegionFragment(
            Gson().toJson(region),
            viewModel.liked[region.title]!!
        )
        findNavController().navigate(action)
    }

    private fun renderList(regions: List<RegionModel>, likes: MutableMap<String, Boolean>) {
        adapter.clear()
        adapter.addData(regions)
        adapter.updateLikes(likes)
        adapter.notifyDataSetChanged()
    }
}