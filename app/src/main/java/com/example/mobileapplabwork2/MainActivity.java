package com.example.mobileapplabwork2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobileapplabwork2.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplabwork2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
}