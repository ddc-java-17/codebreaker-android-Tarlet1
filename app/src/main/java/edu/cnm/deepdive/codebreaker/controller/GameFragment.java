package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.databinding.FragmentGameBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.CodebreakerViewModel;

public class GameFragment extends Fragment {

  private FragmentGameBinding binding;
  private CodebreakerViewModel viewModel;

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
    // TODO: 2/7/2024 Initialize any view widgets, as necessary.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Connect to view models.
    viewModel = new ViewModelProvider(requireActivity()).get(CodebreakerViewModel.class);
    getLifecycle().addObserver(viewModel);
    LifecycleOwner owner = getViewLifecycleOwner();
    viewModel
        .getGame()
        .observe(owner, (game) -> { /* todo updateUI for change of game. */});
    viewModel
        .getGuess()
        .observe(owner, (guess) -> {/* TODO update UI (list of guesses displayed) for new guess */});
    viewModel
        .getInProgress()
        .observe(owner, (inProgress) -> {/* TODO Enable/disable controls on change of game state. */});
  }

}