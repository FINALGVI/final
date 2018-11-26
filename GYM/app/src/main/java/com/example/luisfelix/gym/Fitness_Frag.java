package com.example.luisfelix.gym;


import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;




/**
 * A simple {@link Fragment} subclass.
 */
public class Fitness_Frag extends Fragment {

    public Button masAgua, menosAgua, btnGuardar, btnDormir, btnLevantarse;
    public TextView textAgua, textNombre2, aviso;
    public EditText editPeso;
    public int contadorAgua=0;
    public int peso, minutos, horaDormirDef, horaLevantarseDef, minDormirDef, minLevantarseDef, horasDormidas, minutosDormidos, hora, intermediario;
    public String horadef, stringHoraD, stringMinD, stringHoraL, stringMinL, horaInsertar, aguaRes, pesoRes;
    public Calendar date;
    public boolean dormirClick, levantarseClick, compruebaError;
    public RelativeLayout cuadroDatos;
    public SQLiteDatabase db;
    public SQLiteDatabase db1;
    View v;

    public Fitness_Frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_fitness, container, false);

        masAgua=v.findViewById(R.id.btnAgua_mas);
        menosAgua=v.findViewById(R.id.btnAgua_menos);
        textAgua=v.findViewById(R.id.CantidadAgua);
        editPeso=v.findViewById(R.id.editPeso);
        btnGuardar = v.findViewById(R.id.btnguardar);
        textNombre2 = v.findViewById(R.id.textNombre2);
        btnDormir = v.findViewById(R.id.btnDormidas);
        btnLevantarse = v.findViewById(R.id.btnLevantarse);
        cuadroDatos = v.findViewById(R.id.cuadroDatos);
        aviso = v.findViewById(R.id.aviso);

        BDD con = new BDD(v.getContext(), "Salud", null, 2);
        db = con.getWritableDatabase();

        traerNombre2(v.getContext(), textNombre2);

        dormirClick = false;
        levantarseClick = false;
        compruebaError = false;

        btnLevantarse.setEnabled(false);


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

        btnDormir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dormirClick = true;
                horasDescanso(btnDormir);
                btnLevantarse.setEnabled(true);
            }
        });

        btnLevantarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levantarseClick = true;
                horasDescanso(btnLevantarse);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aguaRes = textAgua.getText().toString();
                pesoRes = editPeso.getText().toString();

                try
                {
                    horaDormirDef = Integer.valueOf(stringHoraD);
                    horaLevantarseDef = Integer.valueOf(stringHoraL);

                    if (stringMinD != null)
                    {
                        minDormirDef = Integer.valueOf(stringMinD);
                    } else if (stringMinD == null)
                    {
                        minDormirDef = 0;
                    }

                    if (stringMinL != null)
                    {
                        minLevantarseDef = Integer.valueOf(stringMinL);
                    } else if (stringMinL == null)
                    {
                        minLevantarseDef = 0;
                    }

                    if (minDormirDef == 0)
                    {
                        minDormirDef = 0;
                    }

                    if (horaDormirDef > horaLevantarseDef)
                    {
                        horasDormidas = (horaLevantarseDef + 24) - horaDormirDef;
                    } else if (horaLevantarseDef > horaDormirDef)
                    {
                        horasDormidas = horaLevantarseDef - horaDormirDef;
                    } else if (horaDormirDef == horaLevantarseDef)
                    {
                        horasDormidas = 24;
                    }

                    if (horasDormidas == 24 && minLevantarseDef < minDormirDef)
                    {
                        intermediario = minDormirDef - minLevantarseDef;
                        minutosDormidos = 59 - (intermediario - 1);
                        horasDormidas = horasDormidas-1;
                    } else
                    {
                        if (minDormirDef == 0)
                        {
                            minutosDormidos = minLevantarseDef;
                        }
                        else if (minLevantarseDef == 0)
                        {
                            minutosDormidos = minDormirDef;
                        }
                        else if (minDormirDef >= minLevantarseDef)
                        {
                            minutosDormidos = minDormirDef - minLevantarseDef;
                        } else if (minLevantarseDef >= minDormirDef)
                        {
                            minutosDormidos = minLevantarseDef - minDormirDef;
                        }
                    }
                    Toast prueba = Toast.makeText(v.getContext(), horaInsertar, Toast.LENGTH_SHORT);
                    prueba.show();

                    agregar(aguaRes, pesoRes, horaInsertar);//agrega a la base de datos

                } catch (Exception e)
                {
                    Toast error = Toast.makeText(v.getContext(), "Parece haber un error ¿seleccionó ambas horas " + horaInsertar, Toast.LENGTH_LONG);
                    error.show();
                }
                if (compruebaError)
                {
                    btnGuardar.setEnabled(false);
                } else
                {
                    btnGuardar.setEnabled(true);
                }

                horaInsertar = "Se durmieron " + String.valueOf(horasDormidas) + " horas con " + String.valueOf(minutosDormidos) + " minutos";
            }
        });

        return v;
    }

    public void horasDescanso(final Button boton)
    {
        final Calendar c2= Calendar.getInstance();
        hora = c2.get(Calendar.HOUR_OF_DAY);
        minutos = c2.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horadef = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                boton.setText(String.valueOf(horadef) + " AST");
                if (dormirClick)
                {
                    if (hourOfDay > 9)
                    {
                        stringHoraD = horadef.substring(0, 2);
                    } else if (hourOfDay < 10)
                    {
                        stringHoraD = horadef.substring(0, 1);
                    }

                    if (minute > 9 && hourOfDay > 9)
                    {
                        stringMinD = horadef.substring(3, 5);
                    } else if (minute < 10 && hourOfDay > 9)
                    {
                        stringMinD = horadef.substring(3, 4);
                    } else if (minute < 10 && hourOfDay < 9)
                    {
                        stringMinD = horadef.substring(2, 3);
                    } else if (minute > 9 && hourOfDay < 10)
                    {
                        stringMinD = horadef.substring(2, 4);
                    }
                    dormirClick = false;
                }

                if (levantarseClick)
                {
                    if (hourOfDay > 9)
                    {
                        stringHoraL = horadef.substring(0, 2);
                    } else if (hourOfDay < 10)
                    {
                        stringHoraL = horadef.substring(0, 1);
                    }

                    if (minute > 9 && hourOfDay > 9)
                    {
                        stringMinL = horadef.substring(3, 5);
                    } else if (minute < 10 && hourOfDay > 9)
                    {
                        stringMinL = horadef.substring(3, 4);
                    } else if (minute < 10 && hourOfDay < 9)
                    {
                        stringMinL = horadef.substring(2, 3);
                    } else if (minute > 9 && hourOfDay < 10)
                    {
                        stringMinL = horadef.substring(2, 4);
                    }
                    levantarseClick = false;
                }

            }
        },hora,minutos,false);
        timePickerDialog.show();
    }

