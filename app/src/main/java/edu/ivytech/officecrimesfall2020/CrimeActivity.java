package edu.ivytech.officecrimesfall2020;

import androidx.fragment.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new FirstFragment();
    }
}
