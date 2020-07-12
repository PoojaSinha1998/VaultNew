package info.vault.com.myvault.Dailogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import info.vault.com.myvault.Activities.AddNewRecord;
import info.vault.com.myvault.ApplicationClass;
import info.vault.com.myvault.DatabaseHandler.DBHelper;
import info.vault.com.myvault.Fragments.All_Data_fragment;
import info.vault.com.myvault.Listeners.ConfirmationListener;
import info.vault.com.myvault.R;


public class validatePin extends DialogFragment {
    View mainView;
    Button m_close;
    EditText pass;
    Bundle b;
    DBHelper dbHelper;
    String pin, uPin;
    ImageView mClose;
    Activity activity;
    private ConfirmationListener confirmationListener;
    private boolean makeEdit = false;
    private boolean makeDelete = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.custom_dailog, container, false);
        dbHelper = new DBHelper(getContext());
        b = new Bundle();
        m_close = mainView.findViewById(R.id.proceed);
        pass = mainView.findViewById(R.id.Pro_pin);
        // show soft keyboard
        pass.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextView dialogTitle = mainView.findViewById(R.id.dialogtitle);

        if(makeDelete)
        {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText("Delete");
        }
       else if(makeEdit)
        {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText("Edit");
        }
        else
        {
            dialogTitle.setVisibility(View.INVISIBLE);
        }
      //  mClose = mainView.findViewById(R.id.close);


        pin = dbHelper.getPinForPassword(ApplicationClass.getUserName());



        m_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPin = pass.getText().toString();
                Log.d("VT", "pin from database : "+pin);
                Log.d("VT", "pin from editText : "+uPin);


                if (uPin.length() == 0) {
                    Toast.makeText(activity, "Please provide pin.", Toast.LENGTH_LONG).show();
                    return;
                }


                if (uPin.equals(pin)) {
//                    Toast.makeText(activity, "Provided pin is correct.", Toast.LENGTH_LONG).show();
                    if(makeEdit)
                    {
                        ApplicationClass.setEdit(true);
                        getDialog().dismiss();
                        Intent i = new Intent(getActivity(), AddNewRecord.class);
                        startActivity(i);

                    }
                    if(makeDelete)
                    {


                        getDialog().dismiss();
                        DeleteData();
                        //((All_Data_fragment)getParentFragment() ).fetAllRecordsFromDb();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        All_Data_fragment parent = (All_Data_fragment)fm.findFragmentByTag("HOME");
                        parent.fetAllRecordsFromDb();


                    }
//                    confirmationListener.onPinExit(true);

                } else if (!uPin.equals(pin)) {
                    pass.setText("");
                    Toast.makeText(activity, "Please provide correct pin.", Toast.LENGTH_LONG).show();
//                    confirmationListener.onPinExit(false);
                }
            }
        });
//        mClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getDialog().dismiss();
//            }
//        });

        return mainView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;

    }





    public void isEdit() {
        makeEdit = true;
    }
    public void isDelete() {
        makeDelete = true;
    }
    public void DeleteData()
    {

        String title = ApplicationClass.getTitle();
        String username = ApplicationClass.getUserTitle();
        String password = ApplicationClass.getPassword();
        final String id = dbHelper.getDataToDelete(title, username, password);



                boolean a = dbHelper.deleteRow(id);


                if (a) {
                    Toast.makeText(activity, "Record deleted successfully.", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(activity, "Record not deleted.", Toast.LENGTH_LONG).show();






    }

}
