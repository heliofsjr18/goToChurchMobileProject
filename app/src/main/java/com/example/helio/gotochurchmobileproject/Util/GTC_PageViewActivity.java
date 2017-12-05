package com.example.helio.gotochurchmobileproject.Util;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.helio.gotochurchmobileproject.R;

public class GTC_PageViewActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__page_view);
        Bundle args = new Bundle();
        String[] t = new String[3];
        t[0] = getString(R.string.title_activity_gtc__church_list);
        t[1] = getString(R.string.title_activity_gtc__lista_setores);
        t[2] = getString(R.string.title_activity_gtc__lista_area);
        
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), t);
        vpPager.setAdapter(adapterViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("GTC - "+getString(R.string.quick_acsess));

    }



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
}
