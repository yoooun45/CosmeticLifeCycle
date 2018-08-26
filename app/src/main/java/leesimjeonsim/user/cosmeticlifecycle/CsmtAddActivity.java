package leesimjeonsim.user.cosmeticlifecycle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsmtAddActivity extends Activity {
    final int OPEN_DATE = 1;
    final int MAKE_DATE = 2;
    final int END_DATE = 3;

    private List<String> list = new ArrayList<>();
    private Map<String, CsmtData> csmtMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csmt_add);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("csmt");

        final TextView brand = (TextView) findViewById(R.id.brand);
        final TextView product = (TextView) findViewById(R.id.product);
        final TextView category = (TextView) findViewById(R.id.category);

        TextView open = (TextView) findViewById(R.id.openDay);
        TextView make = (TextView) findViewById(R.id.makeDay);
        TextView end = (TextView) findViewById(R.id.endDay);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                csmtMap.clear();
                for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    CsmtData csmt = fileSnapshot.getValue(CsmtData.class);
                    String tmp = fileSnapshot.child("name").getValue(String.class);
                    System.out.println(tmp);

                    list.add(tmp);
                    csmtMap.put(tmp, csmt);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchProduct);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list));

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String search_csmt = autoCompleteTextView.getText().toString();
                CsmtData csmt =  csmtMap.get(search_csmt);
                brand.setText(csmt.brand);
                product.setText(csmt.name);
                category.setText(csmt.category);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(OPEN_DATE); // 날짜 설정 다이얼로그 띄우기
            }
        });

        make.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(MAKE_DATE); // 날짜 설정 다이얼로그 띄우기
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_DATE); // 날짜 설정 다이얼로그 띄우기
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        DatePickerDialog dpd;

        switch (id) {
            case OPEN_DATE:
                dpd = new DatePickerDialog
                        (CsmtAddActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        TextView open = (TextView) findViewById(R.id.openDay);
                                        open.setText(year + "년  " + (monthOfYear + 1) + "월  " + dayOfMonth + "일");
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                2018, 7, 26); // 기본값 연월일, 월은 1달 빼서 넣기
                return dpd;

            case MAKE_DATE:
                dpd = new DatePickerDialog
                        (CsmtAddActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        TextView make = (TextView) findViewById(R.id.makeDay);
                                        make.setText(year + "년  " + (monthOfYear + 1) + "월  " + dayOfMonth + "일");
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                2018, 7, 26); // 기본값 연월일, 월은 1달 빼서 넣기
                return dpd;

            case END_DATE:
                dpd = new DatePickerDialog
                        (CsmtAddActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        TextView end = (TextView) findViewById(R.id.endDay);
                                        end.setText(year + "년  " + (monthOfYear + 1) + "월  " + dayOfMonth + "일");
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                2018, 7, 26 ); // 기본값 연월일, 월은 1달 빼서 넣기
                return dpd;
        }
        return super.onCreateDialog(id);
    }

}
