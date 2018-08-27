package leesimjeonsim.user.cosmeticlifecycle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.EndElementListener;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

public class CsmtAddActivity extends Activity {
    final int OPEN_DATE = 1;
    final int MAKE_DATE = 2;
    final int END_DATE = 3;

    long mNow;
    Date mDate;
    // SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd"); // Log 출력때만 쓰임.

    GregorianCalendar today = new GregorianCalendar();

    private int mDdayOpen = 0;
    private int mDdayMake = 0;
    private int mDdayEnd = 0;
    private int mDdayReal = 0;

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

        final TextView open = (TextView) findViewById(R.id.openDay);
        final TextView make = (TextView) findViewById(R.id.makeDay);
        final TextView end = (TextView) findViewById(R.id.endDay);

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchProduct);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                csmtMap.clear();
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
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

        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list));

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String search_csmt = autoCompleteTextView.getText().toString();
                CsmtData csmt = csmtMap.get(search_csmt);
                brand.setText(csmt.brand);
                product.setText(csmt.name);
                category.setText(csmt.category);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
            }
        });


        Button button = (Button) findViewById(R.id.add_finish_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("brand", brand.getText().toString());
                intent.putExtra("product", product.getText().toString());
                intent.putExtra("category", category.getText().toString());
                intent.putExtra("name", autoCompleteTextView.getText().toString());
                intent.putExtra("open", open.getText().toString());
                intent.putExtra("make", make.getText().toString());
                intent.putExtra("end", end.getText().toString());

                final String search_csmt = autoCompleteTextView.getText().toString();
                CsmtData csmt =  csmtMap.get(search_csmt);
                intent.putExtra("image", csmt.image);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        open.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(OPEN_DATE); // 날짜 설정 다이얼로그 띄우기
                //   mTextView.setText(getTime());
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

  /*    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        mDate.getYear();

        return mFormat.format(mDate);
    }
    */


    // D-day 계산하는 함수
    public int countdday(int id, int myear, int mmonth, int mday) {
        String string = "a";
        // 제조일 기준, 카테고리에 따라 myear, mmonth 에 넣는 수가 달라짐.
        switch (string) {
            case "Skin":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Lotion":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Cream":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Cleansing Form":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Cleansing Water":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Cleansing Oil":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Sunscreen":
                mmonth = mmonth + 6;  // 제조일로부터 6개월
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Foundation":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Cushion":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Concealer":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Powder":
                myear = myear + 3;  // 제조일로부터 3년
                break;

            case "Lipstick":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Lip Tint":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Lip Balm":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Eyeliner":
                myear = myear + 1;  // 제조일로부터 1년
                break;

            case "Mascara":
                mmonth = mmonth + 6;  // 제조일로부터 6개월
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

            case "Shadow":
                myear = myear + 1;  // 제조일로부터 1년 6개월
                mmonth = mmonth + 6;
                if (mmonth >= 12) {
                    mmonth = mmonth - 12;
                    myear = myear + 1;
                }
                break;

        }
        switch (id) {
            case OPEN_DATE:
                TextView open = (TextView) findViewById(R.id.openDay);
                open.setText(myear + "년  " + (mmonth + 1) + "월  " + mday + "일");
                break;
            case MAKE_DATE:
                TextView make = (TextView) findViewById(R.id.makeDay);
                make.setText(myear + "년  " + (mmonth + 1) + "월  " + mday + "일");
                myear = myear + 3;    // 제조일로부터 3년
                break;
            case END_DATE:
                TextView end = (TextView) findViewById(R.id.endDay);
                end.setText(myear + "년  " + (mmonth + 1) + "월  " + mday + "일");
                break;
        }

        try {
            Calendar todaCal = Calendar.getInstance(); //오늘날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날짜를 가져와 변경시킴

            //mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear, mmonth, mday);// D-day의 날짜를 입력
            Log.e("테스트", mFormat.format(todaCal.getTime()) + "");
            Log.e("테스트", mFormat.format(ddayCal.getTime()) + "");

            //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long today = todaCal.getTimeInMillis() / 86400000;
            long dday = ddayCal.getTimeInMillis() / 86400000;
            long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.

            return (int) count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int pickDday() {
        if (mDdayMake == 0 && mDdayEnd == 0) {
            mDdayReal = mDdayOpen;
        } else if (mDdayEnd == 0) {
            if (mDdayOpen < mDdayMake)
                mDdayReal = mDdayOpen;
            else mDdayReal = mDdayMake;
        } else {
            if (mDdayOpen < mDdayEnd)
                mDdayReal = mDdayOpen;
            else mDdayReal = mDdayEnd;
        }
        Toast.makeText(CsmtAddActivity.this, "D" + mDdayReal, Toast.LENGTH_SHORT).show();
        return mDdayReal;
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
                                        mDdayOpen = countdday(OPEN_DATE, year, monthOfYear, dayOfMonth);
                                        pickDday();
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                today.get(today.YEAR), today.get(today.MONTH), today.get(today.DAY_OF_MONTH)); // 기본값 연월일, 월은 1달 빼서 넣기
                return dpd;

            case MAKE_DATE:
                dpd = new DatePickerDialog
                        (CsmtAddActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {

                                        mDdayMake = countdday(MAKE_DATE, year, monthOfYear, dayOfMonth);
                                        pickDday();
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                today.get(today.YEAR), today.get(today.MONTH), today.get(today.DAY_OF_MONTH)); // 기본값 연월일, 월은 1달 빼서 넣기
                return dpd;

            case END_DATE:
                dpd = new DatePickerDialog
                        (CsmtAddActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {

                                        mDdayEnd = countdday(END_DATE, year, monthOfYear, dayOfMonth);
                                        pickDday();
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                today.get(today.YEAR), today.get(today.MONTH), today.get(today.DAY_OF_MONTH)); // 기본값 연월일, 월은 1달 빼서 넣기
                return dpd;
        }

        return super.onCreateDialog(id);
    }
}
