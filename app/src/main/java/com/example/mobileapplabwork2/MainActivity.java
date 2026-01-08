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
            Str06 = "Главная таблица " + String.valueOf(JMainTabl) + " повреждена или отсутствует. Удалите базу данных и создайте её заново!";
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

        // Обработка нажатия на пункт меню с id R.id.menu_11
        if (item.getItemId() == R.id.menu_11) {
            Toast.makeText(MainActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
            // Вызов функции добавления вкладок в HomeFragment (статический метод)
            HomeFragment.addTabLayout(findViewById(R.id.tabLayout1));
        }

        return super.onOptionsItemSelected(item);
    }

    // Методы слушателя для синхронизации прокрутки ScrollView из HomeFragment
    @Override
    public void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY) {
        // Синхронизация прокрутки ScrollView3 с ScrollView4 и ScrollView5
        if (HomeFragment.scrollView3.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView4).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView5).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY) {
        // Синхронизация прокрутки ScrollView4 с ScrollView3 и ScrollView5
        if (HomeFragment.scrollView4.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView3).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView5).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY) {
        // Синхронизация прокрутки ScrollView5 с ScrollView3 и ScrollView4
        if (HomeFragment.scrollView5.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView3).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView4).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY) {
        // Синхронизация горизонтальной прокрутки HorizontalScrollView4 с HorizontalScrollView6
        if (HomeFragment.horizontalScrollView4.isShown()) {
            if (ScrollDir == 1) {
                findViewById(R.id.horizontalScrollView6).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY) {
        // Синхронизация горизонтальной прокрутки HorizontalScrollView6 с HorizontalScrollView4
        if (HomeFragment.horizontalScrollView6.isShown()) {
            if (ScrollDir == 1) {
                findViewById(R.id.horizontalScrollView4).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void JournalFragmentCreate(int num, String name, int page, int learn, int students) {
        if (num == 1) {
            JBaseName = name;
            JNumPage = page;
            JNumLearn = learn;
            JNumStudents = students;
        }

        this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, this.HomeFragment).commit();
        this.setTitle("Home");
    }
}