package com.herfa.android.herfa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MarketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MarketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //BottomNavigationView navigation;

    ArrayList<MarketDetails> marketDetailsArrayList;
    RecyclerView recyclerViewMarket;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public MarketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarketFragment newInstance(String param1, String param2) {
        MarketFragment fragment = new MarketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        getActivity().setTitle(R.string.market);

        recyclerViewMarket = view.findViewById(R.id.recyclerView_marketFragment);
        marketDetailsArrayList = new ArrayList<>();

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL,false);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3,
                GridLayoutManager.VERTICAL,false);


//        GridLayoutManager layoutManager=new GridLayoutManager (getContext(),
//                3,GridLayoutManager.VERTICAL , false);

        MarketAdapter adapter= new MarketAdapter(getContext(),marketDetailsArrayList);
        //adapter.setOnAdapterItemClick(this);


        recyclerViewMarket.setLayoutManager(layoutManager);
        recyclerViewMarket.setAdapter(adapter);


        //navigation = view.findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            View view;
//            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager()
//                    .beginTransaction();
//            Fragment fragment=null;
//
//            switch (item.getItemId()) {
//
//                case R.id.navigation_home:
//                    fragment=new LevelsFragment();
//                    break;
//
//                case R.id.navigation_market:
//                    fragment = new MarketFragment();
//                    break;
//
//                case R.id.navigation_achievements:
//                    fragment=new AchievementsFragment();
//                    break;
//
//                case R.id.navigation_profile:
//                    fragment = new UserProfileFragment();
//                    break;
//            }
//            if(fragment!=null){
//                fragmentTransaction.replace(R.id.main_container,fragment);
//                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
//                fragmentTransaction.commit();
//            }
//            return false;
//        }
//    };


}
