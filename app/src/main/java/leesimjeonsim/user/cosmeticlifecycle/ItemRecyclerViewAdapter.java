package leesimjeonsim.user.cosmeticlifecycle;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<ItemLifeData> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ItemRecyclerViewAdapter(List<ItemLifeData> items, ItemlifeFragment listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //ViewHolder생성 부분 자세한건 올린 카톡글(재활용 개념)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //정보를 부착.
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailsView.setText(mValues.get(position).details);
        holder.mD_dayView.setText(mValues.get(position).d_day);
        Glide.with(holder.mImageView.getContext()).load(mValues.get(position).id).into(holder.mImageView);

        String strDay = (String) holder.mD_dayView.getText();
        int Dday = Integer.parseInt(strDay.substring(2));
        if (Dday < 7 || strDay.contains("D+")) {
            holder.mD_dayView.setTextColor(Color.RED);
        } else {
            holder.mD_dayView.setTextColor(Color.BLACK);
        }

        holder.mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                System.out.println("delete click22");
            }
        });
        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public final TextView mDetailsView;
        public final TextView mD_dayView;
        public Button mDeleteView;

        public ItemLifeData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDetailsView = (TextView) view.findViewById(R.id.details);
            mD_dayView = (TextView) view.findViewById(R.id.d_day);
            mDeleteView = (Button) view.findViewById(R.id.delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
