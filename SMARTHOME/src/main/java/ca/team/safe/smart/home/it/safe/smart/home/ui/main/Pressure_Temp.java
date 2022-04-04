package ca.team.safe.smart.home.it.safe.smart.home.ui.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.team.safe.smart.home.it.safe.smart.home.R;

public class Pressure_Temp  extends AppCompatActivity {
    View view;
    TextView Dis;
    TextView Per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_temp);
        Dis = findViewById(R.id.Dis);
        Per = findViewById(R.id.txtPressure);
        FirebaseApp.initializeApp(this);
        getTemp();
        getPress();
    }



    public void getTemp() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Temp");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //getting values on hashmap from firebase in random order
                List<String> d1 = new ArrayList<>();
                String value;
                //getting values from firebase , setting on array list
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //get value one by one in "value" variable
                  //  value=String.valueOf(postSnapshot.getValue());
                    value="Temp= "+String.valueOf(postSnapshot.getValue());
                   value=value+" , Pressure = "+postSnapshot.getValue();

                    if (!value.contains("null"))
                        d1.add(value);
                }
                try {
                    if (d1.size()>0)
                    {
                        final int[] i = {0};
                        new CountDownTimer(1000*d1.size(), 1000) {
                            public void onTick(long millisUntilFinished) {
                                Dis.setText(" "+d1.get(i[0]));
                                i[0]++;
                            }

                         public void onFinish() {

                         }

                        }.start();

                    }

                }catch (Exception e){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    public void getPress() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Pressure");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //getting values on hashmap from firebase in random order
                List<String> d1 = new ArrayList<>();
                String value;
                //getting values from firebase , setting on array list
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //get value one by one in "value" variable
                    //  value=String.valueOf(postSnapshot.getValue());
                    value="Temp= "+String.valueOf(postSnapshot.getValue());
                   // value=value+" , Pressure = "+postSnapshot.getValue();

                    if (!value.contains("null"))
                        d1.add(value);
                }
                try {
                    if (d1.size()>0)
                    {
                        final int[] i = {0};
                        new CountDownTimer(1000*d1.size(), 1000) {
                            public void onTick(long millisUntilFinished) {
                                Per.setText(" "+d1.get(i[0]));
                                i[0]++;
                            }

                            public void onFinish() {

                            }

                        }.start();

                    }

                }catch (Exception e){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
