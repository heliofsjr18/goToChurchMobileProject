package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.User;
import com.example.helio.gotochurchmobileproject.Data.DAOUser;
import com.example.helio.gotochurchmobileproject.Util.WebConection;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

public class GTC_NewAddressActivity extends AppCompatActivity {

    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/congregacao/novaCongregacao";
    public int coordenadorSelecionado;
    public String nomeIgreja;
    public int assentos;
    private WebService ws;
    private WebConection wc;

    private View.OnClickListener onClickListenerSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveChurch(v);
        }
    };


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

        Button bt = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(onClickListenerSave);

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


    void saveChurch(View v){
        EditText editText_city = (EditText) findViewById(R.id.editText_city);
        EditText editText_district = (EditText) findViewById(R.id.editText_district);
        EditText editText_homeNumber = (EditText) findViewById(R.id.editText_homeNumber);
        EditText editText_streetName = (EditText) findViewById(R.id.editText_streetName);
        String cidade = String.valueOf(editText_city.getText());
        String bairro = String.valueOf(editText_district.getText());
        String num = String.valueOf(editText_homeNumber.getText());
        String rua = String.valueOf(editText_streetName.getText());
        try {
            wc = new WebConection();
            if (wc.isOnline(this)) {
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
                
                ws = new WebService();
                String resultado = ws.getUrlContents(this.URL);

                JSONArray resultJson = new JSONArray(resultado);// coverte a string de resultado em um array Json
                JSONObject result;

                int numResultado = 0;
                String errorMessage = "";

                for (int i = 0; i < resultJson.length(); i++) {
                    result = new JSONObject(resultJson.getString(i));

                    numResultado = Integer.parseInt(result.getString("data"));

                    if(numResultado == 0)
                        errorMessage = result.getString("error");

                }

                if(numResultado == 0){
                    Toast.makeText(this, "Error - ( "+errorMessage+" )", Toast.LENGTH_SHORT).show();
                }else if(numResultado == 1){
                    Toast.makeText(this, "Cadastrado", Toast.LENGTH_SHORT).show();

                    try {
                        DAOUser crud = new DAOUser(this);
                        User u = crud.carregaDados();
                        if (u != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("dadosUsuario", u.getDados());

                            Intent it = new Intent(this, GTC_WelcomeActivity.class);
                            it.putExtras(bundle);
                            startActivity(it);
                        }
                    }catch (Exception e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            } else {
                Snackbar.make(getCurrentFocus(), "Verifique sua conexão com a Internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
