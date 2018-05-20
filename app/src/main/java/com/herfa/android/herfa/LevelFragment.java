package com.herfa.android.herfa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelFragment extends Fragment implements LevelsAdapter.OnAdapterItemClick{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Equipements> levelDetailsArrayList;
    private ArrayList<EquipmentsBasicTools> equipmentsBasicTools;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView equi_List;

    //private ArrayList<Equipements> arrayList = new ArrayList<>();
    //private ArrayAdapter<Equipements> adapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView title;

    @Override
    public Object getExitTransition() {
        return super.getExitTransition();
    }

    MediaController mediaController;

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    private OnFragmentInteractionListener mListener;

    public LevelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LevelFragment newInstance(int param1, int param2) {
        LevelFragment fragment = new LevelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level, container, false);

        title = view.findViewById(R.id.title);
        title.setText(getString(R.string.learn));


        final TextView descInfo=view.findViewById(R.id.desc_info_txt);
        final VideoView videoView=view.findViewById(R.id.videoView);
        equi_List=view.findViewById(R.id.eqipment_listview);
        equi_List.setNestedScrollingEnabled(true);
        layoutManager= new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false);

        equi_List.setLayoutManager(layoutManager);

        levelDetailsArrayList=new ArrayList<>();
        equipmentsBasicTools=new ArrayList<>();

        //levelAdapter.setOnAdapterItemClick(this);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mediaController = new MediaController(getContext());

