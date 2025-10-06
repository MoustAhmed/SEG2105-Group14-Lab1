package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Digits
        setDigit(R.id.btn0, "0");
        setDigit(R.id.btn1, "1");
        setDigit(R.id.btn2, "2");
        setDigit(R.id.btn3, "3");
        setDigit(R.id.btn4, "4");
        setDigit(R.id.btn5, "5");
        setDigit(R.id.btn6, "6");
        setDigit(R.id.btn7, "7");
        setDigit(R.id.btn8, "8");
        setDigit(R.id.btn9, "9");

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());

        // (Ops & equals will come in later commits)
        refresh();
    }

    private void setDigit(int id, String d) {
        Button b = findViewById(id);
        b.setOnClickListener((View v) -> {
            // avoid leading zeros like "00..."; keep single "0"
            if (d.equals("0") && input.toString().equals("0")) return;
            if (input.toString().equals("0") && !d.equals(".")) input.setLength(0);
            input.append(d);
            refresh();
        });
    }

    private void clearAll() {
        input.setLength(0);
        tvDisplay.setText("0");
    }

    private void refresh() {
        tvDisplay.setText(input.length() == 0 ? "0" : input.toString());
    }
}