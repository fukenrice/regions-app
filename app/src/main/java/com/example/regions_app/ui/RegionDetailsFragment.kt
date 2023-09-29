package com.example.regions_app.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.regions_app.App
import com.example.regions_app.R
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.databinding.FragmentRegionDetailsBinding
import com.example.regions_app.ui.adapter.ImagesAdapter
import com.example.regions_app.viewmodel.RegionDetailsViewModel
import com.google.gson.Gson
import javax.inject.Inject

class RegionDetailsFragment : Fragment() {

    private var _binding: FragmentRegionDetailsBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: RegionDetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.region = Gson().fromJson(it.getString("region").toString(), RegionModel::class.java)
        }
        viewModel.initLike()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupObserver() {
        viewModel.liked.observe(requireActivity(), Observer {

            when (it) {
                true -> {
                    binding.ivRegionLiked.setImageResource(R.drawable.ic_heart_on)
                }
                false -> {
                    binding.ivRegionLiked.setImageResource(R.drawable.ic_heart_off)
                }
            }
        })
    }

    private fun setupView() {
        binding.apply {
            viewpagerImages.adapter = ImagesAdapter(
                requireContext(),
                viewModel.region!!.thumbUrls
            )
            tvViews.text = getString(R.string.views_count, viewModel.region!!.viewsCount.toString())
            ivRegionLiked.setOnClickListener {
                viewModel.setLiked()
            }
        }
    }
}