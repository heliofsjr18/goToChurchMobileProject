package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class ChurchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ListView listaChurchs;
    List<Church> churchs;
    private Church mChurch;
    private int layout;
    private static final String EXTRA_CHURCH = "church";
    private ListView lvChurch;
    private WebService ws = new WebService();
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/congregacao/listarCongregacao";
    public ChurchFragment() {
        // Required empty public constructor
    }

    public static ChurchFragment newInstance(int church) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CHURCH, church);
        ChurchFragment dialog = new ChurchFragment();
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            layout = getArguments().getInt(EXTRA_CHURCH);
        }
        carregarLista();

        /*listaChurchs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Church church = (Church) lvChurch.getItemAtPosition(position);

                String rua = church.getAddress().getStreetName();
                String bairro = church.getAddress().getDistrict();
                String numero = church.getAddress().getHomeNumber();
                String cidade = church.getAddress().getCity();
                String estado = church.getAddress().getState();
                String pais = "Brasil";


                Uri gmmIntentUri = Uri.parse("google.navigation:q="+rua+",+"+numero+",+"+cidade+"+-+"+estado+",+"+pais);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });*/
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarLista();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(
                R.layout.fragment_church, container, false);
        listaChurchs = (ListView) layout.findViewById(R.id.lvChurchFragment);
        //listaChurchs.requestFocus();
        //Toast.makeText(getContext(), ""+churchs, Toast.LENGTH_SHORT).show();
        if (churchs != null) {
            ChurchAdapter ad = new ChurchAdapter(getContext(), R.layout.content_gtc_listview, churchs);
            listaChurchs.setAdapter(ad);
        }

        /**/
        try{
        listaChurchs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Church church = (Church) listaChurchs.getItemAtPosition(position);

                String rua = church.getAddress().getStreetName();
                String bairro = church.getAddress().getDistrict();
                String numero = church.getAddress().getHomeNumber();
                String cidade = church.getAddress().getCity();
                String estado = church.getAddress().getState();
                String pais = "Brasil";


                Uri gmmIntentUri = Uri.parse("google.navigation:q="+rua+",+"+numero+",+"+cidade+"+-+"+estado+",+"+pais);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });}catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


        // Exibe o teclado virtual ao exibir o Dialog
       /* getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(R.string.acao_novo);*/
        return layout;
    }

    public void carregarLista() {
        //fachada = Fachada.getInstance(getActivity());

        //listaServicos = (ListView) getActivity().findViewById(R.id.lvSericosAbertos);
        /*List<Servico> servicos = null;
        servicos = fachada.ListarServicosUsuario(fachada.usuarioLogado());
        ListaAdapterServico adapterServico = new ListaAdapterServico(getActivity(), (ArrayList<Servico>) servicos);
        listaServicos.setAdapter(adapterServico);*/

        //GTC_ChurchListActivity obj =  new GTC_ChurchListActivity();
        //obj.listChurch();
        listChurch();
    }


    public void listChurch(){

        try {
            String stringChurch = this.ws.getUrlContents(this.URL); //Chama função que consome o web service

            JSONArray churchJson = new JSONArray(stringChurch);
            JSONObject church;

            //Toast.makeText(this, this.URL, Toast.LENGTH_LONG).show();

            //int idChurch;
            //int idSetor;
            //int numero;

            //ListView lista = (ListView) layout.findViewById(R.id.listViewChurch);
            //String[] strings = new String[churchJson.length()];
            this.churchs = new ArrayList<>();

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
                this.churchs.add(c);



                //String setorInfo = "Setor 0"+numero+" | Coordenador: "+nomeCoordenador;
                //strings[i] = setorInfo;


            }

            //ChurchAdapter ad = new ChurchAdapter(getContext(), R.layout.content_gtc_listview, this.church);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);

            //mChurch.setAdapter(ad);



        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}