package br.com.renancsdev.hinovaoficinas.ui.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.adpter.recycler.ViewPagerAdapter;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityMainBinding;
import br.com.renancsdev.hinovaoficinas.ui.activity.login.Login;
import br.com.renancsdev.hinovaoficinas.util.permissao.Permissao;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding = null;
    private static final  Integer LAYOUT = R.layout.activity_main;
    ViewPagerAdapter viewPagerAdapter;

    Permissao permissao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setarConfig();
        checarPermissoes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setarTabLayout();
    }

    /* Configuração Inicial */
    private void setarConfig(){
        setarConfigLayout();
        setarConfigDataBinding();

    }

    private void setarConfigLayout(){
        setContentView(LAYOUT);
    }

    private void setarConfigDataBinding(){
        binding = DataBindingUtil.setContentView(this , LAYOUT);
    }

    private void checarPermissoes(){
        permissao = new Permissao(MainActivity.this);
        permissao.verificarPermissao();
    }

    private void setarTabLayout(){

        binding.viewPager.setAdapter(setarFragmentManagerTabLayout());
        binding.tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // não usado
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // não usado
            }
        });
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tlMain.selectTab(binding.tlMain.getTabAt(position));
            }
        });
    }

    private ViewPagerAdapter setarFragmentManagerTabLayout(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        return new ViewPagerAdapter(fragmentManager , getLifecycle());
    }


}