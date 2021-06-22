package com.example.glicodexvo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.glicodexvo1.Fragments.ControlListaBuscar;
import com.example.glicodexvo1.Fragments.ControlListaPrueba;
import com.example.glicodexvo1.Fragments.ReporteAnalisis;
import com.example.glicodexvo1.Fragments.ReporteControles;
import com.example.glicodexvo1.Models.PorcentajeHiper;
import com.example.glicodexvo1.Models.PromediosControles;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.example.glicodexvo1.Utilidades.ControladorControles;
import com.example.glicodexvo1.Utilidades.ControladorReportes;
import com.example.glicodexvo1.Utilidades.ListAdapterCtrl;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ReportesActivity extends AppCompatActivity {

    TabLayout tabControles;
    ViewPager2 viewPager2;
    ControladorReportes controlador;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        tabControles = findViewById(R.id.tabReportes);
        viewPager2 = findViewById(R.id.vpReportes);
        controlador= new ControladorReportes(getSupportFragmentManager(), getLifecycle());

        controlador.Add(new ReporteControles());
        controlador.Add(new ReporteAnalisis());

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
                        tab.setText("Analisis");
                        break;
                }
            }
        }).attach();

    }

}