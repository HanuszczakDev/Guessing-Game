package com.hanuszczak.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class GameFragment : Fragment() {
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view?.findNavController()?.navigate(action)
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface {
                        GameFragmentContent(viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun GameFragmentContent(viewModel: GameViewModel) {
        val guess = remember {
            mutableStateOf("")
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SecretWordDisplay(viewModel = viewModel)
            }
            LivesLeftText(viewModel = viewModel)
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
    fun SecretWordDisplay(viewModel: GameViewModel) {
        val display = viewModel.secretWordDisplay.observeAsState()
        display.value?.let {
            Text(
                text = it,
                letterSpacing = 0.1.em,
                fontSize = 36.sp
            )
        }
    }

    @Composable
    fun LivesLeftText(viewModel: GameViewModel) {
        val livesLeft = viewModel.livesLeft.observeAsState()
        livesLeft.value?.let {
            Text(text = stringResource(id = R.string.lives_left, it))
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
}