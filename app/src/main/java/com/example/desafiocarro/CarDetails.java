package com.example.desafiocarro;

import android.arch.persistence.room.Room;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.database.CarDAO;
import com.example.desafiocarro.models.Car;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.NumberFormat;

public class CarDetails extends AppCompatActivity {

    private Car carro =  null;
    private AppDatabase db;
    private TextView btnAddToCart;
    private int quantidade;
    private Snackbar snackbar;

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
        final TextView totalPrice;

        marca = findViewById(R.id.MarcaID);
        descricao = findViewById(R.id.DescricaoID);
        imagem = findViewById(R.id.ImagemID);
        quantidadetxt = findViewById(R.id.QuantidadeID);
        seekbar = findViewById(R.id.seekBarID);
        totalPrice = findViewById(R.id.TotalPriceID);
        btnAddToCart = findViewById(R.id.BtnAddToCart);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-car").allowMainThreadQueries().build();


        if (getIntent().getExtras() != null) {
            int id = (int) getIntent().getExtras().get("CARRO_ID");
            carro = db.carDAO().getCarByID(id);
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
                carro = db.carDAO().getCarByID(carro.getId());
                int itensRestantes = carro.getQuantidade() - quantidade;
                db.carDAO().setQuantidade(carro.getId(), itensRestantes);
                Toast.makeText(CarDetails.this, String.format("%d items adicionados ao carrnho", quantidade), Toast.LENGTH_SHORT).show();
                seekbar.setMax(itensRestantes);
                seekbar.setProgress(0);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantidade = seekBar.getProgress();
                quantidadetxt.setText(String.format("%d items de %d", quantidade, seekBar.getMax()));
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
