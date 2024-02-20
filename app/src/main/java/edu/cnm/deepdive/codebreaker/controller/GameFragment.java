package edu.cnm.deepdive.codebreaker.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.GuessesAdapter;
import edu.cnm.deepdive.codebreaker.adapter.SwatchesAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentGameBinding;
import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import edu.cnm.deepdive.codebreaker.viewmodel.CodebreakerViewModel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameFragment extends Fragment implements MenuProvider {

  private FragmentGameBinding binding;
  private CodebreakerViewModel viewModel;
  private GuessesAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // this is where we read arguments passed to the fragment.
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // load and bind layout.
    binding = FragmentGameBinding.inflate(inflater, container, false);
    binding.submit.setOnClickListener((v) -> submitGuess());
    // TODO: 2/7/2024 Initialize any view widgets, as necessary.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    requireActivity().addMenuProvider(this, getViewLifecycleOwner(), State.RESUMED);
    // Connect to viewmodels.
    SetupViewModels();
  }

  @Override
  public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
    menuInflater.inflate(R.menu.game_options, menu);
  }

  @Override
  public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
    boolean handled = true;
    if (menuItem.getItemId() == R.id.new_game) {
      adapter = null;
      viewModel.startGame();
    } else {
      handled = false;
    }
    return handled;
  }

  private void SetupViewModels() {
    ConnectToViewModel();
    LifecycleOwner owner = getViewLifecycleOwner();
    ObserveGame(owner);
    ObserveInProgress(owner);
  }

  private void ConnectToViewModel() {
    viewModel = new ViewModelProvider(requireActivity()).get(CodebreakerViewModel.class);
    getLifecycle().addObserver(viewModel);
  }

  private void ObserveGame(LifecycleOwner owner) {
    viewModel
        .getGame()
        .observe(owner, (game) -> {
          List<Guess> guesses = game.getGuesses();
          if (adapter == null) {
            adapter = new GuessesAdapter(requireContext(), guesses);
            binding.guesses.setAdapter(adapter);
            createSpinners(game);
          }
          adapter.notifyDataSetChanged();
          binding.guesses.post(() -> binding.guesses.smoothScrollToPosition(guesses.size() - 1));
        });
  }

  private void ObserveInProgress(LifecycleOwner owner) {
    viewModel
        .getInProgress()
        .observe(owner,
            (inProgress) -> {
          int visibility = inProgress ? View.VISIBLE : View.INVISIBLE;
          binding.colorSelectors.setVisibility(visibility);
          binding.submit.setVisibility(visibility);
            });
  }

  private void createSpinners(Game game) {
    int codeLength = game.getLength();
    List<Guess> guesses = game.getGuesses();
    String lastGuess = (guesses.isEmpty()) ? null : guesses.get(guesses.size() - 1).getContent();
    // TODO: 2/20/2024 Use last guess to reset spinners to colors of last guess.
    Context context = requireContext();
    binding.colorSelectors.removeAllViews();
    for (int i = 0; i < codeLength; i++) {
      Spinner spinner = (Spinner)
          getLayoutInflater().inflate(R.layout.color_spinner, binding.colorSelectors, false);
      spinner.setAdapter(new SwatchesAdapter(context));
      binding.colorSelectors.addView(spinner);
    }
  }

  private void submitGuess() {
    String guess = IntStream.range(0, binding.colorSelectors.getChildCount())
        .mapToObj((pos) -> (Spinner) binding.colorSelectors.getChildAt(pos))
        .map((spinner) -> (String) spinner.getSelectedItem())
        .map((colorName) -> colorName.substring(0,1))
        .collect(Collectors.joining());
    viewModel.submitGuess(guess);
  }

}