package com.example.luisfelix.gym;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.RutinaViewHolder> {

    public CardViewAdapter(List<Rutina> rutinas) {
        this.rutinas = rutinas;
    }
    // Lista de rutinas
    public List<Rutina> rutinas;

    public static class RutinaViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public View mView;
        public TextView txtRutina;
        public TextView txtDescripcion;
        private RecyclerView rv;
        public int position;

        //ViewHolder de las rutinas, me muestra la información y aquí se declaran las variables necesarias para accesar a ella.
        RutinaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            txtRutina = (TextView)itemView.findViewById(R.id.nombreRutina);
            txtDescripcion = (TextView)itemView.findViewById(R.id.rutinaDescripcion);
            rv = itemView.findViewById((R.id.rv));
            mView = itemView;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RutinaViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        RutinaViewHolder rvh = new RutinaViewHolder(v);

        /*
        *
        * --- Método onClickListener a la escucha de cada CardView de manera individual. Busca como
        * meta final el abrir el fragment de la información correspondiente a cada CardView.
        *
         */
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Toast.makeText(v.getContext(),"Este es la rutina número " + Integer.toString(position+1),Toast.LENGTH_LONG).show();

                /*
                *
                * --- Bundle que captura el nombre de la rutina dentro del ArrayList
                * en la posición correspondiente al CardView
                *
                 */

                Bundle var = new Bundle();
                var.putString("rutinaNombre", String.valueOf(rutinas.get(position).rutinaNombre));

                /*
                *
                * --- Se envia el Bundle al Fragment información luego de ser pasado como argumento.
                *
                 */

                informacion informacion = new informacion();
                informacion.setArguments(var);

                /*
                *
                * --- Código que toma el contexto actual desde AppCompatActivity (Hijo de FragmentActivity) y lo
                * utiliza para iniciar un nuevo fragmentoen relación al CardView que se haya seleccionado
                * por el método onClickListener
                *
                */
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Home_Frag h = new Home_Frag();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, informacion).addToBackStack(null).commit();

            }
        });
        return rvh;
    }

    @Override
    public void onBindViewHolder(RutinaViewHolder rutinaViewHolder, int i) {

        /*
        *
        * --- Método en donde el nombre y la rutina se le es asignado a cada CardView, los cuales
        * sirven como un Preview de las rutinas. Adicionalmente, se le asigna un "tag" a CardView
        * en su respectiva posición para poder acceder a ellos desde el método RutinaViewHolder.
        *
         */
        rutinaViewHolder.txtRutina.setText(rutinas.get(i).rutinaNombre);
        rutinaViewHolder.txtDescripcion.setText(rutinas.get(i).descripcionRutina);
        rutinaViewHolder.cv.setTag(i);
    }

    //Me cuenta la cantidad de rutinas
    @Override
    public int getItemCount() {
        return rutinas.size();
    }
}
