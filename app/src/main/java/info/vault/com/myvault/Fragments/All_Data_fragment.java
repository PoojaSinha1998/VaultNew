package info.vault.com.myvault.Fragments;



import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import info.vault.com.myvault.Activities.AddNewRecord;
import info.vault.com.myvault.AdapterClass.AllRecordeAdapter;
import info.vault.com.myvault.ApplicationClass;
import info.vault.com.myvault.Dailogs.customDailog;
import info.vault.com.myvault.DatabaseHandler.DBHelper;
import info.vault.com.myvault.Listeners.itemClickListener;
import info.vault.com.myvault.Pojo.RecordsPojo;
import info.vault.com.myvault.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class All_Data_fragment extends Fragment implements View.OnClickListener, AllRecordeAdapter.ItemClickListener {
View mainView;
    FloatingActionButton fab;
    RecyclerView mrecyclerView;
    DBHelper dbHelper;
    itemClickListener itemClick;
    AllRecordeAdapter allRecordeAdapter;
    boolean yes=false;
    AlertDialog dialog;

    public All_Data_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView =  inflater.inflate(R.layout.fragment_all__data_fragment, container, false);
        Log.d("LIFECYCLE","onCreateView");
        dbHelper = new DBHelper(getActivity());

        initilieVariable();

        return  mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIFECYCLE","onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LIFECYCLE","onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LIFECYCLE","onPause");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        allRecordeAdapter.getItemSelected(item);
        return super.onContextItemSelected(item);
    }
    private void initilieVariable() {
        fab = mainView.findViewById(R.id.fab);
        mrecyclerView =mainView.findViewById(R.id.recyclerView);
        fetAllRecordsFromDb();
        fab.setOnClickListener(this);
    }

    public void fetAllRecordsFromDb() {
        String userName= ApplicationClass.getUserName();
        int i =  dbHelper.getAllRecord(userName);
        Log.d("LIFECYCLE","fetchRecord :"+i);
        if(i>0)
        {
            setAdapterOnRecyclerView();
        }
        else
        {
            Log.d("LIFECYCLE","fetchRecord else :");
            if(yes) {
                Log.d("LIFECYCLE","fetchRecord else if:");
                setAdapterOnRecyclerView();
            }
        }
    }

    private void setAdapterOnRecyclerView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(linearLayout);
        allRecordeAdapter = new AllRecordeAdapter(getContext(), ApplicationClass.getRecordsPojoArrayList());
        allRecordeAdapter.setClickListener(this);
        mrecyclerView.setAdapter(allRecordeAdapter);

        allRecordeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Fragment fragment;
        String tag = null;
        FragmentManager fm = getFragmentManager();
        boolean beginFragment= false;
        String fragmentId = null;
        switch (id)
        {
            case R.id.fab:
                Intent intent = new Intent(getActivity(), AddNewRecord.class);
                yes=true;
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(ArrayList<RecordsPojo> item, int i) {
        customDailog myadapterDailogBox = new customDailog();

        Bundle b = new Bundle();
        b.putString("NAME",item.get(i).getName());
        b.putString("URL",item.get(i).getUserId());
        b.putString("PASSWORD",item.get(i).getUserPassword());
        b.putString("DES",item.get(i).getDes());
        myadapterDailogBox.setArguments(b);
      //  myadapterDailogBox.setCancelable(false);
        myadapterDailogBox.show(getFragmentManager(),"pooja");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LIFECYCLE","onResume");
//        if (yes)
//        {
            fetAllRecordsFromDb();
//        }
    }
  /*  @Override
    public void onPinExit(boolean proceed) {
        if(!proceed)
        {
            return;
        }
        else
        {
            allRecordeAdapter.getEditOrDelete();

        }
    }*/


//    @Override
//    public void confirmExit(boolean proceed) {
//        if (proceed)
//        {
//            Log.d("VT","Confirm Listener called");
//            allRecordeAdapter.callShowPassword();
//
//        }
//    }
}
