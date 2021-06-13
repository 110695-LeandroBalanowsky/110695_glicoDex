package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.glicodexvo1.Models.PorcentajeHiper;
import com.example.glicodexvo1.Models.PromediosControles;
import com.example.glicodexvo1.Utilidades.AccesoBD;
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

import java.util.ArrayList;

public class ReportesActivity extends AppCompatActivity {
    /*Controles*/
    private PieChart graficoTorta;
    private BarChart graficoBarra;
    private Spinner comboFiltro;
    private TextView promGeneral;
    /*Arrays de la BD*/
    private ArrayList<PromediosControles> promedios;
    private ArrayList<PorcentajeHiper> porcentajesHiper;
    /*Arrays de grafico barras*/
    private String[] fechas;
    private float[] valoresPromedio;
    private int[] colorBar;
    private int[] colorLeyenda = {Color.rgb(138, 209, 235), Color.rgb(81, 196, 111), Color.rgb(242, 94, 94)};
    /*Arrays de grafico torta*/
    private String[] horarios;
    private float[] porcentajes;
    private int[] colorPie = {Color.rgb(156, 17, 9),Color.rgb(184, 137, 9),Color.rgb(161, 184, 9),Color.rgb(115, 184, 9),
            Color.rgb(9, 129, 184),Color.rgb(106, 6, 189),Color.rgb(191, 8, 161),Color.rgb(173, 5, 72),Color.rgb(87, 81, 84)};

    AccesoBD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        bd = AccesoBD.getInstance(getApplicationContext());
        promedios = bd.getPromediosSemanales();
        porcentajesHiper = bd.getPorcentajesHiperSemanales();

        graficoTorta = findViewById(R.id.graficopastel);
        graficoBarra = findViewById(R.id.graficoBarra);
        comboFiltro = (Spinner) findViewById(R.id.spnFechasDiasRep);
        promGeneral = findViewById(R.id.txtValorPromedio);

        ArrayList<String> catCombo= new ArrayList<>();
        catCombo.add("Ultima semana");
        catCombo.add("Ultimos 15 dias");
        catCombo.add("Ultimo mes");
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_glicodex, catCombo );
        comboFiltro.setAdapter(adapter);
        comboFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    promedios.clear();
                    porcentajesHiper.clear();
                    promedios = bd.getPromediosSemanales();
                    porcentajesHiper = bd.getPorcentajesHiperSemanales();
                    promGeneral.setText(String.valueOf(bd.getPromedioTotalSemanal()));
                    CargarListas();
                    CrearGrafica();

                }
                else
                {
                    if (position == 1)
                    {
                        promedios.clear();
                        porcentajesHiper.clear();
                        promedios = bd.getPromediosQuincenal();
                        porcentajesHiper = bd.getPorcentajesHiperQuincenales();
                        promGeneral.setText(String.valueOf(bd.getPromedioTotalQuincenal()));
                        CargarListas();
                        CrearGrafica();
                    }
                    else
                    {
                        promedios.clear();
                        porcentajesHiper.clear();
                        promedios = bd.getPromediosMensual();
                        porcentajesHiper = bd.getPorcentajesHiperMensual();
                        promGeneral.setText(String.valueOf(bd.getPromedioTotalMensual()));
                        CargarListas();
                        CrearGrafica();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        promGeneral.setText(String.valueOf(bd.getPromedioTotalSemanal()));

        CargarListas();
        CrearGrafica();
    }
    public void CargarListas()
    {
        fechas = new String[promedios.size()];
        valoresPromedio = new float[promedios.size()];
        colorBar = new int[promedios.size()];

        for (int i = 0; i < promedios.size(); i++)
        {
            fechas[i] = promedios.get(i).getFecha();
            valoresPromedio[i] = promedios.get(i).getPromedio();

            if (promedios.get(i).getPromedio() < 70)
            {
                colorBar[i] = Color.rgb(138, 209, 235);
            }
            if (promedios.get(i).getPromedio() >= 70 && promedios.get(i).getPromedio() <=140)
            {
                colorBar[i] = Color.rgb(81, 196, 111);
            }
            if (promedios.get(i).getPromedio() > 140)
            {
                colorBar[i] = Color.rgb(242, 94, 94);
            }
        }

        horarios = new String[porcentajesHiper.size()];
        porcentajes = new float[porcentajesHiper.size()];

        for (int i = 0; i < porcentajesHiper.size(); i++)
        {
            horarios[i] = porcentajesHiper.get(i).getHorario();
            porcentajes[i] = porcentajesHiper.get(i).getPorcentaje();
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


        graficoTorta = (PieChart)getPieChart(graficoTorta, "Porcentajes de hiperglucemias segun horario", Color.WHITE, 1500);
        graficoTorta.setHoleRadius(50);
        graficoTorta.setUsePercentValues(true);
        graficoTorta.setTransparentCircleRadius(60);
        graficoTorta.setData(getPieDataSet());
        graficoTorta.invalidate();

    }

    /*GRAFICO BARRA*/
    private Chart getBarChart(Chart chart, String desc, int textColor, int animateY)
    {
        chart.getDescription().setText(desc);
        chart.getDescription().setTextSize(15);
        chart.animateY(animateY);
        legendBarchart(chart);

        return chart;
    }
    /*Carga de la leyenda de los costados*/
    private void legendBarchart(Chart chart)
    {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entradas = new ArrayList<>();

        for (int i = 0; i < colorLeyenda.length; i++)
        {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorLeyenda[i];
            if (i == 0)
                entry.label = "Bajo";
            if (i == 1)
                entry.label= "Normal";
            if(i == 2)
                entry.label = "Alto";

            entradas.add(entry);
        }
        legend.setCustom(entradas);

    }
    private ArrayList<BarEntry> getBarEntrys()
    {
        ArrayList<BarEntry> entradas = new ArrayList<>();

        for (int i = 0; i < valoresPromedio.length; i++) {
            BarEntry barEntry = new BarEntry(i, valoresPromedio[i]);
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
        dataSet.setColors(colorBar);
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

    /*GRAFICO DE TORTA*/
    private Chart getPieChart(Chart chart, String desc, int textColor, int animateY)
    {
        chart.getDescription().setText(desc);
        chart.getDescription().setTextSize(15);
        chart.animateY(animateY);
        legendPiechart(chart);

        return chart;
    }
    private void legendPiechart(Chart chart)
    {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        ArrayList<LegendEntry> entradas = new ArrayList<>();

        for (int i = 0; i < horarios.length; i++)
        {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorPie[i];
            entry.label = horarios[i];

            entradas.add(entry);
        }
        legend.setCustom(entradas);

    }

    private ArrayList<PieEntry> getPieEntrys()
    {
        ArrayList<PieEntry> entradas = new ArrayList<>();

        for (int i = 0; i < porcentajes.length; i++) {
            PieEntry pieEntry = new PieEntry(porcentajes[i]);
            entradas.add(pieEntry);
        }

        return entradas;
    }
    private DataSet getDatapieSet(DataSet dataSet)
    {
        dataSet.setColors(colorPie);
        dataSet.setValueTextSize(20);
        dataSet.setValueTextColor(Color.WHITE);
        return dataSet;
    }
    private PieData getPieDataSet()
    {
        PieDataSet pieDataSet = (PieDataSet) getDatapieSet(new PieDataSet(getPieEntrys(), ""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }



}