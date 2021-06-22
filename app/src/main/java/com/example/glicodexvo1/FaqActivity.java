package com.example.glicodexvo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.glicodexvo1.Models.AnalisisEstudios;
import com.example.glicodexvo1.Utilidades.ExpListAnalisisAdapter;
import com.example.glicodexvo1.Utilidades.ExpListFaqAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FaqActivity extends AppCompatActivity {

    private ExpandableListView listaPreguntas;
    private ExpListFaqAdapter adapter;
    private ArrayList<String> preguntas;
    private Map<String, String> respuestas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        listaPreguntas = findViewById(R.id.listFaq);
        preguntas = new ArrayList<>();
        respuestas = new HashMap<>();
        CargarDatos();
    }
    private void CargarDatos()
    {

        preguntas.add("¿Como se ingresa un nuevo registro en el diario?");
        String respuesta1 = "En la pagina principal del diario hay un boton azul, ingresando ahi podras cargar un nuevo control.";
        respuestas.put(preguntas.get(0), respuesta1);

        preguntas.add("¿Como puedo modificar un registro en el diario?");
        String respuesta2 = "Cada registro tiene un sub menu el cual aparece cuando haces un toque largo en el boton de los tres puntos, Alli podras modificar o eliminar un registro.";
        respuestas.put(preguntas.get(1), respuesta2);

        preguntas.add("¿Como puedo ver registros de fechas anteriores a los del ultimo mes?");
        String respuesta3 = "En la sub pestaña buscar, que se encuentra en la pantalla del diario, podras sseleccionar una fecha particular y ver todos los registros de dicha fecha.";
        respuestas.put(preguntas.get(2), respuesta3);

        preguntas.add("¿Como se ingresa un nuevo registro en los analisis?");
        String respuesta4 = "Al igual que en la paginn del diario, hay un boton azul que te permite cargar un nuevo anallissi. Los analisis se agrupan automaticamente por fecha.";
        respuestas.put(preguntas.get(3), respuesta4);

        preguntas.add("¿Como puedo modificar un analisis?");
        String respuesta5 = "Los analisis se modifican con el sub menu de cada registro, en caso de modificar la fecha esta aparecera como un nuevo registro final.";
        respuestas.put(preguntas.get(4), respuesta5);

        preguntas.add("¿Que es el conteo de hidratos?");
        String respuesta6 = "El conteo de hidratos es una herramienta la cual permite un control glucemico dependiendo de los alimentos que consumas, siempre acompañado de un medico.";
        respuestas.put(preguntas.get(5), respuesta6);

        preguntas.add("¿Que informacion puedo encontrar en cada alimento del conteo de hidratos?");
        String respuesta7 = "En cada alimento podemos encontrar: la porcion recomendada, el indice glucemico, la cantidad de carbohidratos y las calorias.";
        respuestas.put(preguntas.get(6), respuesta7);

        preguntas.add("¿Que informacion de mis glucemias me muestran los reportes?");
        String respuesta8 = "En los reportes podemos encontrar los promedios diarios de la ultima semana,los ultimos 15 dias y el ultimo mes de controles glucemicos. Tambien encontramos " +
                "el porcentaje de valores ALTOS por momento del dia para ayuda a tomar decisiones.";
        respuestas.put(preguntas.get(7), respuesta8);

        preguntas.add("¿Que informacion de mis analisis me muestran los reportes?");
        String respuesta9 = "En los reportes podemos encontrar una comparacion de los valores de todos los analisis divididos por estudio y año.";
        respuestas.put(preguntas.get(8), respuesta9);

        preguntas.add("¿Como puedo modificar mis datos personales?");
        String respuesta10 = "En la pagina datos del usuario hay un boton con forma de lapiz, usandolo podras modificar tus datos personales.";
        respuestas.put(preguntas.get(9), respuesta10);

        adapter = new ExpListFaqAdapter(preguntas, respuestas, this);
        listaPreguntas.setAdapter(adapter);
    }
}