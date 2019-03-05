package ldz.madridord2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActividadMovilidad extends AppCompatActivity {

    EditText clave, articulo, euros, puntos, descripcion;
    Button buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movilidad);

        clave = (EditText)findViewById(R.id.edit_clave);
        articulo = (EditText)findViewById(R.id.edit_articulo);
        euros = (EditText)findViewById(R.id.edit_euros);
        puntos = (EditText)findViewById(R.id.edit_puntos);
        descripcion = (EditText)findViewById(R.id.edit_descripcion);

        buscar = (Button)findViewById(R.id.btn_buscar);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActividadMovilidad.this, ActividadMovilidadLista.class);
                intent.putExtra("clave", clave.getText().toString());
                intent.putExtra("articulo", articulo.getText().toString());
                intent.putExtra("euros", euros.getText().toString());
                intent.putExtra("puntos", puntos.getText().toString());
                intent.putExtra("descripcion", descripcion.getText().toString());
                startActivity(intent);
            }
        });
    }
}
