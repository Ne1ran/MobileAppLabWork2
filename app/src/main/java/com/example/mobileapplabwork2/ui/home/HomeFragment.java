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
    public static HorizontalScrollView horizontalScrollView4;
    public static ScrollView scrollView3, scrollView4, scrollView5;
    public static TableRow.LayoutParams tlpF3;
    public static TableRow[] tableRow1, tableRow2, tableRow3, tableRow4, tableRow5;
    public static TextView[][] tv1, tv2, tv3, tv4, tv5;

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

}
