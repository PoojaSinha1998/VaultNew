package info.vault.com.myvault.Dailogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import info.vault.com.myvault.Activities.ShowPasssword;
import info.vault.com.myvault.ApplicationClass;
import info.vault.com.myvault.DatabaseHandler.DBHelper;
import info.vault.com.myvault.Listeners.ConfirmListener;
import info.vault.com.myvault.R;


public class customDailog extends DialogFragment {
    View mainView;
    Button m_close;
    EditText pass;
    private ConfirmListener mListener;
    Bundle b;
    DBHelper dbHelper;
    String pin, uPin;
    ImageView mClose;
    String Args1, Args2, Args3, Args4;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.custom_dailog, container, false);
        dbHelper = new DBHelper(getContext());
        b = new Bundle();
        m_close = mainView.findViewById(R.id.proceed);
        pass = mainView.findViewById(R.id.Pro_pin);
        pass.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //  mClose = mainView.findViewById(R.id.close);

        Args1 = getArguments().getString("NAME");
        Args2 = getArguments().getString("URL");
        Args3 = getArguments().getString("PASSWORD");
        Args4 = getArguments().getString("DES");

        pin = dbHelper.getPinForPassword(ApplicationClass.getUserName());



        m_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPin = pass.getText().toString();

                if (uPin.length() == 0) {
                    Toast.makeText(getActivity(), "Please provide pin.", Toast.LENGTH_LONG).show();
                    return;
                }


                if (uPin.equals(pin)) {

                  //  mListener.confirmExit(true);
                    b.putString("NAME",Args1);
                    b.putString("URL",Args2);
                    b.putString("PASSWORD",Args3);
                    b.putString("DES",Args4);
                    Intent intent = new Intent(getActivity(), ShowPasssword.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    getDialog().dismiss();
                } else if (!uPin.equals(pin)) {
                    Toast.makeText(getActivity(), "Please provide correct pin.", Toast.LENGTH_LONG).show();
                    pass.setText("");
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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mListener = (ConfirmListener) getParentFragment();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement confirmListener");
//        }
//    }
}
