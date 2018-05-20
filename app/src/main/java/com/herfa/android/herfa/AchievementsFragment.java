package com.herfa.android.herfa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AchievementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AchievementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementsFragment extends Fragment implements AchievementsAdapter.OnAdapterItemClick{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView title;

    ArrayList<AchievementsDetails> achievementsDetailsArrayList;
    AchievementsDetails achievementsDetails;
    RecyclerView recyclerView;
    View view;

    public int []achievements;

    private OnFragmentInteractionListener mListener;

    //BottomNavigationView navigation;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AchievementsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AchievementsFragment newInstance(String param1, String param2) {
        AchievementsFragment fragment = new AchievementsFragment();
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
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);

        title = view.findViewById(R.id.title);
        title.setText(getString(R.string.achievements));

        //getActivity().setTitle(getString(R.string.achievements));


              achievements = new int[]{R.drawable.wood_badge1, R.drawable.wood_badge2, R.drawable.wood_badge3,
                R.drawable.wood_badge2, R.drawable.wood_badge3, R.drawable.wood_badge1,
                R.drawable.wood_badge1, R.drawable.wood_badge1, R.drawable.wood_badge2,
                R.drawable.wood_badge3, R.drawable.wood_badge2, R.drawable.wood_badge3,
                R.drawable.wood_badge2, R.drawable.wood_badge1, R.drawable.wood_badge3,
                R.drawable.wood_badge1, R.drawable.wood_badge3, R.drawable.wood_badge2};

        recyclerView = view.findViewById(R.id.recyclerView_achievements);

        //achievementsDetailsArrayList = new ArrayList<>();
       // createAchievements();

        GridLayoutManager layoutManager=new GridLayoutManager (getContext(),
                3,GridLayoutManager.VERTICAL , false);

        AchievementsAdapter achievementsAdapter = new AchievementsAdapter(getContext(), achievements);
        achievementsAdapter.setOnAdapterItemClick(this);

        recyclerView.setAdapter(achievementsAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //navigation = view.findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return view;
    }

    private void createAchievements() {
        for(int i=0; i<achievements.length;i++){
            AchievementsDetails achievementsDetails = new AchievementsDetails();
            achievementsDetails.setBadge(achievements[i]);
            achievementsDetailsArrayList.add(achievementsDetails);
        }
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

    @Override
    public void onItemClick(int position) {
        Fragment fragment=null;
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager()
                .beginTransaction();

        fragment=AchievementDetailsFragment.newInstance(achievements[position]);
        //fragment=BottomNavigationHomeFragment.newInstance(images[position]);
//
        if(fragment!=null){
            fragmentTransaction.replace(R.id.frame_container,fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }
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
