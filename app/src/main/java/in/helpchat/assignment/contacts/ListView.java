package in.helpchat.assignment.contacts;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.List;

/**
 * Created by admin on 15/02/16.
 */


public class ListView extends Fragment{

    ProgressBar progress;
    Context context;
    private RecyclerView mRecyclerView;
    CollectAndDisplay task;
    boolean started=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_view, container, false);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        context=getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        final DatabaseHandler db=new DatabaseHandler(context);

        List<Contacts> contacts = db.getAllContacts();
        if(contacts==null){
            progress.setVisibility(View.VISIBLE);
            progress.bringToFront();
            task=new CollectAndDisplay();
            task.execute();
            started=true;
        }
        else{
            progress.setVisibility(View.GONE);
            MyAdapter mAdapter = new MyAdapter(contacts);
            mRecyclerView.setAdapter(mAdapter);
        }

        ImageButton fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new AddView();
                fragment.setArguments(null);
                MainActivity mainActivity=(MainActivity) context;
                mainActivity.switchContentHistory(R.id.list_view, fragment);
            }
        });
        return view;
    }


    private class CollectAndDisplay extends AsyncTask<Void,Void,Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            Looper.prepare();
            ContactReader creator=new ContactReader();
            creator.createContacts(context);
            return 1;
        }

        protected void onPostExecute(Integer result) {
            Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_LONG).show();
            DatabaseHandler db=new DatabaseHandler(context);
            List<Contacts> contacts = db.getAllContacts();
            MyAdapter mAdapter = new MyAdapter(contacts);
            mRecyclerView.setAdapter(mAdapter);
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(started) {
            task.cancel(true);
            task.isCancelled();
        }
    }
}
