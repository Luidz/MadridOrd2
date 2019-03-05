package ldz.madridord2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Button btn_movilidad = (Button) findViewById(R.id.btn_movilidad);
        Button btn_lopsc = (Button) findViewById(R.id.btn_lopsc);

        btn_movilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, ActividadMovilidad.class);
                startActivity(intent);
            }
        });

        btn_lopsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, ActividadOrdenanzas.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed (){
        finish();
    }
}
