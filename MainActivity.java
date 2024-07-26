package com.example.calculator_july;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private String display="";
    private EditText inputtext;
    private TextView displaytext;
    private String currentOperator="";
    private String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton deletevar = (ImageButton) findViewById(R.id.butdelet);
        deletevar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deletenumber();
            }
        });
        screen = (TextView) findViewById(R.id.input_box);
        screen.setText(display);
        inputtext = findViewById(R.id.input_box);
        displaytext = findViewById(R.id.result_box);
    }

    private void appendToLast(String str) {
        this.inputtext.getText().append(str);
    }

    public void onClickNumber(View v) {
        Button b = (Button) v;
        display += b.getText();
        appendToLast(display);
        display = "";
    }

    public void onClickOperator(View v) {
        Button b = (Button) v;
        display += b.getText();
        if (endsWithOperator()) {
            replace(display);
        }
        else {
            appendToLast(display);
        }
        currentOperator = b.getText().toString();
        display = "";
    }
    public void onClearButton(View v) {
        inputtext.getText().clear();
        displaytext.setText("");
    }

    public void deletenumber(){
        this.inputtext.getText().delete(getinput().length() - 1, getinput().length());
    }

    private String getinput() {
        return this.inputtext.getText().toString();
    }

    private boolean endsWithOperator() {
        return getinput().endsWith("+") ||
                getinput().endsWith("-") ||
                getinput().endsWith("x") ||
                getinput().endsWith("/");
    }

    private void replace(String str) {
        inputtext.getText().replace(getinput().length() - 1, getinput().length(), str);
    }
private double operate(String a, String b, String cp) {
        switch(cp) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "x":
                return Double.valueOf(a) * Double.valueOf(b);
            case "/":
                return Double.valueOf(a) / Double.valueOf(b);
            default: return -1;
        }
    }
    public void equalresult(View v) {
        String input = getinput();
        if (!endsWithOperator()) {
            if (input.contains("x")) {
                input = input.replaceAll("x", "*");
            }
            //2+3-9*8
            Expression expression = new ExpressionBuilder(input).build();
            double result = expression.evaluate();

            displaytext.setText(String.valueOf(result));
        }
        else {
            displaytext.setText("");
        }
        System.out.println(result);
    }
}
