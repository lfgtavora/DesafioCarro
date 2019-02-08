package com.example.desafiocarro.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desafiocarro.R;
import com.example.desafiocarro.models.Car;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;


public class CarlistAdapter extends RecyclerView.Adapter<CarlistAdapter.ViewHolder> {

    private List<Car> carlist;

    public CarlistAdapter(List<Car> cars) {
        this.carlist = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemList  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carlist, parent, false);
        return new ViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Car carro = carlist.get(position);

        viewHolder.nome.setText(carro.getNome());
        viewHolder.descricao.setText(carro.getDescricao());
        viewHolder.preco.setText(NumberFormat.getCurrencyInstance().format(carro.getPreco()));
        Picasso.get()
                .load(carro.getImagem())
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.ic_error_black_24dp)
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return carlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //@BindView(R.id.NomeID)
        private TextView nome;
        //@BindView(R.id.precoID)
        private TextView preco;
        //@BindView(R.id.descricaoID)
        private TextView descricao;
        //@BindView(R.id.imageView)
        private ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nome = itemView.findViewById(R.id.NomeID);
            this.preco = itemView.findViewById(R.id.precoID);
            this.descricao = itemView.findViewById(R.id.descricaoID);
            this.image = itemView.findViewById(R.id.imageID);
        }
    }
}
