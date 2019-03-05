package ldz.madridord2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ADAPTADOR DE LA LISTA DE INFRACCIONES
 */

public class AdaptadorInfracciones extends RecyclerView.Adapter<AdaptadorInfracciones.ViewHolder>{

    ArrayList<Infraccion> array;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView clave, articulo, euros, puntos, hecho;

        ViewHolder(View itemView){
            super(itemView);
            clave = (TextView)itemView.findViewById((R.id.txt_clave2));
            articulo = (TextView)itemView.findViewById((R.id.txt_art2));
            euros = (TextView)itemView.findViewById((R.id.txt_euros2));
            puntos = (TextView)itemView.findViewById((R.id.txt_puntos2));
            hecho = (TextView)itemView.findViewById((R.id.txt_hecho2));
        }

    }//FIN VIEWHOLDER

    //CONSTRUCTOR DEL ADAPTADOR
    public AdaptadorInfracciones(ArrayList<Infraccion> array, Context context){
        this.context = context;
        this.array = array;
    }

    //RECUPERACIÓN DELA PLANTILLA DE LA TARJETA
    @Override
    public AdaptadorInfracciones.ViewHolder onCreateViewHolder (ViewGroup parent, int tipoVista){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_infraccion, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //COLOCACIÓN DE LOS ELEMENTOS DEL ARRAY EN LA PLANTILLA
    public void onBindViewHolder (final ViewHolder itemView, int posicion){
        itemView.clave.setText(array.get(posicion).getClave());
        itemView.articulo.setText(array.get(posicion).getArticulo());
        itemView.euros.setText(array.get(posicion).getEuros());
        itemView.puntos.setText(array.get(posicion).getPuntos());
        itemView.hecho.setText(array.get(posicion).getDescripcion());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv){
        super.onAttachedToRecyclerView(rv);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

}
