package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.User;
import com.example.helio.gotochurchmobileproject.Data.DAOUser;
import com.example.helio.gotochurchmobileproject.Util.WebConection;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

public class GTC_LoginActivity extends AppCompatActivity {
    WebService ws;
    String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/usuario/login";
    String dadosUsuario = "";






    private View.OnClickListener onClickListenerSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intentSignUp = new Intent(GTC_LoginActivity.this, GTC_SignUpActivity.class);
            //startActivity(intentSignUp);
            Uri uri = Uri.parse("http://dayvsonnascimento.pythonanywhere.com/gotochurch/");
            Intent intentSignUp = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intentSignUp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        findViewById(R.id.button_signUp).setOnClickListener(onClickListenerSignUp);
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

    private boolean verificaLogin(){

        EditText editTextEmail = (EditText) findViewById(R.id.editText);//Referência ao editText de Email
        EditText editTextSenha = (EditText) findViewById(R.id.editText2);//Referência ao editText de Senha

        String email = String.valueOf(editTextEmail.getText());//pega valor do editText de email
        String senha = String.valueOf(editTextSenha.getText());//pega valor do editTest de senha
        this.URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/usuario/login";
        this.URL += "?email="+email+"&senha="+senha;
        ws = new WebService();
        boolean retorno = false;

        try{

            String resultado = ws.getUrlContents(this.URL);  //Chama função que consome o web service

            /**/JSONArray resultJson = new JSONArray(resultado);// coverte a string de resultado em um array Json
            JSONObject result;
            int numResultado = 0;

            for (int i = 0; i < resultJson.length(); i++) {
                result = new JSONObject(resultJson.getString(i));

                numResultado = Integer.parseInt(result.getString("resultado"));

            }

            if(numResultado == 0){
                retorno = false;
            }else if(numResultado == 1){
                retorno = true;
                this.dadosUsuario = resultado;
            }

        }catch (Exception  e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return retorno;
    }

    void entrar(View v){
        try {
            WebConection wc = new WebConection();

            if(wc.isOnline(this)){
                if(this.verificaLogin()){
                    Bundle bundle = new Bundle();
                    bundle.putString("dadosUsuario", this.dadosUsuario);


                    JSONArray resultJson = new JSONArray(this.dadosUsuario);
                    JSONObject result;

                    for (int i = 0; i < resultJson.length(); i++) {
                        result = new JSONObject(resultJson.getString(i));

                        JSONArray dadosJson = new JSONArray(result.getString("dados"));
                        JSONObject dados;

                        for (int x = 0; x < dadosJson.length(); x++) {
                            dados = new JSONObject(dadosJson.getString(x));//pega dados do usuario
                            User u = new User();

                            u.setId(Integer.parseInt(dados.getString("id")));
                            u.setName(dados.getString("first_name"));
                            u.setEmail(dados.getString("email"));
                            u.setPassword(dados.getString("senha"));
                            u.setDados(this.dadosUsuario);



                            DAOUser daoUser = new DAOUser(getBaseContext());
                            //Toast.makeText(this, "SQLite", Toast.LENGTH_SHORT).show();
                            daoUser.insereUsuario(u);
                        }
                    }



                    Intent it = new Intent(this, GTC_WelcomeActivity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                }else{
                    Toast.makeText(this, "Login Inválido!", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Verifique sua conexão com a internet!", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        System.exit(0);
    }
}