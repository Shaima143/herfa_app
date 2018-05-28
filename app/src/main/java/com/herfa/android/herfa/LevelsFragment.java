package com.herfa.android.herfa;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelsFragment extends Fragment implements LevelsAdapter.OnAdapterItemClick{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;

    TextView title, status;
    ImageView backButton;
    ProgressBar progress;

    private int [] levels={R.string.introduction,R.string.basic_tools,
            R.string.the_first_project,R.string.the_second_project,R.string.more_professional_tools};

//    private int[] lock_imags={R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera
//            ,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera};

    private ArrayList<LevelsDetails> carpenteryDetailsList;

    ProgressBar progressBar_Ls;
    TextView userName,craftJunior;
    CircleImageView profileImage;

    //BottomNavigationView navigation;

    //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;


    // TODO: Rename and change types of parameters
    //private String mParam1;
    private int mParam2;

    private OnFragmentInteractionListener mListener;

    public LevelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LevelsFragment newInstance(int param2) {
        LevelsFragment fragment = new LevelsFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_levels, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        title = view.findViewById(R.id.title);
        backButton = view.findViewById(R.id.back_button);
        title.setText(getString(R.string.learn));

        progressBar_Ls=view.findViewById(R.id.progressBar_LevelsFragment);

        status=view.findViewById(R.id.levelsStatus);
        String pr = String.valueOf(progressBar_Ls.getProgress());
        status.setText(pr+" %");

        //toolbar.setNavigationIcon(R.drawable.icon_carpentry);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        //getActivity().setTitle(getString(R.string.levels));


        //toolbar.setTitle(getString(R.string.app_name));
       // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager()
////                        .beginTransaction();
////                Fragment fragment=new CraftsFragment();
////                fragmentTransaction.replace(R.id.main_container,fragment);
////                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
////                fragmentTransaction.commit();
//            }
//        });




        carpenteryDetailsList= new ArrayList<>();

        recyclerView= view.findViewById(R.id.recyclerView_carpentryLevels);

        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        profileImage = view.findViewById(R.id.profile_image);


        progressBar_Ls= view.findViewById(R.id.progressBar_LevelsFragment);

        userName=view.findViewById(R.id.name_txt_levelsFragment);

        progress=view.findViewById(R.id.progressImage);

        craftJunior=view.findViewById(R.id.craftJunior);
        craftJunior.setText(getString(mParam2)+" junior");

        //navigation = view.findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        createlist();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager
                (getContext(),LinearLayoutManager.VERTICAL,false);
        LevelsAdapter carpenteryLevelsAdapter=new LevelsAdapter
                (getContext(),carpenteryDetailsList);
        carpenteryLevelsAdapter.setOnAdapterItemClick(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(carpenteryLevelsAdapter);
        //displayLevelsData(carpenteryDetailsList);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()){
                    if (snap.getKey().equals(user.getUid())){
                        databaseReference.child(snap.getKey()) .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               SignUpInfo info= dataSnapshot.getValue(SignUpInfo.class);
                               //Toast.makeText(getContext(),info.getUserImageURL(),Toast.LENGTH_LONG).show();
                                Picasso.with(getContext()).load(info.getUserImageURL()).resize(430,430).into(profileImage);
                               // userName.setText(info.getUsername());
                                progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }) ;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if(user != null){
            String uid = user.getUid();
            //String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            //userName.setText(name);
            //profileImage.setImageURI(photoUrl);
            //Toast.makeText(getContext(), "logged in "+uid, Toast.LENGTH_LONG).show();
            //Toast.makeText(getContext(), "hiiiii "+email, Toast.LENGTH_LONG).show();
        }
        else
        {

            Toast.makeText(getContext(), "You are not logged in", Toast.LENGTH_LONG).show();
        }


        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        String uname = firebaseUser.getDisplayName();
        //Toast.makeText(getContext(), ""+uname,Toast.LENGTH_LONG).show();
        userName.setText(uname);



        return view;

    }




    public void createlist() {
        for (int i = 0; i < levels.length; i++) {
            LevelsDetails carpenteryDetails = new LevelsDetails();
            carpenteryDetails.setLevel(levels[i]);
            //carpenteryDetails.setImage(lock_imags[i]);
            carpenteryDetailsList.add(carpenteryDetails);

        }
    }
//    private void displayLevelsData(ArrayList<LevelsDetails> carpenteryDetailsList) {
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL, false);
//        LevelsAdapter adapter=new LevelsAdapter(getContext(),carpenteryDetailsList);
//        adapter.setOnAdapterItemClick(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }

//    public void onMyFavoriteClick(int position) {
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference=firebaseDatabase.getReference("My Favourites");
//        Pet pet=petsArrayList.get(position);
//        String key=databaseReference.push().getKey();
//        databaseReference.child(key).setValue(pet);
//
//    }

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

    public void onAdapterItemClick(int position) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment=new LevelFragment();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(int position) {
        Fragment fragment=null;
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager()
                .beginTransaction();
        switch (position){
            case 0:
                fragment=LevelFragment.newInstance(mParam2,levels[0]);
                break;
            case 1:
                fragment=LevelFragment.newInstance(mParam2,levels[1]);
                break;
            case 2:
                fragment=LevelFragment.newInstance(mParam2,levels[2]);
                break;
            case 3:
                fragment=LevelFragment.newInstance(mParam2,levels[3]);
                break;
            case 4:
                fragment=LevelFragment.newInstance(mParam2,levels[4]);
                break;
            case 5:
                fragment=LevelFragment.newInstance(mParam2,levels[5]);
                break;
        }

        if(fragment!=null){
            fragmentTransaction.replace(R.id.main_container,fragment);
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



    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            final String str = firebaseAuth.getCurrentUser().getUid();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if(firebaseUser != null){
                Toast.makeText(getContext(), ""+str, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), "you are not signed in", Toast.LENGTH_SHORT).show();
            }
        }
    };


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
//                    fragment=new UserProfileFragment();
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
