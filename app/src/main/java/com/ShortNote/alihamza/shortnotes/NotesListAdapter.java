package com.ShortNote.alihamza.shortnotes;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ShortNote.alihamza.shortnotes.Data.NotesContract;

import java.util.LinkedList;

/**
 * Created by Ali Hamza on 10/14/2017.
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {
    private Context mContext;

    private Cursor mCursor;
    private final ForecastAdapterOnClickHandler mClickhandler;

    public interface ForecastAdapterOnClickHandler {
        void onClick(String name, String defination);
    }


    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    // TODO (9) Update the Adapter constructor to accept an integer for the count along with the context
    public NotesListAdapter(Context context, Cursor cursor, ForecastAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        // TODO (10) Set the local mCount to be equal to count
        this.mCursor = cursor;
        mClickhandler = clickHandler;

    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.notes_list_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        // Call getString on the cursor to get the title
        String name = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TTILE_NAME));
        // Call getString on the cursor to get the defination
        String partySize = mCursor.getString(mCursor.getColumnIndex(NotesContract.NotesEntry.COLUMN__DEFINATION));
        long id = mCursor.getLong(mCursor.getColumnIndex(NotesContract.NotesEntry._ID));
        // Set the holder's nameTextView text to the title
        // Display the guest name
        holder.nameTextView.setText(name);
        //Set the holder's partySizeTextView text to the defination
        // Display the party count
        holder.definationTextView.setText(partySize);
        holder.itemView.setTag(id);


    }


    // TODO (11) Modify the getItemCount to return the mCount value rather than 0
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView nameTextView;
        // Will display the party size number
        TextView definationTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link NotesListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public NotesViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            definationTextView = (TextView) itemView.findViewById(R.id.defination_text_view);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            String name = nameTextView.getText().toString();
            String size = definationTextView.getText().toString();
            mClickhandler.onClick(name, size);

        }


    }

}
