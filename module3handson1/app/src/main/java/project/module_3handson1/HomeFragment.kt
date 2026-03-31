package project.module_3handson1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import project.module_3handson1.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.btnGoToDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dashboardFragment)
        }

        binding.btnGoToSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settings_graph)
        }

        binding.btnGoToProfile.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileDetailFragment(userId = "123")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}