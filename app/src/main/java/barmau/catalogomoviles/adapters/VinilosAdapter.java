package barmau.catalogomoviles.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.cardImage.setBackgroundColor(Color.parseColor("#" + vinilo.getCodColor()));
        holder.txtCodigo.setText(vinilo.getCodigo());
        holder.txtNombre.setText(vinilo.getNombre());
        holder.txtMarca.setText(vinilo.getMarca());
        holder.txtPrecio.setText("$" + vinilo.getPrecio());
    }

    @Override
    public int getItemCount() {
        return vinilos.size();
    }

    static class VinilosViewHolder extends RecyclerView.ViewHolder{

        ImageView cardImage;
        TextView txtCodigo;
        TextView txtNombre;
        TextView txtMarca;
        TextView txtPrecio;

//        private int imageId;

        public VinilosViewHolder(View parent) {
            super(parent);

            cardImage = parent.findViewById(R.id.cardImage);
            txtCodigo = parent.findViewById(R.id.txtCodigo);
            txtNombre = parent.findViewById(R.id.txtNombre);
            txtMarca = parent.findViewById(R.id.txtMarca);
            txtPrecio = parent.findViewById(R.id.txtPrecio);
        }

//        @OnClick(R.id.cardImage)
//        public void callServiceCardPressed(ImageView cardImage) {
//            RxBus.post(new CallServiceCardObserver.CallServiceCardPressed(imageId));
//        }
    }
}