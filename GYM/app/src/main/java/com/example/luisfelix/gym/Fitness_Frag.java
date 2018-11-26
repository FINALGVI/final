package com.example.luisfelix.gym;


import android.app.TimePickerDialog;
import android.content.Context;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fitness_Frag extends Fragment {

    Button masAgua, menosAgua;
    TextView textAgua;
    EditText editPeso, editLevantarse, editAcostarse;
    Button btnGuardar;
    TextView textNombre2;
    int contadorAgua=0;
    int peso;

    public Fitness_Frag() {
        // Required empty public constructor
    }

    public SQLiteDatabase db;
    public SQLiteDatabase db1;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_fitness, container, false);

        try{
            BDD con = new BDD(v.getContext(), "Salud", null, 2);
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
                String ac = editAcostarse.getText().toString();
                String al = editLevantarse.getText().toString();
                String hora = "";

                if (!ac.equals("")||!al.equals("")){
                    hora = dormido(ac, al);//calcular el tiempo dormido

                }


                    agregar(agua, peso, hora);//agrega a la base de datos

            }
        });

        textNombre2=v.findViewById(R.id.textNombre2);
        traerNombre2(v.getContext(), textNombre2);

        return v;
    }

    public String dormido(String ac, String al){
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date time1, time2;
        int milis=0, segundos, minutos, horas;
        String time="";

        try{
            time1 = df.parse(ac);
            time2 = df.parse(al);
            milis = (int) (time2.getTime()-time1.getTime());
            if (milis<0) milis*=-1; //la respuesta siempre sera positiva

            segundos=milis/1000;
            horas = segundos/3600;
            segundos-=(horas*3600);
            minutos = segundos/60;

            time=horas+" horas y "+minutos+" minutos";
        }catch(Exception e){
            Toast toast1 =
                    Toast.makeText(v.getContext(),
                            "No se puede obtener las horas dormidas", Toast.LENGTH_SHORT);
            toast1.show();
        }
        
        return time;
    }
/*Sergio*/
    public void agregar(String agua, String peso, String hora){

        try{
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            Date date = new Date();

            Cursor c = db.rawQuery("select * from Salud where fecha=current_date",null);
            if(!c.moveToFirst()){
                db.execSQL("insert into Salud(agua,peso,dormir,fecha) " +
                        "values('" + agua + "','" + peso + "','"+hora+"',current_date)");
            }else {
                db.execSQL("update Salud set agua='"+agua+"',peso='"+peso+"',dormir='"+hora+"' where fecha=current_date");
            }

            Toast toast1 =
                    Toast.makeText(v.getContext(),
                            "Guardado con exito", Toast.LENGTH_SHORT);
            toast1.show();

        }catch (Exception e){
            Toast toast1 =
                    Toast.makeText(v.getContext(),
                            "No se pudo guardar", Toast.LENGTH_SHORT);
            toast1.show();
        }


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

    void traerNombre2(Context context, TextView texto){
        BDD con2 = new BDD(context, "Nombre", null, 2);
        db1 = con2.getWritableDatabase();
        Cursor c3 = db1.rawQuery("SELECT nombre FROM Nombre WHERE id >= 1", null);
        while (c3.moveToNext())
        {
            texto.setText(c3.getString(0)+" aqui pones cosas más personales:");
        }
        db1.close();
    }


}
