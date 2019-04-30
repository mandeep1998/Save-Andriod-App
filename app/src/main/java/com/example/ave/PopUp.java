package com.example.ave;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ave.Database.DatabaseSql;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PopUp extends Activity {
    private EditText amountInput,storeInput;
    private Button done;
    DatabaseSql DB=new DatabaseSql();
    private String email=DB.getAuth().getCurrentUser().getEmail();
    private MainActivity main=new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);
        storeInput=(EditText) findViewById(R.id.storeInput);
        amountInput=(EditText)findViewById(R.id.amountInput);
        done=(Button)findViewById(R.id.done);
        DisplayMetrics metrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height=metrics.heightPixels;
        int width=metrics.widthPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.6));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=amountInput.getText().toString();
                String store=storeInput.getText().toString();

                String stores=(store+ " $"+amount);

                HashMap hash=new HashMap();
                hash.put("amount",amount);
                hash.put("Store",stores);
                DB.getfire().collection(email).add(hash);
                Toast.makeText(PopUp.this, "Expense Successfully added",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
