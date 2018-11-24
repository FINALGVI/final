package com.example.luisfelix.gym;


import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fitness_Frag extends Fragment {

    Button masAgua, menosAgua;
    TextView textAgua;
    EditText editPeso, editLevantarse, editAcostarse;
    Button btnGuardar;
    int contadorAgua=0;
    int peso;

    public Fitness_Frag() {
        // Required empty public constructor
    }

    public SQLiteDatabase db;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_fitness, container, false);

        try{
            BDD con = new BDD(v.getContext(), "Salud", null, 1);
            db = con.getWritableDatabase();
        }catch(Exception e){
            Toast toast1 =
                    Toast.makeText(v.getContext(),
                            "Error al conectar", Toast.LENGTH_SHORT);

            toast1.show();
        }

        masAgua=v.findViewById(R.id.btnAgua_mas);
        menosAgua=v.findViewById(R.id.btnAgua_menos);
        textAgua=v.findViewById(R.id.CantidadAgua);

        masAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    contadorAgua++;
                    textAgua.setText(" "+contadorAgua);
            }
        });
        menosAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contadorAgua--;
                textAgua.setText(" "+contadorAgua);
            }
        });

        editPeso=v.findViewById(R.id.editPeso);
        editAcostarse = v.findViewById(R.id.editAcostarse);
        editLevantarse = v.findViewById(R.id.editLevantarse);

        btnGuardar = v.findViewById(R.id.btnguardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String agua = textAgua.getText().toString();
                String peso = editPeso.getText().toString();
                String hora = editAcostarse.getText().toString();
                agregar(agua, peso, hora);
            }
        });
        return v;
    }

    public void agregar(String agua, String peso, String horas){
        Toast toast1 =
                Toast.makeText(v.getContext(),
                        "yay", Toast.LENGTH_SHORT);

        toast1.show();

    }

    public void time(View v){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                editAcostarse.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}
