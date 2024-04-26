package com.bangkit.nyancat.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bangkit.nyancat.R
import com.bangkit.nyancat.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getImageId = activity?.intent?.getStringExtra(DetailActivity.EXTRA_CAT_AVATAR)
        viewModel.setDetailProfileData(getImageId ?: "")
        observeData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() = with(binding) {
        viewModel.isProfileError.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvErrorPro.visibility =  View.VISIBLE
                binding.viewProfileContent.isVisible = false
            } else {
                binding.tvErrorPro.visibility =  View.GONE
                binding.viewProfileContent.isVisible = true
            }
        }
        viewModel.isProfileLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbProfile.visibility = View.VISIBLE
                binding.viewProfileContent.isVisible = false
            } else {
                binding.pbProfile.visibility = View.GONE
                binding.viewProfileContent.isVisible = true
            }
        }
        viewModel.profileData.observe(viewLifecycleOwner) {
            val profile = it.breeds?.first()
            viewProfileContent.isVisible = true
            tvAdaptable.text = "Adaptability Rate: ${profile?.adaptability ?: "-"}"
            tvAffection.text = "Affection Level: ${profile?.affectionLevel ?: "-"}"
            tvChildfriendly.text = "Child Friendly Rate: ${profile?.childFriendly ?: "-"}"
            tvEnergy.text = "Energic Rate: ${profile?.energyLevel ?: "-"}"
            tvHealthIssue.text = "Health Issue Rate: ${profile?.healthIssues ?: "-"}"
            tvIntelligence.text = "Intelligence Rate: ${profile?.intelligence ?: "-"}"
            tvLifespan.text = "Life Span (Year): ${profile?.lifeSpan ?: "-"}"
            tvOrigin.text = "Origin (Country): ${profile?.origin ?: "-"}"
            tvSocialNeeds.text = "Social Needs Rate: ${profile?.socialNeeds ?: "-"}"
            tvStrangerfriendly.text = "Stranger Friendly Rate: ${profile?.strangerFriendly ?: "-"}"
            tvTemprament.text = profile?.temperament ?: "-"
        }
    }
}