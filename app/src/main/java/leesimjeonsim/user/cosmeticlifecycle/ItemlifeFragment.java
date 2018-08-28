package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

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
                    ITEMS.add(lifeData);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("fragment", "onActivityResult");
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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase.child("users").child(user.getUid()).child("list").push().setValue(lifeItem);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


    @Override
    public void onListFragmentInteraction(final ItemLifeData item) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        database.getReference().child("users").child(user.getUid()).child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ItemLifeData lifeData = snapshot.getValue(ItemLifeData.class);
                    System.out.println(lifeData.title);
                    if (item.equals(lifeData)) {
                        String key = snapshot.getRef().getKey();
                        System.out.println(key);
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
}
