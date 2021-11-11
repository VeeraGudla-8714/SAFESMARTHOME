//Veera Gudla N01218714
//Jacob Stephens N01100888
//Patrick Loboda N01309086
package ca.team.safe.smart.home.it.safe.smart.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ReviewFragment extends Fragment{

    Button review;
    EditText editTextName;
    EditText editTextNumber;
    EditText editTextEmail;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        // Inflate the layout for this fragment

        editTextName = (EditText) rootView.findViewById(R.id.editTextTextPersonName);
        editTextNumber = (EditText) rootView.findViewById(R.id.editTextPhone);
        editTextEmail = (EditText) rootView.findViewById(R.id.editTextTextEmailAddress);

        editTextName.addTextChangedListener(reviwTextWatcher);
        editTextNumber.addTextChangedListener(reviwTextWatcher);
        editTextEmail.addTextChangedListener(reviwTextWatcher);

        review = (Button) rootView.findViewById(R.id.review_btn);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Thank you for submiting.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    TextWatcher reviwTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = editTextName.getText().toString().trim();
            String numberInput = editTextNumber.getText().toString().trim();
            String emailInput = editTextEmail.getText().toString().trim();

            review.setEnabled(!nameInput.isEmpty() && !numberInput.isEmpty() && !emailInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
