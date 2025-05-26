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
import android.widget.TableLayout;
import android.widget.TableRow;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // Параметры табличной разметки
    public static TabLayout.LayoutParams tlp3;
    // Компонент произвольной разметки
    public static RelativeLayout relativeLayout1;
    // Компонент со вкладками
    public static TabLayout tabLayout1;
    // Компонент табличной разметки
    public static TableLayout tableLayout1;
    // Объявление объекта табличная разметка tableLayout2
    public static TableLayout tableLayout2; // Объявление TableLayout2
    // Массив строк таблицы tablRow1
    public static TableRow.LayoutParams tlpF3;
    public static TableRow[] tablRow1;
    // Массив строк таблицы tablRow2
    public static TableRow[] tablRow2; // Объявление TableRow[] для второй таблицы
    // Одномерный массив текстовых меток tv1
    public static TextView[][] tv1;
    // Двумерный массив текстовых меток tv2
    public static TextView[][] tv2; // Объявление TextView[][] для второй таблицы
    // Счётчик циклов
    public static int i1;
    // Счётчик циклов j1
    public static int j1; // Объявление счётчика j1

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
        // Подключение tableLayout1 к разметке
        tableLayout1 = (TableLayout) root.findViewById(R.id.tableLayout1);
        // Подключение tableLayout2 к разметке
        tableLayout2 = (TableLayout) root.findViewById(R.id.tableLayout2);

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
                loadTable(i1);
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
        tableLayout1 = (TableLayout) getActivity().findViewById(R.id.tableLayout1);
        tableLayout2 = (TableLayout) getActivity().findViewById(R.id.tableLayout2);

        // Загрузка таблицы при старте фрагмента
        loadTable(i1);
    }

    public static void addTabLayout(TabLayout tabLayout) {
        // Создание и добавление вкладок
        tabLayout.addTab(tabLayout.newTab().setText("21ИВ16зи\n" + "КТОП ЭВМ "), 3);
        tabLayout.addTab(tabLayout.newTab().setText("21ИВ16зи\n" + "Проектир ПО "), 4);
        tabLayout.addTab(tabLayout.newTab().setText("21ИВ16зи\n" + "Проектир моб. прил. "), 5);
    }

    // Функция загрузки таблицы
    public void loadTable(int i1) {
        // Очистка TableLayout перед добавлением новых строк
        tableLayout1.removeAllViews();
        tableLayout2.removeAllViews();

        // Создание массива строк таблицы (1 строка)
        tablRow1 = new TableRow[1];
        // Создание массива текстовых меток (1 строка, 3 столбца)
        tv1 = new TextView[1][3];

        // Растягиваем колонки 2 и 3 (индексы 1 и 2) для tableLayout1
        tableLayout1.setColumnStretchable(1, true);
        tableLayout1.setColumnStretchable(2, true);

        // Создание параметров табличной разметки для строки
        tlpF3 = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        // Создание строки в tablRow1
        tablRow1[0] = new TableRow(getActivity());
        // Установка параметров строки
        tablRow1[0].setLayoutParams(tlpF3);
        // Установка интервалов между компонентами типа TextView
        tablRow1[0].setPadding(1, 1, 1, 1);

        // Обнуление счётчика ячеек
        int i = 0;
        // Цикл по элементам строки таблицы 1 таблицы 1
        while (i < 3) {
            // Создание очередного TextView в текущей Activity
            tv1[0][i] = new TextView(getActivity());
            // Установка высоты шрифта
            tv1[0][i].setTextSize(14);
            // Установка цвета текста текущего TextView
            tv1[0][i].setTextColor(getResources().getColor(R.color.black));

            if (i == 0) {
                // Если первая ячейка строки tablRow1
                tv1[0][i].setText("");
                tv1[0][i].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_0));
                // Установка цвета фона текущего TextView
                tv1[0][i].setBackgroundColor(getResources().getColor(R.color.blueLitCyan));
            } else if (i == 1) {
                // Если вторая ячейка строки tablRow1
                tv1[0][i].setText("Дисциплина");
                tv1[0][i].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_1));
                // Установка цвета фона текущего TextView
                tv1[0][i].setBackgroundColor(getResources().getColor(R.color.blueMidCyan));
            } else if (i == 2) {
                // Если третья ячейка строки tablRow1
                tv1[0][i].setText("Проектирование мобильных приложений");
                tv1[0][i].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_2));
                // Установка цвета фона текущего TextView
                tv1[0][i].setBackgroundColor(getResources().getColor(R.color.white));
            }
            // Добавление TextView в строку tablRow1
            tablRow1[0].addView(tv1[0][i], i);

            i++;
        }
        // Добавление строки tablRow1 в таблицу
        tableLayout1.addView(tablRow1[0], 0);

        // Начало работы со второй таблицей (tableLayout2)

        // Создание массива строк таблицы tablRow2 (2 строки)
        tablRow2 = new TableRow[2];
        // Создание массива текстовых меток tv2 (2 строки, 6 столбцов)
        tv2 = new TextView[2][6];

        // Растягиваем колонку 2 (индекс 1) для tableLayout2
        tableLayout2.setColumnStretchable(1, true);

        // Обнуление счётчика цикла по строкам таблицы 2
        int row_i = 0;
        // Цикл по строкам таблицы 2
        while (row_i < 2) {
             // Создание первой ячейки в строки tablRow2
            tablRow2[row_i] = new TableRow(getActivity());
            tablRow2[row_i].setPadding(1, 1, 1, 1);
            tablRow2[row_i].setLayoutParams(tlpF3);

            // Обнуление счётчика цикла по элементам строки tablRow2 таблицы 2
            j1 = 0;
            // Цикл по элементам строки tablRow2 таблицы 2
            while (j1 < 6) {
                // Создание очередного TextView в текущей Activity
                tv2[row_i][j1] = new TextView(getActivity());
                tv2[row_i][j1].setTextSize(14);
                tv2[row_i][j1].setTextColor(getResources().getColor(R.color.black));

                if (j1 == 0) {
                    tv2[row_i][j1].setText("");
                    tv2[row_i][j1].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_0));
                    tv2[row_i][j1].setBackgroundColor(getResources().getColor(R.color.blueLitCyan));
                } else if (j1 == 1) {
                    tv2[row_i][j1].setText("Ф И О");
                     tv2[row_i][j1].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_1));
                    tv2[row_i][j1].setBackgroundColor(getResources().getColor(R.color.blueMidCyan));
                } else if (j1 == 2) {
                    tv2[row_i][j1].setText("");
                    tv2[row_i][j1].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_2));
                    tv2[row_i][j1].setBackgroundColor(getResources().getColor(R.color.blueLitCyan));
                } else if (j1 == 3) {
                     tv2[row_i][j1].setText("Зач");
                     tv2[row_i][j1].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_3));
                     tv2[row_i][j1].setBackgroundColor(getResources().getColor(R.color.blueMidCyan));
                } else if (j1 == 4) {
                     tv2[row_i][j1].setText("КП");
                     tv2[row_i][j1].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_4));
                     tv2[row_i][j1].setBackgroundColor(getResources().getColor(R.color.blueLitCyan));
                } else if (j1 == 5) {
                     tv2[row_i][j1].setText("Экз");
                     tv2[row_i][j1].setWidth(getResources().getDimensionPixelSize(R.dimen.cell_width_5));
                     tv2[row_i][j1].setBackgroundColor(getResources().getColor(R.color.blueMidCyan));
                }

                // Добавление TextView в строку tablRow2
                tablRow2[row_i].addView(tv2[row_i][j1], j1);

                j1++;
            }
            // Добавление строки tablRow2 в таблицу
            tableLayout2.addView(tablRow2[row_i], row_i);

            row_i++;
        }
    }
}