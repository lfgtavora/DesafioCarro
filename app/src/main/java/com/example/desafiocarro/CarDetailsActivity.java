package com.example.desafiocarro;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class CarDetailsActivity extends AppCompatActivity {

    private Car carro =  null;
    private AppDatabase db;
    private TextView btnAddToCart;
    private int quantidade;
    private float totalPrice;
    private TextView warningQtdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView marca;
        TextView descricao;
        ImageView imagem;
        final TextView quantidadetxt;
        final SeekBar seekbar;
        final TextView totalPriceTxt;

        marca = findViewById(R.id.MarcaID);
        descricao = findViewById(R.id.DescricaoID);
        imagem = findViewById(R.id.ImagemID);
        quantidadetxt = findViewById(R.id.QuantidadeID);
        seekbar = findViewById(R.id.seekBarID);
        totalPriceTxt = findViewById(R.id.TotalPriceID);
        warningQtdTxt = findViewById(R.id.WarningQtdTxt);
        btnAddToCart = findViewById(R.id.BtnAddToCart);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-car").allowMainThreadQueries().build();


        if (getIntent().getExtras() != null) {
            carro = (Car) getIntent().getExtras().get("CARRO_OBJ");
            marca.setText(carro.getMarca());
            descricao.setText(carro.getDescricao());
            Picasso.get().load(carro.getImagem()).into(imagem);
            quantidadetxt.setText("0 items");
            seekbar.setMax(carro.getQuantidade());
            setTitle(carro.getNome());
        } else {
            Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show();
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itensRestantes = carro.getQuantidade() - quantidade;
                db.carDAO().setQuantidade(carro.getId(), itensRestantes);
                Toast.makeText(CarDetailsActivity.this, String.format("%d items adicionados ao carrinho", quantidade), Toast.LENGTH_SHORT).show();

                insertItemOnCart(carro, quantidade);

                seekbar.setMax(itensRestantes);
                seekbar.setProgress(0);

                setAlertDialog();
            }
        });

        lidaComSeekBar(quantidadetxt, seekbar, totalPriceTxt);

    }

    private void lidaComSeekBar(final TextView quantidadetxt, SeekBar seekbar, final TextView totalPriceTxt) {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantidade = seekBar.getProgress();
                totalPrice = quantidade * carro.getPreco();
                quantidadetxt.setText(String.format("%d items de %d", quantidade, seekBar.getMax()));
                totalPriceTxt.setText(transformCurrency(totalPrice));
                minItemCartValid(seekBar.getProgress(), totalPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String calculateTotalPrice(float preco, int quantidade){
        float totalPrice = preco * quantidade;
        String currency = transformCurrency(totalPrice);
        return currency;
    }

    private String transformCurrency(float totalPrice){
        String currency = NumberFormat.getCurrencyInstance().format(totalPrice);
        return currency;
    }

    private void minItemCartValid(int numberItems, float totalPrice){
        if (numberItems > 0) {
            btnAddToCart.setVisibility(View.VISIBLE);
            if (totalPrice > 100000.0){
                warningQtdTxt.setVisibility(View.VISIBLE);
                btnAddToCart.setVisibility(View.INVISIBLE);
            }
            else
                warningQtdTxt.setVisibility(View.GONE);
        } else {
            btnAddToCart.setVisibility(View.INVISIBLE);
        }
    }

    public void setAlertDialog(){
        AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
        alertadd.setTitle("Deseja ir para o carrinho?");
        alertadd.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("GOTOCART", true );
                startActivity(intent);
            }
        });
        alertadd.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                //DO NOTHING
            }
        });
        alertadd.show();
    }

    public void insertItemOnCart(Car carro, int quantidade){
        List<ItemCart> itemCarts = db.itemCartDAO().getItemsCart();
        int position = -1;
        for(int i =0; i < itemCarts.size();i++){
            if(itemCarts.get(i).getIdCar() == carro.getId()){
                position = i;
            }
        }
        if(position == -1){
            ItemCart itemCart = new ItemCart(carro.getId(), quantidade, carro.getPreco());
            db.itemCartDAO().insertItemOnCart(itemCart);
        } else{
            int quantidade2 = itemCarts.get(position).getQuantidade() + quantidade;
            int idCar = itemCarts.get(position).getIdCar();
            db.itemCartDAO().updateQuantidade(quantidade2, idCar);
        }
    }


}
