package com.hanuszczak.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.hanuszczak.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false).apply { 
            composeView.setContent {
                MaterialTheme {
                    Surface {
                        ResultFragmentContent()
                    }
                }
            }
        }
        val view = binding.root

        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)

        binding.resultViewModel = viewModel

        binding.newGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        return view
    }

    @Composable
    private fun ResultFragmentContent() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}