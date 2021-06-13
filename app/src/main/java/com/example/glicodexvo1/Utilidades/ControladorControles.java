package com.example.glicodexvo1.Utilidades;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ControladorControles extends FragmentStateAdapter {

    ArrayList<Fragment> fragmentControles = new ArrayList<>();

    public ControladorControles(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return fragmentControles.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentControles.size();
    }

    public void Add(Fragment fragment)
    {
        fragmentControles.add(fragment);
    }

    public Fragment getFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0:
                return fragmentControles.get(0);
            case 1:
                return fragmentControles.get(1);

            case 2:
                return fragmentControles.get(2);

            case 3:
                return fragmentControles.get(3);

        }
        return fragmentControles.get(0);
    }
}
