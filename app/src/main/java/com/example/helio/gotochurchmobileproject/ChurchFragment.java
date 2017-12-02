package com.example.helio.gotochurchmobileproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.helio.gotochurchmobileproject.Basic.Church;
import com.example.helio.gotochurchmobileproject.Fachada.Fachada;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChurchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChurchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChurchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listaServicos;
    List<Church> churchs;

    public ChurchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChurchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChurchFragment newInstance(String param1, String param2) {
        ChurchFragment fragment = new ChurchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static ChurchFragment newInstance(String text) {
        ChurchFragment f = new ChurchFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }
    public void carregarLista() {
        //fachada = Fachada.getInstance(getActivity());

        //listaServicos = (ListView) getActivity().findViewById(R.id.lvSericosAbertos);
        /*List<Servico> servicos = null;
        servicos = fachada.ListarServicosUsuario(fachada.usuarioLogado());
        ListaAdapterServico adapterServico = new ListaAdapterServico(getActivity(), (ArrayList<Servico>) servicos);
        listaServicos.setAdapter(adapterServico);*/

        GTC_ChurchListActivity obj =  new GTC_ChurchListActivity();
        obj.listChurch();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_church, container, false);
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_church, container, false);
        listaServicos = (ListView) view.findViewById(R.id.lvChurchFragment);
        listaServicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {

                Intent it = new Intent(getActivity(), GTC_ChurchListActivity.class);
                it.putExtra("Church", churchs.get(pos));
                startActivity(it);
            }
        });
        return view;
    }
}