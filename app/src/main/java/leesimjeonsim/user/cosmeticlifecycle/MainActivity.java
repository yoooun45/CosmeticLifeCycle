package leesimjeonsim.user.cosmeticlifecycle;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public static Activity _MainActivity;
    private static final int RC_SIGN_IN = 10;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mJoinButton;
    private Button mGoogleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addOnConnectionFailedListener(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        _MainActivity = MainActivity.this;
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_pw);
        mLoginButton = findViewById(R.id.login_button);
        mJoinButton = findViewById(R.id.join_button);
        mGoogleButton = findViewById(R.id.google_login);

        mLoginButton.setOnClickListener(this);
        mJoinButton.setOnClickListener(this);
        mGoogleButton.setOnClickListener(this);

       // passPushTokenToServer();
    }

    /****** 푸시 메세지 *******

    void passPushTokenToServer() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("pushToken", token);

        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);


    }
    ***** 푸시 메세지 끝 *******/
    @Override
    protected void onStart() {
        super.onStart();
        // add Checking if user is signed in (non-null) and update UI accordingly.

       // mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
        if (mAuthListener != null) {
            //  mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                FirebaseUser user = mAuth.getCurrentUser();
                                boolean already = false;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot Snapshot : dataSnapshot.getChildren()){
                                        String str = Snapshot.getKey().toString();
                                        Log.d("TAG", "dataSnapshot.getValue"+str);
                                        if(user.getUid().toString().equals(str)){
                                            already = true;
                                        }
                                    }
                                    if(already == false){
                                        Intent intent = new Intent(getBaseContext(),GetInformActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Intent intent = new Intent(getBaseContext(),MainList_Activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    // [END auth_with_google]

    private void loginAccount(String email, String password) {
        Log.d("TAG", "loginAccount access");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "로그인 성공",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(),MainList_Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "아이디와 비밀번호를 확인해 주세요",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        Log.d("TAG", "onClick success");
        if (i == R.id.join_button) {
            Log.d("TAG", "createID button success");
            Intent intent = new Intent(getBaseContext(),JoinActivity.class);
            startActivity(intent);
            finish();
        }
        else if (i == R.id.login_button){
            Log.d("TAG", "login button success");
            loginAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        else if (i == R.id.google_login){
            Log.d("TAG", "Google login button success");
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}