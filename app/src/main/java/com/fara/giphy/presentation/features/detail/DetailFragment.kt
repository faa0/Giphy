package com.fara.giphy.presentation.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.fara.giphy.databinding.FragmentDetailBinding
import com.fara.giphy.presentation.base.BaseFragment
import com.fara.giphy.presentation.features.detail.adapter.DetailPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {

    override val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var adapter: DetailPagingAdapter? = null

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) restorePagerPosition()
    }

    override fun initialize() {
        initRecycler()
    }

    override fun setupSubscribers() {
        subscribeToGifs()
    }

    private fun subscribeToGifs() {
        observe(viewModel.gifsFlow) { pagingData ->
            adapter?.submitData(pagingData)
        }
    }

    private fun restorePagerPosition() {
        binding.viewPagerGif.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.viewPagerGif.setCurrentItem(args.offset, false)
                binding.viewPagerGif.unregisterOnPageChangeCallback(this)
            }
        })
    }

    private fun initRecycler() {
        adapter = DetailPagingAdapter()
        binding.viewPagerGif.adapter = adapter
    }
}
