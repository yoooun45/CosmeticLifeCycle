package leesimjeonsim.user.cosmeticlifecycle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import leesimjeonsim.user.cosmeticlifecycle.ItemLifeData.LifeItem;
import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private final List<LifeItem> mValues;
    private final ItemlifeFragment.OnListFragmentInteractionListener mListener;

    public ItemRecyclerViewAdapter(List<LifeItem> items, ItemlifeFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        Log.d("TAG","itema");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //ViewHolder생성 부분 자세한건 올린 카톡글(재활용 개념)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        Log.d("TAG","itemb");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //정보를 부착.
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailsView.setText(mValues.get(position).details);
        holder.mD_dayView.setText(mValues.get(position).d_day);

        Log.d("TAG","itemc");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public final TextView mDetailsView;
        public final TextView mD_dayView;
        public LifeItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDetailsView = (TextView) view.findViewById(R.id.details);
            mD_dayView = (TextView) view.findViewById(R.id.d_day);

            Log.d("TAG","itemd");
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
