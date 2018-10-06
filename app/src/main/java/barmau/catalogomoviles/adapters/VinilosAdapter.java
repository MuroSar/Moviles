package barmau.catalogomoviles.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import barmau.catalogomoviles.R;
import barmau.catalogomoviles.elementos.Vinilo;

public class VinilosAdapter extends RecyclerView.Adapter<VinilosAdapter.VinilosViewHolder> {

    private List<Vinilo> vinilos;

    public VinilosAdapter(List<Vinilo> vinilos)
    {
        this.vinilos = vinilos;
    }

    @NonNull
    @Override
    public VinilosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        if(context != null){
            View itemView = LayoutInflater.from(context).inflate(R.layout.card, parent,false);
            return new VinilosViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VinilosViewHolder holder, int position) {
        Vinilo vinilo = vinilos.get(position);

        holder.cardImage.setBackgroundColor(Color.parseColor("#" + vinilo.getColor()));
        holder.txtColor.setText(new StringBuilder().append("#").append(vinilo.getColor()).toString());
        holder.txtCodigo.setText(vinilo.getCodigo());
        holder.txtNombre.setText(vinilo.getNombre());
        holder.txtPrecio.setText(new StringBuilder().append("$").append(vinilo.getPrecio()).toString());
    }

    @Override
    public int getItemCount() {
        return vinilos.size();
    }

    static class VinilosViewHolder extends RecyclerView.ViewHolder{

        ImageView cardImage;
        TextView txtCodigo;
        TextView txtNombre;
        TextView txtColor;
        TextView txtPrecio;

        public VinilosViewHolder(View parent) {
            super(parent);

            cardImage = parent.findViewById(R.id.cardImage);
            txtCodigo = parent.findViewById(R.id.txtCodigo);
            txtNombre = parent.findViewById(R.id.txtNombre);
            txtColor = parent.findViewById(R.id.txtColor);
            txtPrecio = parent.findViewById(R.id.txtPrecio);
        }
    }
}