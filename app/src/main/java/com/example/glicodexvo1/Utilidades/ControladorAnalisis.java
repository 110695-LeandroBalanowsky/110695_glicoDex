package com.example.glicodexvo1.Utilidades;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ControladorAnalisis extends FragmentStateAdapter {

    ArrayList<Fragment> fragmentAnalisis = new ArrayList<>();

    public ControladorAnalisis(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentAnalisis.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentAnalisis.size();
    }
    public void Add(Fragment fragment)
    {
        fragmentAnalisis.add(fragment);
    }
}
