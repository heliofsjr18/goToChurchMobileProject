package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.helio.gotochurchmobileproject.Util.WebConection;
import com.example.helio.gotochurchmobileproject.Util.WebService;

public class GTC_NewAddressActivity extends AppCompatActivity {

    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/congregacao/novaCongregacao";
    public int coordenadorSelecionado;
    public String nomeIgreja;
    public int assentos;
    private WebService ws;
    private WebConection wc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__new_address);

        Intent it = getIntent();
        Bundle b = it.getExtras();
        this.coordenadorSelecionado = b.getInt("idCoordenador");
        this.nomeIgreja = b.getString("nomeIgreja");
        this.assentos = b.getInt("assentos");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seta BackActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Endereço - "+this.nomeIgreja);

    }

    //Método de ações dos itens selecionados da ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do botão (gerado automaticamente pelo android)
                finish();
                break;
            default:break;
        }
        return true;
    }


    private void saveChurch(){
        EditText editText_city = (EditText) findViewById(R.id.editText_city);
        EditText editText_district = (EditText) findViewById(R.id.editText_district);
        EditText editText_homeNumber = (EditText) findViewById(R.id.editText_homeNumber);
        EditText editText_streetName = (EditText) findViewById(R.id.editText_streetName);
        String cidade = String.valueOf(editText_city.getText());
        String bairro = String.valueOf(editText_district.getText());
        String num = String.valueOf(editText_homeNumber.getText());
        String rua = String.valueOf(editText_streetName.getText());

        wc = new WebConection();
        if(wc.isOnline(this)) {
            this.URL += "?nome=" + this.nomeIgreja;
            this.URL += "&coordenador=" + this.coordenadorSelecionado;
            this.URL += "&qtd_assentos=" + this.assentos;
            this.URL += "&rua=" + rua;
            this.URL += "&bairro=" + bairro;
            this.URL += "&cidade=" + cidade;
            this.URL += "&complemento=";
            this.URL += "&numero=" + num;
            this.URL += "&latitude=";
            this.URL += "&longitude=";
        }else{
            Snackbar.make(getCurrentFocus(), "Verifique sua conexão com a Internet", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
