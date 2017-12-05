package com.example.helio.gotochurchmobileproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Area;
import com.example.helio.gotochurchmobileproject.Basic.Coordenador;
import com.example.helio.gotochurchmobileproject.Util.AreaAdapter;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GTC_ListaAreaActivity extends AppCompatActivity {

    private ArrayList<Area> areas;
    public WebService ws;
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/area/listaAreas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__lista_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Seta BackActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ws = new WebService();
        listAreas();
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


    private void listAreas(){
        try {
            String stringAreas = this.ws.getUrlContents(this.URL); //Chama função que consome o web service

            JSONArray areasJson = new JSONArray(stringAreas);
            JSONObject area;

            int idCoordenador;
            int idSetor;
            int numero;
            String nomeCoordenador;

            ListView lista = (ListView) findViewById(R.id.listViewArea);

            this.areas = new ArrayList<>();

            for (int i = 0; i < areasJson.length(); i++) {
                area = new JSONObject(areasJson.getString(i));

                Area a = new Area(); //objeto setor
                Coordenador co = new Coordenador();// objeto Coordenador

                idCoordenador = Integer.parseInt(area.getString("idcoordenador"));
                idSetor = Integer.parseInt(area.getString("idsetor"));

                numero = Integer.parseInt(area.getString("id"));
                nomeCoordenador = area.getString("coordenador");

                co.setId(idCoordenador);
                co.setName(nomeCoordenador);

                a.setId(idSetor);
                a.setNum(numero);
                a.setCoordenador(co);


                this.areas.add(a);

                //String setorInfo = "Setor 0"+numero+" | Coordenador: "+nomeCoordenador;
                //strings[i] = setorInfo;


            }

            AreaAdapter ad = new AreaAdapter(getBaseContext(), R.layout.content_gtc_listview, this.areas);
            lista.setAdapter(ad);



        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
