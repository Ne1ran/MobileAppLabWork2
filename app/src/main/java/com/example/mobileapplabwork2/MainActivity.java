package com.example.mobileapplabwork2;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobileapplabwork2.databinding.ActivityMainBinding;
import com.example.mobileapplabwork2.sql.DBHandler;
import com.example.mobileapplabwork2.ui.home.HomeFragment;
import com.example.mobileapplabwork2.ui.journal.JournalFragment;
import com.example.mobileapplabwork2.ui.openfile.OpenFileDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, JournalFragment.onJournalFragmentListener {


    private static final String WRITE_EXT_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int WRITE_EXT_STORAGE_REQUEST_CODE = 10101;
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

    public static String GlobalFolderName = "";
    public static String GlobalFilessName = "";
    public static int OFD_ButtonPress = 0;
    public static File sdPath, sdFile;

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

        if (isPermissionGranted(WRITE_EXT_STORAGE_PERMISSION)) {
            Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
        } else {
            requestPermission(WRITE_EXT_STORAGE_PERMISSION, WRITE_EXT_STORAGE_REQUEST_CODE);
            Toast.makeText(this, R.string.permission_request, Toast.LENGTH_SHORT).show();
        }

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
                homeFragment.createPage(DBHandler.databaseContext, 0, JBaseName, JNumPage);
            } catch (Exception e) {
                System.out.println("DB error message " + e.getMessage());
                Toast.makeText(MainActivity.this, "Unknown error while trying to open database. e.message=" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId() == R.id.menu_13) { // Удаление журнала
            Cursor cursor = db.query("\"" + JMainTabl + "\"", null, null, null, null, null, null);
            cursor.moveToFirst();
            db.execSQL("drop table if exists " + cursor.getString(1));
            db.execSQL("drop table if exists " + JMainTabl);
        }
        if (item.getItemId() == R.id.menu_14) { // Сохранение в файл
            OFD_ButtonPress = 4;
            OpenFileDialog fileDialog = new OpenFileDialog(this);
            fileDialog.show();
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
                    T_Atten[j1] = "d2025_02_12_" + (j1 + 1);
                    Str03 = Str03 + T_Atten[j1] + " text, ";
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


    public static void fileWrite_SD(String fPath, String fFile, Context context) {
        if
        (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            OFD_ButtonPress = 0;
            AlertDialog allDB = new AlertDialog.Builder(context).create();
            allDB.setMessage("SD-карта не доступна");
            allDB.show();
            return;
        }
        sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(fPath);
        if (!sdPath.exists()) {
            sdPath.mkdir();
        }
        sdFile = new File(sdPath, fFile);
        SQLiteDatabase database = DBHandler.database;
        var cursor = database.query(JBaseName, null, null, null, null, null, null);
        try {
            StringBuilder header = new StringBuilder();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                header.append(cursor.getColumnName(i));
                if (i < cursor.getColumnCount() - 1) header.append(";");
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            bw.write(header.toString());
            bw.newLine();

            if (cursor.moveToFirst()) {
                do {
                    StringBuilder line = new StringBuilder();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        line.append(cursor.getString(i));
                        if (i < cursor.getColumnCount() - 1) line.append(";");
                    }
                    bw.write(line.toString());
                    bw.newLine();
                } while (cursor.moveToNext());
            }
            bw.close();
            Toast.makeText(context, "Successfully saved database to file!", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            AlertDialog allDB = new AlertDialog.Builder(context).create();
            allDB.setMessage("Файл не найден");
            allDB.show();
            e.printStackTrace();
        } catch (IOException e) {
            AlertDialog allDB = new AlertDialog.Builder(context).create();
            allDB.setMessage("Ошибка ввода-вывода");
            allDB.show();
            e.printStackTrace();
        }
    }

    public static void fileRead_SD(String fPath, String fFile, Context context) {
        if
        (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            OFD_ButtonPress = 0;
            AlertDialog allDB = new AlertDialog.Builder(context).create();
            allDB.setMessage("SD-карта не доступна");
            allDB.show();
            return;
        }
        String Str01 = "";
        Str01 = fFile.substring(fFile.length() - 3, fFile.length());
        OFD_ButtonPress = 0;
        if (Str01.equals("txt")) {
//            editText1.setText("");
//            sdPath = Environment.getExternalStorageDirectory();
//            sdPath = new File(fPath);
//            sdFile = new File(sdPath, fFile);
//            try {
//                BufferedReader br = new BufferedReader(new FileReader(sdFile));
//                while ((Str01 = br.readLine()) != null) {
//                    editText1.setText(editText1.getText() + Str01 + "\n");
//                }
//                br.close();
//
//
//                Toast.makeText(editText1.getContext(), "Файл успешно загружен: " + fFile, Toast.LENGTH_SHORT).show();
//            } catch (FileNotFoundException e) {
//                AlertDialog allDB = new AlertDialog.Builder(MainActivity.editText1.getContext()).create();
//                allDB.setMessage("Файл не найден");
//                allDB.show();
//                e.printStackTrace();// Отображение трассировки стека
//            } catch (IOException e) {
//                AlertDialog allDB = new AlertDialog.Builder(MainActivity.editText1.getContext()).create();
//                allDB.setMessage("Ошибка ввода-вывода");
//                allDB.show();
//                e.printStackTrace();// Отображение трассировки стека
//            }
        }
    }

    public void showSaveAsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сохранить как...");

        final EditText input = new EditText(this);
        input.setHint("Введите имя файла");

        if (!GlobalFilessName.equals("")) {
            input.setText(GlobalFilessName);
        }

        builder.setView(input);

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String fileName = input.getText().toString();

            if (fileName.isEmpty()) {
                Toast.makeText(MainActivity.this, "Имя файла не может быть пустым", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!fileName.toLowerCase().endsWith(".txt")) {
                fileName += ".txt";
            }

            GlobalFilessName = fileName;
            fileWrite_SD(GlobalFolderName, GlobalFilessName, this.getApplicationContext());
            Toast.makeText(MainActivity.this, "Файл сохранен: " + GlobalFilessName, Toast.LENGTH_LONG).show();
            setTitle("Текст.ред.: " + GlobalFilessName);
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private boolean isPermissionGranted(String permission) {
        int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXT_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.permission_not_granted, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, R.string.storage_not_available, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Uri uri = Uri.parse("package:" + getPackageName());
                    Intent intent = null;
                    intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                    startActivity(intent);
                    Toast.makeText(this, R.string.try_all_files_access, Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, getString(R.string.exception_occurred, ex.toString()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            }
        }
    }
}