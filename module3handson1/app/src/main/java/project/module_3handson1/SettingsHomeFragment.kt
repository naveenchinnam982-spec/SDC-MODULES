package project.module_3handson1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import project.module_3handson1.databinding.FragmentSettingsHomeBinding

class SettingsHomeFragment : Fragment(R.layout.fragment_settings_home) {
    private var _binding: FragmentSettingsHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsHomeBinding.bind(view)

        binding.btnViewProfile.setOnClickListener {
            val action = SettingsHomeFragmentDirections.actionSettingsHomeFragmentToProfileDetailFragment(userId = "456")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}