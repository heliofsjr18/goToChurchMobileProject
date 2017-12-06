package com.example.helio.gotochurchmobileproject;

//import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Coordenador;
import com.example.helio.gotochurchmobileproject.Basic.Setor;
import com.example.helio.gotochurchmobileproject.Util.SetoresAdapter;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam1Layout;
    private OnFragmentInteractionListener mListener;

    private WebService ws = new WebService();
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/setor/listaSetores";
    private ListView listaSectors;
    List<Setor> setores;
    Setor mSetor;
    private static final String EXTRA_SECTOR = "sector";

    public SectorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SectorFragment newInstance(int param1) {
        SectorFragment fragment = new SectorFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_SECTOR, param1);
        fragment.setArguments(args);
        return fragment;
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
        listSetores();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1Layout = getArguments().getInt(EXTRA_SECTOR);
        }
        carregarLista();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */




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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_sector, container, false);
        listaSectors = (ListView) view.findViewById(R.id.lvSectorFragment);
        //listaSectors.requestFocus();
        if (setores != null) {
            SetoresAdapter ad = new SetoresAdapter(getContext(), R.layout.content_gtc_listview, setores);
            listaSectors.setAdapter(ad);
        }

        return view;
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

            //ListView lista = (ListView) findViewById(R.id.listViewSetores);

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





        }catch (Exception e){
            Toast.makeText(getContext(), "sector", Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}
