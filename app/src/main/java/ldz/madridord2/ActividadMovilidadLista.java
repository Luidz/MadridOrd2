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

public class ActividadMovilidadLista extends AppCompatActivity {

    final ArrayList<Infraccion> listaInfra = new ArrayList<Infraccion>();
    final ArrayList<Infraccion> listaAux = new ArrayList<Infraccion>();
    Infraccion infraccion;
    RecyclerView rv;
    RecyclerView.Adapter adaptador;
    RecyclerView.LayoutManager interfaz;
    String clave, articulo, puntos, euros, descripcion;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movilidad_lista);

        intent = getIntent();
        clave = intent.getStringExtra("clave");
        articulo = intent.getStringExtra("articulo");
        puntos = intent.getStringExtra("puntos");
        euros = intent.getStringExtra("euros");
        descripcion = intent.getStringExtra("descripcion");

        /*CONEXIÓN A LA BASE DE DATOS FIREBASE*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movilidad").child("infraccion");

        /*CREACIÓN DEL ARRAYLIST COMPLETO CON LAS INFRACCIONES*/
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAux.clear();
                /*Bucle para coger todas las infraccionez*/
                for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                    SpannableString cadena = new SpannableString(data1.child("descripcion").getValue().toString());
                    Infraccion infraccion = new Infraccion(
                            data1.child("clave").getValue().toString(),
                            data1.child("articulo").getValue().toString(),
                            data1.child("euros").getValue().toString(),
                            data1.child("puntos").getValue().toString(),
                            cadena
                    );
                    listaAux.add(infraccion);
                }
                    try{
                        listaInfra.clear();
                        for (int i=0; i < listaAux.size(); i++){
                            infraccion = new Infraccion();
                            infraccion = listaAux.get(i);
                            if (infraccion != null){
                                Boolean coincide = true;
                                String[] discriminantes;

                                String claveAux = infraccion.getClave();
                                String articuloAux = infraccion.getArticulo();
                                String eurosAux = infraccion.getEuros();
                                String puntosAux = infraccion.getPuntos();

                                //AÑADIDO DE SPANNABLESTRING
                                SpannableString descripcionSP =  infraccion.getDescripcion();
                                String descripcionAux = infraccion.getDescripcion().toString();

                                if (descripcion.trim().replace(" ", "").length() == 0 &&
                                        clave.trim().replace(" ", "").length() == 0 &&
                                        euros.trim().replace(" ", "").length() == 0 &&
                                        puntos.trim().replace(" ", "").length() == 0 &&
                                        articulo.trim().replace(" ", "").length() == 0){
                                    coincide = true;
                                }else{
                                    if (!(descripcion.trim().replace(" ", "").length() == 0)) {
                                        discriminantes = descripcion.split(" ");
                                        for (int j = 0; j < discriminantes.length; j++) {
                                            if (!(tildes(descripcionAux).toUpperCase().contains(tildes(discriminantes[j]).toUpperCase()))) {
                                                coincide = false;
                                            }else {
                                                // SUSTITUCIÓN DE LOS DISCRIMINANTES POR EL DISCRIMINANTE CON EL COLOR CAMBIADO DENTRO DEL TEXTO
                                                String aux = tildes(descripcionAux).toUpperCase();
                                                int posicion = 0;
                                                do{
                                                    posicion = aux.indexOf(tildes(discriminantes[j].toUpperCase()), posicion);
                                                    if (!(posicion==-1)){
                                                        ForegroundColorSpan colorDiscriminante = new ForegroundColorSpan(Color.RED);
                                                        descripcionSP.setSpan(colorDiscriminante, posicion , posicion+discriminantes[j].toString().length(), Spanned.SPAN_COMPOSING);
                                                        posicion++;
                                                    }
                                                }while (posicion!=-1);
                                            }
                                        }
                                    }
                                    if (!(clave.trim().replace(" ", "").length() == 0)) {
                                        discriminantes = clave.split(" ");
                                        for (int j = 0; j < discriminantes.length; j++) {
                                            if (!(claveAux.contains(discriminantes[j]))) {
                                                coincide = false;
                                            }
                                        }
                                    }
                                    if (!(euros.trim().replace(" ", "").length() == 0)) {
                                        discriminantes = euros.split(" ");
                                        for (int j = 0; j < discriminantes.length; j++) {
                                            if (!(eurosAux.contains(discriminantes[j]))) {
                                                coincide = false;
                                            }
                                        }
                                    }
                                    if (!(puntos.trim().replace(" ", "").length() == 0)) {
                                        discriminantes = puntos.split(" ");
                                        for (int j = 0; j < discriminantes.length; j++) {
                                            if (!(puntosAux.contains(discriminantes[j]))) {
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

                                if (coincide){
                                    infraccion.setClave(claveAux);

                                    infraccion.setArticulo(articuloAux);

                                    infraccion.setEuros(eurosAux);

                                    infraccion.setPuntos(puntosAux);

                                    //infraccion.setDescripcion(descripcionAux);
                                    //infraccion.setDescripcion(new SpannableString(descripcionAux));
                                    infraccion.setDescripcion(descripcionSP);
                                    listaInfra.add(infraccion);
                                }
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                //Recuperamos la vista
                rv = (RecyclerView) findViewById(R.id.listaClaves);

                //Preparamos la interfaz
                interfaz = new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(interfaz);

                //Introducimos los datos de la lista en el adapatador.
                adaptador = new AdaptadorInfracciones(listaInfra, getApplicationContext());
                rv.setAdapter(adaptador);

                Toast.makeText(getApplicationContext(), "Coincidencias: " + adaptador.getItemCount(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR: ", "Failed to read value.", error.toException());

            }
        });


    }
    @Override
    public void onBackPressed (){
        finish();
    }
    public static String tildes(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
