package com.example.desafiocarro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.models.Car;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.NumberFormat;

public class CarDetails extends AppCompatActivity {

    private Car carro =  null;
    private TextView btnAddToCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView marca;
        TextView descricao;
        ImageView imagem;
        final TextView quantidade;
        SeekBar seekbar;
        final TextView totalPrice;

        marca = findViewById(R.id.MarcaID);
        descricao = findViewById(R.id.DescricaoID);
        imagem = findViewById(R.id.ImagemID);
        quantidade = findViewById(R.id.QuantidadeID);
        seekbar = findViewById(R.id.seekBarID);
        totalPrice = findViewById(R.id.TotalPriceID);
        btnAddToCart = findViewById(R.id.BtnAddToCart);

        if (getIntent().getExtras() != null) {
            carro = (Car) getIntent().getExtras().get("CARRO_OBJ");
            marca.setText(carro.getMarca());
            descricao.setText(carro.getDescricao());
            Picasso.get().load(carro.getImagem()).into(imagem);
            quantidade.setText("0 items");
            seekbar.setMax(carro.getQuantidade());
            setTitle(carro.getNome());


        } else {
            Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show();
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carro.setQuantidade(5);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantidade.setText(String.format("%d items", seekBar.getProgress()));
                totalPrice.setText(calculateTotalPrice(carro.getPreco(), seekBar.getProgress()));
                minItemCartValid(seekBar.getProgress());
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

    private void minItemCartValid(int numberItems){
        if (numberItems > 0) {
            btnAddToCart.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.INVISIBLE);
        }
    }

}
