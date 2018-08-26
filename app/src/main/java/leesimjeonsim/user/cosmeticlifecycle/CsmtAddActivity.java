package leesimjeonsim.user.cosmeticlifecycle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class CsmtAddActivity extends Activity {
    final int OPEN_DATE = 1;
    final int MAKE_DATE = 2;
    final int END_DATE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csmt_add);

        TextView open = (TextView) findViewById(R.id.openDay);
        TextView make = (TextView) findViewById(R.id.makeDay);
        TextView end = (TextView) findViewById(R.id.endDay);

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
