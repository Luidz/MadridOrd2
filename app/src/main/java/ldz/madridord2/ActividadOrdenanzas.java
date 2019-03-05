package ldz.madridord2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActividadOrdenanzas extends AppCompatActivity {

    EditText tipo, articulo, descripcion;
    Button buscar;
    String seleccion = "-";
    Spinner spn_ley;
    String ley = "0"; //PARA GUARDAR EL NOMBRE DE LA LEY
    final ArrayList<Indice> listaAux = new ArrayList<Indice>();
    final ArrayList<String> arraySpinner = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_ordenanzas);

        spn_ley = (Spinner) findViewById(R.id.spn_ley);
        tipo = (EditText)findViewById(R.id.edit_tipo);
        articulo = (EditText)findViewById(R.id.edit_articulo);
        descripcion = (EditText)findViewById(R.id.edit_descripcion);
        buscar = (Button)findViewById(R.id.btn_buscar);

        /*CONEXIÓN A LA BASE DE DATOS FIREBASE*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("indice");

        /*CREACIÓN DEL ARRAYLIST COMPLETO CON LAS INFRACCIONES*/
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAux.clear();
                arraySpinner.clear();
                /*Bucle para coger todas la normativa presente en el indice*/
                for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                    if (!(data1.getKey().toString().equals("movilidad"))){
                        Indice indice = new Indice(
                                data1.getKey().toString(),
                                data1.getValue().toString()
                        );
                        arraySpinner.add(data1.getValue().toString());
                        listaAux.add(indice);
                    }

                }
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,arraySpinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_lista,arraySpinner);
                spn_ley.setAdapter(adapter);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR: ", "Failed to read value.", error.toException());

            }
        });
        spn_ley.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                seleccion = spn_ley.getSelectedItem().toString();
                Log.i("SELECCIONADO: ", "******************************** "+ seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*BOTÓN BUSCAR*/
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(seleccion.equals("-"))) {
                    for (int i = 0; i < listaAux.size(); i++ ){
                        if (seleccion.equals(listaAux.get(i).valor)){
                            ley = listaAux.get(i).clave;
                        }
                    }
                    Intent intent = new Intent(ActividadOrdenanzas.this, ActividadOrdenanzasLista.class);
                    intent.putExtra("ley", ley);
                    intent.putExtra("tipo", tipo.getText().toString());
                    intent.putExtra("articulo", articulo.getText().toString());
                    intent.putExtra("descripcion", descripcion.getText().toString());
                    intent.putExtra("norma", seleccion);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Selecciona una norma", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onBackPressed (){
        finish();
    }
}
