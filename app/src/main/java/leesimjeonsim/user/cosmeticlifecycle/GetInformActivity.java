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
            mUserfeature.set_Oily(true);
        }
        else if (i == R.id.join_skintype2) {
            mUserfeature.set_Dry(true);
        }
        else if (i == R.id.join_skintype3) {
            mUserfeature.set_Complexity(true);
        }

        else if (i == R.id.join_interest1) {
            mUserfeature.set_Atopy(true);
        }
        else if (i == R.id.join_interest2) {
            mUserfeature.set_Acne(true);
        }
        else if (i == R.id.join_interest3) {
            mUserfeature.set_Sensitivity(true);
        }
        else if (i == R.id.join_interest4) {
            mUserfeature.set_Blush(true);
        }
        else if (i == R.id.join_interest5) {
            mUserfeature.set_Dead(true);
        }
        else if (i == R.id.join_interest6) {
            mUserfeature.set_Pore(true);
        }
        else if (i == R.id.join_interest7) {
            mUserfeature.set_Elasticity(true);
        }

        else if (i == R.id.get_finish_button){
            setUserdata();
            Intent intent = new Intent(getBaseContext(),MainList_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
