package com.example.desafiocarro.CarDetails;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desafiocarro.MainActivity;
import com.example.desafiocarro.R;
import com.example.desafiocarro.database.AppDatabase;
import com.example.desafiocarro.models.Car;
import com.example.desafiocarro.models.ItemCart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarDetailsActivity extends AppCompatActivity implements CarDetailsContract.view {

    private CarDetailsPresenter presenter;
    private Car carro = null;
    private AppDatabase db;
    private TextView btnAddToCart;
    private int quantidade;
    private float totalPrice;
    private TextView warningQtdTxt;

    TextView marca;
    TextView descricao;
    ImageView imagem;
    TextView quantidadetxt;
    SeekBar seekbar;
    TextView totalPriceTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        //BIND
        marca = findViewById(R.id.MarcaID);
        descricao = findViewById(R.id.DescricaoID);
        imagem = findViewById(R.id.ImagemID);
        quantidadetxt = findViewById(R.id.QuantidadeID);
        seekbar = findViewById(R.id.seekBarID);
        totalPriceTxt = findViewById(R.id.TotalPriceID);
        warningQtdTxt = findViewById(R.id.WarningQtdTxt);
        btnAddToCart = findViewById(R.id.BtnAddToCart);
        //BIND END


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new CarDetailsPresenter(this);
        db = presenter.getDatabase();

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
                presenter.doWhenbtnAddToCartisClicked(carro, quantidade);
                Toast.makeText(CarDetailsActivity.this, String.format(Locale.getDefault(), "%d items adicionados ao carrinho", quantidade), Toast.LENGTH_SHORT).show();
                seekbar.setMax(presenter.getDatabase().carDAO().getCarByID(carro.getId()).getQuantidade());
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
                quantidadetxt.setText(String.format(Locale.getDefault(), "%d items de %d", quantidade, seekBar.getMax()));
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


    private String transformCurrency(float totalPrice) {
        String currency = NumberFormat.getCurrencyInstance().format(totalPrice);
        return currency;
    }

    private void minItemCartValid(int numberItems, float totalPrice) {
        if (numberItems > 0) {
            btnAddToCart.setVisibility(View.VISIBLE);
            if (totalPrice > 100000.0) {
                warningQtdTxt.setVisibility(View.VISIBLE);
                btnAddToCart.setVisibility(View.INVISIBLE);
            } else
                warningQtdTxt.setVisibility(View.GONE);
        } else {
            btnAddToCart.setVisibility(View.INVISIBLE);
        }
    }

    public void setAlertDialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
        alertadd.setTitle("Deseja ir para o carrinho?");
        alertadd.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("GOTOCART", true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Context getViewContext() {
        return getApplicationContext();
    }

}
