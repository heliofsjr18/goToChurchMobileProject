package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.User;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GTC_NewChurchActivity extends AppCompatActivity {

    private static final String URL = "https://dayvsonnascimento.pythonanywhere.com/gotochurch/usuario/listaUsuarios";
    private WebService ws;
    private String dadosUsuario;
    private ArrayList<User> users = new ArrayList<>();
    private String[] items;

    public int coordenadorSelecionado;
    public String nomeIgreja;
    public int assentos;


    private View.OnClickListener onClickListenerContinue = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //GTC_NewChurchActivity.this.salvar(v);
            //Toast.makeText(GTC_NewChurchActivity.this, "Cad", Toast.LENGTH_SHORT).show();
        try{
            EditText cName = (EditText) findViewById(R.id.editText_name);
            EditText cAssentos = (EditText) findViewById(R.id.editText_seat);

            GTC_NewChurchActivity.this.nomeIgreja = String.valueOf(cName.getText());
            GTC_NewChurchActivity.this.assentos = Integer.parseInt(String.valueOf(cAssentos.getText()));

            Bundle b = new Bundle();
            b.putString("nomeIgreja",GTC_NewChurchActivity.this.nomeIgreja);
            b.putInt("idCoordenador",GTC_NewChurchActivity.this.coordenadorSelecionado);
            b.putInt("assentos",GTC_NewChurchActivity.this.assentos);



            Intent it = new Intent(GTC_NewChurchActivity.this, GTC_NewAddressActivity.class);
            it.putExtras(b);
            startActivity(it);
        }catch (Exception e) {
            Toast.makeText(GTC_NewChurchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__new_church);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seta BackActivity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão


        Spinner dropdown = (Spinner)findViewById(R.id.spin_responsible);

        items = listUser();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(GTC_NewChurchActivity.this, ""+GTC_NewChurchActivity.this.users.get(position).getName(), Toast.LENGTH_SHORT).show();
                GTC_NewChurchActivity.this.coordenadorSelecionado = GTC_NewChurchActivity.this.users.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        findViewById(R.id.btContinue).setOnClickListener(onClickListenerContinue);

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


    private String[] listUser(){

        ws = new WebService();
        String[] list = {};
        try{

            String resultado = ws.getUrlContents(this.URL);  //Chama função que consome o web service

            /**/JSONArray resultJson = new JSONArray(resultado);// coverte a string de resultado em um array Json
            JSONObject result;

            list = new String[resultJson.length()];

            for (int i = 0; i < resultJson.length(); i++) {
                result = new JSONObject(resultJson.getString(i));
                User u = new User();
                u.setId(Integer.parseInt(result.getString("id")));
                u.setName(result.getString("first_name"));
                u.setEmail(result.getString("email"));

                this.users.add(u);
                list[i] = result.getString("first_name");
            }



        }catch (Exception  e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return list;
    }


    private void salvar(View v){
        EditText cName = (EditText) findViewById(R.id.editText_name);
        EditText cAssentos = (EditText) findViewById(R.id.editText_name);

        this.nomeIgreja = String.valueOf(cName.getText());
        this.assentos = Integer.parseInt(String.valueOf(cAssentos.getText()));

        Bundle b = new Bundle();
        b.putString("nomeIgreja",this.nomeIgreja);
        b.putInt("idCoordenador",this.coordenadorSelecionado);
        b.putInt("assentos",this.assentos);

        Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(this, GTC_NewAddressActivity.class);
        it.putExtras(b);
        startActivity(it);
    }
}
