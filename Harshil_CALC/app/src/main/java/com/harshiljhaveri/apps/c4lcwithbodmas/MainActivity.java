package com.harshiljhaveri.apps.c4lcwithbodmas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.EmptyStackException;

public class MainActivity extends AppCompatActivity {

    private Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bDot;
    private Button bPlus, bMinus, bTimes, bBy;
    private Button bBrace1, bBrace2;
    private Button bAllClear, bDelete, bEquals;
    private TextView expressionTV, answerTV;
    private  static final String exp = "expression";
    private static final String answer = "answer";
    private boolean gotAnswer = false, dotTest = true;
    private static final String gAns = "gAns";
    private static final String dTest = "dTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b0 = findViewById(R.id.button0);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);

        bPlus = findViewById(R.id.buttonPlus);
        bMinus = findViewById(R.id.buttonMinus);
        bTimes = findViewById(R.id.buttonTimes);
        bBy = findViewById(R.id.buttonBy);

        bBrace1 = findViewById(R.id.buttonB1);
        bBrace2 = findViewById(R.id.buttonB2);

        bAllClear = findViewById(R.id.buttonClear);
        bDelete = findViewById(R.id.buttonDelete);
        bEquals = findViewById(R.id.buttonEquals);

        bDot = findViewById(R.id.buttonDot);

        expressionTV = findViewById(R.id.expressionTV);
        answerTV = findViewById(R.id.answerTV);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gotAnswer){
                    expressionTV.setText(answerTV.getText());
                    Button button = (Button) v;
                    expressionTV.append(button.getText());
                    answerTV.setText(" ");
                    gotAnswer = false;
                } else {
                    expressionTV.append(((Button) v).getText());
                }
                dotTest = true;
            }
        };
        setListeners(listener);

        bEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String ex = expressionTV.getText().toString();
                try{
                    Expression expression = new ExpressionBuilder(ex).build();
                    double a = expression.evaluate();
                    answerTV.setText(" " + a);
                    gotAnswer = true;
                    expressionTV.setText("");
                    dotTest = false;
                } catch (ArithmeticException e){
                    Toast.makeText(MainActivity.this,"Mathematical Error",Toast.LENGTH_LONG).show();
                    gotAnswer = false;
                } catch (IllegalArgumentException e){
                    Toast.makeText(MainActivity.this,"Incorrect Expression",Toast.LENGTH_LONG).show();
                } catch (EmptyStackException e){
                    Toast.makeText(MainActivity.this,"No Number Input",Toast.LENGTH_LONG).show();
                }
            }
        });

        bAllClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expressionTV.setText(" ");
                answerTV.setText(" ");
                gotAnswer = false;
                dotTest = true;
            }
        });

        bDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gotAnswer) {
                    expressionTV.setText(answerTV.getText());
                    answerTV.setText(" ");
                    gotAnswer = false;
                }
                if(dotTest){
                    expressionTV.append(".");
                    dotTest = false;
                }
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gotAnswer){
                    expressionTV.setText(answerTV.getText());
                    answerTV.setText("");
                    gotAnswer = false;
                    answerTV.setText(" ");
                    gotAnswer = false;
                }
                String current = expressionTV.getText().toString();
                if(current.length() != 0){
                    expressionTV.setText(current.substring(0,current.length()-1));
                }
            }
        });
    }

    private void setListeners(View.OnClickListener listener){
        b0.setOnClickListener(listener);
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
        b6.setOnClickListener(listener);
        b7.setOnClickListener(listener);
        b8.setOnClickListener(listener);
        b9.setOnClickListener(listener);

        bPlus.setOnClickListener(listener);
        bMinus.setOnClickListener(listener);
        bTimes.setOnClickListener(listener);
        bBy.setOnClickListener(listener);

        bBrace1.setOnClickListener(listener);
        bBrace2.setOnClickListener(listener);
    }

    public void appendBrace(View v){
        if(gotAnswer){
            expressionTV.setText(answerTV.getText());
            answerTV.setText(" ");
            gotAnswer = false;
        }
        Button button = (Button) v;
        expressionTV.append(button.getText());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(exp,expressionTV.getText().toString());
        outState.putString(answer,answerTV.getText().toString());
        outState.putBoolean(gAns,gotAnswer);
        outState.putBoolean(dTest,dotTest);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        expressionTV.setText(savedInstanceState.getString(exp));
        answerTV.setText(savedInstanceState.getString(answer));
        gotAnswer = savedInstanceState.getBoolean(gAns);
        dotTest = savedInstanceState.getBoolean(dTest);
    }
}
