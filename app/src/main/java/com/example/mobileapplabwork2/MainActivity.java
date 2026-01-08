package com.example.mobileapplabwork2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobileapplabwork2.databinding.ActivityMainBinding;
import com.example.mobileapplabwork2.sql.DBHandler;
import com.example.mobileapplabwork2.ui.home.HomeFragment;
import com.example.mobileapplabwork2.ui.journal.JournalFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, JournalFragment.onJournalFragmentListener {

    public static int DatabaseVersion = 1;
    public static int i1, j1;
    public static String DatabaseName = "tbookdb.db",
            JMainTabl = "JMainTabl",
            T_idTabl = "_idTabl",
            TNameTab = "NameTab",
            T_id_FIO = "_idFIO_",
            TFIO = "FIO",
            TSertifZ = "SertifZ",
            TSertifK = "SertifK",
            TSertifE = "SertifE",
            TSertifD = "SertifD",
            Str01, Str02, Str03, Str04, Str05, Str06;

    public static String[] T_Atten;
    private AppBarConfiguration mAppBarConfiguration;

    public static String JBaseName;
    public static int JNumPage, JNumLearn, JNumStudents, FragmentStart = 0;
    public static File SDPath, SDFile;
    public static AlertDialog.Builder allDB;
    public HomeFragment HomeFragment;
    public DBHandler dbHelper;
    public SQLiteDatabase db;
    public ContentValues contentValues;
    public Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomeFragment = new HomeFragment();

        // Инициализация привязки представлений и установка содержимого
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Настройка панели действий (Toolbar)
        setSupportActionBar(binding.appBarMain.toolbar);
        // Настройка плавающей кнопки действия (FAB)
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Динамическое добавление пунктов в навигационное меню
        final Menu navMenu = navigationView.getMenu();
        for (int i = 1; i <= 6; i++) {
            int itemId = 1000 + i;
            MenuItem item = navMenu.add(0, itemId, 0, "Runtime item " + i);
            item.setOnMenuItemClickListener(item1 -> {
                Toast.makeText(MainActivity.this, String.valueOf(item1.getItemId()), Toast.LENGTH_LONG).show();
                return false;
            });
        }

        // Настройка навигационного компонента
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_journal, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Создание объекта dbHelper для работы с базой данных
        dbHelper = new DBHandler(MainActivity.this, DatabaseName, DBHandler.factory, DatabaseVersion);
        db = dbHelper.getReadableDatabase();
        contentValues = new ContentValues();
        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + JMainTabl + "'", null);
        if (cursor.getCount() == 0) {
            Str06 = "Главная таблица " + JMainTabl + " повреждена или отсутствует. Удалите базу данных и создайте её заново!";
            Toast.makeText(MainActivity.this, Str06, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Раздувание меню опций
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Обработка навигации вверх (Up navigation)
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка нажатий на пункты меню опций
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

//        if (item.getItemId() == R.id.menu_11) {
//            Toast.makeText(MainActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
//            // Вызов функции добавления вкладок в HomeFragment (статический метод)
//            com.example.mobileapplabwork2.ui.home.HomeFragment.addTabLayout(findViewById(R.id.tabLayout1));
//        }

        // Обработка нажатия на пункт меню с id R.id.menu_11
        if (item.getItemId() == R.id.menu_11) {
            cursor = db.query(JMainTabl, null, null, null, null, null, null);
            cursor.moveToFirst(); // Установка курсора на первую позицию
            HomeFragment.createPage(DBHandler.databaseContext, 0, cursor.getString(1), JNumPage);
        }

        return super.onOptionsItemSelected(item);
    }

    // Методы слушателя для синхронизации прокрутки ScrollView из HomeFragment
    @Override
    public void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY) {
        // Синхронизация прокрутки ScrollView3 с ScrollView4 и ScrollView5
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.scrollView3.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView4).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView5).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY) {
        // Синхронизация прокрутки ScrollView4 с ScrollView3 и ScrollView5
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.scrollView4.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView3).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView5).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY) {
        // Синхронизация прокрутки ScrollView5 с ScrollView3 и ScrollView4
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.scrollView5.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView3).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView4).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY) {
        // Синхронизация горизонтальной прокрутки HorizontalScrollView4 с HorizontalScrollView6
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.horizontalScrollView4.isShown()) {
            if (ScrollDir == 1) {
                findViewById(R.id.horizontalScrollView6).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY) {
        // Синхронизация горизонтальной прокрутки HorizontalScrollView6 с HorizontalScrollView4
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.horizontalScrollView6.isShown()) {
            if (ScrollDir == 1) {
                findViewById(R.id.horizontalScrollView4).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void JournalFragmentCreate(int num, String name, int page, int learn, int students) {
        // Если кнопка OK была нажата в фрагменте создания базы данных
        if (num == 1) {
            db = dbHelper.getWritableDatabase();
            
            // Удаление таблицы JMainTabl
            db.execSQL("drop table if exists '" + JMainTabl + "'");
            // Создание главной таблицы журнала
            Str01 = "create table if not exists "; // Строковая переменная "create table if not exists"
            Str02 = Str01 + JMainTabl + " ('" + T_idTabl + "' integer primary key, '" + TNameTab + "' text)";
            db.execSQL(Str02); // Создание и выполнение SQL команды

            // Вставка записей в первую строку главной таблицы журнала
            contentValues.clear(); // Очистка объекта contentValues
            contentValues.put(T_idTabl, "1"); // Создание записи для добавления номера строки
            contentValues.put(TNameTab, String.valueOf(name)); // Создание записи для добавления названий таблиц
            db.insert(JMainTabl, null, contentValues); // Вставка записи в таблицу

            // Создание таблиц журнала
            db.execSQL("drop table if exists '" + name + "'"); // Удаление таблицы BaseName
            T_Atten = new String[learn]; // Создание массива названий полей таблицы
            Str01 = "create table if not exists "; // Строковая переменная "create table if not exists"
            Str02 = Str01 + name + " ('" + T_id_FIO + "' integer primary key, '" + TFIO + "' text, "; // Создание SQL команды
            Str03 = ""; // Очистка строковой переменной Str03

            // Обнуление счётчика цикла по колонкам дат проведения занятий
            j1 = 0;
            // Цикл по датам проведения занятий
            while (j1 < learn) {
                // Создание названия поля таблицы
                T_Atten[j1] = "2024.02.12." + String.valueOf(j1 + 1);
                // Создание SQL команды
                Str03 = Str03 + T_Atten[j1] + "' text, '";
                // Наращивание счётчика j1
                j1++;
            }

            // Создание SQL команды
            Str04 = TSertifZ + "' text, '" + TSertifK + "' integer, '" + TSertifE + "' integer, '" + TSertifD + "' text)";
            Str05 = Str02 + Str03 + Str04;
            db.execSQL(Str05); // Выполнение SQL команды

            // Вставка записей в другие строки таблицы
            // Вставка первой строки (шапка/шаблон строки)
            contentValues.clear(); // Очистка объекта contentValues
            contentValues.put(T_id_FIO, "0"); // Создание записи для добавления номера строки
            contentValues.put(TFIO, "ФИО"); // Создание записи для добавления фамилий
            // Обнуление счётчика цикла по колонкам дат проведения занятий
            j1 = 0;
            // Цикл по датам проведения занятий
            while (j1 < learn) {
                // Создание записи для добавления дат проведения занятий
                contentValues.put(T_Atten[j1], String.valueOf(T_Atten[j1]));
                // Наращивание счётчика j1
                j1++;
            }
            contentValues.put(TSertifZ, "Z"); // Создание записи для добавления оценки по зачету
            contentValues.put(TSertifK, "0"); // Создание записи для добавления оценки по курсовому проекту
            contentValues.put(TSertifE, "0"); // Создание записи для добавления оценки по экзамену
            contentValues.put(TSertifD, "D"); // Создание записи для добавления даты аттестации
            db.insert(name, null, contentValues); // Вставка записи в таблицу

            // Вставка записей в другие строки таблицы
            // Обнуление счётчика цикла по записям таблицы
            i1 = 0;
            // Цикл по количеству студентов (по строкам таблицы)
            while (i1 < students) {
                contentValues.clear(); // Очистка объекта contentValues
                Str01 = String.valueOf(i1 + 1); // Строка с номером записи
                contentValues.put(T_id_FIO, String.valueOf(Str01)); // Создание записи для добавления номера строки
                // Создание записи для добавления ФИО
                contentValues.put(TFIO, "Фамилия" + "\n" + "Имя" + "\n" + "Отчество " + Str01);
                // Обнуление счётчика цикла по элементам записи таблицы
                j1 = 0;
                // Цикл по колонкам дат проведения занятий
                while (j1 < learn) {
                    // Создание записи для добавления дат проведения занятий
                    contentValues.put(T_Atten[j1], Str01 + "\n" + String.valueOf(j1 + 1));
                    // Наращивание счётчика j1
                    j1++;
                }
                contentValues.put(TSertifZ, "Z" + "\n" + Str01); // Создание записи для добавления оценки по зачету
                contentValues.put(TSertifK, "0" + "\n" + Str01); // Создание записи для добавления оценки по курсовому проекту
                contentValues.put(TSertifE, "0" + "\n" + Str01); // Создание записи для добавления оценки по экзамену
                contentValues.put(TSertifD, "D" + "\n" + Str01); // Создание записи для добавления даты аттестации
                db.insert(name, null, contentValues); // Вставка записи в таблицу
                // Наращивание счётчика записи i1
                i1++;
            }
            // Конец цикла while (i1 < NumStud)

            JBaseName = name;
            JNumPage = page;
            JNumLearn = learn;
            JNumStudents = students;
        }

        // Удаление фрагмента
        this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, this.HomeFragment).commit();
        // Установка заголовка фрагменту
        this.setTitle("Home");
    }
}