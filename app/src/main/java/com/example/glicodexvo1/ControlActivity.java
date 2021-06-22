package com.example.glicodexvo1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.glicodexvo1.Fragments.ControlCargar;
import com.example.glicodexvo1.Fragments.ControlListaBuscar;
import com.example.glicodexvo1.Fragments.ControlListaPrueba;
import com.example.glicodexvo1.Utilidades.ControladorControles;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ControlActivity extends AppCompatActivity
{

    TabLayout tabControles;
    ViewPager2 viewPager2;
    ControladorControles controlador;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_controles);

        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        tabControles = findViewById(R.id.tabControles);
        viewPager2 = findViewById(R.id.vpControles);
        controlador= new ControladorControles(getSupportFragmentManager(), getLifecycle());

        controlador.Add(new ControlListaPrueba());
        controlador.Add(new ControlListaBuscar());



        viewPager2.setAdapter(controlador);

        new TabLayoutMediator(tabControles, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Controles");
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_baseline_search_48);
                        break;
                }
            }
        }).attach();
    }


    public void Cargar_control(View vista)
    {
        ControlCargar cargarControl = new ControlCargar();
        cargarControl.setCancelable(false);
        Bundle bundle = new Bundle();
        cargarControl.setTargetFragment(controlador.getFragment(0), 1);
        bundle.putInt("idUs", idUsuario);
        cargarControl.setArguments(bundle);
        cargarControl.show(getSupportFragmentManager(),"Cargar control");
    }

}