package com.example.luisfelix.gym;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tracking_Frag extends Fragment {
    TextView textNombre3;
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
        traerNombre3(v.getContext(), textNombre3);
        return v;
    }

    void traerNombre3(Context context, TextView texto){
        BDD con2 = new BDD(context, "Nombre", null, 2);
        db = con2.getWritableDatabase();
        Cursor c3 = db.rawQuery("SELECT nombre FROM Nombre WHERE id >= 1", null);
        while (c3.moveToNext())
        {
            texto.setText(c3.getString(0)+" aqui ves las cosas selecionadas anteriormente:");
        }
        db.close();
    }

}
