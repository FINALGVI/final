package com.example.luisfelix.gym;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tracking_Frag extends Fragment {
    TextView textNombre3;
    ListView listView;
    public SQLiteDatabase db;
    View v;
    ListView lista;
    ArrayList<String> arreglo;
    BDD database;

    public Tracking_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tracking_, container, false);

        lista = v.findViewById(R.id.listView);

        arreglo = new ArrayList<String>();
        database=new BDD(v.getContext(), "Salud", null, 2);
        arreglo=database.llenar_lv();
        final ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, arreglo);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), arreglo.get(position), Toast.LENGTH_LONG).show();
                }
        });

        textNombre3=v.findViewById(R.id.textNombre3);
        traerNombre3(v.getContext(), textNombre3);
        return v;
    }

    void traerNombre3(Context context, TextView texto){
        BDD con2 = new BDD(context, "Nombre", null, 2);
        db = con2.getWritableDatabase();
        Cursor c3 = db.rawQuery("SELECT nombre FROM Nombre WHERE id >= 1", null);
        while (c3.moveToNext())
        {
            texto.setText(c3.getString(0)+" aqui ves tu historial:");
        }
        db.close();
    }
}
