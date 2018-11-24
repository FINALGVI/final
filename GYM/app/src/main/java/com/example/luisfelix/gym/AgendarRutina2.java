package com.example.luisfelix.gym;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendarRutina2 extends Fragment {


    public AgendarRutina2() {
        // Required empty public constructor
    }

    public MaterialCardView CV, CV2, CV3, CV4, CV5;
    public AppCompatImageView imgUser, imgPiernas, imgBrazos, imgPecho, imgEspalda;
    public TextInputEditText editNombreRutina, editPiernas, editBrazos, editPecho, editEspalda, editDescripcion;
    public Button btnAceptarRutina;
    public FrameLayout layoutAgregarRutina;
    public SQLiteDatabase db;
    public View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_agendar_rutina2, container, false);

        layoutAgregarRutina = v.findViewById(R.id.agregarRutinas_fragment);

        CV = v.findViewById(R.id.CV);
        CV2 = v.findViewById(R.id.CV2);
        CV3 = v.findViewById(R.id.CV3);
        CV4 = v.findViewById(R.id.CV4);
        CV5 = v.findViewById(R.id.CV5);

        imgUser = v.findViewById(R.id.imgUser);
        imgPiernas = v.findViewById(R.id.imgPiernas);
        imgBrazos = v.findViewById(R.id.imgBrazos);
        imgPecho = v.findViewById(R.id.imgPecho);
        imgEspalda = v.findViewById(R.id.imgEspalda);

        editNombreRutina = v.findViewById(R.id.editNombreRutina);
        editPiernas = v.findViewById(R.id.editPiernas);
        editBrazos = v.findViewById(R.id.editBrazos);
        editPecho = v.findViewById(R.id.editPecho);
        editEspalda = v.findViewById(R.id.editEspalda);
        editDescripcion = v.findViewById(R.id.editDescripcion);

        btnAceptarRutina = v.findViewById(R.id.btnRutina);

        BDD con = new BDD(v.getContext(), "Rutinas", null, 1);
        db = con.getWritableDatabase();

        btnAceptarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Boolean cfm = false, cfm2 = false, cfm3 = false, cfm4 = false, cfm5 = false, cfm6 = false;

                /*Me confirma que el nombre de la rutina tenga al menos 4 letras (por favor, poner el resto de condiciones..
                o quitarlas si lo ven oportuno, tengo demasiado sueño*/

                if(editNombreRutina.getText().toString().length() >= 4)
                {
                    //cfm = true;
                } else
                {
                    Toast t1 = Toast.makeText(v.getContext(), "El nombre es muy corto. Debe ingresar uno con al menos 4 carácteres", Toast.LENGTH_LONG);
                    t1.setGravity(Gravity.CENTER, 0, 0);
                }

                //Me inserta el valor de cada campo en la base de datos

                db.execSQL("INSERT INTO Rutinas(nombreRutina, rutinaPiernas, rutinaBrazos, rutinaPecho, rutinaEspalda, rutinaDescripcion) VALUES ('"
                        + editNombreRutina.getText().toString() +
                        "', '" + editPiernas.getText().toString() + "', '" + editBrazos.getText().toString()+
                        "', '" + editPecho.getText().toString() + "', '" + editEspalda.getText().toString() +
                        "', '" + editDescripcion.getText().toString() + "')");
                Home_Frag h = new Home_Frag();
                h.initializeData(v.getContext());

                //A partir de aquí programa lo que te dije

                Home_Frag home=new Home_Frag();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, home);

                ft.addToBackStack(null);
                ft.commit();

            }
        });

        return v;
    }

}
