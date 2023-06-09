package loans;

import android.content.Context;
import android.graphics.Color;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.multilevelview.MultiLevelAdapter;
import com.multilevelview.MultiLevelRecyclerView;
import com.odedtech.mff.client.R;

import java.util.ArrayList;
import java.util.List;

import loans.model.Datum;

public class MyAdapter extends MultiLevelAdapter {

    private final IOnItemClickListener iOnItemClickListener;
    private Holder mViewHolder;
    private Context mContext;
    private List<LoansFragment.Item> mListItems = new ArrayList<>();
    private LoansFragment.Item mItem;
    private MultiLevelRecyclerView mMultiLevelRecyclerView;

    MyAdapter(Context mContext, List<LoansFragment.Item> mListItems, MultiLevelRecyclerView mMultiLevelRecyclerView,
              IOnItemClickListener iOnItemClickListener, IOnNoDataFiltered iOnNoDataFiltered) {
        super(mListItems);
        this.mListItems = mListItems;
        this.mContext = mContext;
        this.iOnItemClickListener = iOnItemClickListener;
        this.mMultiLevelRecyclerView = mMultiLevelRecyclerView;
    }

    private void setExpandButton(ImageView expandButton, boolean isExpanded) {
        // set the icon based on the current state
        expandButton.setImageResource(isExpanded ? R.drawable.ic_keyboard_arrow_down_black_24dp : R.drawable.ic_keyboard_arrow_up_black_24dp);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mViewHolder = (Holder) holder;
        mItem = mListItems.get(position);

        switch (getItemViewType(position)) {
            case 1:
                holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
                break;
            case 2:
                holder.itemView.setBackgroundColor(Color.parseColor("#dedede"));
                break;
            default:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
        mViewHolder.mTitle.setText(mItem.getText());
        if (!TextUtils.isEmpty(mItem.getSecondText())) {
            mViewHolder.mSubtitle.setVisibility(View.VISIBLE);
            mViewHolder.mSubtitle.setText(mItem.getSecondText());
        } else {
            mViewHolder.mSubtitle.setVisibility(View.GONE);
        }

        if (mItem.hasChildren() && mItem.getChildren().size() > 0) {
            setExpandButton(mViewHolder.mExpandIcon, mItem.isExpanded());
            mViewHolder.mExpandButton.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mExpandButton.setVisibility(View.GONE);
        }

        Log.e("MuditLog", mItem.getLevel() + " " + mItem.getPosition() + " " + mItem.isExpanded() + "");

        // indent child items
        // Note: the parent item should start at zero to have no indentation
        // e.g. in populateFakeData(); the very first Item shold be instantiate like this: Item item = new Item(0);
        float density = mContext.getResources().getDisplayMetrics().density;
        ((ViewGroup.MarginLayoutParams) mViewHolder.mTextBox.getLayoutParams()).leftMargin = (int) ((getItemViewType(position) * 20) * density + 0.5f);
    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView mTitle, mSubtitle;
        ImageView mExpandIcon;
        LinearLayout mTextBox, mExpandButton;

        Holder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mSubtitle = itemView.findViewById(R.id.subtitle);
            mExpandIcon = itemView.findViewById(R.id.image_view);
            mTextBox = itemView.findViewById(R.id.text_box);
            mExpandButton = itemView.findViewById(R.id.expand_field);

            // The following code snippets are only necessary if you set multiLevelRecyclerView.removeItemClickListeners(); in MainActivity.java
            // this enables more than one click event on an item (e.g. Click Event on the item itself and click event on the expand button)

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LoansFragment.Item item = mListItems.get(getAdapterPosition());
                    if (item.datum != null) {
                        iOnItemClickListener.onItemClicked(item.datum);
                    } else {
                        mExpandIcon.animate().rotation(item.isExpanded() ? 0 : -180).start();
                    }

                    //set click event on item here
//                    Toast.makeText(mContext, String.format(Locale.ENGLISH, "Item at position %d was clicked!", getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            });
//
//            //set click listener on LinearLayout because the click area is bigger than the ImageView
//            mExpandButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // set click event on expand button here
//                    mMultiLevelRecyclerView.toggleItemsGroup(getAdapterPosition());
//                    // rotate the icon based on the current state
//                    // but only here because otherwise we'd see the animation on expanded items too while scrolling
//                    mExpandIcon.animate().rotation(item.isExpanded() ? -180 : 0).start();
//                }
//            });
        }
    }


    public interface IOnItemClickListener {
        void onItemClicked(Datum datum);
    }

    public interface IOnNoDataFiltered {
        void noDataFiltered(boolean noData);
    }

}