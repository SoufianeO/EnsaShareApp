package com.example.myapplication.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.DAO.AdminDao;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private View view;
    private AdminDao adminDao;


    public void init(){
        adminDao = new AdminDao(view.getContext(), "afaf");
        adminDao.getAdmin();
    }

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_notification, container, false);

        init();

        return view;
    }

}
