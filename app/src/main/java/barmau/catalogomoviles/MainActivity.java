package barmau.catalogomoviles;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import barmau.catalogomoviles.conections.LocalReceiver;
import barmau.catalogomoviles.elementos.Vinilo;

public class MainActivity extends AppCompatActivity {

    private LocalReceiver reciever = new LocalReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnVerCatalogo = findViewById(R.id.btnVerCatalogo);
        Button btnAgregarVinilo = findViewById(R.id.btnAgregarVinilo);
        Button btnModificarVinilo = findViewById(R.id.btnModificarVinilo);
        Button btnEliminarVinilo = findViewById(R.id.btnEliminarVinilo);
        Button btnSobreNosotros = findViewById(R.id.btnSobreNosotros);

        btnVerCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //primero llamo al servicio para que me traiga una lista de vinilos
                //List<Vinilo> vinilos = new ArrayList<>();
                //Vinilo v1 = new Vinilo("a1", "marca1", "vinilo-a1", "rojo", "FF0000", 11.5);
                //Vinilo v2 = new Vinilo("b2", "marca1", "vinilo-b2", "azul", "0101DF", 12.4);
                //vinilos.add(v1);
                //vinilos.add(v2);
                //showVinilos(vinilos);

                LocalBroadcastManager.getInstance(getContext()).registerReceiver(reciever, new IntentFilter(CallService.RESPONSE_ACTION));
                final Intent mServiceIntent = new Intent(MainActivity.this, CallService.class);

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                startService(mServiceIntent);
            }
        });

        btnAgregarVinilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnModificarVinilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEliminarVinilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSobreNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SobreNosotrosActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showVinilos(List<Vinilo> vinilos) {
        if(!vinilos.isEmpty()){
            //pasarle por parametro la lista con las imagenes
            Intent intent = new Intent(getApplicationContext(), CatalogoActivity.class);

            Bundle parametro = new Bundle();
            parametro.putParcelableArrayList("vinilos", (ArrayList<? extends Parcelable>) vinilos);
            intent.putExtras(parametro);

            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.no_hay_elementos, Toast.LENGTH_SHORT).show();
        }
    }

    public Context getContext() {
        return this;
    }
}