//        String vidURL = ("https://firebasestorage.googleapis.com/v0/b/herfa-app.appspot.com/o/imags%2Fvideos%2FcarpentryVideos%2Fbidirectionally%20flat-foldable%20cellular%20origami%20structure.mp4?alt=media&token=82570964-33ea-4ca0-bbd8-be31cfb5f423");
//        Uri uri = Uri.parse(vidURL);
//
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//        videoView.start();

        //adapter = new ArrayAdapter<Equipements>(getContext(), R.layout.equips_cell,arrayList);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Crafts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get crafts name
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    if(mParam1==R.string.carpentry && mParam2==R.string.introduction){

                       // int craft_name=R.string.carpentry_small;
                        //int craft_level=R.string.introduction_small;

                        //Toast.makeText(getContext(),getString(mParam1),Toast.LENGTH_LONG).show();
                        //chech if craft=current craft (carpentry)
                        if (snap.getKey().equals(getString(mParam1))){
                            //get children of (carpentry) which is Introduction
                            for(DataSnapshot snap2:snap.getChildren()){
                                if(snap2.getKey().equals(getString(mParam2))){
                                    // for(DataSnapshot snap3:dataSnapshot.getChildren()){
                                    Introduction info= snap2.getValue(Introduction.class);
                                    Equipements equip=info.getEquipments();
                                    levelDetailsArrayList.add(equip);

                                    descInfo.setText(info.getDesc());

                                    videoView.setMediaController(mediaController);
                                    videoView.setVideoURI(Uri.parse(info.getVideo()));
                                    videoView.requestFocus();
                                   // videoView.start();
                                }
                            }
                        }
                    }

                     if(mParam1 == R.string.carpentry &&mParam2 == R.string.basic_tools){
                        int craft_name=R.string.carpentry_small;
                        String craft_level= getString(R.string.Basictools);
                       // Toast.makeText(getContext(),getString(mParam1)+getString(mParam2),Toast.LENGTH_LONG).show();
                        if (snap.getKey().equals(getString(mParam1))){
                            //get children of (carpentry) which is Introduction
                            for(DataSnapshot snap2:snap.getChildren()){
                                if(snap2.getKey().equals(craft_level)){
                                    // for(DataSnapshot snap3:dataSnapshot.getChildren()){
                                    Toast.makeText(getContext(),snap2.getKey(),Toast.LENGTH_LONG).show();
                                    BasicTools info= snap2.getValue(BasicTools.class);
                                    EquipmentsBasicTools equip=info.getEquipments();
                                    equipmentsBasicTools.add(equip);

                                    descInfo.setText(info.getDesc());

                                    videoView.setMediaController(mediaController);
                                    videoView.setVideoURI(Uri.parse(info.getVideo()));
                                    videoView.requestFocus();
                                    //videoView.start();
                                }
                            }
                        }
                    }

                    if(mParam1 == R.string.carpentry && mParam2==R.string.the_first_project){
                        int craft_name=R.string.carpentry_small;
                        String craft_level= getString(R.string.Firstproject);

                        if (snap.getKey().equals(getString(mParam1))){
                            //get children of (carpentry) which is Introduction
                            for(DataSnapshot snap2:snap.getChildren()){
                                if(snap2.getKey().equals(craft_level)){
                                    // for(DataSnapshot snap3:dataSnapshot.getChildren()){
                                    Introduction info= snap2.getValue(Introduction.class);
                                    Equipements equip=info.getEquipments();
                                    levelDetailsArrayList.add(equip);

                                    descInfo.setText(info.getDesc());

                                    videoView.setMediaController(mediaController);
                                    videoView.setVideoURI(Uri.parse(info.getVideo()));
                                    videoView.requestFocus();
                                    //videoView.start();


                                }
                            }
                        }

                    }
                    if(mParam1 == R.string.carpentry && mParam2==R.string.the_second_project){
                        int craft_name=R.string.carpentry_small;
                        String craft_level= getString(R.string.Secondproject);

                        if (snap.getKey().equals(getString(mParam1))){
                            //get children of (carpentry) which is Introduction
                            for(DataSnapshot snap2:snap.getChildren()){
                                if(snap2.getKey().equals(craft_level)){
                                    // for(DataSnapshot snap3:dataSnapshot.getChildren()){
                                    Introduction info= snap2.getValue(Introduction.class);
                                    Equipements equip=info.getEquipments();
                                    levelDetailsArrayList.add(equip);

                                    descInfo.setText(info.getDesc());

                                    videoView.setMediaController(mediaController);
                                    videoView.setVideoURI(Uri.parse(info.getVideo()));
                                    videoView.requestFocus();
                                    //videoView.start();


                                }
                            }
                        }

                    }
                    if(mParam1 == R.string.carpentry && mParam2==R.string.more_professional_tools){
                        int craft_name=R.string.carpentry_small;
                        String craft_level= getString(R.string.Moreprotools);

                        if (snap.getKey().equals(getString(mParam1))){
                            //get children of (carpentry) which is Introduction
                            for(DataSnapshot snap2:snap.getChildren()){
                                if(snap2.getKey().equals(craft_level)){
                                    // for(DataSnapshot snap3:dataSnapshot.getChildren()){
                                    Introduction info= snap2.getValue(Introduction.class);
                                    Equipements equip=info.getEquipments();
                                    levelDetailsArrayList.add(equip);

                                    descInfo.setText(info.getDesc());

                                    videoView.setMediaController(mediaController);
                                    videoView.setVideoURI(Uri.parse(info.getVideo()));
                                    videoView.requestFocus();
                                    //videoView.start();


                                }
                            }
                        }

                    }
                }




                if(levelDetailsArrayList.size()>0){
                    LevelAdapter levelAdapter = new LevelAdapter(getContext(),levelDetailsArrayList);
                    equi_List.setLayoutManager(layoutManager);
                    levelAdapter.notifyDataSetChanged();
                    equi_List.setAdapter(levelAdapter);
                }
                //Toast.makeText(getContext(),levelDetailsArrayList.size()+"",Toast.LENGTH_SHORT).show();
                if (equipmentsBasicTools.size()>0){
                    LevelAdapterForBasicTools levelAdapter = new LevelAdapterForBasicTools(getContext(),equipmentsBasicTools);
                    equi_List.setLayoutManager(layoutManager);
                    levelAdapter.notifyDataSetChanged();
                    equi_List.setAdapter(levelAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
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

        //if(position==0){
            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
            Fragment fragment= new CraftsFragment();
            fragmentTransaction.replace(R.id.main_container,fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        //}

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
}
