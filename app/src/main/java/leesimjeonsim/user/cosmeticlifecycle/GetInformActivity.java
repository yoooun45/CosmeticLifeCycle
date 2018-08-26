package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetInformActivity extends Activity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private UserData mUserdata;
    private UserFeature mUserfeature;

    private EditText mAgeText;
    private Button mWomanButton;
    private Button mManButton;

    private Button mOilyButton;
    private Button mDryButton;
    private Button mComplexityButton;

    private Button mAtopyButton;
    private Button mAcneButton;
    private Button mSensitivityButton;

    private Button mBlushButton;
    private Button mDeadButton;
    private Button mPoreButton;

    private Button mElasticityButton;

    private Button mFinishButton;

    private double[][] SkintypeWeight = {
            {0.8,0.1,0.6,0.8,0.5,0.4},//Oil
            {1,0.55,0.6,0.8,0.7,0.6},//Dry
            {0.6,0.6,0.6,0.6,0.6,0.5},//Complexity
            {0.8,0.2,0.6,0.8,0.7,1},//Atopy
            {0.8,0.2,0.6,0.8,0.7,0.8},//Acne
            {0.8,0.1,0.6,0.8,0.7,1},//Sensitivity
            {0.8,0.5,0.6,0.8,0.7,0.7},//Blush
            {0.9,0.8,0.6,0.8,0.7,0.4},//Dead
            {0.8,0.7,0.6,0.8,0.7,0.4},//Pore
            {0.7,0.8,0.6,0.8,0.7,0.4}};//Elasticity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_inform);

        MainActivity LA = (MainActivity)MainActivity._MainActivity;
        LA.finish();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mUserdata = new UserData();
        mUserfeature = new UserFeature();

        mAgeText = findViewById(R.id.join_age);

        mWomanButton = findViewById(R.id.join_woman);
        mManButton = findViewById(R.id.join_man);

        //SkinTipeButtons
        mOilyButton = findViewById(R.id.join_skintype1);
        mDryButton = findViewById(R.id.join_skintype2);
        mComplexityButton = findViewById(R.id.join_skintype3);

        //SkinInterestButtons
        mAtopyButton = findViewById(R.id.join_interest1);
        mAcneButton = findViewById(R.id.join_interest2);
        mSensitivityButton = findViewById(R.id.join_interest3);

        mBlushButton = findViewById(R.id.join_interest4);
        mDeadButton = findViewById(R.id.join_interest5);
        mPoreButton = findViewById(R.id.join_interest6);

        mElasticityButton = findViewById(R.id.join_interest7);

        mFinishButton = findViewById(R.id.get_finish_button);

        //ButtonListerner
        mWomanButton.setOnClickListener(this);
        mManButton.setOnClickListener(this);

        mOilyButton.setOnClickListener(this);
        mDryButton.setOnClickListener(this);
        mComplexityButton.setOnClickListener(this);

        mAtopyButton.setOnClickListener(this);
        mAcneButton.setOnClickListener(this);
        mSensitivityButton.setOnClickListener(this);

        mBlushButton.setOnClickListener(this);
        mDeadButton.setOnClickListener(this);
        mPoreButton.setOnClickListener(this);

        mElasticityButton.setOnClickListener(this);

        mFinishButton.setOnClickListener(this);

    }

    private  void setUserdata() {
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("TAG", user.getUid());
        mUserdata.setAge(Integer.parseInt(mAgeText.getText().toString()));
        mUserdata.setFeature(mUserfeature);
        mUserdata.setUserName("dd");
        mUserdata.setPreference(0);
        mDatabase.child("users").child(user.getUid()).setValue(mUserdata);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.join_woman) {
            mUserdata.setSex(true);
        }
        else if (i == R.id.join_man) {
            mUserdata.setSex(false);
        }

        else if (i == R.id.join_skintype1) {
            mUserfeature.setOily(true);
        }
        else if (i == R.id.join_skintype2) {
            mUserfeature.setDry(true);
        }
        else if (i == R.id.join_skintype3) {
            mUserfeature.setComplexity(true);
        }

        else if (i == R.id.join_interest1) {
            mUserfeature.setAtopy(true);
        }
        else if (i == R.id.join_interest2) {
            mUserfeature.setAcne(true);
        }
        else if (i == R.id.join_interest3) {
            mUserfeature.setSensitivity(true);
        }
        else if (i == R.id.join_interest4) {
            mUserfeature.setBlush(true);
        }
        else if (i == R.id.join_interest5) {
            mUserfeature.setDead(true);
        }
        else if (i == R.id.join_interest6) {
            mUserfeature.setPore(true);
        }
        else if (i == R.id.join_interest7) {
            mUserfeature.setElasticity(true);
        }

        else if (i == R.id.get_finish_button){
            setUserdata();
            Intent intent = new Intent(getBaseContext(),MainList_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
