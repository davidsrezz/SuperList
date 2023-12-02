package com.david.superlist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AniadirListaActivity extends AppCompatActivity{
    private ImageButton botonVolver;
    private EditText txtNombre, txtDescripcion;
    private String nombre, descripcion, fechaLimite, tipoLista;
    private Spinner tipoListaSpinner;
    private Button botonDatePicker;
    private TextView ShowDateTextView;
    private DatePickerDialog datePickerDialog;
    private String fechaElegida;
    private Button botonAniadir;
    private ArrayList<String> listaDeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_descripcion);


        botonVolver = findViewById(R.id.BotonVolverAniadirLista);
        botonVolver.setOnClickListener(v -> {
            finish();
        });

        txtNombre = findViewById(R.id.NombreEditText);
        txtDescripcion = findViewById(R.id.DescripcionEditText);

        tipoListaSpinner = findViewById(R.id.spinnerTipoLista);

        botonDatePicker = findViewById(R.id.botonAniadirFecha);
        ShowDateTextView = findViewById(R.id.SelectedDaytextView);
        iniciarDatePicker();
        botonDatePicker.setOnClickListener(v -> {
            datePickerDialog.show();
        });


        botonAniadir = findViewById(R.id.botonAniadirListaActivity);
        botonAniadir.setOnClickListener(View -> {
            checkAndSendLista();
        });
    }

    private void checkAndSendLista() {
        Boolean thereIsAnError = false;
        nombre = txtNombre.getText().toString();
        descripcion = txtDescripcion.getText().toString();
        fechaLimite = ShowDateTextView.getText().toString();
        tipoLista = (String) tipoListaSpinner.getSelectedItem();
        System.out.println(fechaLimite);
        System.out.println(tipoLista);


        if (TextUtils.isEmpty(nombre.trim())) {
            thereIsAnError = true;
            ponerError(txtNombre);
        }
        if (TextUtils.isEmpty(descripcion.trim())) {
            thereIsAnError = true;
            ponerError(txtDescripcion);
        }
        if (fechaLimite.equalsIgnoreCase("Fecha límite")) {
            thereIsAnError = true;
            ShowDateTextView.setError("Elige la fecha");
        } else {
            ShowDateTextView.setError(null);
        }

        if (thereIsAnError) {
            return;
        }

        MainActivity.aniadirLista(nombre,descripcion,fechaLimite,tipoLista, listaDeItems);



        finish();
    }

    private void iniciarDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                fechaElegida = dayOfMonth + "/" + (month + 1) + "/" + year;
                ShowDateTextView.setText(fechaElegida);
            }
        };

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        //int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, dateSetListener, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private void ponerError(EditText et) {
        et.setError("Este campo es obligatorio.");
    }


}