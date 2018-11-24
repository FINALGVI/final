package com.example.luisfelix.gym;


import android.database.Cursor;
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

import java.util.ArrayList;


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
    public String referencia;
    public Boolean bundleEdit, cfm;
    public Bundle contenido;
    public Cursor c;

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

        /*
        *
        *  --- Me atrapa los argumentos o Bundles de los fragment que se estén pasando. En caso de no
        *  capturar ninguno, se queda siendo NULL, dando lugar a un NPE.
        *
         */
        contenido = getArguments();

        /*
        *
        * --- En caso de que contenido no sea null, se sobreentiende que se ha seleccionado editar una rutina desde el Fragment "información"
        * por lo que el texto del botón "ACEPTAR" pasa a ser "GUARDAR CAMBIOS" (dando referencia a una edición).
        *
         */

        if (contenido != null)
        {

            btnAceptarRutina.setText("GUARDAR CAMBIOS");

            /*
             *
             * --- Si el Boolean con el Key "editConf" se envía satisfactoriametne desde el Fragment información
             * al Fragment AgendarRutina, entonces los datos correspondientes se llenan por sí solos en el formulario luego de ser capturados.
             * Se utiliza como un comprobante de que todo esté funcionando correctamente.
             *
             */

            bundleEdit = contenido.getBoolean("editConf");


            if (bundleEdit)
            {
                referencia = getArguments().getString("rutinaNombre");
                editNombreRutina.setText(contenido.getString("rutinaNombre"));
                editPiernas.setText(contenido.getString("rutinaPiernas"));
                editBrazos.setText(contenido.getString("rutinaBrazos"));
                editPecho.setText(contenido.getString("rutinaPecho"));
                editEspalda.setText(contenido.getString("rutinaEspalda"));
                editDescripcion.setText(contenido.getString("rutinaDescripcion"));
            }

        }

        btnAceptarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*
                *
                * --- Se declara el Home_Frag que se usará luego para poder moverse al Home nuevamente después de ingresar la Rutina.
                * --- Se le asigna el valor true al Boolean CFM que se usa para comprobar el estado del campo nombreTitulo que se usa
                * como si fuera la PrimaryKey a la hora de trabajar con los datos.
                *
                * */

                Home_Frag h = new Home_Frag();
                Cursor c = db.rawQuery("SELECT  nombreRutina, rutinaDescripcion FROM Rutinas", null);
                cfm = true;

                /*
                *
                * --- Se utiliza un bucle con el Cursor C para evaluar los nombres de rutinas que ya están en la BDD, así
                * no se repetirá ningún nombre y no habrá inconsistencia de datos.
                * --- En caso de que haya un nombre repetido, el valor de CFM pasa a ser False, por lo que no se podrá
                * registrar la rutina de manera satisfactoria.
                * --- Este bucle es válido tanto en el caso de editar una rutina o agregar una nueva.
                *
                 */

                while (c.moveToNext())
                {
                    if(c.getString(0).equals(editNombreRutina.getText().toString()) || editNombreRutina.getText().toString().length() <= 3)
                    {
                        Toast t1 = Toast.makeText(v.getContext(), "Una rutina con este nombre ya existe o el nombre es muy corto", Toast.LENGTH_LONG);
                        t1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        t1.show();
                        cfm = false;
                        break;
                    }
                }

                /*
                 *
                 * --- En caso de que contenido no sea null, se sobreentiende que se ha seleccionado editar una rutina desde el Fragment "información"
                 * por lo que se ejecuta un UPDATE sobre los datos luego de confirmar el valor del Boolean CFM, así no se agregará una rutina nueva,
                 * sino que se actualizará la actual. El boolean almacenado en bundleEdit pasará a ser false nuevamente (valor original) para mantener
                 * la consistencia del proceso.
                 *
                 */

                if (contenido != null)
                {
                    if (bundleEdit && cfm)
                    {
                        bundleEdit = false;

                        db.execSQL("UPDATE Rutinas SET nombreRutina ='" + editNombreRutina.getText().toString() + "', rutinaPiernas = '"
                                + editPiernas.getText().toString() + "', rutinaBrazos = '" + editBrazos.getText().toString() + "', rutinaPecho = '" + editPecho.getText().toString() + "', rutinaEspalda = '"
                                + editDescripcion.getText().toString() + "', rutinaDescripcion = '" + editDescripcion.getText().toString() + "' WHERE nombreRutina = '" + referencia + "'");
                        h.initializeData(v.getContext());
                    }

                /*
                *
                * -- De otra forma (en caso de que bundleEdit no se esté tomando en cuenta, y por ende, no se haya ejecutado
                * la acción "Editar" desde el fragment información, se insertará una nueva rutina porque se sobreentiende que
                * se ha seleccionado el botón NUEVA RUTINA.
                *
                 */

                } else if (cfm)
                {
                    db.execSQL("INSERT INTO Rutinas(nombreRutina, rutinaPiernas, rutinaBrazos, rutinaPecho, rutinaEspalda, rutinaDescripcion) VALUES ('"
                            + editNombreRutina.getText().toString() +
                            "', '" + editPiernas.getText().toString() + "', '" + editBrazos.getText().toString()+
                            "', '" + editPecho.getText().toString() + "', '" + editEspalda.getText().toString() +
                            "', '" + editDescripcion.getText().toString() + "')");
                    h.initializeData(v.getContext());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main_fragment, h);

                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

        return v;
    }

}
