package com.soumen.rideshearing;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class CarChoiceActivity extends AppCompatActivity {
ArrayList<CarChoiceModel>arrCarModel=new ArrayList<>();
    private static final String[] STATE_CODES = {
            "AP", "AR", "AS", "BR", "CG", "DL", "GA", "GJ", "HR", "HP", "JK", "JH",
            "KA", "KL", "MP", "MH", "MN", "ML", "MZ", "NL", "OD", "PB", "RJ", "SK",
            "TN", "TS", "TR", "UP", "UK", "WB"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_choice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView=findViewById(R.id.carChoiceRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrCarModel.add(new CarChoiceModel(R.drawable.car,"Amit Kumar",generatePrice(),"Four Wheeler",generateVehicleNumber(),generateContactNumber()));
        arrCarModel.add(new CarChoiceModel(R.drawable.motorcycle,"Rajib Singha",generatePrice(),"Motor Cycle",generateVehicleNumber(),generateContactNumber()));
        arrCarModel.add(new CarChoiceModel(R.drawable.scooter,"Rahul Roy",generatePrice(),"Scooter",generateVehicleNumber(),generateContactNumber()));
        arrCarModel.add(new CarChoiceModel(R.drawable.rickshaw,"Subham Sen",generatePrice(),"Auto Rickshaw",generateVehicleNumber(),generateContactNumber()));

        CarChoiceAdapter adapter=new CarChoiceAdapter(arrCarModel, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public int generatePrice(){
        int otp=0;

        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900);
        return randomNumber;
    }

    public String generateContactNumber() {
        Random random = new Random();
        long randomNumber = 1000000000L + (long) (random.nextDouble() * 9000000000L); // Ensures 10-digit number
        return String.valueOf(randomNumber);
    }

    public static String generateVehicleNumber() {
        Random random = new Random();
        String state = STATE_CODES[random.nextInt(STATE_CODES.length)];
        int rtoCode = random.nextInt(99) + 1;
        char letter1 = (char) ('A' + random.nextInt(26));
        char letter2 = (char) ('A' + random.nextInt(26));
        int uniqueNumber = random.nextInt(9000) + 1000;
        return String.format("%s%02d%c%c%d", state, rtoCode, letter1, letter2, uniqueNumber);
    }

}