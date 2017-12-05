package com.example.helio.gotochurchmobileproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Area;
import com.example.helio.gotochurchmobileproject.Basic.Coordenador;
import com.example.helio.gotochurchmobileproject.Util.AreaAdapter;
import com.example.helio.gotochurchmobileproject.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AreaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AreaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private WebService ws = new WebService();
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/area/listaAreas";
    private List<Area> lAreas;
    public ListView lvArea;
    // TODO: Rename and change types of parameters
    private int layParam1;

    private OnFragmentInteractionListener mListener;

    public AreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AreaFragment newInstance(int param1) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layParam1 = getArguments().getInt(ARG_PARAM1);
        }
        listAreas();
    }

    @Override
    public void onResume() {
        super.onResume();
        listAreas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        lvArea = (ListView) view.findViewById(R.id.lvAreaFragment);
        if(lAreas != null){
            AreaAdapter ad = new AreaAdapter(getContext(), R.layout.content_gtc_listview, lAreas);
            lvArea.setAdapter(ad);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

            //ListView lista = (ListView) findViewById(R.id.listViewArea);

            this.lAreas = new ArrayList<>();

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


                this.lAreas.add(a);

                //String setorInfo = "Setor 0"+numero+" | Coordenador: "+nomeCoordenador;
                //strings[i] = setorInfo;


            }



        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
