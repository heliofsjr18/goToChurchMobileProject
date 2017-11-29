package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Address;
import com.example.helio.gotochurchmobileproject.Basic.Church;
import com.example.helio.gotochurchmobileproject.Util.ChurchAdapter;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GTC_ChurchListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public WebService ws;
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/congregacao/listarCongregacao";
    public ArrayList<Church> church;
    private ListView lvChurch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__church_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seta BackActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lvChurch = (ListView) findViewById(R.id.listViewChurch);

        ws = new WebService();
        this.listChurch();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lvChurch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Church church = (Church) lvChurch.getItemAtPosition(position);
                Toast.makeText(GTC_ChurchListActivity.this, "id = "+id+", position+"+position+", bairro ="+church.getAddress().getDistrict()+", cidade="+church.getAddress().getCity()+", num="+church.getAddress().getHomeNumber(), Toast.LENGTH_SHORT).show();

                String rua = church.getAddress().getStreetName();
                String bairro = church.getAddress().getDistrict();
                String numero = church.getAddress().getHomeNumber();
                String cidade = church.getAddress().getCity();
                String estado = church.getAddress().getState();
                String pais = "Brasil";


                Toast.makeText(GTC_ChurchListActivity.this, "google.navigation:q="+rua+",+"+numero+"+-+"+bairro+",+"+cidade+"+-+"+estado+",+"+pais, Toast.LENGTH_SHORT).show();

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+rua+",+"+numero+",+"+cidade+"+-+"+estado+",+"+pais);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

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




    private void listChurch(){
        try {
            String stringChurch = this.ws.getUrlContents(this.URL); //Chama função que consome o web service

            JSONArray churchJson = new JSONArray(stringChurch);
            JSONObject church;

            //Toast.makeText(this, this.URL, Toast.LENGTH_LONG).show();

            //int idChurch;
            //int idSetor;
            //int numero;

            ListView lista = (ListView)findViewById(R.id.listViewChurch);
            //String[] strings = new String[churchJson.length()];
            this.church = new ArrayList<>();

            for (int i = 0; i < churchJson.length(); i++) {
                church = new JSONObject(churchJson.getString(i));

                Church c = new Church();
                Address a = new Address();
                c.setId(Integer.parseInt(church.getString("id")));
                c.setName(church.getString("nome"));
                c.setResponsible("Coordenador: "+church.getString("coordenador"));


                a.setCity(church.getString("cidade"));
                a.setHomeNumber(church.getString("numero"));
                a.setStreetName(church.getString("rua"));
                a.setDistrict(church.getString("bairro"));
                a.setState("Pernambuco");


                c.setAddress(a);
                //Toast.makeText(this, church.getString("nome"), Toast.LENGTH_LONG).show();
                this.church.add(c);



                //String setorInfo = "Setor 0"+numero+" | Coordenador: "+nomeCoordenador;
                //strings[i] = setorInfo;


            }

            ChurchAdapter ad = new ChurchAdapter(getBaseContext(), R.layout.content_gtc_listview, this.church);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);

            lista.setAdapter(ad);



        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
    }
}
