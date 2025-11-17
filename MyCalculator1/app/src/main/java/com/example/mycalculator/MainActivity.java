package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;

    private enum Op { NONE, ADD, SUB, MUL, DIV }
    private Op currentOp = Op.NONE;

    private StringBuilder input = new StringBuilder();
    private Double first = null;

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

        // Dot
        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if (input.indexOf(".") == -1) {
                if (input.length() == 0) input.append("0");
                input.append(".");
                refresh();
            }
        });

        // +/- toggle
        findViewById(R.id.btnSign).setOnClickListener(v -> {
            if (input.length() == 0) {
                input.append("-");
            } else if (input.charAt(0) == '-') {
                input.deleteCharAt(0);
            } else {
                input.insert(0, '-');
            }
            refresh();
        });

        // Backspace
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                refresh();
            }
        });

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());

        // Operations
        findViewById(R.id.btnPlus).setOnClickListener(v -> selectOp(Op.ADD));
        findViewById(R.id.btnMinus).setOnClickListener(v -> selectOp(Op.SUB));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> selectOp(Op.MUL));
        findViewById(R.id.btnDivide).setOnClickListener(v -> selectOp(Op.DIV));

        // Equals
        findViewById(R.id.btnEquals).setOnClickListener(v -> onEquals());
    }

    private void setDigit(int id, String d) {
        Button b = findViewById(id);
        b.setOnClickListener((View v) -> {
            if (d.equals("0") && input.toString().equals("0")) return;
            if (input.toString().equals("0") && !d.equals(".")) input.setLength(0);
            input.append(d);
            refresh();
        });
    }

    private void selectOp(Op op) {
        Double number = parseInputOrNull();
        if (number != null) {
            if (first == null) {
                first = number;
            } else if (currentOp != Op.NONE) {
                first = compute(first, number, currentOp);
                tvDisplay.setText(formatResult(first));
            }
            input.setLength(0);
        }
        currentOp = op;
    }

    private void onEquals() {
        if (currentOp == Op.NONE) return;

        Double second = parseInputOrNull();
        if (second == null) second = 0.0;
        double a = (first != null) ? first : 0.0;
        double b = second;

        Double result = null;
        switch (currentOp) {
            case ADD: result = a + b; break;
            case SUB: result = a - b; break;
            case MUL: result = a * b; break;
            case DIV:
                if (b == 0.0) {
                    tvDisplay.setText("∞");
                    resetAfterResult();
                    return;
                } else result = a / b;
                break;
            case NONE: break;
        }

        if (result != null) tvDisplay.setText(formatResult(result));
        resetAfterResult();
    }

    private void clearAll() {
        first = null;
        currentOp = Op.NONE;
        input.setLength(0);
        tvDisplay.setText("0");
    }

    private Double parseInputOrNull() {
        if (input.length() == 0 || input.toString().equals("-")) return null;
        try { return Double.parseDouble(input.toString()); }
        catch (NumberFormatException e) { return null; }
    }

    private void refresh() {
        tvDisplay.setText(input.length() == 0 ? "0" : input.toString());
    }

    private void resetAfterResult() {
        input.setLength(0);
        first = null;
        currentOp = Op.NONE;
    }

    private String formatResult(double v) {
        if (v == Math.rint(v)) return String.valueOf((long) v);
        return String.valueOf(v);
    }

    private Double compute(Double a, Double b, Op op) {
        switch (op) {
            case ADD: return a + b;
            case SUB: return a - b;
            case MUL: return a * b;
            case DIV: return (b == 0.0) ? null : a / b;
            default:  return b;
        }
    }
}
