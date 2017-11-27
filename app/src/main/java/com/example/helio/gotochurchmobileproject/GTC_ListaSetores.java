package com.example.helio.gotochurchmobileproject;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Coordenador;
import com.example.helio.gotochurchmobileproject.Basic.Setor;
import com.example.helio.gotochurchmobileproject.Util.SetoresAdapter;
import com.example.helio.gotochurchmobileproject.Util.WebConection;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GTC_ListaSetores extends AppCompatActivity {
    public WebService ws;
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/setor/listaSetores";
    public ArrayList<Setor> setores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__lista_setores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seta BackActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ws = new WebService();
        WebConection wc = new WebConection();

        if(wc.isOnline(this)){
            this.listSetores();
        }else{
            Toast.makeText(this, "Verifique sua conexão com a internet!", Toast.LENGTH_LONG).show();
        }

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

    private void listSetores(){
        try {
            String stringSetores = this.ws.getUrlContents(this.URL); //Chama função que consome o web service

            JSONArray setoresJson = new JSONArray(stringSetores);
            JSONObject setor;

            int idCoordenador;
            int idSetor;
            int numero;
            String nomeCoordenador;

            ListView lista = (ListView) findViewById(R.id.listViewSetores);

            this.setores = new ArrayList<>();

            for (int i = 0; i < setoresJson.length(); i++) {
                setor = new JSONObject(setoresJson.getString(i));

                Setor s = new Setor(); //objeto setor
                Coordenador co = new Coordenador();// objeto Coordenador

                idCoordenador = Integer.parseInt(setor.getString("idcoordenador"));
                idSetor = Integer.parseInt(setor.getString("id"));

                numero = Integer.parseInt(setor.getString("numero"));
                nomeCoordenador = setor.getString("coordenador");

                co.setId(idCoordenador);
                co.setName(nomeCoordenador);

                s.setId(idSetor);
                s.setNumero(numero);
                s.setCoordenador(co);


                this.setores.add(s);

                //String setorInfo = "Setor 0"+numero+" | Coordenador: "+nomeCoordenador;
                //strings[i] = setorInfo;


            }

            SetoresAdapter ad = new SetoresAdapter(getBaseContext(), R.layout.content_gtc_listview, this.setores);
            lista.setAdapter(ad);



        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}