/*Sergio*/
    public void agregar(String agua, String peso, String hora){

        try{
            Cursor c = db.rawQuery("SELECT * FROM Salud WHERE fecha = current_date",null);
            if(!c.moveToFirst()){
                db.execSQL("INSERT INTO Salud(agua, peso, dormir, fecha) " +
                        "VALUES('" + agua + "', '" + peso + "', '"+ hora +"', current_date)");
            }else {
                db.execSQL("UPDATE Salud SET agua = '" + agua + "', peso = '" + peso + "', dormir = '" + hora + "' WHERE fecha = current_date");
            }

            Toast toast1 = Toast.makeText(v.getContext(), "Guardado con exito", Toast.LENGTH_SHORT);
            toast1.show();

        }catch (Exception e){
            Toast toast1 = Toast.makeText(v.getContext(), "No se pudo guardar", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }


    void traerNombre2(Context context, TextView texto){
        BDD con2 = new BDD(context, "Nombre", null, 2);
        db1 = con2.getWritableDatabase();
        Cursor c3 = db1.rawQuery("SELECT nombre FROM Nombre WHERE id >= 1", null);
        while (c3.moveToNext())
        {
            texto.setText(c3.getString(0)+
                    ", aquí hay algunas funciones que te pueden ayudar a llevar un control de tu salud:");
        }
        db1.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        textAgua.setText("" + contadorAgua);
        editPeso.setText(pesoRes);

    }
}
