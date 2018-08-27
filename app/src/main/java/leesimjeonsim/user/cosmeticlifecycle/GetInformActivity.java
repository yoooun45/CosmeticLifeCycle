package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    private CheckBox mOilyButton;
    private CheckBox mDryButton;
    private CheckBox mComplexityButton;

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

        MainActivity LA = (MainActivity) MainActivity._MainActivity;
        LA.finish();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mUserdata = new UserData();
        mUserfeature = new UserFeature();

        mAgeText = findViewById(R.id.join_age);

        mWomanButton = findViewById(R.id.join_woman);
        mManButton = findViewById(R.id.join_man);

        //SkinTipeButtons
        mOilyButton = findViewById(R.id.oily_skin);
        mDryButton = findViewById(R.id.dry_skin);
        mComplexityButton = findViewById(R.id.complexity_skin);

        //SkinInterestButtons
        mAtopyButton = findViewById(R.id.atopy_interest);
        mAcneButton = findViewById(R.id.acne_interest);
        mSensitivityButton = findViewById(R.id.sensitivity_interest);

        mBlushButton = findViewById(R.id.blush_interest);
        mDeadButton = findViewById(R.id.dead_interest);
        mPoreButton = findViewById(R.id.pore_interest);

        mElasticityButton = findViewById(R.id.elasticity_interest);

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

    private void setUserdata() {
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
        } else if (i == R.id.join_man) {
            mUserdata.setSex(false);
        } else if (i == R.id.oily_skin) {

            if (((CheckBox) v).isChecked()) {
                mUserfeature.set_Oily(true);
                mDryButton.setChecked(false);
                mUserfeature.set_Dry(false);
                mComplexityButton.setChecked(false);
                mUserfeature.set_Complexity(false);
            } else {
                mUserfeature.set_Oily(false);
            }

        } else if (i == R.id.dry_skin) {

            if (((CheckBox) v).isChecked()) {
                mUserfeature.set_Dry(true);
                mOilyButton.setChecked(false);
                mUserfeature.set_Oily(false);
                mComplexityButton.setChecked(false);
                mUserfeature.set_Complexity(false);
            } else {
                mUserfeature.set_Dry(false);
            }

        } else if (i == R.id.complexity_skin) {

            if (((CheckBox) v).isChecked()) {
                mUserfeature.set_Complexity(true);
                mOilyButton.setChecked(false);
                mUserfeature.set_Oily(false);
                mDryButton.setChecked(false);
                mUserfeature.set_Dry(false);
            } else {
                mUserfeature.set_Complexity(false);
            }

        } else if (i == R.id.atopy_interest) {

            if (mUserfeature.Atopy == true) {
                mUserfeature.set_Atopy(false);
            } else {
                mUserfeature.set_Atopy(true);
            }

        } else if (i == R.id.acne_interest) {

            if (mUserfeature.Acne == true) {
                mUserfeature.set_Acne(false);
            } else {
                mUserfeature.set_Acne(true);
            }

        } else if (i == R.id.sensitivity_interest) {

            if (mUserfeature.Sensitivity == true) {
                mUserfeature.set_Sensitivity(false);
            } else {
                mUserfeature.set_Sensitivity(true);
            }

        } else if (i == R.id.blush_interest) {

            if (mUserfeature.Blush == true) {
                mUserfeature.set_Blush(false);
            } else {
                mUserfeature.set_Blush(true);
            }

        } else if (i == R.id.dead_interest) {

            if (mUserfeature.Dead == true) {
                mUserfeature.set_Dead(false);
            } else {
                mUserfeature.set_Dead(true);
            }

        } else if (i == R.id.pore_interest) {

            if (mUserfeature.Pore == true) {
                mUserfeature.set_Pore(false);
            } else {
                mUserfeature.set_Pore(true);
            }

        } else if (i == R.id.elasticity_interest) {

            if (mUserfeature.Elasticity == true) {
                mUserfeature.set_Elasticity(false);
            } else {
                mUserfeature.set_Elasticity(true);
            }

        } else if (i == R.id.get_finish_button) {
            setUserdata();
            Intent intent = new Intent(getBaseContext(), MainList_Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
