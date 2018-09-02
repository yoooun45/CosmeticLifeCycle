package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link rcmdFragmend.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link rcmdFragmend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rcmdFragmend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    public List<ItemLifeData> userListITEMS = new ArrayList<>();
    public List<CsmtData> CsmtITEMS = new ArrayList<>();
    public List<rcmdItemData> ITEMS = new ArrayList<>();
    public HashMap<Integer, Double> ScoreList = new HashMap<>();
    public UserFeature feature = new UserFeature();
    public rcmdRecyclerViewAdapter adapter;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    private double[][] SkintypeWeight = {//피부타입별 가중치표
            {0.8, 0.1, 0.6, 0.8, 0.5, 0.4},//Oil
            {1, 0.55, 0.6, 0.8, 0.7, 0.6},//Dry
            {0.6, 0.6, 0.6, 0.6, 0.6, 0.5},//Complexity
            {0.8, 0.2, 0.6, 0.8, 0.7, 1},//Atopy
            {0.8, 0.2, 0.6, 0.8, 0.7, 0.8},//Acne
            {0.8, 0.1, 0.6, 0.8, 0.7, 1},//Sensitivity
            {0.8, 0.5, 0.6, 0.8, 0.7, 0.7},//Blush
            {0.9, 0.8, 0.6, 0.8, 0.7, 0.4},//Dead
            {0.8, 0.7, 0.6, 0.8, 0.7, 0.4},//Pore
            {0.7, 0.8, 0.6, 0.8, 0.7, 0.4}};//Elasticity

    public rcmdFragmend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rcmdFragmend.
     */
    // TODO: Rename and change types and number of parameters
    public static rcmdFragmend newInstance(String param1, String param2) {
        rcmdFragmend fragment = new rcmdFragmend();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("TAG", "onCreate success");
        //Start

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rcmd, container, false);

        Log.d("TAG", "rcmdonCreateview success");
        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.rcmdlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new rcmdRecyclerViewAdapter(ITEMS, mListener);
        mRecyclerView.setAdapter(adapter);

        setUserListITEMS();
        setCsmtITEMS();
        setFeature();


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setUserListITEMS() {

        Log.d("TAG", "파이어베이스 리스너 시작");
        //사용자 등록 화장품 리스트 저장 시작
        database.getReference().child("users").child(user.getUid()).child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userListITEMS.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemLifeData lifeData = snapshot.getValue(ItemLifeData.class);

                    lifeData.d_day = TodoDday(lifeData.end_day);
                    int Dday = Integer.parseInt(lifeData.d_day.substring(2));
                    if(Dday < 60 && lifeData.d_day.contains("D-") || lifeData.d_day.contains("D+")) {
                        userListITEMS.add(lifeData);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //사용자 등록 화장품 리스트 저장 끝
    }


    public void setCsmtITEMS() {
        //화장품 정보 저장
        database.getReference().child("csmt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CsmtITEMS.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CsmtData Data = snapshot.getValue(CsmtData.class);
                    CsmtITEMS.add(Data);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setFeature() {
        //사용자 특성 저장

        database.getReference().child("users").child(user.getUid()).child("feature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().equals("Acne"))
                        feature.Acne =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Pore"))
                        feature.Pore =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Elasticity"))
                        feature.Elasticity =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Dead"))
                        feature.Dead =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Blush"))
                        feature.Blush =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Sensitivity"))
                        feature.Sensitivity =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Atopy"))
                        feature.Atopy =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Complexity"))
                        feature.Complexity =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Dry"))
                        feature.Dry =snapshot.getValue(Boolean.class);
                    if(snapshot.getKey().equals("Oily"))
                        feature.Oily = snapshot.getValue(Boolean.class);
                }

                //추천제품을 알려줄 사용자의 화장품 정보 확인
                //사용자 화장품의 디데이 정보확인해서 추천하는것 구현 필요 및 추가 필요
                for (int i = 0; i < userListITEMS.size(); i++) {
                    //디데이 확인구현하기
                    setScoreList(i,userListITEMS.get(i).category);
                    //카테고리 user의List에 따라 더 구현할것

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setScoreList(int i,String category) {
        //ScoreList에 정보를 넣고 처리
        if (userListITEMS.get(i).content.contains(category)) {//카테고리 체크
            ScoreList.clear();
            for (int j = 0; j < CsmtITEMS.size(); j++) {
                if (CsmtITEMS.get(j).category.contains(category)) {//화장품 db 카테고리 별로 작동
                    ScoreList.put(j, CsmtITEMS.get(j).Score(feature.calFinalFeature(SkintypeWeight)));//점수 내기
                    System.out.println(CsmtITEMS.get(j).name+CsmtITEMS.get(j).Score(feature.calFinalFeature(SkintypeWeight)));
                }
            }
            //점수에 따라 내림차순으로 정렬
            Iterator it = sortByValue(ScoreList).iterator();
            rcmdItemData itemdata = new rcmdItemData();
            int n = 0;
            itemdata.name = userListITEMS.get(i).title;
            itemdata.rcmd_dday = userListITEMS.get(i).d_day;
            while (it.hasNext() && n < 3) {
                Integer temp = (Integer) it.next();
                System.out.println("Score :"+n+" "+temp+" "+CsmtITEMS.get(temp).name+ScoreList.get(temp));
                switch (n) {
                    case 0:
                        itemdata.item_id1 = CsmtITEMS.get(temp).image;
                        itemdata.item_name1 = CsmtITEMS.get(temp).name;
                        itemdata.item_brand1 = CsmtITEMS.get(temp).brand;
                        break;
                    case 1:
                        itemdata.item_id2 = CsmtITEMS.get(temp).image;
                        itemdata.item_name2 = CsmtITEMS.get(temp).name;
                        itemdata.item_brand2 = CsmtITEMS.get(temp).brand;
                        break;
                    case 2:
                        itemdata.item_id3 = CsmtITEMS.get(temp).image;
                        itemdata.item_name3 = CsmtITEMS.get(temp).name;
                        itemdata.item_brand3 = CsmtITEMS.get(temp).brand;
                        break;
                }
                n++;
            }
            ITEMS.add(itemdata);
            listSort();
            adapter.notifyDataSetChanged();
        }
    }


    public List sortByValue(final HashMap map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });
        //Collections.reverse(list); // 주석시 오름차순
        return list;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        //리스트를 선택했을때 일을 여기서 처리할수 있도록 함
        // TODO: Update argument type and name

        void onListFragmentInteraction(rcmdItemData item);

    }
    public String TodoDday (String end_day) {
        StringTokenizer tokenizer = new StringTokenizer(end_day);
        String syear = tokenizer.nextToken(".");
        String smonth = tokenizer.nextToken(".");
        String sday = tokenizer.nextToken(".");
        String Dday;

        int myear = Integer.parseInt(syear);
        int mmonth = Integer.parseInt(smonth)-1;
        int mday = Integer.parseInt(sday);

        try {
            Calendar todaCal = Calendar.getInstance(); //오늘날짜 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날짜를 가져와 변경시킴

            //mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear, mmonth, mday);// D-day의 날짜를 입력

            //        checkEnd = mFormat.format(ddayCal.getTime());

            //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long today = todaCal.getTimeInMillis() / 86400000;
            long dday = ddayCal.getTimeInMillis() / 86400000;
            long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.

            if (count >= 0) {
                 Dday = "D-"+ count;
            } else {
                int absR = (int)Math.abs(count);
                Dday = "D+"+absR;
            }
            return Dday;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void listSort() {
        Collections.sort(ITEMS, new Comparator<rcmdItemData>() {
            @Override
            public int compare(rcmdItemData o1, rcmdItemData o2) {
                int day1 = Integer.parseInt(o1.rcmd_dday.substring(2));
                int day2 = Integer.parseInt(o2.rcmd_dday.substring(2));

                // 오름 차순
                if(o1.rcmd_dday.contains("D-") && o2.rcmd_dday.contains("D-")) {
                    if (day1 > day2) {
                        return 1;
                    } else if (day1 < day2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
                // 내림 차순
                else if (o1.rcmd_dday.contains("D+") && o2.rcmd_dday.contains("D+")) {
                    if (day1 < day2) {
                        return 1;
                    } else if (day1 > day2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
                else {
                    if (o1.rcmd_dday.contains("D-")) {
                        return 1;
                    } else if (o1.rcmd_dday.contains("D+")) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });
    }


}
