package com.example.luisfelix.gym;

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
    public RutinaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        RutinaViewHolder rvh = new RutinaViewHolder(v);

        //Al hacer click en un CardView, este se abrirá y mostrará la información de ese cardview (aún no está implementado, falta chequear los FRAGMENTS DE MIERDA)
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Toast.makeText(v.getContext(),"Este es la rutina número " + Integer.toString(position+1),Toast.LENGTH_LONG).show();
                /*Intent pe = new Intent("com.example.intent.prueba");
                Bundle var = new Bundle();
                var.putString("nombre", String.valueOf(eventos.get(position).eventoNombre));
                pe.putExtras(var);
                v.getContext().startActivity(pe);*/
            }
        });
        return rvh;
    }

    @Override
    public void onBindViewHolder(RutinaViewHolder rutinaViewHolder, int i) {
        //Le asigna un número a cada CardView creado
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
