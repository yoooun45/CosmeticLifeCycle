package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemlifeFragment extends Fragment implements OnListFragmentInteractionListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column_count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    public  List<ItemLifeData> ITEMS = new ArrayList<>();
    private FirebaseDatabase database;


    static final int[] a = { 100,200 };
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    String checkEnd;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemlifeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemlifeFragment newInstance(int columnCount) {
        //프래그먼트를 만드는 함수
        ItemlifeFragment fragment = new ItemlifeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment", "onCreate");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프래그먼트의 화면을 만듬
        View view = inflater.inflate(R.layout.fragment_itemlife, container, false);

        fab = view.findViewById(R.id.fab);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        final ItemRecyclerViewAdapter itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(ITEMS, this);
        mRecyclerView.setAdapter(itemRecyclerViewAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        database.getReference().child("users").child(user.getUid()).child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ITEMS.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ItemLifeData lifeData = snapshot.getValue(ItemLifeData.class);

                    StringTokenizer tokenizer = new StringTokenizer(lifeData.end_day);
                    String syear = tokenizer.nextToken(".");
                    String smonth = tokenizer.nextToken(".");
                    String sday = tokenizer.nextToken(".");

                    System.out.println(syear+"."+smonth+"."+sday);

                    int myear = Integer.parseInt(syear);
                    int mmonth = Integer.parseInt(smonth);
                    int mday = Integer.parseInt(sday);

                    int Dday = TodoDday(myear, mmonth-1, mday);
                    if (Dday >= 0) {
                        lifeData.d_day = "D-"+Dday;
                    } else {
                        int absR = Math.abs(Dday);
                        lifeData.d_day = "D+"+absR;
                    }
                    ITEMS.add(lifeData);
                    listSort();

                }
                itemRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CsmtAddActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        Log.d("fragment", "onCreateView");
        return view;
    }


    @Override
    public void onAttach(Context context) {
        //프래그먼트가 엑티비티에 부착될때 호출되는 함수
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("fragment", "onActivityResult");

        if(resultCode == 1) {
            System.out.println("fragment" + data.getStringExtra("name"));
            ItemLifeData lifeItem = new ItemLifeData();
            lifeItem.id = data.getStringExtra("image");
            lifeItem.title = data.getStringExtra("name");
            String brand = data.getStringExtra("brand");
            String category = data.getStringExtra("category");
            lifeItem.content = brand + "-" + category;
            lifeItem.category = category;
            String open = data.getStringExtra("open");
            String end = data.getStringExtra("end");
            lifeItem.details = open + " ~ " + end;
            lifeItem.end_day = end;


            mDatabase = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();

            FirebaseUser user = mAuth.getCurrentUser();
            mDatabase.child("users").child(user.getUid()).child("list").push().setValue(lifeItem);

            //FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.detach(this).attach(this).commit();
            ((MainList_Activity)getActivity()).refresh();
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    @Override
    public void onListFragmentInteraction(final ItemLifeData item) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();
        database.getReference().child("users").child(user.getUid()).child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ItemLifeData lifeData = snapshot.getValue(ItemLifeData.class);
                    System.out.println("lifeData "+lifeData.title);
                    System.out.print("item "+item.title);

                    if (item.equals(lifeData)) {
                        String key = snapshot.getRef().getKey();
                        System.out.println(key);
                        mDatabase.child("users").child(user.getUid()).child("list").child(key).removeValue();
                        //한번 삭제후 화면 refresh
                        ((MainList_Activity)getActivity()).refresh();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        System.out.println(item.content);
    }


    public int TodoDday (int myear, int mmonth, int mday) {
        try {
            Calendar todaCal = Calendar.getInstance(); //오늘날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날짜를 가져와 변경시킴

            //mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear, mmonth, mday);// D-day의 날짜를 입력
            Log.e("테스트", mFormat.format(todaCal.getTime()) + "");
            Log.e("테스트", mFormat.format(ddayCal.getTime()));

    //        checkEnd = mFormat.format(ddayCal.getTime());

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

    public void listSort() {
        Collections.sort(ITEMS, new Comparator<ItemLifeData>() {
            @Override
            public int compare(ItemLifeData o1, ItemLifeData o2) {
                int day1 = Integer.parseInt(o1.d_day.substring(2));
                int day2 = Integer.parseInt(o2.d_day.substring(2));

                // 오름 차순
                if(o1.d_day.contains("D-") && o2.d_day.contains("D-")) {
                    if (day1 > day2) {
                        return 1;
                    } else if (day1 < day2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
                // 내림 차순
                else if (o1.d_day.contains("D+") && o2.d_day.contains("D+")) {
                    if (day1 < day2) {
                        return 1;
                    } else if (day1 > day2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
                else {
                    if (o1.d_day.contains("D-")) {
                        return 1;
                    } else if (o1.d_day.contains("D+")) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });
    }
}
