package br.com.renancsdev.hinovaoficinas.adpter.holder;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.renancsdev.hinovaoficinas.databinding.ItemOficinaBinding;
import br.com.renancsdev.hinovaoficinas.model.oficina.ListaOficinasItem;
import br.com.renancsdev.hinovaoficinas.ui.activity.oficina.ActivityDetalhesOficina;

public class ViewHolderOficina extends RecyclerView.ViewHolder{

    public ItemOficinaBinding itemRowBinding;

    public ViewHolderOficina(@NonNull ItemOficinaBinding itemRowBinding) {
        super(itemRowBinding.getRoot());
        this.itemRowBinding = itemRowBinding;
    }

    public void bind(ListaOficinasItem oficina){
        itemRowBinding.tvItemOficinaNome.setText(oficina.getNome());
        itemRowBinding.imgItemOficinaNome.setImageBitmap(base64ToBitmap(oficina.getFoto()));
        itemRowBinding.tvItemOficinaDescricaoCurta.setText(oficina.getDescricaoCurta());
        itemRowBinding.cardItemOficina.setOnClickListener(v -> redirecionar(itemRowBinding.getRoot().getContext() , oficina));
    }

    private Bitmap base64ToBitmap(String base64){
        byte[] decodedString = Base64.decode(base64 , Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private void redirecionar(Context context , ListaOficinasItem oficina){
        Intent intent = new Intent(context , ActivityDetalhesOficina.class);
        intent.putExtra("foto" , oficina.getFoto());
        intent.putExtra("nome" , oficina.getNome());
        intent.putExtra("telefone" , oficina.getTelefone1());
        intent.putExtra("descricao" , oficina.getDescricaoCurta());
        intent.putExtra("endereco" ,  oficina.getEndereco());
        intent.putExtra("latitude" , oficina.getLatitude());
        intent.putExtra("longitude" , oficina.getLongitude());
        intent.putExtra("avaliacao" , oficina.getAvaliacaoUsuario());
        context.startActivity(intent);
    }

}
