package com.bangkit.nyancat.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ProfileFragment()
            else -> DescriptionFragment()
        }
    }
}