package com.example.luisfelix.gym;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class Fitness_Frag extends Fragment {

    FloatingActionButton masAgua, menosAgua;
    TextView textAgua;
    Button botonPeso;
    EditText editPeso;
    int contadorAgua=0;
    int peso;

    public Fitness_Frag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fitness, container, false);

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

        botonPeso=v.findViewById(R.id.btnPeso);
        editPeso=v.findViewById(R.id.editPeso);

        botonPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peso=Integer.parseInt(editPeso.getText().toString());

            }
        });



        return v;
    }
   /* public void masAgua(View view){
        int contador=0;
        while(contador<50){
            contador=contador+1;
            editAgua.setText(contador);
        }
    }*/

}
