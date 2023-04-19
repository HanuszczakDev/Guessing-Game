package com.hanuszczak.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hanuszczak.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }
        val view = binding.root

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }
        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }
        return view
    }

    @Composable
    private fun GameFragmentContent(viewModel: GameViewModel) {
        val guess = remember {
            mutableStateOf("")
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            IncorrectGuessesText(viewModel = viewModel)
            EnterGuess(guess = guess.value) { guess.value = it }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GuessButton {
                    viewModel.makeGuess(guess.value.uppercase())
                    guess.value = ""
                }
                FinishGameButton {
                    viewModel.finishGame()
                }
            }
        }
    }

    @Composable
    fun IncorrectGuessesText(viewModel: GameViewModel) {
        val incorrectGuesses = viewModel.incorrectGuesses.observeAsState()
        incorrectGuesses.value?.let {
            Text(text = stringResource(id = R.string.incorrect_guesses, it))
        }
    }
    
    @Composable
    fun GuessButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text(text = "Guess!")
        }
    }
    
    @Composable
    fun EnterGuess(guess: String, changed: (String) -> Unit) {
        TextField(
            value = guess,
            onValueChange = changed,
            label = { Text(text = "Guess a letter") })
    }

    @Composable
    fun FinishGameButton(clicked: () -> Unit) {
        Button(onClick = clicked) {
            Text(text = "Finish Game")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}