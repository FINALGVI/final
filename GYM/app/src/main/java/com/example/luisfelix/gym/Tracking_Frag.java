package com.example.luisfelix.gym;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public Tracking_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tracking_, container, false);
        textNombre3=v.findViewById(R.id.textNombre3);
        listView = v.findViewById(R.id.listView);
        traerNombre3(v.getContext(), textNombre3);
        lista();
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

    void lista(){
        BDD con2 = new BDD(v.getContext(), "Nombre", null, 2);
        db = con2.getWritableDatabase();

        List<String> List = new ArrayList<String>();

        try{
            Cursor c = db.rawQuery("SELECT fecha, agua, peso, dormir FROM Salud", null);

            if (c.moveToFirst()) {
                do {
                    String s = c.getString(0) + "\n" + c.getString(1);
                    List.add(s);
                } while (c.moveToNext());
            }

            ArrayAdapter<String> adaptador =
                    new ArrayAdapter<String>(v.getContext(),
                            android.R.layout.simple_list_item_1, List);
            listView.setAdapter(adaptador);
        }catch(Exception e){
            Toast toast1 =
                    Toast.makeText(v.getContext(),
                            "Error al conectar", Toast.LENGTH_SHORT);
            toast1.show();
        }

    }

}
