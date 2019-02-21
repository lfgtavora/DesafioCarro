package com.example.desafiocarro.adapters;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desafiocarro.DelegateItemCart;
import com.example.desafiocarro.R;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.fragments.MyCart;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder> {

    private List<ItemCart> itemsList;
    private MyCart context;
    private AppDatabase db;

    public CartItemsAdapter(List<ItemCart> itemsList, MyCart context, AppDatabase db) {
        this.itemsList = itemsList;
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_cart,parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        final ItemCart item = itemsList.get(position);
        final int idCar = item.getIdCar();
        final Car carro = db.carDAO().getCarByID(idCar);

        bindItems(myViewHolder, item, carro);
        removerItemLista(myViewHolder, position, item, carro);
    }

    private void removerItemLista(@NonNull MyViewHolder myViewHolder, final int position, final ItemCart item, final Car carro) {
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //remove item do DB e da lista
                db.itemCartDAO().deleta(item);
                itemsList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

                //acrescenta qtd
                carro.setQuantidade(item.getQuantidade() + carro.getQuantidade());
                db.carDAO().setQtd(carro);

                context.setTotal();

                return true;
            }
        });
    }

    private void bindItems(@NonNull MyViewHolder myViewHolder, ItemCart item, Car carro) {
        myViewHolder.nomeCarro.setText(carro.getNome());
        Picasso.get().load(carro.getImagem()).into(myViewHolder.imageCar);
        myViewHolder.quantidade.setText(String.format("Qtd: %d", item.getQuantidade()));
        myViewHolder.total.setText(String.format("R$ %.2f", item.getQuantidade() * item.getPreco()));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeCarro;
        private TextView quantidade;
        private TextView total;
        private ImageView imageCar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageCar = itemView.findViewById(R.id.imageCartItemID);
            this.nomeCarro = itemView.findViewById(R.id.NomeIDTxt);
            this.quantidade = itemView.findViewById(R.id.QuantidadeIDTxt);
            this.total = itemView.findViewById(R.id.precoIDTxt);
        }
    }
}
