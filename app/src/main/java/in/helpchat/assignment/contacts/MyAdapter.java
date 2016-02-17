package in.helpchat.assignment.contacts;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;


/**
 * Created by admin on 15/02/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Contacts> mDataset;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView icon;
        public RelativeLayout clickable;

        public ViewHolder(View v) {
            super(v);
            icon=(ImageView) v.findViewById(R.id.icon);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            clickable=(RelativeLayout) v.findViewById(R.id.clickable);
        }
    }

    public void add(int position, Contacts item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Contacts item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }


    public void edit(Contacts item) {
        int id=item.getID();
        Bundle bundle=new Bundle();
        String id_sent=String.valueOf(id);
        bundle.putString("id", id_sent);
        Fragment fragment=new DetailView();
        fragment.setArguments(bundle);
        MainActivity mainActivity=(MainActivity) context;
        mainActivity.switchContentHistory(R.id.list_view, fragment);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Contacts> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        context=parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Contacts contact = mDataset.get(position);
        holder.txtHeader.setText(contact.getName());
        holder.txtFooter.setText(contact.getPhoneNumber());
        if(contact.getImagePath()!=null){
            holder.icon.setImageURI(Uri.parse(contact.getImagePath()));
        }
        else{
            int id2 = context.getResources().getIdentifier("ic_launcher", "mipmap", context.getPackageName());
            holder.icon.setImageResource(id2);
        }
        holder.clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(contact);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}