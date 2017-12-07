package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Church;
import com.example.helio.gotochurchmobileproject.Data.DAOFavoritChurch;
import com.example.helio.gotochurchmobileproject.Util.ChurchAdapter;

import java.util.List;

public class GTC_FavoritsActivity extends AppCompatActivity {
    public ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__favorits);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seta BackActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        lista = (ListView) findViewById(R.id.lvFavorits);

        DAOFavoritChurch  daofc = new DAOFavoritChurch(getBaseContext());


        try {

            List<Church> lc = daofc.carregaDados();
            if(lc != null) {
                ChurchAdapter ad = new ChurchAdapter(getBaseContext(), R.layout.content_gtc_listview, lc);
                lista.setAdapter(ad);
            }else{
                TextView tx = (TextView) findViewById(R.id.tx);
                tx.setText("Nenhum favorito!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Church church = (Church) lista.getItemAtPosition(position);
                Toast.makeText(GTC_FavoritsActivity.this, "id = "+id+", position+"+position+", bairro ="+church.getAddress().getDistrict()+", cidade="+church.getAddress().getCity()+", num="+church.getAddress().getHomeNumber(), Toast.LENGTH_SHORT).show();

                String rua = church.getAddress().getStreetName();
                String bairro = church.getAddress().getDistrict();
                String numero = church.getAddress().getHomeNumber();
                String cidade = church.getAddress().getCity();
                String estado = church.getAddress().getState();
                String pais = "Brasil";


                Toast.makeText(getBaseContext(), "Navegação GPS Iniciada...", Toast.LENGTH_SHORT).show();

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+rua+",+"+numero+",+"+cidade+"+-+"+estado+",+"+pais);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Church church = (Church) lista.getItemAtPosition(position);
                DAOFavoritChurch daofc = new DAOFavoritChurch(getBaseContext());
                try {
                    if (daofc.delete(church) == true) {
                        Toast.makeText(getBaseContext(), "Removido dos favoritos!", Toast.LENGTH_LONG).show();
                        listar();

                    } else {
                        Toast.makeText(getBaseContext(), "Não foi possivel remover dos favoritos!", Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e){
                    Toast.makeText(GTC_FavoritsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
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

    public void listar(){
        DAOFavoritChurch  daofc = new DAOFavoritChurch(getBaseContext());
        try{
            List<Church> lc = daofc.carregaDados();
            if(lc != null) {
                ChurchAdapter ad = new ChurchAdapter(getBaseContext(), R.layout.content_gtc_listview, lc);
                lista.setAdapter(ad);
            }else{
                TextView tx = (TextView) findViewById(R.id.tx);
                tx.setText("Nenhum favorito!");
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
