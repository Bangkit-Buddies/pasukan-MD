package com.bangkit.nyancat.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bangkit.nyancat.R
import com.bangkit.nyancat.databinding.FragmentDescriptionBinding
import com.bumptech.glide.Glide

class DescriptionFragment : Fragment() {

    private var _binding : FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getImageId = activity?.intent?.getStringExtra(DetailActivity.EXTRA_CAT_AVATAR)
        viewModel.setDetailDescriptionData(getImageId ?: "")
        observeData()
    }

    private fun observeData() {
        viewModel.isDescriptionError.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvErrordesc.visibility =  View.VISIBLE
                binding.tvDescription.isVisible = false
            } else {
                binding.tvErrordesc.visibility =  View.GONE
                binding.tvDescription.isVisible = true
            }
        }
        viewModel.isDescriptionLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbDesc.visibility = View.VISIBLE
                binding.tvDescription.isVisible = false
            } else {
                binding.pbDesc.visibility = View.GONE
                binding.tvDescription.isVisible = true
            }
        }
        viewModel.descriptionData.observe(viewLifecycleOwner) {
            binding.tvDescription.isVisible = true
            binding.tvDescription.text = it.breeds?.first()?.description ?: ""
        }
    }
}