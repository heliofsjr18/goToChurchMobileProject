package com.example.helio.gotochurchmobileproject.Fachada;

/**
 * Created by helio on 30/11/2017.
 */

import android.content.Context;

import com.example.helio.gotochurchmobileproject.Basic.User;
import com.example.helio.gotochurchmobileproject.Negocio.UserNegocio;


public class Fachada {

    private static Fachada fachada;

    public static Fachada getInstance(Context context) {
        if (fachada == null)
            fachada = new Fachada(context);
        return fachada;
    }

    public Fachada(Context context) {
        UserNegocio userNegocio= new UserNegocio(context);
    }

    /*//Usuário
    public User UserLogar(String email, String senha) throws NegocioException {
        return UserNegocio.logar(email, senha);
    }

    public void UserAlterar(User User) {
        UserNegocio.alterar(User);
    }

    public void UserInserir(User User) {
        UserNegocio.inserir(User);
    }

    public User UserLogado() {
        return UserNegocio.UserLogado();
    }

    public void UserExcluirLogado() {
        UserNegocio.UserExcluirLogado();
    }

    public void UserAtualizarUserLogado(User User){ UserNegocio.UserAtualizarUserLogado(User);}

    //Serviços
    public List<Servico> ListarServicosDoUserLogado(User UserLogado) {
        return servicoNegocio.ListarServicosDoUserLogado(UserLogado);
    }

    public List<Servico> ListarTodosOsServicos() {
        return servicoNegocio.ListarTodosOsServicos();
    }

    public void servicoInserir(Servico servico) {
        servicoNegocio.inserir(servico);
    }

    //Usuários profissionais vinculados a um determinado serviço
    public List<ServicoUser> ListarProfIntServico(Servico servico) {
        return servicoUserNegocio.ListarProfIntServico(servico);
    }

    public void servicoUserInserir(ServicoUser servicoUser) { servicoUserNegocio.inserir(servicoUser); }*/

}