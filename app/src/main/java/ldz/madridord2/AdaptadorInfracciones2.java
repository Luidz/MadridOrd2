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

public class AdaptadorInfracciones2 extends RecyclerView.Adapter<AdaptadorInfracciones2.ViewHolder>{

    ArrayList<Infraccion> array;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tipo, articulo, euros, puntos, hecho, norma;

        ViewHolder(View itemView){
            super(itemView);
            tipo = (TextView)itemView.findViewById((R.id.txt_tipo2));
            articulo = (TextView)itemView.findViewById((R.id.txt_art2));
            euros = (TextView)itemView.findViewById((R.id.txt_euros2));
            hecho = (TextView)itemView.findViewById((R.id.txt_hecho2));
            norma = (TextView)itemView.findViewById((R.id.norma));
        }

    }//FIN VIEWHOLDER

    //CONSTRUCTOR DEL ADAPTADOR
    public AdaptadorInfracciones2(ArrayList<Infraccion> array, Context context){
        this.context = context;
        this.array = array;
    }

    //RECUPERACIÓN DE LA PLANTILLA DE LA TARJETA
    @Override
    public AdaptadorInfracciones2.ViewHolder onCreateViewHolder (ViewGroup parent, int tipoVista){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_infraccion2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //COLOCACIÓN DE LOS ELEMENTOS DEL ARRAY EN LA PLANTILLA
    public void onBindViewHolder (final ViewHolder itemView, int posicion){
        itemView.tipo.setText(array.get(posicion).getTipo());
        itemView.articulo.setText(array.get(posicion).getArticulo());
        itemView.euros.setText(array.get(posicion).getEuros());
        itemView.hecho.setText(array.get(posicion).getDescripcion());
        itemView.norma.setText(array.get(posicion).getNorma());
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
