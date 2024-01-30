package br.com.renancsdev.hinovaoficinas.adpter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.renancsdev.hinovaoficinas.adpter.holder.ViewHolderOficina;
import br.com.renancsdev.hinovaoficinas.databinding.ItemOficinaBinding;
import br.com.renancsdev.hinovaoficinas.model.oficina.ListaOficinasItem;

public class RecyclerOficina extends RecyclerView.Adapter<ViewHolderOficina> {

    private final List<ListaOficinasItem> oficinas;
    private Context context;

    public RecyclerOficina(List<ListaOficinasItem> oficinas , Context ctx) {
        this.oficinas = oficinas;
        this.context = ctx;
    }

    @Override
    public ViewHolderOficina onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemOficinaBinding binding = ItemOficinaBinding.inflate(layoutInflater, parent , false);
        return new ViewHolderOficina(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolderOficina holder, int position) {
        holder.bind(oficinas.get(position));
    }

    @Override
    public int getItemCount() {
        return oficinas.size();
    }
}
