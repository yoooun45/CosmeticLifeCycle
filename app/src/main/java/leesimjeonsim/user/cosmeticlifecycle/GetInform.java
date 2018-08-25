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

public class GetInform extends Activity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private UserData mUserdata;
    private UserFeature mUserfeature;

    private EditText mAgeText;
    private Button mWomanButton;
    private Button mManButton;
    private Button mInterest1Button;
    private Button mInterest2Button;
    private Button mInterest3Button;
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
        mInterest1Button = findViewById(R.id.join_interest1);
        mInterest2Button = findViewById(R.id.join_interest2);
        mInterest3Button = findViewById(R.id.join_interest3);
        mFinishButton = findViewById(R.id.get_finish_button);

        mWomanButton.setOnClickListener(this);
        mManButton.setOnClickListener(this);
        mInterest1Button.setOnClickListener(this);
        mInterest2Button.setOnClickListener(this);
        mInterest3Button.setOnClickListener(this);
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

        else if (i == R.id.join_interest1) {
            mUserfeature.seta(true);
        }

        else if (i == R.id.join_interest2) {
            mUserfeature.setb(true);
        }

        else if (i == R.id.join_interest3) {
            mUserfeature.setc(true);
        }

        else if (i == R.id.get_finish_button){
            setUserdata();
            Intent intent = new Intent(getBaseContext(),MainList_Activity.class);
            startActivity(intent);
        }
    }
}
