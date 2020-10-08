//package com.droiddevstar.historyline.fragments
//
//
//import androidx.fragment.app.Fragment
//import by.kirich1409.viewbindingdelegate.viewBinding
//import com.droiddevstar.historyline.R
//import com.droiddevstar.historyline.databinding.FragmentProfileBinding
//
//class ProfileFragment : Fragment(R.layout.fragment_profile) {
//    private val viewBindingUsingReflection: FragmentProfileBinding by viewBinding()
//
//    private val viewBindingWithoutReflection by viewBinding {fragment ->
//        FragmentProfileBinding.bind(fragment.requireView())
//    }
//
//    override fun onResume() {
//        super.onResume()
//        viewBindingUsingReflection.tvText.text = "EDITED"
//    }
//}