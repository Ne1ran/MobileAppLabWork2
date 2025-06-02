package com.example.mobileapplabwork2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mobileapplabwork2.R;
import com.example.mobileapplabwork2.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;

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
    // Объявление объекта tableLayout4
    public static TableLayout tableLayout4;
    // Объявление объекта tableLayout5
    public static TableLayout tableLayout5;
    // Объявление объекта horizontalScrollView4
    public static HorizontalScrollView horizontalScrollView4;
    // Объявление объекта scrollView3, scrollView4 и scrollView5
    public static ScrollView scrollView3, scrollView4, scrollView5;
    // Массив строк таблицы tablRow1
    public static TableRow.LayoutParams tlpF3;
    public static TableRow[] tablRow1;
    // Массив строк таблицы tablRow2
    public static TableRow[] tablRow2; // Объявление TableRow[] для второй таблицы
    // Массив строк таблицы tablRow3
    public static TableRow[] tablRow3;
    // Массив строк таблицы tablRow4
    public static TableRow[] tablRow4;
    // Массив строк таблицы tablRow5
    public static TableRow[] tablRow5;
    // Одномерный массив текстовых меток tv1
    public static TextView[][] tv1;
    // Двумерный массив текстовых меток tv2
    public static TextView[][] tv2; // Объявление TextView[][] для второй таблицы
    // Двумерный массив текстовых меток tv3
    public static TextView[][] tv3;
    // Двумерный массив текстовых меток tv4, tv5
    public static TextView[][] tv4, tv5;
    // Счётчик циклов
    public static int i1;
    // Счётчик циклов j1
    public static int j1; // Объявление счётчика j1
    // Для управления загрузкой данных во вкладках в заголовок класса HomeFragment добавляется атрибут для хранения номера:
    public static int NumTab; // Номер вкладки

    // Объявление объекта HomeFragmentListener
    HomeFragmentListener homeFragmentScrollview3;
    HomeFragmentListener homeFragmentScrollview4;
    HomeFragmentListener homeFragmentScrollview5;
    HomeFragmentListener homeFragmentHorScroll4;
    // Объявление объекта horizontalScrollView6
    public static HorizontalScrollView horizontalScrollView6;
    HomeFragmentListener homeFragmentHorScroll6;

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
        // Подключение horizontalScrollView4 к разметке
        horizontalScrollView4 = (HorizontalScrollView) root.findViewById(R.id.horizontalScrollView4);
        // Подключение tableLayout4 к разметке
        tableLayout4 = (TableLayout) root.findViewById(R.id.tableLayout4);
        // Подключение tableLayout5 к разметке
        tableLayout5 = (TableLayout) root.findViewById(R.id.tableLayout5);
        // Подключение scrollView3 к разметке
        scrollView3 = (ScrollView) root.findViewById(R.id.scrollView3);
        // Подключение scrollView4 к разметке
        scrollView4 = (ScrollView) root.findViewById(R.id.scrollView4);
        // Подключение scrollView5 к разметке
        scrollView5 = (ScrollView) root.findViewById(R.id.scrollView5);
        // Подключение horizontalScrollView6 к разметке
        horizontalScrollView6 = (HorizontalScrollView) root.findViewById(R.id.horizontalScrollView6);

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
                // В файле HomeFragment.java в функции onCreateView в функции-обработчике onTabSelected событий навигации по вкладкам в случае смены вкладки выполняется удаление визуальных компонентов для вкладки с предыдущим номером и создание визуальных компонентов для вкладки с текущим номером:
                deleteTable(NumTab); // Функция очистки таблиц
                loadTable(i1); // Функция загрузки таблиц
                NumTab = i1; // После выполнения указанных действий при переходе ко вкладки на вкладку будет выполнятся очистка визуальных компонентов и обновление содержимого вкладки.
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

        // В функции onCreateView размещаются функции открытого интерфейса:
        // Функция-слушатель для события прокрутки scrollView3 по вертикали
        scrollView3.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                homeFragmentScrollview3.onHomeFragmentScrollview3(0, scrollX, scrollY); // Обращение к функции HomeFragmentScrollview3
            }
        });

        // Функция-слушатель для события прокрутки scrollView4 по вертикали
        scrollView4.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                homeFragmentScrollview4.onHomeFragmentScrollview4(0, scrollX, scrollY); // Обращение к функции HomeFragmentScrollview4
            }
        });

        // Функция-слушатель для события прокрутки scrollView5 по вертикали
        scrollView5.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                homeFragmentScrollview5.onHomeFragmentScrollview5(0, scrollX, scrollY); // Обращение к функции HomeFragmentScrollview5
            }
        });

        // Функция-слушатель для события прокрутки horizontalScrollView4 по горизонтали
        horizontalScrollView4.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                homeFragmentHorScroll4.onHomeFragmentHorScroll4(1, scrollX, scrollY); // Обращение к функции HomeFragmentHorScroll4
            }
        });

        // Функция-слушатель для события прокрутки horizontalScrollView6 по горизонтали
        horizontalScrollView6.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                homeFragmentHorScroll6.onHomeFragmentHorScroll6(1, scrollX, scrollY); // Обращение к функции HomeFragmentHorScroll6
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
        // Подключение horizontalScrollView4 к разметке в onStart
        horizontalScrollView4 = (HorizontalScrollView) getActivity().findViewById(R.id.horizontalScrollView4);
        // Подключение tableLayout4 к разметке в onStart
        tableLayout4 = (TableLayout) getActivity().findViewById(R.id.tableLayout4);
        // Подключение tableLayout5 к разметке в onStart
        tableLayout5 = (TableLayout) getActivity().findViewById(R.id.tableLayout5);
        // Подключение scrollView3 к разметке в onStart
        scrollView3 = (ScrollView) getActivity().findViewById(R.id.scrollView3);
        // Подключение scrollView4 к разметке в onStart
        scrollView4 = (ScrollView) getActivity().findViewById(R.id.scrollView4);
        // Подключение scrollView5 к разметке в onStart
        scrollView5 = (ScrollView) getActivity().findViewById(R.id.scrollView5);
        // Подключение horizontalScrollView6 к разметке в onStart
        horizontalScrollView6 = (HorizontalScrollView) getActivity().findViewById(R.id.horizontalScrollView6);

        // Загрузка таблицы при старте фрагмента
        loadTable(i1);
        // В функции onStart при запуске приложения выполняется загрузка данных на вкладку с номером 0:
        NumTab = 0; // Номер вкладки
        loadTable(NumTab); // Функция загрузки таблиц
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
        tableLayout4.removeAllViews(); // Очистка tableLayout4
        tableLayout5.removeAllViews(); // Очистка tableLayout5

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

        // Создание массива строк таблицы tablRow4 (20 строк)
        tablRow4 = new TableRow[20];
        // Создание массива строк таблицы tablRow5 (20 строк)
        tablRow5 = new TableRow[20];
        // Создание массива текстовых меток tv4 (20 строк, 20 столбцов)
        tv4 = new TextView[20][20];
        // Создание массива текстовых меток tv5 (20 строк, 3 столбца)
        tv5 = new TextView[20][3];

        // Обнуление счётчика цикла по строкам таблицы 4
        int rowIndex4 = 0;
        // Цикл по строкам таблицы 4 (до 20, обучаемых)
        while (rowIndex4 < 20) {
            // Создание строки в tablRow4
            tablRow4[rowIndex4] = new TableRow(getActivity());
            // Установка интервалов между компонентами
            tablRow4[rowIndex4].setPadding(1, 1, 1, 1);
            // Установка параметров строки
            tablRow4[rowIndex4].setLayoutParams(tlpF3);

            // Обнуление счётчика цикла по элементам строки таблицы 4
            int colIndex4 = 0;
            // Цикл по элементам строки таблицы 4
            while (colIndex4 < 20) {
                // Создание очередного TextView
                tv4[rowIndex4][colIndex4] = new TextView(getActivity());
                // Установка цвета текста
                tv4[rowIndex4][colIndex4].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                // Установка размера шрифта
                tv4[rowIndex4][colIndex4].setTextSize((float) 14);

                if ((colIndex4 % 2) != 0) { // Если нечётная ячейка
                    // Ввод номера строки в текущий TextView
                    tv4[rowIndex4][colIndex4].setText(String.valueOf(rowIndex4 + 1) + "\n" + "\n");
                    // Установка минимальной ширины
                    tv4[rowIndex4][colIndex4].setWidth(100);
                    // Установка цвета фона
                    tv4[rowIndex4][colIndex4].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                } else { // Если чётная ячейка
                    // Ввод текста
                    tv4[rowIndex4][colIndex4].setText(String.valueOf(rowIndex4 + 1) + "\n" + "\n");
                    // Установка минимальной ширины
                    tv4[rowIndex4][colIndex4].setWidth(100);
                    // Установка цвета фона
                    tv4[rowIndex4][colIndex4].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                }
                // Добавление TextView в строку
                tablRow4[rowIndex4].addView(tv4[rowIndex4][colIndex4], colIndex4);

                colIndex4++;
            }
            // Добавление строки в таблицу 4
            tableLayout4.addView(tablRow4[rowIndex4], rowIndex4);

            rowIndex4++;
        }

        // Обнуление счётчика цикла по строкам таблицы 5
        int rowIndex5 = 0;
        // Цикл по строкам таблицы 5 (до 20, обучаемых)
        while (rowIndex5 < 20) {
            // Создание строки в tablRow5
            tablRow5[rowIndex5] = new TableRow(getActivity());
            // Установка интервалов между компонентами
            tablRow5[rowIndex5].setPadding(1, 1, 1, 1);
            // Установка параметров строки
            tablRow5[rowIndex5].setLayoutParams(tlpF3);

            // Обнуление счётчика цикла по элементам строки таблицы 5
            int colIndex5 = 0;
            // Цикл по элементам строки таблицы 5
            while (colIndex5 < 3) {
                // Создание очередного TextView
                tv5[rowIndex5][colIndex5] = new TextView(getActivity());
                // Установка цвета текста
                tv5[rowIndex5][colIndex5].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                // Установка размера шрифта
                tv5[rowIndex5][colIndex5].setTextSize((float) 14);

                // Логика заполнения ячеек tableLayout5 в соответствии со скриншотом ТЗ
                if (colIndex5 == 0) {
                    // Первая ячейка (индекс 0)
                    tv5[rowIndex5][colIndex5].setText(String.valueOf(rowIndex5 + 1) + "\n" + "\n"); // Ввод номера строки
                    tv5[rowIndex5][colIndex5].setWidth(70); // Ширина
                    tv5[rowIndex5][colIndex5].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan)); // Цвет фона
                } else if (colIndex5 == 1) {
                    // Вторая ячейка (индекс 1)
                    tv5[rowIndex5][colIndex5].setText("Фамилия" + "\n" + "Имя" + "\n" +
                            "Отчество " + String.valueOf(rowIndex5 + 1)); // Ввод текста
                    tv5[rowIndex5][colIndex5].setWidth(240); // Ширина
                    tv5[rowIndex5][colIndex5].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white)); // Цвет фона
                } else if (colIndex5 == 2) {
                    // Третья ячейка (индекс 2)
                    tv5[rowIndex5][colIndex5].setText(String.valueOf(rowIndex5 + 1)); // Ввод текста (номер строки)
                    tv5[rowIndex5][colIndex5].setWidth(70); // Ширина
                    tv5[rowIndex5][colIndex5].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueMidCyan)); // Цвет фона
                }

                // Добавление TextView в строку
                tablRow5[rowIndex5].addView(tv5[rowIndex5][colIndex5], colIndex5);

                colIndex5++;
            }
            // Добавление строки в таблицу 5
            tableLayout5.addView(tablRow5[rowIndex5], rowIndex5);

            rowIndex5++;
        }
    }

    // Также добавляется функция deleteTable для удаления всех визуальных компонентов со вкладки с номером NPL.
    public void deleteTable(int NPL) {
        if (tableLayout1 != null) {
            ((TableLayout) requireView().findViewById(R.id.tableLayout1)).removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout2 != null) {
            ((TableLayout) requireView().findViewById(R.id.tableLayout2)).removeAllViews(); // Удаление компонентов из таблицы tableLayout2
        }
        if (tableLayout3 != null) {
            ((TableLayout) requireView().findViewById(R.id.tableLayout3)).removeAllViews(); // Удаление компонентов из таблицы tableLayout3
        }
        if (tableLayout4 != null) {
            ((TableLayout) requireView().findViewById(R.id.tableLayout4)).removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout5 != null) {
            ((TableLayout) requireView().findViewById(R.id.tableLayout5)).removeAllViews(); // Удаление компонентов из таблицы tableLayout5
        }
        if (horizontalScrollView6 != null) {
            ((HorizontalScrollView) requireView().findViewById(R.id.horizontalScrollView6)).removeAllViews(); // Удаление компонентов из таблицы tableLayout6
        }
    }

    // Для организации взаимодействия компонентов и обеспечения синхронной прокрутки компонентов ScrollView3, ScrollView4, ScrollView5, horizontalScrollView4, horizontalScrollView6 создаётся открытый интерфейс и объявляются соответствующие методы:
    public interface HomeFragmentListener {
        public void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY); // Функция прокрутки ScrollView3

        public void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY); // Функция прокрутки ScrollView4

        public void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY); // Функция прокрутки ScrollView5

        public void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY); // Функция прокрутки horizontalScrollView4

        public void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY); // Функция прокрутки horizontalScrollView6
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context); // Функция присоединения интерфейса к контексту
        try {
            homeFragmentScrollview3 = (HomeFragmentListener) context;
            homeFragmentScrollview4 = (HomeFragmentListener) context;
            homeFragmentScrollview5 = (HomeFragmentListener) context;
            homeFragmentHorScroll4 = (HomeFragmentListener) context;
            homeFragmentHorScroll6 = (HomeFragmentListener) context;
        } catch (ClassCastException e) {
            // В случае ошибки
            throw new ClassCastException(context + " must implement OnHomeFragmentListener"); // Вывод предупредительного сообщения
        } // Конец секции обработки ошибки
    }

}
