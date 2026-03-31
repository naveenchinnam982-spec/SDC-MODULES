package project.module_3handson1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import project.module_3handson1.databinding.FragmentProfileDetailBinding

class ProfileDetailFragment : Fragment(R.layout.fragment_profile_detail) {
    private var _binding: FragmentProfileDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ProfileDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileDetailBinding.bind(view)

        binding.tvUserId.text = "User ID: ${args.userId}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}