package com.soumen.rideshearing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarChoiceAdapter extends RecyclerView.Adapter<CarChoiceAdapter.viewHolder> {

    ArrayList<CarChoiceModel>arrayCarChoice;
    Context context;

    public CarChoiceAdapter(ArrayList<CarChoiceModel> arrayCarChoice, Context context) {
        this.arrayCarChoice = arrayCarChoice;
        this.context = context;
    }

    @NonNull
    @Override
    public CarChoiceAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_ad,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarChoiceAdapter.viewHolder holder, int position) {
        CarChoiceModel carModel=arrayCarChoice.get(position);
        holder.txtCarType.setText(carModel.getCarType());
        holder.txtPrice.setText(String.format("%.2f", carModel.getPrice()));
        holder.txtContactNumber.setText(carModel.getContactNumber());
        holder.txtCarNumber.setText(carModel.getCarNumber());
        holder.imgCar.setImageResource(carModel.getCarLogo());
        holder.layout.setOnClickListener(v->{
            Intent intent=new Intent(context, RideActivity.class);
            intent.putExtra("carLogo",carModel.getCarLogo());
            intent.putExtra("price",carModel.getPrice());
            intent.putExtra("contactNumber",carModel.getContactNumber());
            intent.putExtra("carNumber",carModel.getCarNumber());
            intent.putExtra("carType",carModel.getCarType());
            intent.putExtra("carName",carModel.getCarName());
            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayCarChoice.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgCar;
        TextView txtCarType,txtCarNumber,txtPrice,txtContactNumber;
        ConstraintLayout layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgCar=itemView.findViewById(R.id.imgCar);
            txtCarNumber=itemView.findViewById(R.id.txtCarNumber);
            txtContactNumber=itemView.findViewById(R.id.txtContactNumber);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtCarType=itemView.findViewById(R.id.txtCarType);
            layout=itemView.findViewById(R.id.layout);

        }
    }
}
