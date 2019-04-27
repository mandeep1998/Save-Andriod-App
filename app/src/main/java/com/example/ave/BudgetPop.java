package com.example.ave;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class BudgetPop extends Activity {
    private CheckBox WeeklyBox;
    private CheckBox MonthlyBox;
    private EditText BudgetText;
    private Boolean mCheck;
    private Boolean wCheck;
    private String Budget;
    private double budget;
    private Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budgetpop_layout);
        WeeklyBox=(CheckBox) findViewById(R.id.WeeklyBox);
        MonthlyBox=(CheckBox) findViewById(R.id.MonthlyBox);
        BudgetText=(EditText) findViewById(R.id.BudgetText);
        done=(Button) findViewById(R.id.fButton);
        DisplayMetrics metrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height=metrics.heightPixels;
        int width=metrics.widthPixels;

        getWindow().setLayout((int)(width*.7),(int)(height *.5));

        WeeklyBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wCheck=true;
                if(mCheck)
                {
                    Toast.makeText(BudgetPop.this, "Check Only One",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(BudgetPop.this, "Please Input Budget",Toast.LENGTH_LONG).show();

                }
            }
        });

        MonthlyBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheck=true;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Budget=BudgetText.getText().toString();
                budget=Double.parseDouble(Budget);
                if(budget==0||budget<0)
                {
                    Toast.makeText(BudgetPop.this, "Budget must be greater than zero",Toast.LENGTH_LONG).show();
                }
                else
                {
                    finish();
                    //upload info to database
                }

            }
        });
    }

}
