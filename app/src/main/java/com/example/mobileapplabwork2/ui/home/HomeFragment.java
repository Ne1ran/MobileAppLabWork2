package com.example.mobileapplabwork2.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mobileapplabwork2.MainActivity;
import com.example.mobileapplabwork2.R;
import com.example.mobileapplabwork2.databinding.FragmentHomeBinding;
import com.example.mobileapplabwork2.sql.DBHandler;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    public static int i1, j1, idRowI, idColI, idNPLI, HigCell, CouCell = 3, NumRec, NumCol, NumTab, WidFIO = 220;
    public static float MulCell = 1.057F;

    private FragmentHomeBinding binding;

    public static RelativeLayout relativeLayout1;
    public static TabLayout tabLayout1;
    public static TableLayout tableLayout1;
    public static TableLayout tableLayout2;
    public static TableLayout tableLayout3;
    public static TableLayout tableLayout4;
    public static TableLayout tableLayout5;
    public static TableLayout tableLayout6;
    public static HorizontalScrollView horizontalScrollView4;
    public static ScrollView scrollView3, scrollView4, scrollView5;
    public static TableRow.LayoutParams tlpF3;
    public static TableRow[] tableRow1, tableRow2, tableRow3, tableRow4, tableRow5, tableRow6;
    public static TextView[][] tv1, tv2, tv3, tv4, tv5, tv6;
    public static SQLiteDatabase db;
    public static Cursor cursor;
    public static String JBaseName;

    HomeFragmentListener homeFragmentScrollview3;
    HomeFragmentListener homeFragmentScrollview4;
    HomeFragmentListener homeFragmentScrollview5;
    HomeFragmentListener homeFragmentHorScroll4;
    public static HorizontalScrollView horizontalScrollView6;
    HomeFragmentListener homeFragmentHorScroll6;

    // Создание и инициализация представления фрагмента
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Подключение представлений из макета
        relativeLayout1 = root.findViewById(R.id.relativeLayout1);
        tabLayout1 = root.findViewById(R.id.tabLayout1);
        tableLayout1 = root.findViewById(R.id.tableLayout1);
        tableLayout2 = root.findViewById(R.id.tableLayout2);
        tableLayout3 = root.findViewById(R.id.tableLayout3);
        horizontalScrollView4 = root.findViewById(R.id.horizontalScrollView4);
        tableLayout4 = root.findViewById(R.id.tableLayout4);
        tableLayout5 = root.findViewById(R.id.tableLayout5);
        scrollView3 = root.findViewById(R.id.scrollView3);
        scrollView4 = root.findViewById(R.id.scrollView4);
        scrollView5 = root.findViewById(R.id.scrollView5);
        horizontalScrollView6 = root.findViewById(R.id.horizontalScrollView6);

        // Настройка TabLayout
        tabLayout1.setTabIndicatorFullWidth(true);
        tabLayout1.setTabMode(TabLayout.MODE_SCROLLABLE);
        // Добавление слушателя выбора вкладки
        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabSelected " + i1, Toast.LENGTH_LONG).show();
                // Удаление и загрузка данных таблицы при выборе вкладки
                deleteTableData();
                loadTable(i1);
                NumTab = i1;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabUnselected " + i1, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabReselected " + i1, Toast.LENGTH_LONG).show();
            }
        });

        // Настройка слушателей прокрутки для синхронизации
        scrollView3.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            homeFragmentScrollview3.onHomeFragmentScrollview3(0, scrollX, scrollY);
        });

        scrollView4.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            homeFragmentScrollview4.onHomeFragmentScrollview4(0, scrollX, scrollY);
        });

        scrollView5.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            homeFragmentScrollview5.onHomeFragmentScrollview5(0, scrollX, scrollY);
        });

        horizontalScrollView4.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            homeFragmentHorScroll4.onHomeFragmentHorScroll4(1, scrollX, scrollY);
        });

        horizontalScrollView6.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            homeFragmentHorScroll6.onHomeFragmentHorScroll6(1, scrollX, scrollY);
        });

        return root;
    }

    // Очистка привязки представлений при уничтожении фрагмента
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Повторное подключение представлений при старте фрагмента
    @Override
    public void onStart() {
        super.onStart();
        relativeLayout1 = getActivity().findViewById(R.id.relativeLayout1);
        tabLayout1 = getActivity().findViewById(R.id.tabLayout1);
        tableLayout1 = getActivity().findViewById(R.id.tableLayout1);
        tableLayout2 = getActivity().findViewById(R.id.tableLayout2);
        tableLayout3 = getActivity().findViewById(R.id.tableLayout3);
        horizontalScrollView4 = getActivity().findViewById(R.id.horizontalScrollView4);
        tableLayout4 = getActivity().findViewById(R.id.tableLayout4);
        tableLayout5 = getActivity().findViewById(R.id.tableLayout5);
        scrollView3 = getActivity().findViewById(R.id.scrollView3);
        scrollView4 = getActivity().findViewById(R.id.scrollView4);
        scrollView5 = getActivity().findViewById(R.id.scrollView5);
        horizontalScrollView6 = getActivity().findViewById(R.id.horizontalScrollView6);
    }

    // Статический метод для добавления вкладок в TabLayout
    public static void addTabLayout(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("23ИВ16з\n" + "Информационные системы "), 0);
        tabLayout.addTab(tabLayout.newTab().setText("23ИВ16з\n" + "Технология ПО "), 1);
        tabLayout.addTab(tabLayout.newTab().setText("23ИВ16з\n" + "Проектир моб. прил. "), 2);
    }

    // Загрузка данных таблиц в зависимости от выбранной вкладки
    public void loadTable(int i1) {

        tlpF3 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

        // Вызов методов для создания и заполнения таблиц
        createTable1();
        createTable2();
        createTable3();
        createTable4();
        createTable5();
    }

    // Создание и заполнение TableLayout1
    private void createTable1() {
        tv1 = new TextView[1][3];
        tableRow1 = new TableRow[1];
        tableRow1[0] = new TableRow(getActivity());
        tableRow1[0].setPadding(1, 1, 1, 1);
        tableRow1[0].setLayoutParams(tlpF3);

        int i1 = 0;
        while (i1 < 3) {
            tv1[0][i1] = new TextView(getActivity());
            tv1[0][i1].setTextSize((float) 14);
            tv1[0][i1].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            if (i1 == 0) {
                tv1[0][i1].setText(" ");
                tv1[0][i1].setWidth(65);
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
            } else if (i1 == 1) {
                tv1[0][i1].setText(" Дисциплина ");
                tv1[0][i1].setWidth(240);
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueMidCyan));
            } else {
                tv1[0][i1].setText(" Проектирование мобильных приложений ");
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
            }
            tableRow1[0].addView(tv1[0][i1], i1);

            i1++;
        }

        tableLayout1.addView(tableRow1[0], 0);
    }

    // Создание и заполнение TableLayout2
    private void createTable2() {
        tableRow2 = new TableRow[2];
        tv2 = new TextView[2][6];
        tableLayout2.setColumnStretchable(2, true);

        int i1 = 0;
        while (i1 < 2) {
            tableRow2[i1] = new TableRow(getActivity());
            tableRow2[i1].setPadding(1, 1, 1, 1);
            tableRow2[i1].setLayoutParams(tlpF3);

            int j1 = 0;
            while (j1 < 6) {
                tv2[i1][j1] = new TextView(getActivity());
                tv2[i1][j1].setTextSize((float) 14);
                tv2[i1][j1].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                if (i1 == 0) {
                    if (j1 == 0) {
                        tv2[i1][j1].setText("№");
                        tv2[i1][j1].setWidth(55);
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                    } else if (j1 == 1) {
                        tv2[i1][j1].setText("Ф И О");
                        tv2[i1][j1].setWidth(240);
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueMidCyan));
                    } else if (j1 == 2) {
                        tv2[i1][j1].setText(" ");
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                    } else if (j1 == 3) {
                        tv2[i1][j1].setText("Зач");
                        tv2[i1][j1].setWidth(70);
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueMidCyan));
                    } else if (j1 == 4) {
                        tv2[i1][j1].setText("КП");
                        tv2[i1][j1].setWidth(70);
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                    } else {
                        tv2[i1][j1].setText("Экз");
                        tv2[i1][j1].setWidth(70);
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueMidCyan));
                    }
                } else {
                    tv2[i1][j1].setText(" ");
                    tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                }

                tableRow2[i1].addView(tv2[i1][j1], j1);
                j1++;
            }
            tableLayout2.addView(tableRow2[i1], i1);
            i1++;
        }
    }

    // Создание и заполнение TableLayout3
    private void createTable3() {
        tableRow3 = new TableRow[20];
        tv3 = new TextView[20][2];
        int rowIndex = 0;
        while (rowIndex < 20) {
            tableRow3[rowIndex] = new TableRow(getActivity());
            tableRow3[rowIndex].setPadding(1, 1, 1, 1);
            tableRow3[rowIndex].setLayoutParams(tlpF3);
            int colIndex = 0;
            while (colIndex < 2) {
                tv3[rowIndex][colIndex] = new TextView(getActivity());
                tv3[rowIndex][colIndex].setTextSize(14);
                tv3[rowIndex][colIndex].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                if (colIndex == 0) {
                    tv3[rowIndex][colIndex].setText(rowIndex + 1 + "\n" + "\n");
                    tv3[rowIndex][colIndex].setWidth(70);
                    tv3[rowIndex][colIndex].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                } else if (colIndex == 1) {
                    tv3[rowIndex][colIndex].setText("Фамилия" + "\n" + "Имя" + "\n" + "Отчество " + (rowIndex + 1));
                    tv3[rowIndex][colIndex].setWidth(240);
                    tv3[rowIndex][colIndex].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
                tableRow3[rowIndex].addView(tv3[rowIndex][colIndex], colIndex);

                colIndex++;
            }
            tableLayout3.addView(tableRow3[rowIndex], rowIndex);
            rowIndex++;
        }
    }

    // Создание и заполнение TableLayout4
    private void createTable4() {
        tableRow4 = new TableRow[20];
        tv4 = new TextView[20][20];

        int rowIndex4 = 0;
        while (rowIndex4 < 20) {
            tableRow4[rowIndex4] = new TableRow(getActivity());
            tableRow4[rowIndex4].setPadding(1, 1, 1, 1);
            tableRow4[rowIndex4].setLayoutParams(tlpF3);

            int colIndex4 = 0;
            while (colIndex4 < 20) {
                tv4[rowIndex4][colIndex4] = new TextView(getActivity());
                tv4[rowIndex4][colIndex4].setTextSize((float) 14);
                tv4[rowIndex4][colIndex4].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                if ((colIndex4 % 2) != 0) {
                    tv4[rowIndex4][colIndex4].setText(rowIndex4 + 1 + "\n" + "\n");
                    tv4[rowIndex4][colIndex4].setWidth(100);
                    tv4[rowIndex4][colIndex4].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                } else {
                    tv4[rowIndex4][colIndex4].setText(rowIndex4 + 1 + "\n" + "\n");
                    tv4[rowIndex4][colIndex4].setWidth(100);
                    tv4[rowIndex4][colIndex4].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blueLitCyan));
                }
                tableRow4[rowIndex4].addView(tv4[rowIndex4][colIndex4], colIndex4);

                colIndex4++;
            }
            tableLayout4.addView(tableRow4[rowIndex4], rowIndex4);

            rowIndex4++;
        }
    }

    // Создание и заполнение TableLayout5
    private void createTable5() {
        tableRow5 = new TableRow[20];
        tv5 = new TextView[20][3];

        int i1 = 0;
        while (i1 < 20) {
            tableRow5[i1] = new TableRow(getActivity());
            tableRow5[i1].setPadding(1, 1, 1, 1);
            tableRow5[i1].setLayoutParams(tlpF3);

            int j1 = 0;
            while (j1 < 3) {
                tv5[i1][j1] = new TextView(getActivity());
                tv5[i1][j1].setTextSize((float) 14);
                tv5[i1][j1].setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                if ((j1 % 2) != 0) {
                    tv5[i1][j1].setText(i1 + 1 + "\n" + "\n");
                    tv5[i1][j1].setWidth(70);
                    tv5[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colBlueLitCyan));
                } else {
                    tv5[i1][j1].setText(j1 + 1 + "\n" + "\n");
                    tv5[i1][j1].setWidth(70);
                    tv5[i1][j1].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colBlueMidCyan));
                }

                tableRow5[i1].addView(tv5[i1][j1], j1);
                j1++;
            }

            tableLayout5.addView(tableRow5[i1], i1);

            i1++;
        }
    }

    // Удаление данных из всех таблиц
    public void deleteTableData() {
        tableLayout1.removeAllViews();
        tableLayout2.removeAllViews();
        tableLayout3.removeAllViews();
        tableLayout4.removeAllViews();
        tableLayout5.removeAllViews();
        horizontalScrollView6.removeAllViews();
    }

    // Интерфейс для взаимодействия с активностью-слушателем прокрутки
    public interface HomeFragmentListener {
        void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY);
    }

    // Присоединение фрагмента к активности и инициализация слушателей
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            homeFragmentScrollview3 = (HomeFragmentListener) context;
            homeFragmentScrollview4 = (HomeFragmentListener) context;
            homeFragmentScrollview5 = (HomeFragmentListener) context;
            homeFragmentHorScroll4 = (HomeFragmentListener) context;
            homeFragmentHorScroll6 = (HomeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnHomeFragmentListener");
        }
    }

    // В функции createPage класса HomeFragment выполняется удаление визуальных компонентов предыдущей вкладки и создание визуальных компонентов текущей вкладки:
    public static void createPage(Context hContext, int NPL, String BaseName, int NumPage) {
        if (tableLayout1 != null) {
            tableLayout1.removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout2 != null) {
            tableLayout2.removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout3 != null) {
            tableLayout3.removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout4 != null) {
            tableLayout4.removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout5 != null) {
            tableLayout5.removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }
        if (tableLayout6 != null) {
            tableLayout6.removeAllViews(); // Удаление компонентов из таблицы tableLayout1
        }

        // Запрос в базу и извлечение данных в cursor
        JBaseName = BaseName; // Название таблицы базы данных
        db = DBHandler.database;
        cursor = db.query(BaseName, null, null, null, null, null, null);
        NumTab = NPL; // Сохранение номера вкладки
        NumRec = cursor.getCount(); // Количество строк (записей) таблицы
        NumCol = cursor.getColumnCount(); // Количество колонок (полей) таблицы

        tableRow1 = new TableRow[1]; // Создание массива строк tablRow1
        tableRow2 = new TableRow[2]; // Создание массива строк tablRow2
        tableRow3 = new TableRow[NumRec]; // Создание массива строк tablRow3
        tableRow4 = new TableRow[NumRec]; // Создание массива строк tablRow4
        tableRow5 = new TableRow[NumRec]; // Создание массива строк tablRow5
        tableRow6 = new TableRow[1]; // Создание массива строк tablRow6
        tv1 = new TextView[0][3]; // Создание массива текстовых меток tv1
        tv2 = new TextView[2][6]; // Создание массива текстовых меток tv2
        tv3 = new TextView[NumRec][2]; // Создание массива текстовых меток tv3
        tv4 = new TextView[NumRec][NumCol]; // Создание массива текстовых меток tv4
        tv5 = new TextView[NumRec][3]; // Создание массива текстовых меток tv5
        tv6 = new TextView[0][NumRec]; // Создание массива текстовых меток tv6

        tableLayout1.setColumnStretchable(2, true);
        tableLayout2.setColumnStretchable(2, true);

        tlpF3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow1[0] = new TableRow(hContext);
        tableRow1[0].setPadding(1, 1, 1, 1);
        tableRow1[0].setLayoutParams(tlpF3);
        i1 = 0;
        while (i1 < 3)
        {
            tv1[0][i1] = new TextView(hContext);
            tv1[0][i1].setTextSize((float) 14);
            tv1[0][i1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
            if (i1 == 0)
            {
                tv1[0][i1].setText(" ");
                tv1[0][i1].setWidth(35);
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));
            }
            if (i1 == 1)
            {
                tv1[0][i1].setText(" Дисциплина ");
                tv1[0][i1].setWidth(WidFIO);
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
            }
            if (i1 == 2) {
                tv1[0][i1].setText(" Проектирование мобильных приложений ");
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.white));
            }
            tableRow1[0].addView(tv1[0][i1], i1);
            i1++;
        }
        ((TableLayout) tableLayout1).addView(tableRow1[0], 0);

        i1 = 0;
        while (i1 < 2)
        {
            tableRow2[i1] = new TableRow(hContext);
            tableRow2[i1].setPadding(1, 1, 1, 1);
            tableRow2[i1].setLayoutParams(tlpF3);
            j1 = 0;
            while (j1 < 6)
            {
                tv2[i1][j1] = new TextView(hContext);
                tv2[i1][j1].setText(" ");
                tv2[i1][j1].setTextSize((float) 14);
                tv2[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
                if (i1 == 0)
                {
                    if (j1 == 0) // Если первая ячейка строки tablRow2
                    {
                        tv2[i1][j1].setText("№");
                        tv2[i1][j1].setWidth(35);
                        // Установка минимальной ширины tv2
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));
                        // Установка цвета фона текущего TextView
                    }
                    // Конец условия if (j1 == 0)
                    if (j1 == 1) // Если вторая ячейка строки tablRow2
                    {
                        tv2[i1][j1].setText(" Ф И О");
                        tv2[i1][j1].setWidth(WidFIO);
                        // Установка минимальной ширины tv2
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
                        // Установка цвета фона текущего TextView
                    }
                    // Конец условия if (j1 == 1)
                    if (j1 == 2) // Если третья ячейка строки tablRow2
                    {
                        tv2[i1][j1].setText(" ");
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
                        // Установка цвета фона текущего TextView
                    }
                    // Конец условия if (j1 == 2)
                    if (j1 == 3) // Если четвёртая ячейка строки tablRow2
                    {
                        tv2[i1][j1].setText("Зач");
                        tv2[i1][j1].setWidth(55);
                        // Установка минимальной ширины tv2
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
                        // Установка цвета фона текущего TextView
                    }
                    // Конец условия if (j1 == 3)
                    if (j1 == 4) // Если пятая ячейка строки tablRow2
                    {
                        tv2[i1][j1].setText("КП");
                        tv2[i1][j1].setWidth(55);
                        // Установка минимальной ширины tv2
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.purple_200));
                        // Установка цвета фона текущего TextView
                    }
                    // Конец условия if (j1 == 4)
                    if (j1 == 5) // Если шестая ячейка строки tablRow2
                    {
                        tv2[i1][j1].setText("Экз");
                        tv2[i1][j1].setWidth(55);
                        // Установка минимальной ширины tv2
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
                        // Установка цвета фона текущего TextView
                    }
                    // Конец условия if (j1 == 5)
                }
                tableRow2[i1].addView(tv2[i1][j1], j1);
                // Добавление TextView в строку tablRow2 с номером i1
                j1++;
                // Наращивание счётчика j1
            }
            // Конец цикла while (j1 < 6)
            if (i1 == 1) // Если вторая строка
            {
                if ((j1 % 2) != 0) // Если нечётная ячейка второй строки tablRow2
                {
                    tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
                    // Установка цвета фона текущего TextView
                }
                // Конец условия if ((j1 % 2) != 0)
                if ((j1 % 2) == 0) // Если чётная ячейка tablRow2
                {
                    tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));
                    // Установка цвета фона текущего TextView
                }
                // Конец условия if ((j1 % 2) == 0)
            }
            ((TableLayout) tableLayout2).addView(tableRow2[i1], i1);
            i1++;
        }

        // Загрузка данных в визуальные компоненты шестой таблицы:
        cursor.moveToFirst();
        // Установка курсора на первую позицию
        // Создание ячеек в tablRow6
        tableRow6[0] = new TableRow(hContext);
        // Создание первой строки в tablRow6 таблицы 6
        tableRow6[0].setPadding(1, 1, 1, 1);
        // Установка интервалов между компонентами типа TextView
        tableRow6[0].setLayoutParams(tlpF3);
        // Установка параметров строки tablRow6 таблицы 6
        j1 = 0;
        // Обнуление счётчика цикла по элементам строки таблицы
        while (j1 < NumCol - 6) // Цикл по элементам строки таблицы
        {
            tv6[0][j1] = new TextView(hContext);
            // Создание очередного TextView в текущей Activity
            tv6[0][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
            // Установка цвета текста текущего TextView
            tv6[0][j1].setTextSize((float) 14);
            // Установка размера шрифта
            tv6[0][j1].setText(cursor.getString(j1 + 2));
            // Ввод даты проведения занятия в текущий TextView
            tv6[0][j1].setWidth(100);
            // Установка минимальной ширины tv6
            if ((j1 % 2) != 0) // Если нечётная ячейка строки tablRow6
            {
                tv6[0][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.purple_200));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if ((j1 % 2) != 0)
            if ((j1 % 2) == 0) // Если нечётная ячейка строки tablRow6
            {
                tv6[0][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if ((j1 % 2) == 0)
            tableRow6[0].addView(tv6[0][j1], j1);
            // Добавление TextView в строку tablRow6 с номером i1
            j1++;
            // Наращивание счётчика j1
        }
        // Конец цикла while (j1 < NumCol - 5)
        ((TableLayout) tableLayout6).addView(tableRow6[0], 0);
        // Добавление строки tablRow1 в таблицу 1

        // Загрузка данных в визуальные компоненты третьей таблицы:
        cursor.moveToFirst();
        i1 = 0;
        // Обнуление счётчика цикла по записям таблицы
        while (i1 < cursor.getCount() - 1) // Цикл по записям таблицы
        {
            tableRow3[i1] = new TableRow(hContext);
            tableRow3[i1].setPadding(1, 1, 1, 1);
            tableRow3[i1].setLayoutParams(tlpF3);
            tableRow4[i1] = new TableRow(hContext);
            tableRow4[i1].setPadding(1, 1, 1, 1);
            tableRow4[i1].setLayoutParams(tlpF3);
            tableRow5[i1] = new TableRow(hContext);
            tableRow5[i1].setPadding(1, 1, 1, 1);
            tableRow5[i1].setLayoutParams(tlpF3);
            // Создание ячеек в tablRow3
            j1 = 0;
            // Обнуление счётчика цикла по элементам строки таблицы
            while (j1 < 2) // Цикл по элементам строки таблицы
            {
                tv3[i1][j1] = new TextView(hContext);
                // Создание очередного TextView в текущей Activity
                tv3[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
                // Установка цвета текста текущего TextView
                tv3[i1][j1].setTextSize((float) 14);
                // Установка размера шрифта
                if (j1 == 0) // Если первая ячейка
                {
                    tv3[i1][j1].setText(cursor.getString(j1) + "\n" + "\n");
                    // Ввод номера строки в текущий TextView
                    tv3[i1][j1].setWidth(35);
                    // Установка минимальной ширины tv3
                    tv3[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));
                    // Установка цвета фона текущего TextView
                }
                // Конец условия if (j1 == 0)
                if (j1 == 1) // Если вторая ячейка
                {
                    tv3[i1][j1].setText(cursor.getString(j1));
                    // Ввод текста в текущий TextView
                    tv3[i1][j1].setWidth(WidFIO);
                    // Установка минимальной ширины tv3
                }
                if (NPL < 10) // Если номер фрагмента меньше 10 (состоит из одного знака)
                {
                    MainActivity.Str01 = "0" + String.valueOf(NPL);
                    // В строке Str01 перед номером фрагмента устанавливается 0
                }
                // Конец условия if (NPL < 10)
                if (NPL >= 10) // Если номер фрагмента больше или равен 10 (состоит из двух знаков)
                {
                    MainActivity.Str01 = String.valueOf(NPL);
                    // В строке Str01 перед номером фрагмента 0 не устанавливается
                }
                // Конец условия if (NPL >= 10)
                if (i1 < 10) // Если номер колонки в таблице меньше 10 (состоит из одного знака)
                {
                    MainActivity.Str02 = "0" + String.valueOf(i1);
                    // В строке Str04 перед номером строки устанавливается 0
                }
                // Конец условия if (j1 < 10)
                if (i1 >= 10) // Если номер колонки больше или равен 10 (состоит из двух знаков)
                {
                    MainActivity.Str02 = String.valueOf(i1);
                    // В строке Str04 перед номером строки 0 не устанавливается
                }
                // Конец условия if (j1 >= 10)
                if (j1 < 10) // Если номер колонки в таблице меньше 10 (состоит из одного знака)
                {
                    MainActivity.Str03 = "0" + String.valueOf(j1);
                    // В строке Str04 перед номером строки устанавливается 0
                }
                // Конец условия if (j1 < 10)
                if (j1 >= 10) // Если номер колонки больше или равен 10 (состоит из двух знаков)
                {
                    MainActivity.Str03 = String.valueOf(j1);
                    // В строке Str04 перед номером строки 0 не устанавливается
                }
                // Конец условия if (j1 >= 10)
                MainActivity.Str04 = "1" + MainActivity.Str01 + MainActivity.Str02 + MainActivity.Str03;
                // В строке Str05 формируется номер ID компонента tv3[NPL][i1][j1]
                tv3[i1][j1].setId(Integer.parseInt(MainActivity.Str04));
                // Создание ID компонента tv3[NPL][i1][j1]
                tv3[i1][j1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                tv3[i1][j1].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        EditText editText1 = new EditText(hContext);
                        // Создание строки редактирования в текущей Activity
                        idNPLI = Integer.parseInt(String.valueOf(v.getId()).substring(1, 3));
                        // Номер вкладки (таблицы)
                        idRowI = Integer.parseInt(String.valueOf(v.getId()).substring(3, 5));
                        // Номер строки в таблице
                        idColI = Integer.parseInt(String.valueOf(v.getId()).substring(5, 7));
                        // Номер колонки в таблице
                        editText1.setText(tv3[idRowI][idColI].getText());
                        // Занесение текста из ячейки в строку редактирования
                        AlertDialog.Builder dialog = new AlertDialog.Builder(hContext);
                        dialog.setTitle("Изменение данных");
                        dialog.setView(editText1);
                        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Функция нажатия на кнопку ОК
                                ContentValues contentValues = new ContentValues();
                                contentValues.clear();
                                // Очистка объекта contentValues
                                contentValues.put(MainActivity.TFIO, String.valueOf(editText1.getText()));
                                // Создание записи для изменения ФИО обучаемого
                                // Изменение записи в базе данных
                                db.update(BaseName, contentValues, MainActivity.T_id_FIO + " = ?", new String[]{String.valueOf(idRowI + 1)});
                                updatePage(hContext, idNPLI, BaseName, idNPLI);
                                // Функция изменения страницы
                            }
                        });
                        dialog.setNegativeButton(R.string.cancel, null);
                        // Обработка нажатия на кнопку Cancel
                        dialog.create();
                        // Создание диалоговой панели
                        dialog.show();
                        // Демонстрация диалоговой панели
                        return false;
                    }
                });
                HigCell = tv3[i1][j1].getLineHeight();
                // Получение высоты ячейки
                tv3[i1][j1].setMinimumHeight((int) (HigCell * MulCell * CouCell));
                // Установка высоты tv3
                tv3[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.white));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if (j1 == 1)
            tableRow3[i1].addView(tv3[i1][j1], j1);
            // Добавление TextView в строку tablRow3 с номером i1
            j1++;
            // Наращивание счётчика j1
        }
        // Конец цикла while (j1 < 2)

        // Загрузка данных в визуальные компоненты четвёртой таблицы:
        // Создание ячеек в tablRow4
        j1 = 0;
        // Обнуление счётчика цикла по элементам строки таблицы
        while (j1 < NumCol - 6) // Цикл по элементам строки таблицы
        {
            tv4[i1][j1] = new TextView(hContext);
            // Создание очередного TextView в текущей Activity
            tv4[i1][j1].setPadding(1, 1, 1, 1);
            // Создание очередного TextView в текущей Activity
            tv4[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
            // Установка цвета текста текущего TextView
            tv4[i1][j1].setTextSize((float) 14);
            // Установка размера шрифта
            tv4[i1][j1].setText(cursor.getString(j1 + 2));
            // Ввод номера строки в текущий TextView
            tv4[i1][j1].setWidth(100);
            // Установка минимальной ширины tv4
            tv4[i1][j1].setMinimumHeight((int) (HigCell * MulCell * CouCell));
            // Установка высоты tv4
            if ((j1 % 2) != 0) // Если нечётная ячейка строки tablRow4
            {
                tv4[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBAquamarine));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if ((j1 % 2) != 0)
            if ((j1 % 2) == 0) // Если нечётная ячейка строки tablRow4
            {
                tv4[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueTurquoi));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if ((j1 % 2) == 0)
            tableRow4[i1].addView(tv4[i1][j1], j1);
            // Добавление TextView в строку tablRow4 с номером i1
            j1++;
            // Наращивание счётчика j1
        }
        // Конец цикла while (j1 < NumCol - 5)

        // Загрузка данных в визуальные компоненты пятой таблицы:
        // Создание ячеек в tablRow5
        j1 = 0;
        // Обнуление счётчика цикла по элементам строки таблицы
        while (j1 < 3) // Цикл по элементам строки таблицы
        {
            tv5[i1][j1] = new TextView(hContext);
            // Создание очередного TextView в текущей Activity
            tv5[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
            // Установка цвета текста текущего TextView
            tv5[i1][j1].setTextSize((float) 14);
            // Установка размера шрифта
            tv5[i1][j1].setText(cursor.getString(NumCol - 6 + 2 + j1) + "\n");
            // Ввод номера строки в текущий TextView
            tv5[i1][j1].setWidth(55);
            // Установка минимальной ширины tv5
            tv5[i1][j1].setMinimumHeight((int) (HigCell * MulCell * CouCell));
            // Установка высоты tv5
            if ((j1 % 2) != 0) // Если нечётная ячейка строки tablRow5
            {
                tv5[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueAqua));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if ((j1 % 2) != 0)
            if ((j1 % 2) == 0) // Если нечётная ячейка строки tablRow5
            {
                tv5[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueTurquoi));
                // Установка цвета фона текущего TextView
            }
            // Конец условия if ((j1 % 2) == 0)
            tableRow5[i1].addView(tv5[i1][j1], j1);
            // Добавление TextView в строку tablRow5 с номером i1
            j1++;
            // Наращивание счётчика j1
        }
        // Конец цикла while (j1 < 3)
        ((TableLayout) tableLayout3).addView(tableRow3[i1], i1);
        // Добавление строки tablRow1 в таблицу 1
        ((TableLayout) tableLayout4).addView(tableRow4[i1], i1);
        // Добавление строки tablRow1 в таблицу 1
        ((TableLayout) tableLayout5).addView(tableRow5[i1], i1);
        // Добавление строки tablRow1 в таблицу 1
        cursor.moveToNext();
        // Перемещение курсора на следующую запись
        i1++;
        // Наращивание счётчика i1
    }

    public static void updatePage(Context hContext, int NPL, String BaseName, int NumPage) {
        createPage(hContext, NPL, BaseName, NumPage);
    }
}
