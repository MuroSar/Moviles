package barmau.catalogomoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import barmau.catalogomoviles.adapters.VinilosAdapter;
import barmau.catalogomoviles.elementos.Vinilo;

public class CatalogoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        recyclerView = findViewById(R.id.recycler_view);

        Bundle parametroRecibida = getIntent().getExtras();
        List<Vinilo> vinilos = parametroRecibida.getParcelableArrayList("vinilos");

        recyclerView.setAdapter(new VinilosAdapter(vinilos));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
