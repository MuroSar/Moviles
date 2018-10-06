package barmau.catalogomoviles;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import barmau.catalogomoviles.conections.LocalReceiver;
import barmau.catalogomoviles.elementos.Vinilo;

public class MainActivity extends AppCompatActivity {

    public static final String CODIGO = "codigo";
    private static final String EMPTY_STRING = "";

    private LocalReceiver reciever = new LocalReceiver(this);
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnVerCatalogo = findViewById(R.id.btnVerCatalogo);
        Button btnBuscarVinilo = findViewById(R.id.btnBuscarVinilo);
        Button btnSobreNosotros = findViewById(R.id.btnSobreNosotros);

        btnVerCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVinilos();
            }
        });

        btnBuscarVinilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        btnSobreNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SobreNosotrosActivity.class);
                startActivity(intent);
            }
        });

        view = this.getLayoutInflater().inflate(R.layout.dialog_get_vinilo, null);
    }

    public void showVinilos(List<Vinilo> vinilos) {
        if (!vinilos.isEmpty()) {
            Intent intent = new Intent(this, CatalogoActivity.class);

            Bundle parametro = new Bundle();
            parametro.putParcelableArrayList("vinilos", (ArrayList<? extends Parcelable>) vinilos);
            intent.putExtras(parametro);

            startActivity(intent);
        } else {
            showNoElements();
        }
    }

    private void getVinilos() {
        LocalBroadcastManager.getInstance(this).registerReceiver(reciever, new IntentFilter(CallService.RESPONSE_ACTION));
        final Intent mServiceIntent = new Intent(this, CallService.class);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        startService(mServiceIntent);
    }

    private void getVinilo(int codigo) {
        LocalBroadcastManager.getInstance(this).registerReceiver(reciever, new IntentFilter(CallService.RESPONSE_ACTION));
        final Intent mServiceIntent = new Intent(this, CallService.class);
        mServiceIntent.putExtra(CODIGO, codigo);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        startService(mServiceIntent);
    }

    public void setViniloDialog(Vinilo vinilo) {
        EditText edtCodigo = view.findViewById(R.id.edt_codigo);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar);
        Button btnBuscar = view.findViewById(R.id.btn_buscar);
        Button btnCerrar = view.findViewById(R.id.btn_cerrar);
        LinearLayout cardView = view.findViewById(R.id.card);
        ImageView cardImage = view.findViewById(R.id.cardImage);
        TextView txtCodigo = view.findViewById(R.id.txtCodigo);
        TextView txtNombre = view.findViewById(R.id.txtNombre);
        TextView txtColor = view.findViewById(R.id.txtColor);
        TextView txtPrecio = view.findViewById(R.id.txtPrecio);

        edtCodigo.setEnabled(false);

        btnBuscar.setVisibility(View.GONE);
        btnCancelar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        btnCerrar.setVisibility(View.VISIBLE);

        cardImage.setBackgroundColor(Color.parseColor("#" + vinilo.getColor()));
        txtColor.setText(new StringBuilder().append("#").append(vinilo.getColor()).toString());
        txtCodigo.setText(vinilo.getCodigo());
        txtNombre.setText(vinilo.getNombre());
        txtPrecio.setText(new StringBuilder().append("$").append(vinilo.getPrecio()).toString());
    }

    private void showDialog() {
        initDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edtCodigo = view.findViewById(R.id.edt_codigo);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar);
        Button btnBuscar = view.findViewById(R.id.btn_buscar);
        Button btnCerrar = view.findViewById(R.id.btn_cerrar);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCodigo.getText().toString().isEmpty()) {
                    edtCodigo.setError(getText(R.string.no_vacio));
                    return;
                }
                getVinilo(Integer.valueOf(edtCodigo.getText().toString()));
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtCodigo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (edtCodigo.getText().toString().isEmpty()) {
                    edtCodigo.setError(getText(R.string.no_vacio));
                    return false;
                }
                getVinilo(Integer.valueOf(edtCodigo.getText().toString()));
                return true;
            }
        });

    }

    private void initDialog() {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }

        EditText edtCodigo = view.findViewById(R.id.edt_codigo);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar);
        Button btnBuscar = view.findViewById(R.id.btn_buscar);
        Button btnCerrar = view.findViewById(R.id.btn_cerrar);
        LinearLayout cardView = view.findViewById(R.id.card);

        edtCodigo.setEnabled(true);
        edtCodigo.setText(EMPTY_STRING);
        edtCodigo.setError(null);

        btnBuscar.setVisibility(View.VISIBLE);
        btnCancelar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        btnCerrar.setVisibility(View.GONE);
    }

    public void showNoElements() {
        Toast.makeText(getApplicationContext(), R.string.no_hay_elementos, Toast.LENGTH_SHORT).show();
    }
}
