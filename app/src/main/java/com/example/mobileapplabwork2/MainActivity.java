package com.example.mobileapplabwork2;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ScrollView;
import android.widget.HorizontalScrollView;

import com.example.mobileapplabwork2.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplabwork2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Объявление объекта навигационного меню
        final Menu navMenu = navigationView.getMenu();

        // Цикл по пунктам навигационного меню
        for (int i = 1; i <= 6; i++) {
            // Формирование id пункта навигационного меню
            int itemId = 1000 + i;
            // Добавление пункта
            navMenu.add(0, itemId, 0, "Runtime item " + i);
            // Подключение пункта навигационного меню
            MenuItem item = navMenu.findItem(itemId);
            // Установка слушателя пункту навигационного меню
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem item) {
                    // Демонстрация сообщения
                    Toast.makeText(MainActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Демонстрация сообщения
        if (item.getItemId() == R.id.menu_11) {
             Toast.makeText(MainActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
             HomeFragment.addTabLayout(findViewById(R.id.tabLayout1)); // Вызов функции добавления вкладок
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHomeFragmentScrollview3(int ScrollDir, int ScX, int ScY) {
        if (HomeFragment.scrollView3.isShown()) { // Если фрагмент активен
            // Обращение к фрагменту с текущим id
            if (ScrollDir == 0) { // Если прокрутка по вертикали
                ((ScrollView) findViewById(R.id.scrollView4)).scrollTo(ScX, ScY);
                ((ScrollView) findViewById(R.id.scrollView5)).scrollTo(ScX, ScY); // Прокрутка ScrollView5 по вертикали
            } // Конец условия if (ScrollDir == 0)
        } // Конец условия if (HomeFragment.scrollView3.isShown())
    }

    @Override
    public void onHomeFragmentScrollview4(int ScrollDir, int ScX, int ScY) {
        if (HomeFragment.scrollView4.isShown()) { // Если фрагмент активен
            // Обращение к фрагменту с текущим id
            if (ScrollDir == 0) { // Если прокрутка по вертикали
                ((ScrollView) findViewById(R.id.scrollView3)).scrollTo(ScX, ScY);
                ((ScrollView) findViewById(R.id.scrollView5)).scrollTo(ScX, ScY); // Прокрутка ScrollView5 по вертикали
            } // Конец условия if (ScrollDir == 0)
        } // Конец условия if (HomeFragment.scrollView4.isShown())
    }

    @Override
    public void onHomeFragmentScrollview5(int ScrollDir, int ScX, int ScY) {
        if (HomeFragment.scrollView5.isShown()) { // Если фрагмент активен
            // Обращение к фрагменту с текущим id
            if (ScrollDir == 0) { // Если прокрутка по вертикали
                ((ScrollView) findViewById(R.id.scrollView3)).scrollTo(ScX, ScY);
                ((ScrollView) findViewById(R.id.scrollView4)).scrollTo(ScX, ScY); // Прокрутка ScrollView4 по вертикали
            } // Конец условия if (ScrollDir == 0)
        } // Конец условия if (HomeFragment.scrollView5.isShown())
    }

    @Override
    public void onHomeFragmentHorScroll4(int ScrollDir, int ScX, int ScY) {
        if (HomeFragment.horizontalScrollView4.isShown()) { // Если фрагмент активен
            // Обращение к фрагменту с текущим id
            if (ScrollDir == 1) { // Если прокрутка по горизонтали
                ((HorizontalScrollView) findViewById(R.id.horizontalScrollView6)).scrollTo(ScX, ScY);
            } // Конец условия if (ScrollDir == 1)
        } // Конец условия if (HomeFragment.horizontalScrollView4.isShown())
    }

    @Override
    public void onHomeFragmentHorScroll6(int ScrollDir, int ScX, int ScY) {
        if (HomeFragment.horizontalScrollView6.isShown()) { // Если фрагмент активен
            // Обращение к фрагменту с текущим id
            if (ScrollDir == 1) { // Если прокрутка по горизонтали
                ((HorizontalScrollView) findViewById(R.id.horizontalScrollView4)).scrollTo(ScX, ScY);
            } // Конец условия if (ScrollDir == 1)
        } // Конец условия if (HomeFragment.horizontalScrollView6.isShown())
    }
}