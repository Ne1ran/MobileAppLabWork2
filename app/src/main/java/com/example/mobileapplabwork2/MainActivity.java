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
    public HomeFragment homeFragment;
    public DBHandler dbHelper;
    public SQLiteDatabase db;
    public ContentValues contentValues;
    public Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeFragment = new HomeFragment();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        final Menu navMenu = navigationView.getMenu();
        for (int i = 1; i <= 6; i++) {
            int itemId = 1000 + i;
            MenuItem item = navMenu.add(0, itemId, 0, "Runtime item " + i);
            item.setOnMenuItemClickListener(item1 -> {
                Toast.makeText(MainActivity.this, String.valueOf(item1.getItemId()), Toast.LENGTH_LONG).show();
                return false;
            });
        }

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
        dbHelper = new DBHandler(MainActivity.this, DatabaseName, DBHandler.factory, DatabaseVersion);
        db = dbHelper.getReadableDatabase();
        contentValues = new ContentValues();
        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = '" + JMainTabl + "'", null);
        if (cursor.getCount() == 0) {
            Str06 = "Главная таблица " + JMainTabl + " повреждена или отсутствует. Удалите базу данных и создайте её заново!";
            Toast.makeText(MainActivity.this, Str06, Toast.LENGTH_LONG).show();
        } else {
            System.out.println("Database works correctly.");
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId() == R.id.menu_12) {
            Toast.makeText(MainActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
            com.example.mobileapplabwork2.ui.home.HomeFragment.addTabLayout(findViewById(R.id.tabLayout1));
        }

        if (item.getItemId() == R.id.menu_11) {
            try {
                cursor = db.query(JMainTabl, null, null, null, null, null, null);
                cursor.moveToFirst();
                homeFragment.createPage(DBHandler.databaseContext, 0, DatabaseName, JNumPage);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Unknown error while trying to open database. e.message=" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY) {
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.scrollView3.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView4).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView5).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY) {
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.scrollView4.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView3).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView5).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY) {
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.scrollView5.isShown()) {
            if (ScrollDir == 0) {
                findViewById(R.id.scrollView3).scrollTo(ScX, ScY);
                findViewById(R.id.scrollView4).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY) {
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.horizontalScrollView4.isShown()) {
            if (ScrollDir == 1) {
                findViewById(R.id.horizontalScrollView6).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY) {
        if (com.example.mobileapplabwork2.ui.home.HomeFragment.horizontalScrollView6.isShown()) {
            if (ScrollDir == 1) {
                findViewById(R.id.horizontalScrollView4).scrollTo(ScX, ScY);
            }
        }
    }

    @Override
    public void JournalFragmentCreate(int num, String name, int page, int learn, int students) {
        try {
            if (num == 1) {
                db = dbHelper.getWritableDatabase();
                db.execSQL("drop table if exists '" + JMainTabl + "'");
                Str01 = "create table if not exists ";
                Str02 = Str01 + JMainTabl + " ('" + T_idTabl + "' integer primary key, '" + TNameTab + "' text)";
                db.execSQL(Str02);
                contentValues.clear();
                contentValues.put(T_idTabl, "1");
                contentValues.put(TNameTab, String.valueOf(name));
                db.insert(JMainTabl, null, contentValues);
                db.execSQL("drop table if exists '" + name + "'");
                T_Atten = new String[learn];
                Str01 = "create table if not exists ";
                Str02 = Str01 + name + " ('" + T_id_FIO + "' integer primary key, 'FIO' text, ";
                Str03 = "";
                j1 = 0;
                while (j1 < learn) {
                    T_Atten[j1] = "2025.02.12." + (j1 + 1);
                    Str03 = Str03 + "'" + T_Atten[j1] + "' text, ";
                    j1++;
                }
                Str04 = "'" + TSertifZ + "' text, '" + TSertifK + "' integer, '" + TSertifE + "' integer, '" + TSertifD + "' text)";
                Str05 = Str02 + Str03 + Str04;
                db.execSQL(Str05);


                contentValues.clear();
                contentValues.put(T_id_FIO, "0");
                contentValues.put(TFIO, "ФИО");

                j1 = 0;

                while (j1 < learn) {

                    contentValues.put(T_Atten[j1], String.valueOf(T_Atten[j1]));

                    j1++;
                }
                contentValues.put(TSertifZ, "Z");
                contentValues.put(TSertifK, "0");
                contentValues.put(TSertifE, "0");
                contentValues.put(TSertifD, "D");
                db.insert(name, null, contentValues);

                i1 = 0;
                while (i1 < students) {
                    contentValues.clear();
                    Str01 = String.valueOf(i1 + 1);
                    contentValues.put(T_id_FIO, Str01);

                    contentValues.put(TFIO, "Фамилия" + "\n" + "Имя" + "\n" + "Отчество " + Str01);

                    j1 = 0;

                    while (j1 < learn) {

                        contentValues.put(T_Atten[j1], Str01 + "\n" + (j1 + 1));

                        j1++;
                    }
                    contentValues.put(TSertifZ, "Z" + "\n" + Str01);
                    contentValues.put(TSertifK, "0" + "\n" + Str01);
                    contentValues.put(TSertifE, "0" + "\n" + Str01);
                    contentValues.put(TSertifD, "D" + "\n" + Str01);
                    db.insert(name, null, contentValues);

                    i1++;
                }

                JBaseName = name;
                JNumPage = page;
                JNumLearn = learn;
                JNumStudents = students;
            }

        } catch (Exception e) {
            System.out.println("Sql command log=" + Str05);
            Toast.makeText(MainActivity.this, "Unknown error while trying to create journal. e.message=" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, this.homeFragment).commit();
        this.setTitle("Home");
    }
}