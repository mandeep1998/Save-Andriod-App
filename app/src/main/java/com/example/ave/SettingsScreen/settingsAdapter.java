package com.example.ave.SettingsScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ave.R;

import java.util.ArrayList;


public class settingsAdapter extends RecyclerView.Adapter<settingsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    public settingsAdapter(ArrayList<String> list, Context context)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String temp=list.get(0);
        viewHolder.bind(temp);
        System.out.print("Binding: " + temp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView3);
        }

         void bind(String text)
        {
            textView.setText(text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
