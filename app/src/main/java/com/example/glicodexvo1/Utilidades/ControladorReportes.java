package com.example.glicodexvo1.Utilidades;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ControladorReportes extends FragmentStateAdapter {

    ArrayList<Fragment> fragmentReportes = new ArrayList<>();

    public ControladorReportes(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return fragmentReportes.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentReportes.size();
    }

    public void Add(Fragment fragment)
    {
        fragmentReportes.add(fragment);
    }

    public Fragment getFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0:
                return fragmentReportes.get(0);
            case 1:
                return fragmentReportes.get(1);

        }
        return fragmentReportes.get(0);
    }
}
