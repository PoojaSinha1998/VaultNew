package info.vault.com.myvault.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import info.vault.com.myvault.Listeners.HomePageListener;
import info.vault.com.myvault.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePin extends Fragment {
    HomePageListener mListener;
    EditText m_password, m_old_pin, m_new_pin;
    String s_password, s_oldpin, s_newpin;
    View mainView;


    public ChangePin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_change_pin, container, false);


        return mainView;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomePageListener) {
            mListener = (HomePageListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomePageListener");
        }
        mListener.resetHomePage();
    }

}
