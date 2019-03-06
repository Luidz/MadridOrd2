package ldz.madridord2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.Normalizer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ActividadOrdenanzasLista extends AppCompatActivity {

    final ArrayList<Infraccion> listaInfra = new ArrayList<Infraccion>();
    final ArrayList<Infraccion> listaAux = new ArrayList<Infraccion>();
    Infraccion infraccion;
    RecyclerView rv;
    RecyclerView.Adapter adaptador;
    RecyclerView.LayoutManager interfaz;
    String tipo, articulo, descripcion, ley, norma;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_ordenanzas_lista);
        intent = getIntent();
        ley = intent.getStringExtra("ley");
        tipo = intent.getStringExtra("tipo");
        articulo = intent.getStringExtra("articulo");
        descripcion = intent.getStringExtra("descripcion");
        norma = intent.getStringExtra("norma");
        setTitle("Resultados");


        /*CONEXIÓN A LA BASE DE DATOS FIREBASE*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ley).child("infraccion");
        /*CREACIÓN DEL ARRAYLIST COMPLETO CON LAS INFRACCIONES*/
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAux.clear();
                /*Bucle para coger todas las infraccionez*/
                for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                    SpannableString cadena = new SpannableString(data1.child("descripcion").getValue().toString());
                    Infraccion infraccion = new Infraccion(
                            data1.child("tipo").getValue().toString(),
                            cadena,
                            //data1.child("descripcion").getValue().toString(),   HE CAMBIADO EL TIPO DESCRIPCIÓN A SPANNABLESTRING
                            data1.child("articulo").getValue().toString()
                    );
                    infraccion.setNorma(norma);
                    listaAux.add(infraccion);

                }
                try {
                    listaInfra.clear();
                    for (int i = 0; i < listaAux.size(); i++) {
                        infraccion = new Infraccion();
                        infraccion = listaAux.get(i);
                        if (infraccion != null) {
                            Boolean coincide = true;
                            String[] discriminantes;
                            //**********************************************************
                            String descripcionAux = infraccion.getDescripcion().toString();
                            SpannableString descripcionSP = new SpannableString(infraccion.getDescripcion());
                            String articuloAux = infraccion.getArticulo();
                            String tipoAux = infraccion.getTipo();


                            if (descripcion.trim().replace(" ", "").length() == 0 &&
                                    tipo.trim().replace(" ", "").length() == 0 &&
                                    articulo.trim().replace(" ", "").length() == 0) {
                                coincide = true;
                            } else {
                                if (!(descripcion.trim().replace(" ", "").length() == 0)) {
                                    discriminantes = descripcion.split(" ");
                                    for (int j = 0; j < discriminantes.length; j++) {
                                        if (!(tildes(descripcionAux).toUpperCase().contains(tildes(discriminantes[j]).toUpperCase()))) {
                                            coincide = false;
                                        }else{
                                            // SUSTITUCIÓN DE LOS DISCRIMINANTES POR EL DISCRIMINANTE CON EL COLOR CAMBIADO DENTRO DEL TEXTO
                                            String aux = tildes(descripcionAux).toUpperCase();
                                            int posicion = 0;
                                            do{
                                                posicion = aux.indexOf(tildes(discriminantes[j].toUpperCase()), posicion);
                                                if (!(posicion==-1)){
                                                    ForegroundColorSpan colorDiscriminante = new ForegroundColorSpan(Color.RED);
                                                    descripcionSP.setSpan(colorDiscriminante, posicion , posicion+discriminantes[j].toString().length(), Spanned.SPAN_COMPOSING);
                                                    posicion++;
                                                    Log.i ("POSICION: --> ", " " + posicion);
                                                }
                                                if (posicion==-1) Log.i ("POSICION: --> ", " " + posicion);
                                            }while (posicion!=-1);

                                        }

                                    }
                                }
                                if (!(tipo.trim().replace(" ", "").length() == 0)) {
                                    discriminantes = tipo.split(" ");
                                    for (int j = 0; j < discriminantes.length; j++) {
                                        if (!(tildes(tipoAux).toUpperCase().contains(tildes(discriminantes[j]).toUpperCase()))) {
                                            coincide = false;
                                        }
                                    }
                                }

                                if (!(articulo.trim().replace(" ", "").length() == 0)) {
                                    discriminantes = articulo.split(" ");
                                    for (int j = 0; j < discriminantes.length; j++) {
                                        if (!(articuloAux.contains(discriminantes[j].toUpperCase()))) {
                                            coincide = false;
                                        }
                                    }
                                }
                            }

                            if (coincide) {
                                infraccion.setTipo(tipoAux);

                                infraccion.setArticulo(articuloAux);

                                Sancion sancion = new Sancion(ley, tipoAux, articuloAux);
                                infraccion.setEuros(sancion.Calcular());

                                //infraccion.setDescripcion(descripcionAux);
                                infraccion.setDescripcion(descripcionSP);

                                listaInfra.add(infraccion);
                            }
                        }
                    }
                }catch (Exception e){
                e.printStackTrace();
                }

                //Recuperamos la vista
                rv = (RecyclerView) findViewById(R.id.listaOrdenanzas);

                //Preparamos la interfaz
                interfaz = new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(interfaz);

                //Introducimos los datos de la lista en el adapatador.
                adaptador = new AdaptadorInfracciones2(listaInfra, getApplicationContext());
                rv.setAdapter(adaptador);

                Toast.makeText(getApplicationContext(), "Coincidencias: " + adaptador.getItemCount(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR: ", "Failed to read value.", error.toException());

            }
        });//myRef.addListener
    }//ONCREATE
    @Override
    public void onBackPressed (){
        finish();
    }

    public static String tildes(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}

