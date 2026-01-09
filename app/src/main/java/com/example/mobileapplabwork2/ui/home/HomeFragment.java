package com.example.mobileapplabwork2.ui.home;

import android.content.ContentValues;
import android.content.Context;
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

    public static int i1, j1, e1, idRowI, idColI, idNPLI, HigCell, CouCell = 3, NumRec, NumCol, NumTab, WidFIO = 220;
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


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        relativeLayout1 = root.findViewById(R.id.relativeLayout1);
        tabLayout1 = root.findViewById(R.id.tabLayout1);
        tableLayout1 = root.findViewById(R.id.tableLayout1);
        tableLayout2 = root.findViewById(R.id.tableLayout2);
        tableLayout3 = root.findViewById(R.id.tableLayout3);
        horizontalScrollView4 = root.findViewById(R.id.horizontalScrollView4);
        tableLayout4 = root.findViewById(R.id.tableLayout4);
        tableLayout5 = root.findViewById(R.id.tableLayout5);
        tableLayout6 = root.findViewById(R.id.tableLayout6);
        scrollView3 = root.findViewById(R.id.scrollView3);
        scrollView4 = root.findViewById(R.id.scrollView4);
        scrollView5 = root.findViewById(R.id.scrollView5);
        horizontalScrollView6 = root.findViewById(R.id.horizontalScrollView6);


        tabLayout1.setTabIndicatorFullWidth(true);
        tabLayout1.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                i1 = tab.getPosition();
                Toast.makeText(getActivity(), "onTabSelected " + i1, Toast.LENGTH_LONG).show();

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


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
        tableLayout6 = getActivity().findViewById(R.id.tableLayout6);
        scrollView3 = getActivity().findViewById(R.id.scrollView3);
        scrollView4 = getActivity().findViewById(R.id.scrollView4);
        scrollView5 = getActivity().findViewById(R.id.scrollView5);
        horizontalScrollView6 = getActivity().findViewById(R.id.horizontalScrollView6);
    }


    public static void addTabLayout(TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText("23ИВ16з\n" + "Информационные системы "), 0);
        tabLayout.addTab(tabLayout.newTab().setText("23ИВ16з\n" + "Технология ПО "), 1);
        tabLayout.addTab(tabLayout.newTab().setText("23ИВ16з\n" + "Проектир моб. прил. "), 2);
    }


    public void loadTable(int i1) {

        tlpF3 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);


        createTable1();
        createTable2();
        createTable3();
        createTable4();
        createTable5();
    }


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


    public void deleteTableData() {
        tableLayout1.removeAllViews();
        tableLayout2.removeAllViews();
        tableLayout3.removeAllViews();
        tableLayout4.removeAllViews();
        tableLayout5.removeAllViews();
        tableLayout6.removeAllViews();
        horizontalScrollView6.removeAllViews();
    }


    public interface HomeFragmentListener {
        void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY);

        void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY);
    }


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

    public static void createPage(Context hContext, int NPL, String BaseName, int NumPage) {
        if (tableLayout1 != null) {
            tableLayout1.removeAllViews();
        }
        if (tableLayout2 != null) {
            tableLayout2.removeAllViews();
        }
        if (tableLayout3 != null) {
            tableLayout3.removeAllViews();
        }
        if (tableLayout4 != null) {
            tableLayout4.removeAllViews();
        }
        if (tableLayout5 != null) {
            tableLayout5.removeAllViews();
        }
        if (tableLayout6 != null) {
            tableLayout6.removeAllViews();
        }

        JBaseName = BaseName;
        db = DBHandler.database;
        cursor = db.query(BaseName, null, null, null, null, null, null);
        NumTab = NPL;
        NumRec = cursor.getCount();
        NumCol = cursor.getColumnCount();

        tableRow1 = new TableRow[1];
        tableRow2 = new TableRow[2];
        tableRow3 = new TableRow[NumRec];
        tableRow4 = new TableRow[NumRec];
        tableRow5 = new TableRow[NumRec];
        tableRow6 = new TableRow[1];
        tv1 = new TextView[1][3];
        tv2 = new TextView[2][6];
        tv3 = new TextView[NumRec][2];
        tv4 = new TextView[NumRec][NumCol];
        tv5 = new TextView[NumRec][3];
        tv6 = new TextView[1][NumRec];

        tableLayout1.setColumnStretchable(2, true);
        tableLayout2.setColumnStretchable(2, true);

        tlpF3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRow1[0] = new TableRow(hContext);
        tableRow1[0].setPadding(1, 1, 1, 1);
        tableRow1[0].setLayoutParams(tlpF3);
        i1 = 0;
        while (i1 < 3) {
            tv1[0][i1] = new TextView(hContext);
            tv1[0][i1].setTextSize((float) 14);
            tv1[0][i1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
            if (i1 == 0) {
                tv1[0][i1].setText(" ");
                tv1[0][i1].setWidth(35);
                tv1[0][i1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));
            }
            if (i1 == 1) {
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
        tableLayout1.addView(tableRow1[0], 0);

        i1 = 0;
        while (i1 < 2) {
            tableRow2[i1] = new TableRow(hContext);
            tableRow2[i1].setPadding(1, 1, 1, 1);
            tableRow2[i1].setLayoutParams(tlpF3);
            j1 = 0;
            while (j1 < 6) {
                tv2[i1][j1] = new TextView(hContext);
                tv2[i1][j1].setText(" ");
                tv2[i1][j1].setTextSize((float) 14);
                tv2[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
                if (i1 == 0) {
                    if (j1 == 0) {
                        tv2[i1][j1].setText("№");
                        tv2[i1][j1].setWidth(35);

                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));

                    }

                    if (j1 == 1) {
                        tv2[i1][j1].setText(" Ф И О");
                        tv2[i1][j1].setWidth(WidFIO);

                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));

                    }

                    if (j1 == 2) {
                        tv2[i1][j1].setText(" ");
                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));

                    }

                    if (j1 == 3) {
                        tv2[i1][j1].setText("Зач");
                        tv2[i1][j1].setWidth(55);

                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));

                    }

                    if (j1 == 4) {
                        tv2[i1][j1].setText("КП");
                        tv2[i1][j1].setWidth(55);

                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.purple_200));

                    }

                    if (j1 == 5) {
                        tv2[i1][j1].setText("Экз");
                        tv2[i1][j1].setWidth(55);

                        tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));

                    }

                }
                tableRow2[i1].addView(tv2[i1][j1], j1);

                j1++;

            }

            j1 = 5;
            if (i1 == 1) {
                if ((j1 % 2) != 0) {
                    tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));

                }

                if ((j1 % 2) == 0) {
                    tv2[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));

                }
            }
            tableLayout2.addView(tableRow2[i1], i1);
            i1++;
        }

        cursor.moveToFirst();

        tableRow6[0] = new TableRow(hContext);
        tableRow6[0].setPadding(1, 1, 1, 1);
        tableRow6[0].setLayoutParams(tlpF3);
        j1 = 0;
        while (j1 < NumCol - 6) {
            tv6[0][j1] = new TextView(hContext);

            tv6[0][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));

            tv6[0][j1].setTextSize((float) 14);

            tv6[0][j1].setText(cursor.getString(j1 + 2));

            tv6[0][j1].setWidth(100);

            if ((j1 % 2) != 0) {
                tv6[0][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.purple_200));

            }

            if ((j1 % 2) == 0) {
                tv6[0][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueMidCyan));

            }

            tableRow6[0].addView(tv6[0][j1], j1);

            j1++;

        }

        tableLayout6.addView(tableRow6[0], 0);
        cursor.moveToFirst();
        i1 = 0;
        while (i1 < cursor.getCount() - 1) {
            tableRow3[i1] = new TableRow(hContext);
            tableRow3[i1].setPadding(1, 1, 1, 1);
            tableRow3[i1].setLayoutParams(tlpF3);
            tableRow4[i1] = new TableRow(hContext);
            tableRow4[i1].setPadding(1, 1, 1, 1);
            tableRow4[i1].setLayoutParams(tlpF3);
            tableRow5[i1] = new TableRow(hContext);
            tableRow5[i1].setPadding(1, 1, 1, 1);
            tableRow5[i1].setLayoutParams(tlpF3);

            j1 = 0;
            while (j1 < 2) {
                tv3[i1][j1] = new TextView(hContext);
                tv3[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
                tv3[i1][j1].setTextSize((float) 14);

                if (j1 == 0) {
                    tv3[i1][j1].setText(cursor.getString(j1) + "\n" + "\n");

                    tv3[i1][j1].setWidth(35);

                    tv3[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueLitCyan));

                }

                if (j1 == 1) {
                    tv3[i1][j1].setText(cursor.getString(j1));

                    tv3[i1][j1].setWidth(WidFIO);

                }
                if (NPL < 10) {
                    MainActivity.Str01 = "0" + NPL;

                }

                if (NPL >= 10) {
                    MainActivity.Str01 = String.valueOf(NPL);

                }

                if (i1 < 10) {
                    MainActivity.Str02 = "0" + i1;

                }

                if (i1 >= 10) {
                    MainActivity.Str02 = String.valueOf(i1);

                }

                if (j1 < 10) {
                    MainActivity.Str03 = "0" + j1;

                }

                if (j1 >= 10) {
                    MainActivity.Str03 = String.valueOf(j1);

                }

                MainActivity.Str04 = "1" + MainActivity.Str01 + MainActivity.Str02 + MainActivity.Str03;

                tv3[i1][j1].setId(Integer.parseInt(MainActivity.Str04));

                tv3[i1][j1].setOnClickListener(v -> {
                });
                tv3[i1][j1].setOnLongClickListener(v -> {
                    EditText editText1 = new EditText(hContext);

                    idNPLI = Integer.parseInt(String.valueOf(v.getId()).substring(1, 3));

                    idRowI = Integer.parseInt(String.valueOf(v.getId()).substring(3, 5));

                    idColI = Integer.parseInt(String.valueOf(v.getId()).substring(5, 7));

                    editText1.setText(tv3[idRowI][idColI].getText());

                    AlertDialog.Builder dialog = new AlertDialog.Builder(hContext);
                    dialog.setTitle("Изменение данных");
                    dialog.setView(editText1);
                    dialog.setPositiveButton(R.string.ok, (dialog1, which) -> {

                        ContentValues contentValues = new ContentValues();
                        contentValues.clear();

                        contentValues.put(MainActivity.TFIO, String.valueOf(editText1.getText()));


                        db.update(BaseName, contentValues, MainActivity.T_id_FIO + " = ?", new String[]{String.valueOf(idRowI + 1)});
                        updatePage(hContext, idNPLI, BaseName, idNPLI);

                    });
                    dialog.setNegativeButton(R.string.cancel, null);

                    dialog.create();

                    dialog.show();

                    return false;
                });
                HigCell = tv3[i1][j1].getLineHeight();
                tv3[i1][j1].setMinimumHeight((int) (HigCell * MulCell * CouCell));
                tv3[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.white));
                tableRow3[i1].addView(tv3[i1][j1], j1);
                j1++;
            }

            j1 = 0;
            while (j1 < NumCol - 6) {
                tv4[i1][j1] = new TextView(hContext);
                tv4[i1][j1].setPadding(1, 1, 1, 1);
                tv4[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));
                tv4[i1][j1].setTextSize((float) 14);
                tv4[i1][j1].setText(cursor.getString(j1 + 2));
                tv4[i1][j1].setWidth(100);
                tv4[i1][j1].setMinimumHeight((int) (HigCell * MulCell * CouCell));

                if ((j1 % 2) != 0) {
                    tv4[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBAquamarine));
                }

                if ((j1 % 2) == 0) {
                    tv4[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueTurquoi));
                }

                tableRow4[i1].addView(tv4[i1][j1], j1);
                j1++;
            }

            j1 = 0;
            while (j1 < 3) {
                tv5[i1][j1] = new TextView(hContext);

                tv5[i1][j1].setTextColor(ContextCompat.getColor(hContext, R.color.black));

                tv5[i1][j1].setTextSize((float) 14);

                tv5[i1][j1].setText(cursor.getString(NumCol - 6 + 2 + j1) + "\n");

                tv5[i1][j1].setWidth(55);

                tv5[i1][j1].setMinimumHeight((int) (HigCell * MulCell * CouCell));

                if ((j1 % 2) != 0) {
                    tv5[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueAqua));

                }

                if ((j1 % 2) == 0) {
                    tv5[i1][j1].setBackgroundColor(ContextCompat.getColor(hContext, R.color.colBlueTurquoi));

                }

                tableRow5[i1].addView(tv5[i1][j1], j1);

                j1++;
            }

            tableLayout3.addView(tableRow3[i1], i1);
            tableLayout4.addView(tableRow4[i1], i1);
            tableLayout5.addView(tableRow5[i1], i1);
            cursor.moveToNext();
            i1++;
        }
    }

    public static void updatePage(Context hContext, int NPL, String BaseName, int NumPage) {
        createPage(hContext, NPL, BaseName, NumPage);
    }
}
