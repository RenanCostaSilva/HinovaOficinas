package br.com.renancsdev.hinovaoficinas.adpter.recycler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import br.com.renancsdev.hinovaoficinas.ui.fragment.main.FragmentIndicacao;
import br.com.renancsdev.hinovaoficinas.ui.fragment.main.FragmentOficinas;

public class ViewPagerAdapter  extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return  new FragmentOficinas();
        }
        else if(position == 1){
            return  new FragmentIndicacao();
        }
        return new FragmentOficinas();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
