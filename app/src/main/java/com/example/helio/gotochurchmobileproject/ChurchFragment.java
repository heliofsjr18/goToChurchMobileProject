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

public class ChurchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ListView listaChurchs;
    List<Church> churchs;
    private Church mChurch;
    private static final String EXTRA_CHURCH = "church";
    private ListView lvChurch;

    public ChurchFragment() {
        // Required empty public constructor
    }

    public static ChurchFragment newInstance(Church church) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CHURCH, church);
        ChurchFragment dialog = new ChurchFragment();
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mChurch = (Church) getArguments().getSerializable(EXTRA_CHURCH);
        }
    }

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
        listaChurchs = (ListView) view.findViewById(R.id.lvChurchFragment);
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(
                R.layout.fragment_church, container, false);
        listaChurchs = (ListView) layout.findViewById(R.id.lvChurchFragment);
        listaChurchs.requestFocus();

        if (mChurch != null) {
            listaChurchs.setText(mChurch.);
        }
        // Exibe o teclado virtual ao exibir o Dialog
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(R.string.acao_novo);
        return layout;
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
}