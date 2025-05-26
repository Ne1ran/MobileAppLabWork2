package com.example.mobileapplabwork2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobileapplabwork2.databinding.FragmentHomeBinding;

import com.google.android.material.tabs.TabLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.mobileapplabwork2.R;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // Параметры табличной разметки
    public static TabLayout.LayoutParams tlp3;
    // Компонент произвольной разметки
    public static RelativeLayout relativeLayout1;
    // Компонент со вкладками
    public static TabLayout tabLayout1;
    // Счётчик циклов
    public static int i1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Подключение relativeLayout1 к разметке
        relativeLayout1 = (RelativeLayout) root.findViewById(R.id.relativeLayout1);
        // Подключение tabLayout1 к разметке
        tabLayout1 = (TabLayout) root.findViewById(R.id.tabLayout1);

        // Установка полной ширины индикатора
        tabLayout1.setTabIndicatorFullWidth(true);
        // Режим компонента - прокручиваемый
        tabLayout1.setTabMode(TabLayout.MODE_SCROLLABLE);

        // Установка слушателей на компонент tabLayout1
        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Обработка события выбора вкладки
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabSelected " + String.valueOf(i1), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Обработка события ухода со вкладки
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabUnselected " + String.valueOf(i1), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Обработка события повторного выбора вкладки
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabReselected " + String.valueOf(i1), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        relativeLayout1 = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout1);
        tabLayout1 = (TabLayout) getActivity().findViewById(R.id.tabLayout1);
    }

    public static void addTabLayout(TabLayout tabLayout) {
        // Создание и добавление вкладок
        tabLayout.addTab(tabLayout.newTab().setText("21ИВ16зи\n" + "КТОП ЭВМ "), 3);
        tabLayout.addTab(tabLayout.newTab().setText("21ИВ16зи\n" + "Проектир ПО "), 4);
        tabLayout.addTab(tabLayout.newTab().setText("21ИВ16зи\n" + "Проектир моб. прил. "), 5);
    }
}