package com.david.superlist.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Adaptadores.AdaptadorItemsLista;
import com.david.superlist.pojos.TareaLista;
import com.david.superlist.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddItemsListaActivity extends AppCompatActivity {

    RecyclerView recyclerViewItems;
    AdaptadorItemsLista adaptador;
    ArrayList<TareaLista> tareas;
    Button addTareaButton;
    Button botonFinalizarAniadirTareas;
    ImageButton imageButtonVolverAtras;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_itemslista);

        addTareaButton = findViewById(R.id.botonAñadirItem);
        addTareaButton.setOnClickListener(v -> {
            createDialogAddTask();
        });

        botonFinalizarAniadirTareas = findViewById(R.id.botonTerminarLista);
        botonFinalizarAniadirTareas.setOnClickListener(v -> {


            if (getIntent().hasExtra("listaDeTareas")) {

                int posTarea = (int) getIntent().getExtras().getInt("posLista");

                MainActivity.cambiarTareasLista(posTarea, tareas);

            } else {
                agregarNuevaListaAMain();
            }

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();

        });

        imageButtonVolverAtras = findViewById(R.id.BotonVolverAniadirTarea);
        imageButtonVolverAtras.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.MensajeAdvertenciaVolverAtrasTareas));
            //Texto: ¿Estás seguro que quieres volver atrás? ¡Las tareas añadidas se borrarán!
            builder.setPositiveButton(getResources().getString(R.string.AceptarVolverAtrasTareas), new DialogInterface.OnClickListener() {
                //Texto: Aceptar o Accept
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
            //Texto: Cancelar o Cancel
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        recyclerViewItems = findViewById(R.id.recyclerViewTareas);


        if (getIntent().hasExtra("listaDeTareas")) {
            tareas = (ArrayList<TareaLista>) getIntent().getSerializableExtra("listaDeTareas");
        } else {
            tareas = new ArrayList<>();
        }

//        addTarea("Esto es una prueba", "alta");
//        addTarea("Esto es una prueba 2", "baja");
//        addTarea("Esto es una prueba 2", "media");
        adaptador = new AdaptadorItemsLista(this, tareas);
        recyclerViewItems.setAdapter(adaptador);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void agregarNuevaListaAMain() {
        //Datos recibidos de la activity crear lista.
        Bundle datosLista = getIntent().getExtras();

        String nombreLista = datosLista.getString("nombreLista");
        String descripcionLista = datosLista.getString("descripcionLista");
        String fechaLimiteLista = datosLista.getString("fechaLimiteLista");
        String tipoLista = datosLista.getString("tipoLista");

        MainActivity.crearLista(nombreLista, descripcionLista, tipoLista, fechaLimiteLista, tareas);
    }

    private void createDialogAddTask() {
        // Abre un diálogo para crear una tarea.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Introduzca la tarea");

        // Infla el diseño de la vista del diálogo.
        View selector = getLayoutInflater().inflate(R.layout.dialog_introducir_datos_items, null);
        builder.setView(selector);

        // Obtiene referencias a los elementos de la vista del diálogo.
        TextInputEditText inputTarea = selector.findViewById(R.id.textInputTarea);
        Spinner spinnerPrioridadTarea = selector.findViewById(R.id.spinnerPrioridadLista);
        Button botonAgregarTarea = selector.findViewById(R.id.botonAgregarTarea);

        // Configura el botón "Cancelar" para cerrar el diálogo sin hacer nada.
        builder.setNegativeButton("Cancelar", null);

        // Crea el diálogo y lo muestra.
        AlertDialog dialog = builder.create();
        dialog.show();

        // Configura el botón "Agregar tarea" para agregar la tarea y cerrar el diálogo.
        botonAgregarTarea.setOnClickListener(v1 -> {
            String textoTarea = inputTarea.getText().toString();

            if (TextUtils.isEmpty(textoTarea)) {
                inputTarea.setError("Este campo es obligatorio");
                return;
            }

            String prioridadSeleccionada = spinnerPrioridadTarea.getSelectedItem().toString();
            addTarea(textoTarea, prioridadSeleccionada);
            adaptador.notifyDataSetChanged();
            dialog.dismiss(); // Agrega esta línea para cerrar el diálogo.
        });
    }

    public void addTarea(String tarea, String prioridad) {
        TareaLista nuevaTarea = new TareaLista(tarea, prioridad);
        tareas.add(nuevaTarea);
    }
}
