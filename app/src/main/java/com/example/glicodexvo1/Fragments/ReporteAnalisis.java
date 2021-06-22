package com.example.glicodexvo1.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.Models.Estudios;
import com.example.glicodexvo1.Models.Horario;
import com.example.glicodexvo1.Models.PorcentajeHiper;
import com.example.glicodexvo1.Models.PromediosControles;
import com.example.glicodexvo1.R;
import com.example.glicodexvo1.Utilidades.AccesoBD;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
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

import java.util.ArrayList;


public class ReporteAnalisis extends Fragment {

    private BarChart graficoBarra;
    private Spinner comboAnio;
    private Spinner comboEstudio;
    private TextView comparacion;
    /*Arrays de la BD*/
    private ArrayList<AnalisisEstudios> analisis;
    /*Arrays de grafico barras*/
    private String[] fechas;
    private float[] valores;
    AccesoBD bd;
    int idEstudio;
    String fecha;
    String info;
    public ReporteAnalisis() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_reporte_analisis, container, false);

        bd = AccesoBD.getInstance(getContext());


        graficoBarra = vista.findViewById(R.id.graficoBarraAnalisis);
        comboAnio = (Spinner) vista.findViewById(R.id.spnRepoAnios);
        comboEstudio = (Spinner) vista.findViewById(R.id.spnRepoEst);

        comparacion = vista.findViewById(R.id.txtComparacion);

        ArrayList<String> anios= bd.getAnioAnalisis();
        if(anios.isEmpty())
            anios.add("No hay datos");
        ArrayList<Estudios> estudios = bd.getEstudios();

        ArrayAdapter<String> adapterAnios = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, anios );
        comboAnio.setAdapter(adapterAnios);
        ArrayAdapter<String> adapterEs = new ArrayAdapter(getContext(), R.layout.spinner_item_glicodex, estudios );
        comboEstudio.setAdapter(adapterEs);
        comboAnio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fecha = parent.getSelectedItem().toString();
                analisis.clear();
                analisis = bd.getAnalisisEstudiosReporte(idEstudio, fecha);
                CargarListas();
                CrearGrafica();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        comboEstudio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEstudio = ((Estudios)parent.getSelectedItem()).getIdEstudioi();
                analisis.clear();
                analisis = bd.getAnalisisEstudiosReporte(idEstudio, fecha);
                CargarListas();
                CrearGrafica();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //promGeneral.setText(String.valueOf(bd.getPromedioTotalSemanal()));
        if(comboAnio.getSelectedItem() != null)
            fecha = comboAnio.getSelectedItem().toString();
        idEstudio = ((Estudios) comboEstudio.getSelectedItem()).getIdEstudioi();
        analisis = bd.getAnalisisEstudiosReporte(idEstudio, fecha);
        CargarListas();
        CrearGrafica();



        return vista;
    }

    public void CargarListas() {
        fechas = new String[analisis.size()];
        valores = new float[analisis.size()];

        for (int i = 0; i < analisis.size(); i++) {
            fechas[i] = analisis.get(i).getFecha();
            valores[i] = (float) analisis.get(i).getValor();
        }
        if (analisis.isEmpty())
        {
            info = "No hay informacion para mostrar";
            comparacion.setText("No hay informacion para mostrar");
        }
        else{
            info = analisis.get(0).getObservaciones();
            comparacion.setText(analisis.get(0).getEstudio() + " del año " + fecha);
        }

    }
    public void CrearGrafica()
    {
        graficoBarra = (BarChart)getBarChart(graficoBarra, "", Color.BLACK, 1500);
        graficoBarra.setDrawBarShadow(false);
        graficoBarra.setDrawGridBackground(false);
        graficoBarra.setData(getbarDataSet());
        graficoBarra.invalidate();
        axisX(graficoBarra.getXAxis());
        axisY(graficoBarra.getAxisLeft());
        graficoBarra.getAxisRight().setEnabled(false);


    }

    //GRAFICO BARRA
    private Chart getBarChart(Chart chart, String desc, int textColor, int animateY)
    {
        chart.animateY(animateY);
        legendBarchart(chart);

        return chart;
    }
    //Carga de la leyenda de los costados
    private void legendBarchart(Chart chart)
    {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entradas = new ArrayList<>();
        LegendEntry entry = new LegendEntry();
        entry.label = info;
        entradas.add(entry);
        legend.setCustom(entradas);

    }
    private ArrayList<BarEntry> getBarEntrys()
    {
        ArrayList<BarEntry> entradas = new ArrayList<>();

        for (int i = 0; i < valores.length; i++) {
            BarEntry barEntry = new BarEntry(i, valores[i]);
            entradas.add(barEntry);
        }

        return entradas;
    }
    private void axisX(XAxis axisX)
    {
        axisX.setGranularityEnabled(true);
        axisX.setPosition(XAxis.XAxisPosition.BOTTOM);
        axisX.setValueFormatter(new IndexAxisValueFormatter(fechas));
    }
    private void axisY(YAxis axis)
    {
        axis.setSpaceTop(100);
        axis.setAxisMinimum(0);
    }
    private DataSet getDatabarSet(DataSet dataSet)
    {
        dataSet.setValueTextSize(20);
        return dataSet;
    }
    private BarData getbarDataSet()
    {
        BarDataSet barDataSet = (BarDataSet) getDatabarSet(new BarDataSet(getBarEntrys(),""));
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.50f);

        return barData;

    }

}