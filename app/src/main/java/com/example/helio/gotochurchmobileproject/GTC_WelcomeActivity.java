package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Util.GTC_PageViewActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class GTC_WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String dadosUsuario = "";
    private View.OnClickListener onClickListenerMap = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentMap = new Intent(GTC_WelcomeActivity.this, GTC_MapsActivity.class);
            startActivity(intentMap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //findViewById(R.id.fab).setOnClickListener(onClickListenerMap);
        fab.setOnClickListener(this.onClickListenerMap);
        View navHeaderView = navigationView.getHeaderView(0);
        try{

            Intent it = getIntent();
            Bundle bundle = it.getExtras();
            String dadosUsuariou = bundle.getString("dadosUsuario");
            this.dadosUsuario = dadosUsuariou;

            JSONArray resultJson = new JSONArray(dadosUsuariou);
            JSONObject result;

            for (int i = 0; i < resultJson.length(); i++) {
                result = new JSONObject(resultJson.getString(i));

                JSONArray dadosJson = new JSONArray(result.getString("dados"));
                JSONObject dados;

                for(int x = 0; x < dadosJson.length(); x++){
                    dados = new JSONObject(dadosJson.getString(x));//pega dados do usuario

                    TextView txvUserName = (TextView) navHeaderView.findViewById(R.id.userName);
                    TextView txvUserEmail = (TextView) navHeaderView.findViewById(R.id.userEmail);
                    
                    txvUserName.setText(dados.getString("first_name"));
                    txvUserEmail.setText(dados.getString("email"));
                }
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gtc__welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.nav_setor) {
            // Handle the camera action
            Intent it = new Intent(this, GTC_ListaSetores.class);
            startActivity(it);
        } else if (id == R.id.nav_area) {

        } else if (id == R.id.nav_congregacao) {
            Intent it = new Intent(this, GTC_ChurchListActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_manage) {

        }/* else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void cardViewCLick(View v)
    {
        Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();
    }

    void pgv(View v){
        Intent it = new Intent(this, GTC_PageViewActivity.class);
        startActivity(it);
    }
}
