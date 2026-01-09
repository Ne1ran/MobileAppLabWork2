package com.example.mobileapplabwork2.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapplabwork2.R;

public class JournalFragment extends Fragment implements View.OnClickListener {
    onJournalFragmentListener journalFragmentCreate;

    private TextView textView6;
    private TextView textView7;
    private TextView textView2;
    private EditText editTextText;
    private TextView textView3;
    private EditText editTextText2;
    private TextView textView4;
    private EditText editTextText3;
    private TextView textView5;
    private EditText editTextText4;
    private Button button;
    private Button button2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            journalFragmentCreate = (JournalFragment.onJournalFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must be " + onJournalFragmentListener.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_journal, container, false);

        textView6 = root.findViewById(R.id.textView6);
        textView7 = root.findViewById(R.id.textView7);
        textView2 = root.findViewById(R.id.textView2);
        editTextText = root.findViewById(R.id.editTextText);
        textView3 = root.findViewById(R.id.textView3);
        editTextText2 = root.findViewById(R.id.editTextText2);
        textView4 = root.findViewById(R.id.textView4);
        editTextText3 = root.findViewById(R.id.editTextText3);
        textView5 = root.findViewById(R.id.textView5);
        editTextText4 = root.findViewById(R.id.editTextText4);
        button = root.findViewById(R.id.button);
        button2 = root.findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        editTextText.setText("gtu_2025_2");
        editTextText2.setText("12");
        editTextText3.setText("20");
        editTextText4.setText("25");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            journalFragmentCreate.JournalFragmentCreate(1,
                    String.valueOf(editTextText.getText()),
                    Integer.valueOf(String.valueOf(editTextText2.getText())),
                    Integer.valueOf(String.valueOf(editTextText3.getText())),
                    Integer.valueOf(String.valueOf(editTextText4.getText())));
        }
    }

    public interface onJournalFragmentListener {
        public void JournalFragmentCreate(int num, String name, int page, int learn, int students);
    }
}