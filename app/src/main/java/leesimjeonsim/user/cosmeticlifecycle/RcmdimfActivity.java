package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RcmdimfActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name;
    TextView brand;
    Button cancel;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcmdimf);
        System.out.println("setcontent");
        intent = getIntent();
        System.out.println("intent");
        setContent();
    }
    private void setContent() {
        imageView = (ImageView)findViewById(R.id.imfimageView);
        name = (TextView)findViewById(R.id.imfname);
        brand = (TextView)findViewById(R.id.imfbrand);
        cancel = (Button)findViewById(R.id.imf_cancel);
        Glide.with(imageView.getContext()).load(intent.getStringExtra("image")).into(imageView);
        name.setText(intent.getStringExtra("name"));
        brand.setText(intent.getStringExtra("brand"));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
