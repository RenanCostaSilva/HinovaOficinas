package br.com.renancsdev.hinovaoficinas.model.oficina;

import java.util.List;

import br.com.renancsdev.hinovaoficinas.model.oficina.ListaOficinasItem;

public class ResulListOficinas {

    private List<ListaOficinasItem> ListaOficinas;
    private Object token;

    public List<ListaOficinasItem> getListaOficinas() {
        return ListaOficinas;
    }

    public void setListaOficinas(List<ListaOficinasItem> listaOficinas) {
        ListaOficinas = listaOficinas;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }
}
