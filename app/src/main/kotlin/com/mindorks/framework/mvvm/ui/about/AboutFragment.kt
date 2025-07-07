package com.mindorks.framework.mvvm.ui.about

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mindorks.framework.mvvm.databinding.FragmentAboutBinding
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import com.mindorks.framework.mvvm.ui.main.MainActivity

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    companion object {
        const val TAG = "AboutFragment"
        
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        binding.navBackBtn.setOnClickListener {
            (activity as? MainActivity)?.onFragmentDetached(TAG)
        }
    }
}