package com.example.ave;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ave.Database.DatabaseSql;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private DatabaseSql DB=new DatabaseSql();
    private Button addB;
    private TextView budgetText,remainingText;
    private String budget;
    private ListView listView;
    private EditText amountInput,storeInput;
    private String email;
    private String DBremaining;
    private  List<DocumentSnapshot> expenses;
    private ArrayList<String> expenseList=new ArrayList<>();
    private ArrayList<String> amountList=new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   //Nothing here because we are on main page
                    //leave blank so user cant spam home button
                    return true;
                case R.id.navigation_dashboard:
                  startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), NotificationScreen.class));
                    return true;
                case R.id.navigation_settings:
                    startActivity(new Intent (getApplicationContext(), SettingsPage.class));
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeInput=(EditText) findViewById(R.id.storeInput);
        amountInput=(EditText)findViewById(R.id.amountInput);
        budgetText=(TextView) findViewById(R.id.budgetText);
        remainingText=(TextView) findViewById(R.id.remainingText);
        listView=(ListView) findViewById(R.id.ListView);
        getBudget();
        loadList();
        addB=(Button) findViewById(R.id.addB);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //setRemaining();
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=amountInput.getText().toString();
                String store=storeInput.getText().toString();

                if(!(amount.isEmpty())||!(store.isEmpty())) {
                    String stores = (store + " $" + amount);
                    HashMap hash = new HashMap();
                    hash.put("amount", amount);
                    hash.put("listStore", stores);
                    hash.put("Store", store);
                    DB.getfire().collection(email).add(hash);
                    Toast.makeText(MainActivity.this, "Expense Successfully added", Toast.LENGTH_SHORT).show();
                    loadList();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter in amount and store",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

public void getBudget()
{
    email=DB.getAuth().getCurrentUser().getEmail();
    DB.getfire().collection(email).document("Budget").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if(documentSnapshot.exists())
            {
                budget=documentSnapshot.getString("Budget");
                budgetText.setText(budget);
            }
            else
            {
                Toast.makeText(MainActivity.this,"No Budget Has been Set",Toast.LENGTH_SHORT).show();
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });
}

public void loadList()
{

    DB.getfire().collection(email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            expenses=task.getResult().getDocuments();
            for(int i=1; i<expenses.size();i++)
            {
                expenseList.add(expenses.get(i).getString("listStore"));
                amountList.add(expenses.get(i).getString("amount"));
                ListAdapter adapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,expenseList);
                listView.setAdapter(adapter);
                setRemaining();

            }

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });

}

public void getAmountList(ArrayList<String> array)
{
    amountList= (ArrayList<String>) array.clone();
}
public void setRemaining()
{

    Double remaining=0.0;
   for(int i=0;i<amountList.size();i++) {
       remaining+=Double.parseDouble(amountList.get(i));
   }
   Log.e("TAG", remaining + "");
    if (budget == null) {

    } else {
        String remain;
        remain= String.valueOf(Double.parseDouble(budget) - remaining);
        Log.e("TAG", budget);
        remainingText.setText(remain);
    }
}
}
