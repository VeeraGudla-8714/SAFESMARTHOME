package ca.team.safe.smart.home.it.safe.smart.home;

import static ca.team.safe.smart.home.it.safe.smart.home.MainActivity.viewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    EditText mFullName, mEmail, mPassword;
    Button mregisterBtn;
    TextView loginBtn;
    FirebaseAuth fAuth;
    private static View mView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean secureIDValidation(String secureID) {
        if (secureID.length() != 9) {
            // Snackbar.make(mView, "Please enter 9 digit Secure ID", Snackbar.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    static ProgressBar progressBar_register;

    public static String streetAddress, city, provinces, postalcode, country;

    public void getCustomerAddress() {
        databaseReference = firebaseDatabase.getReference("secureID0").child("customer_address");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> map = (Map<String, String>) snapshot.getValue();

                try {
                    streetAddress = map.get("streetAddress");
                    city = map.get("city");
                    provinces = map.get("provinces");
                    postalcode = map.get("postalcode");
                    country = map.get("country");
                } catch (Exception e) {
                }
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_register, container, false);

        progressBar_register = mView.findViewById(R.id.progressBar_register);
        mFullName = mView.findViewById(R.id.register_name);
        mEmail = mView.findViewById(R.id.register_username);
        mPassword = mView.findViewById(R.id.register_password);
        Button registerbutton = mView.findViewById(R.id.registerbutton);
        Button loginbtn = mView.findViewById(R.id.loginbtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), LoginSep.class);
                startActivity(i);
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String secureID = mFullName.getText().toString().trim();

                register(email, password, secureID);
            }
        });

        return mView;


    }

    public static boolean register(String email, String password, String secureID) {
        final int[] chance = {0};

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        if (!email.contains("gmail.com")) {
            Log.e("validation error", "please enter valid email address");
            Snackbar.make(mView, R.string.correct_email, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Snackbar.make(mView, R.string.correct_password, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 8 || !isValidPassword(password)) {

            Snackbar.make(mView, R.string.Password_characters, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (!secureIDValidation(secureID)) {
            Snackbar.make(mView, R.string.Enter_9_digit_secID, Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            // myRef.setValue(secureID);
//            progressBar_register.setVisibility(View.VISIBLE);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    DatabaseReference myRef1 = database.getReference("secureID" + snapshot.getChildrenCount());
                    if (chance[0] == 0) {
                        chance[0] = 1;
                        myRef1.setValue(secureID);
                        try {
                            Snackbar.make(viewPager, R.string.secureID_added_to_FirebaseDB, Snackbar.LENGTH_SHORT).show();
                        } catch (Exception e) {
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // if the data is not added or it is cancelled then
                    // we are displaying a failure toast message.
//                    Toast.makeText(getActivity(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                    progressBar_register.setVisibility(View.GONE);
                }
            });

            return true;
        }
    }


}

