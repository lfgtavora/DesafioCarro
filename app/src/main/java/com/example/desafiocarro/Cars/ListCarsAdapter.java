package com.example.desafiocarro.Cars;

import android.content.Context;
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


public class ListCarsAdapter extends RecyclerView.Adapter<ListCarsAdapter.ViewHolder> {

    private List<Car> carlist;
    private Context context;
    private ListCarsContract.view view;
    private ListCarsPresenter presenter;

    public ListCarsAdapter(List<Car> cars, ListCarsContract.view view) {
        this.carlist = cars;
        this.view = view;
        this.presenter = new ListCarsPresenter(view);
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

        viewHolder.bind(carro,position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startDetailsCarsActivity(carro);
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
            this.nome = itemView.findViewById(R.id.NomeIDTxt);
            this.preco = itemView.findViewById(R.id.precoID);
            this.descricao = itemView.findViewById(R.id.descricaoID);
            this.image = itemView.findViewById(R.id.imageCartItemID);
        }

        private void bind(final Car carro, final int position) {
            nome.setText(carro.getNome());
            descricao.setText(carro.getDescricao());
            preco.setText(NumberFormat.getCurrencyInstance().format(carro.getPreco()));
            Picasso.get()
                    .load(carro.getImagem())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(image, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }
                        @Override
                        public void onError(Exception e) {
                            if(carlist.get(position) == carro){
                                carlist.remove(position);
                                presenter.getDataBase().carDAO().delete(carro);
                                notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}
