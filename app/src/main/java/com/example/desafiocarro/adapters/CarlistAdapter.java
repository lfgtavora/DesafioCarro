package com.example.desafiocarro.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.desafiocarro.CarDetails;
import com.example.desafiocarro.R;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;


public class CarlistAdapter extends RecyclerView.Adapter<CarlistAdapter.ViewHolder> {

    private List<Car> carlist;
    private Context context;
    private AppDatabase db;

    public CarlistAdapter(AppDatabase db, List<Car> cars, Context context) {
        this.carlist = cars;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemList  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carlist, parent, false);
        return new ViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Car carro = carlist.get(position);

        viewHolder.nome.setText(carro.getNome());
        viewHolder.descricao.setText(carro.getDescricao());
        viewHolder.preco.setText(NumberFormat.getCurrencyInstance().format(carro.getPreco()));
        Picasso.get()
                .load(carro.getImagem())
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.ic_error_black_24dp)
                .into(viewHolder.image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        int id = carro.getId();
                        if(carlist.get(position).getId() == id){
                            carlist.remove(position);
                            db.carDAO().deleteByCarId(id);
                            notifyDataSetChanged();
                        }
                    }
                });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CarDetails.class);
                intent.putExtra("CARRO_ID", carro.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nome;
        private TextView preco;
        private TextView descricao;
        private ImageView image;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nome = itemView.findViewById(R.id.NomeID);
            this.preco = itemView.findViewById(R.id.precoID);
            this.descricao = itemView.findViewById(R.id.descricaoID);
            this.image = itemView.findViewById(R.id.imageID);
        }
    }
}
