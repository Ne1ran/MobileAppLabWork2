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
import android.graphics.Color;
import android.content.Context;
import androidx.core.content.ContextCompat;

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
    // Объявление объекта табличная разметка tableLayout3
    public static TableLayout tableLayout3;
    // Массив строк таблицы tablRow1
    public static TableRow.LayoutParams tlpF3;
    public static TableRow[] tablRow1;
    // Массив строк таблицы tablRow2
    public static TableRow[] tablRow2; // Объявление TableRow[] для второй таблицы
    // Массив строк таблицы tablRow3
    public static TableRow[] tablRow3;
    // Одномерный массив текстовых меток tv1
    public static TextView[][] tv1;
    // Двумерный массив текстовых меток tv2
    public static TextView[][] tv2; // Объявление TextView[][] для второй таблицы
    // Двумерный массив текстовых меток tv3
    public static TextView[][] tv3;
    // Счётчик циклов
    public static int i1;
    // Счётчик циклов j1
    public static int j1; // Объявление счётчика j1

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Подключение relativeLayout1 к разметке
        relativeLayout1 = (RelativeLayout) root.findViewById(R.id.relativeLayout1);
        // Подключение tabLayout1 к разметке
        tabLayout1 = (TabLayout) root.findViewById(R.id.tabLayout1);
        // Подключение tableLayout1 к разметке
        tableLayout1 = (TableLayout) root.findViewById(R.id.tableLayout1);
        // Подключение tableLayout2 к разметке
        tableLayout2 = (TableLayout) root.findViewById(R.id.tableLayout2);
        // Подключение tableLayout3 к разметке
        tableLayout3 = (TableLayout) root.findViewById(R.id.tableLayout3);

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
        // Подключение tableLayout3 к разметке в onStart
        tableLayout3 = (TableLayout) getActivity().findViewById(R.id.tableLayout3);

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
        tableLayout3.removeAllViews();

        // Создание массива строк таблицы tablRow3 (20 строк)
        tablRow3 = new TableRow[20];
        // Создание массива текстовых меток tv3 (20 строк, 2 столбца)
        tv3 = new TextView[20][2];

        // Параметры табличной разметки для строки (используем tlpF3, как в скриншоте)
        tlpF3 = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        // Обнуление счётчика цикла по строкам таблицы
        int rowIndex = 0;
        // Цикл по строкам таблицы (до 20, обучаемых)
        while (rowIndex < 20) {
            // Создание первой ячейки в строки tablRow3
            tablRow3[rowIndex] = new TableRow(getActivity());
            // Установка интервалов между компонентами типа tablRow3 таблицы 3
            tablRow3[rowIndex].setPadding(1, 1, 1, 1);
            // Установка параметров строки tablRow3 таблицы 3
            tablRow3[rowIndex].setLayoutParams(tlpF3);

            // Обнуление счётчика цикла по элементам строки tablRow3 таблицы 3
            int colIndex = 0;
            // Цикл по элементам строки tablRow3 таблицы 3
            while (colIndex < 2) {
                // Создание очередного TextView в текущей Activity
                tv3[rowIndex][colIndex] = new TextView(getActivity());

                // Установка высоты шрифта
                tv3[rowIndex][colIndex].setTextSize(14);
                // Установка цвета текста текущего TextView
                tv3[rowIndex][colIndex].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                if (colIndex == 0) {
                    // Если первая ячейка
                    // Ввод номера строки в текущий TextView
                    tv3[rowIndex][colIndex].setText(String.valueOf(rowIndex + 1) + "\n" + "\n");
                    // Установка минимальной ширины tv3
                    tv3[rowIndex][colIndex].setWidth(70);
                    // Установка цвета фона текущего TextView
                    tv3[rowIndex][colIndex].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                } else if (colIndex == 1) {
                    // Если вторая ячейка
                    // Ввод текста в текущий TextView
                    tv3[rowIndex][colIndex].setText("Фамилия" + "\n" + "Имя" + "\n" +
                            "Отчество " + String.valueOf(rowIndex + 1));
                    // Установка минимальной ширины tv3
                    tv3[rowIndex][colIndex].setWidth(240);
                    // Установка цвета фона текущего TextView
                    tv3[rowIndex][colIndex].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                // Добавление TextView в строку tablRow3
                tablRow3[rowIndex].addView(tv3[rowIndex][colIndex], colIndex);

                colIndex++;
            }
            // Добавление строки tablRow3 в таблицу i1
            tableLayout3.addView(tablRow3[rowIndex], rowIndex);

            rowIndex++;
        }
    }
}