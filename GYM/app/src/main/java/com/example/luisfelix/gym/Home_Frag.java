package com.example.luisfelix.gym;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Frag extends Fragment {

    public RecyclerView rv;
    public Button btnNuevaRutina;
    public LinearLayout linearMain;
    public CardView cv;
    public List<Rutina> rutinas;
    public View v;
    public SQLiteDatabase db;
    public Bundle contenido;
    String Nombre;
    TextView textNombre;

    public Home_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
        *
        * --- Toma el RecyclerView, el cual va reciclando los CardViews para luego mostrarlos al inflar el contenido del Layout
        *
         */
        v = inflater.inflate(R.layout.recycler, container, false);

        textNombre=v.findViewById(R.id.textNombre);
        contenido=getArguments();
        Nombre = getArguments().getString("Nombres");

        BDD con = new BDD(getContext(), "Nombre", null, 1);
        db = con.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT nombre FROM Nombre WHERE nombre = '" + Nombre + "'", null);
            textNombre.setText(c.getString(0));
        db.close();


        rv=(RecyclerView) v.findViewById(R.id.rv);
        btnNuevaRutina = v.findViewById(R.id.nuevaRutina);
        linearMain = v.findViewById(R.id.linearLayout);

        /*
        *
        * --- LayoutManager que se utiliza dependiendo del tipo de Layout en el que esté incrustado
        * el RecyclerView. Su función es establecer un orden de aparición a los elementos del RecyclerView
        *
         */
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData(v.getContext());
        initializeAdapter();

        /*
        *
        * --- OnClickListener que tiene en escucha al botón NuevaRutina. En caso de que el evento
        * se ejecute, se instancia el fragment del Agendador de Rutinas y posteriormente es iniciado
        *
         */
        btnNuevaRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AgendarRutina2 agendarRutina2 = new AgendarRutina2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, agendarRutina2);

                ft.addToBackStack(null);
                ft.commit();
            }
        });



        // Inflate the layout for this fragment
        return v;
    }

    /*
    * --- Método que conecta con la base de datos. Requiere un parámetro de tipo Context.
    * --- Lo creo de esta manera porque puede que sea necesario conectar con la base de datos
    * en otras clases, y los contextos de otras clases o fragmentos no son iguales, por
    * lo cual es más eficiente que el método tome un parámetro context, el cual puede
    * ser de diferentes clases y trabaje en base a él
     */
    void initializeData(Context context){

        /*
        *
        * --- Método donde se inicializan los datos. Se inicia una conexión a la base de datos, para
        * luego traer los datos y agregarlos al ArrayList rutinas para que puedan ser vistos
        * dentro del RecyclerView, y por ende, los CardViews.
        *
         */

        BDD con = new BDD(context, "Rutinas", null, 1);
        db = con.getWritableDatabase();
        rutinas = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT  nombreRutina, rutinaDescripcion FROM Rutinas", null);

        while (c.moveToNext())
        {
            rutinas.add(new Rutina(c.getString(0), c.getString(1)));
        }
        db.close();
    }

    /*
    Adaptador del ListArray de Rutinas. Funciona en conjunto al CardView
    y al RecyclerView a la hora de accesar, mostrar y trabajar con los datos
     */
    void initializeAdapter(){
        CardViewAdapter adapter = new CardViewAdapter(rutinas);
        rv.setAdapter(adapter);
    }

}
