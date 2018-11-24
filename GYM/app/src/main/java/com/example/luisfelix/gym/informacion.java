package com.example.luisfelix.gym;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class informacion extends Fragment {


    public informacion() {
        // Required empty public constructor
    }

    public MaterialCardView iCV, iCV2, iCV3, iCV4, iCV5, iCV6;
    public ImageView imgInfoRutina, imgInfoPiernas, imgfInfoBrazos, imgInfoPecho, imgInfoEspalda, imgInfoDescripcion;
    public TextView txtInfoRutina, txtInfoPiernas, txtInfoBrazos, txtInfoPecho, txtInfoEspalda, txtInfoDescripcion;
    public Button btnEditar, btnEliminar;
    public View v;
    public SQLiteDatabase db;
    public String rutinaNombre;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_informacion, container, false);

        iCV = v.findViewById(R.id.iCV);
        iCV2 = v.findViewById(R.id.iCV2);
        iCV3 = v.findViewById(R.id.iCV3);
        iCV4 = v.findViewById(R.id.iCV4);
        iCV5 = v.findViewById(R.id.iCV5);
        iCV6 = v.findViewById(R.id.iCV6);

        imgInfoRutina = v.findViewById(R.id.imgInfoRutina);
        imgInfoPiernas = v.findViewById(R.id.imgInfoPiernas);
        imgfInfoBrazos = v.findViewById(R.id.imgInfoBrazos);
        imgInfoPecho = v.findViewById(R.id.imgInfoPecho);
        imgInfoEspalda = v.findViewById(R.id.imgInfoEspalda);
        imgInfoDescripcion = v.findViewById(R.id.imgInfoDescripcion);

        txtInfoRutina = v.findViewById(R.id.compatTxtRutina);
        txtInfoPiernas = v.findViewById(R.id.compatTxtPiernas);
        txtInfoBrazos = v.findViewById(R.id.compatTxtBrazos);
        txtInfoPecho = v.findViewById(R.id.compatTxtPecho);
        txtInfoEspalda = v.findViewById(R.id.compatTxtEspalda);
        txtInfoDescripcion = v.findViewById(R.id.compatTxtDescripcion);

        btnEditar = v.findViewById(R.id.btnEditar);
        btnEliminar = v.findViewById(R.id.btnEliminar);

        BDD con = new BDD(v.getContext(), "Rutinas", null, 1);
        db = con.getWritableDatabase();

        /*
        *
        * --- Se recoge el Bundle enviado en la clase CardViewAdapter con la información especificada
        *
         */
        rutinaNombre = getArguments().getString("rutinaNombre");


        /*
        *
        * --- Select en la base de datos donde el nombre coincida con la rutina seleccionada
        *
         */

        Cursor c = db.rawQuery("SELECT  nombreRutina, rutinaPiernas, rutinaBrazos, rutinaPecho, rutinaEspalda, rutinaDescripcion FROM Rutinas WHERE nombreRutina = '" + rutinaNombre + "'", null);

        /*
        *
        * --- Se imprimen los datos de la base de datos en los campos correspondientes
        *
         */

        while (c.moveToNext()) {
            txtInfoRutina.setText(c.getString(0));
            txtInfoPiernas.setText(c.getString(1));
            txtInfoBrazos.setText(c.getString(2));
            txtInfoPecho.setText(c.getString(3));
            txtInfoEspalda.setText(c.getString(4));
            txtInfoDescripcion.setText(c.getString(5));
        }

        /*
        *
        * --- En caso de presionar el botón eliminar, se ejecutará un DELETE FROM TABLE
        * donde se encuentre el nombre de la Rutina.
        *
         */

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("DELETE FROM Rutinas WHERE nombreRutina = '" + rutinaNombre + "'");
                getActivity().onBackPressed();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                *
                * --- Instancia al agendador de Rutinas
                *
                 */

                AgendarRutina2 agendarRutina2 = new AgendarRutina2();

                /*
                *
                * --- Se obtienen los valores a tener en cuenta a la hora de editar la Rutina,
                *  para luego almacenarlas en un Bundle, y posteriormente cargarla como argumentos
                *  en dirección al agendador de rutinas.
                *
                 */
                Bundle var2 = new Bundle();
                var2.putString("rutinaNombre", rutinaNombre.toString());
                var2.putString("rutinaPiernas", txtInfoPiernas.getText().toString());
                var2.putString("rutinaBrazos", txtInfoBrazos.getText().toString());
                var2.putString("rutinaPecho", txtInfoPecho.getText().toString());
                var2.putString("rutinaEspalda", txtInfoEspalda.getText().toString());
                var2.putString("rutinaDescripcion", txtInfoDescripcion.getText().toString());
                var2.putBoolean("editConf", true);

                agendarRutina2.setArguments(var2);

                //Al presionar el botón Editar me manda al agendador de Rutinas

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, agendarRutina2);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        // Inflate the layout for this fragment
        return v;
    }

}
