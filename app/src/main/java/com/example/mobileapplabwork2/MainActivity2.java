package com.example.mobileapplabwork2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

import static com.example.mobileapplabwork2.MainActivity.*;

import com.example.mobileapplabwork2.ui.openfile.OpenFileDialog;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextM2_1, editTextM2_2, editTextM2_3;
    private String fPathS, fDirS, fFileS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextM2_1 = findViewById(R.id.editTextM2_1);
        editTextM2_2 = findViewById(R.id.editTextM2_2);
        editTextM2_3 = findViewById(R.id.editTextM2_3);

        Button buttonM2_1 = findViewById(R.id.buttonM2_1);
        Button buttonM2_2 = findViewById(R.id.buttonM2_2);
        Button buttonM2_3 = findViewById(R.id.buttonM2_3);

        editTextM2_1.setText(GlobalFolderName);
        editTextM2_2.setText("");
        editTextM2_3.setText(GlobalFilessName);

        buttonM2_1.setOnClickListener(this);
        buttonM2_2.setOnClickListener(this);
        buttonM2_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.buttonM2_1) {
            String dirName = editTextM2_2.getText().toString();
            if (!dirName.isEmpty()) {
                if (dirName.contains("/") || dirName.contains("\\") || dirName.contains("?") ||
                        dirName.contains("*") || dirName.contains(":") || dirName.contains("\"") ||
                        dirName.contains("<") || dirName.contains(">") || dirName.contains("|")) {
                    Toast.makeText(this, "Имя директория содержит недопустимые символы", Toast.LENGTH_SHORT).show();
                } else {
                    File newDir = new File(GlobalFolderName + "/" + dirName);
                    if (!newDir.exists()) {
                        boolean created = newDir.mkdirs();
                        if (created) {
                            Toast.makeText(this, "Каталог создан: " + newDir.getPath(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Не удалось создать каталог", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Каталог уже существует", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            fPathS = String.valueOf(editTextM2_1.getText());
            fDirS = String.valueOf(editTextM2_2.getText());
            fFileS = String.valueOf(editTextM2_3.getText());
            GlobalFilessName = String.valueOf(editTextM2_3.getText());
            MainActivity.fileWrite_SD(fPathS, fDirS, fFileS);
            this.finish();

        } else if (id == R.id.buttonM2_2) {
            finish();
        } else if (id == R.id.buttonM2_3) {
            OpenFileDialog fileDialog = new OpenFileDialog(this);
            fileDialog.show();
        }
    }
}