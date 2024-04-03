package edu.cnm.deepdive.codebreaker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScoresFragmentsAdapter extends FragmentStateAdapter {

  public ScoresFragmentsAdapter(@NonNull Fragment fragment) {
    super(fragment);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    // TODO: 4/3/2024 Create an instance of scores fragment, pass it a bundle of arguments.
    return null;
  }

  @Override
  public int getItemCount() {
    return 2;
  }

}
