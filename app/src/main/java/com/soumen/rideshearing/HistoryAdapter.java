package com.soumen.rideshearing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {

    Context context;
    ArrayList<CarModel> arrayCar;
    boolean isComplete = false;
    boolean isBooked = false;

    public HistoryAdapter(Context context, ArrayList<CarModel> arrayCar, boolean isComplete, boolean isBooked) {
        this.context = context;
        this.arrayCar = arrayCar;
        this.isComplete = isComplete;
        this.isBooked = isBooked;
    }

    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder holder, int position) {
        CarModel carmodel=arrayCar.get(position);
        holder.carName.setText(carmodel.getCarName());
        holder.carNumber.setText(carmodel.carNumber);
        holder.start.setText(carmodel.getStart());;
        holder.destination.setText(carmodel.getDestination());

        holder.carRow.setOnClickListener(v->{
            Intent intent=new Intent(context,RideActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return arrayCar.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView start, destination,date, carNumber, carName,complete;
        LinearLayout carRow;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.destStart);
            destination = itemView.findViewById(R.id.destiEnd);
            carNumber = itemView.findViewById(R.id.carNumber);
            carName = itemView.findViewById(R.id.carName);
            date=itemView.findViewById(R.id.date);
            complete=itemView.findViewById(R.id.complete);
            carRow=itemView.findViewById(R.id.carRow);
        }
    }
}
