package com.example.luisfelix.gym;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Settings_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Settings_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MaterialButton btnGuardarNombre;
    TextInputEditText editNombre;
    public SQLiteDatabase db;
    private Home_Frag home_frag;
    View v;

    private OnFragmentInteractionListener mListener;

    public Settings_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings_Frag newInstance(String param1, String param2) {
        Settings_Frag fragment = new Settings_Frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_settings_, container, false);

        editNombre=v.findViewById(R.id.nombre);
        btnGuardarNombre=v.findViewById(R.id.btnguardar);
        home_frag=new Home_Frag();

        try{
            BDD con = new BDD(v.getContext(), "Nombre", null, 2);
            db = con.getWritableDatabase();
        }catch(Exception e){
            Toast toast1 =
                    Toast.makeText(v.getContext(),
                            "Error al conectar", Toast.LENGTH_SHORT);

            toast1.show();
        }

        btnGuardarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=editNombre.getText().toString();
                if(nombre.isEmpty()) {
                    final AlertDialog.Builder b =new AlertDialog.Builder(getContext());
                    b.setMessage("Dejaste el campo vacio")
                            .setIcon(R.drawable.ic_user)
                            .setTitle("ALERTA!!!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                    .create().show();
                }else{
                    agregarNombre(nombre);

                    home_frag=new Home_Frag();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment, home_frag);
                    fragmentTransaction.commit();
                }
            }
        });

        return  v;
    }

        public void agregarNombre(String nombre){
            try{
                Cursor c = db.rawQuery("select * from Nombre",null);
                if(!c.moveToFirst()){
                    db.execSQL("insert into Nombre(nombre) values('" +nombre+"')");
                }else {
                    db.execSQL("update Nombre set nombre='"+nombre+"' ");
                }
                Toast.makeText(v.getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(v.getContext(), "No se pudo guardar", Toast.LENGTH_SHORT).show();
            }
        }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
