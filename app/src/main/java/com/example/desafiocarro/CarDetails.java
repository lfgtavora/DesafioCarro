package com.example.desafiocarro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.models.Car;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class CarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView marca;
        TextView descricao;
        ImageView imagem;
        TextView quantidade;

        marca = findViewById(R.id.MarcaID);
        descricao = findViewById(R.id.DescricaoID);
        imagem = findViewById(R.id.ImagemID);
        quantidade = findViewById(R .id.QuantidadeID);


        if (getIntent().getExtras() != null) {
            Car carro = (Car) getIntent().getExtras().get("CARRO_OBJ");
            marca.setText(carro.getMarca());
            descricao.setText(carro.getDescricao());
            Picasso.get().load(carro.getImagem()).into(imagem);
            quantidade.setText(String.valueOf(carro.getQuantidade()));

            setTitle(carro.getNome());
        } else {
            Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show();
        }

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
}
