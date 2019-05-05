package com.example.ave;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ave.Database.DatabaseSql;
import com.example.ave.MainActivity;
import com.example.ave.NotificationScreen;
import com.example.ave.R;
import com.example.ave.SettingsPage;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardScreen extends AppCompatActivity {
    DatabaseSql DB = new DatabaseSql();
    private String email=DB.getAuth().getCurrentUser().getEmail();
    private ArrayList<PieEntry> chartarray= new ArrayList<>();
    private ArrayList<String> amountList= new ArrayList<>();
    private String budget;
    private List<PieEntry> data = new ArrayList<>();
    private PieChart expenseChart;
    private TextView scoretext;
    private int count;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                    //leave empty
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), NotificationScreen.class));
                    return true;
                case R.id.navigation_settings:
                    startActivity(new Intent (getApplicationContext(), SettingsPage.class));
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getData();
        expenseChart=(PieChart)findViewById(R.id.piechart);
        scoretext=(TextView) findViewById(R.id.score);
    }

    private void getData() {
        DB.getfire().collection(email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> snap= task.getResult().getDocuments();
                loadchart(snap);
                snap.get(0).getString("Budget");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DashboardScreen.this, "Cannot connect to database at this time",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadchart(List<DocumentSnapshot> snap) {

        chartarray.clear();
            for(int i=1;i<snap.size();i++)
            {
                if(snap.get(i).getString("Store")==null)
                {

                }
                else
                {
                    float amount=Float.parseFloat(snap.get(i).getString("amount"));
                    String store= snap.get(i).getString("Store");
                    amountList.add(String.valueOf(amount));
                    PieEntry pie= new PieEntry(amount,store);
                    if(getIndex(pie.getLabel()))
                    {

                        float nAmount=amount + data.get(count).getValue();

                        PieEntry temp=new PieEntry(nAmount,store);
                        data.remove(count);
                        data.add(temp);
                    }

                    else {
                        chartarray.add(pie);
                        data.add(pie);
                    }
                }
            }

        PieDataSet set =new PieDataSet(data,"Expense data");
            set.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pData= new PieData(set);
        pData.setValueTextSize(20);
        expenseChart.setData(pData);
        expenseChart.animateY(1000);
        expenseChart.getDescription().setEnabled(false);
        expenseChart.invalidate();
        getBudget();
    }

    public void setScore(String budget)
    {
        double score=0;
        for(int i=0;i<amountList.size();i++)
        {
             score+=Double.parseDouble(amountList.get(i));
        }
        score=score*Integer.parseInt(budget);
        scoretext.setText(String.valueOf(score));
    }
    public void getBudget()
    {
        DB.getfire().collection(email).document("Budget").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    budget=documentSnapshot.getString("Budget");
                    setScore(budget);
                }
                else
                {
                    Toast.makeText(DashboardScreen.this,"No Budget Has been Set",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public boolean getIndex(String label)
    {

        count=0;
        boolean check=false;
        for(int i=0;i<chartarray.size();i++)
        {
            if(chartarray.get(i).getLabel().equals(label))
            {
                count=i;
                check=true;
                break;
            }
        }
        return check;
    }
}
