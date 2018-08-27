package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leesimjeonsim.user.cosmeticlifecycle.ItemLifeData;
import leesimjeonsim.user.cosmeticlifecycle.ItemLifeData.LifeItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemlifeFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column_count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;

    static final int[] a = { 100,200 };
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
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프래그먼트의 화면을 만듬
        View view = inflater.inflate(R.layout.fragment_itemlife, container, false);

        // Set the adapter
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new ItemRecyclerViewAdapter(ItemLifeData.ITEMS, mListener));

        //Set floatbutton
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CsmtAddActivity.class);
                startActivity(intent);
            }
        });

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
    public interface OnListFragmentInteractionListener {
        //리스트를 선택했을때 일을 여기서 처리할수 있도록 함
        // TODO: Update argument type and name
        void onListFragmentInteraction(LifeItem item);
    }
}
