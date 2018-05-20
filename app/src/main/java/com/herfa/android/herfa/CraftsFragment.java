package com.herfa.android.herfa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CraftsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CraftsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CraftsFragment extends Fragment implements CraftsAdapter.OnAdapterItemClick
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static String param2;
    private static final String ARG_PARAM2 = param2;

    private SharedPreferences sharedPref;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    TextView title;

    //    FirebaseDatabase firebaseDatabase;
//    DatabaseReference craftsRefrence;
//    private Bitmap bmp,Animal_photo;

    ArrayList<CraftsDetails> craftsDetailsArrayList;
    RecyclerView recyclerView;
    private  final int IMAGE_CAPTURE_REQUEST_CODE=1;

    public static int [] images={R.drawable.icon_carpentry,R.drawable.icon_blacksmithing,
            R.drawable.icon_sewing,R.drawable.icon_ceramics,
            R.drawable.icon_omanidagger,R.drawable.icon_omanihalwa};

    private static final int[] craftName={R.string.carpentry,R.string.blacksmithing,
            R.string.sewing, R.string.pottery,
            R.string.omani_dagger,R.string.omani_halwa};

    // TODO: Rename and change types of parameters
    //private String mParam1;
    private int mParam2;

    private CraftsFragment.OnFragmentInteractionListener mListener;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;




    public CraftsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CraftsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CraftsFragment newInstance(String param1, String param2) {
        CraftsFragment fragment = new CraftsFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String ARG_SECTION_NUMBER ="section_number";
        View view= inflater.inflate(R.layout.fragment_crafts, container, false);

        title = view.findViewById(R.id.titleCrafts);
        title.setText(getString(R.string.handcrafts));

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        getActivity().setTitle(getString(R.string.handcrafts));

        recyclerView= view.findViewById(R.id.recyclerView_craftsFragment);



        craftsDetailsArrayList =new ArrayList<>();
        createCraft();
        GridLayoutManager layoutManager=new GridLayoutManager (getContext(),
                2,GridLayoutManager.VERTICAL , false);

        CraftsAdapter adapter= new CraftsAdapter(getContext(),craftsDetailsArrayList);
        adapter.setOnAdapterItemClick(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            String uid = user.getUid();
            String email = user.getEmail();
        }
        else
        {
            Toast.makeText(getContext(), "You are not logged in", Toast.LENGTH_LONG).show();
        }



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()){
                    if (snap.getKey().equals(user.getUid())){
                        databaseReference.child(snap.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                SignUpInfo info= dataSnapshot.getValue(SignUpInfo.class);
                                String uname = info.getUsername();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    public void createCraft(){
        for (int i=0; i<craftName.length;i++){
            CraftsDetails craftsDetails=new CraftsDetails();
            craftsDetails.setCraftNameOne(craftName[i]);
            //craftsDetails.setCraftNameTwo(craftNameTwo[i]);
            craftsDetails.setCraftsImageOne(images[i]);
            //craftsDetails.setCraftsImageTwo(imagesTwo[i]);
            craftsDetailsArrayList.add(craftsDetails);

        }
    }
//    private void displayCraftData(ArrayList<CraftsDetails> craftsDetailsArrayList) {
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL, false);
//        CraftsAdapter adapter=new CraftsAdapter(getContext(),craftsDetailsArrayList);
//        adapter.setOnAdapterItemClick(this);
////        adapter.setOnCraftImageOneClick(this);
////        adapter.setOnCraftImageTwoClick(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
//            Bundle extra = data.getExtras();
//            bmp = (Bitmap) extra.get("data");
//            Animal_photo = Bitmap.createScaledBitmap(bmp, 512, 512, true);
//            mProfileImage.setImageBitmap(Animal_photo);
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
        switch (position){
            case 0:
                //fragment=LevelsFragment.newInstance(craftName[0]);
                fragment=BottomNavigationHomeFragment.newInstance(craftName[0]);
                break;
            case 1:
                Toast.makeText(getContext(), getString(craftName[1])+" "+getString(R.string.comingSoon) , Toast.LENGTH_SHORT).show();
                //fragment=BottomNavigationHomeFragment.newInstance(craftName[1]);
                break;
            case 2:
                Toast.makeText(getContext(), getString(craftName[2])+" "+getString(R.string.comingSoon), Toast.LENGTH_SHORT).show();
                //fragment=BottomNavigationHomeFragment.newInstance(craftName[2]);
                break;
            case 3:
                Toast.makeText(getContext(), getString(craftName[3])+" "+getString(R.string.comingSoon), Toast.LENGTH_SHORT).show();
                //fragment=BottomNavigationHomeFragment.newInstance(craftName[3]);
                break;
            case 4:
                Toast.makeText(getContext(), getString(craftName[4])+" "+getString(R.string.comingSoon), Toast.LENGTH_SHORT).show();
                // fragment=BottomNavigationHomeFragment.newInstance(craftName[4]);
                break;
            case 5:
                Toast.makeText(getContext(), getString(craftName[5])+" "+getString(R.string.comingSoon), Toast.LENGTH_SHORT).show();
                // fragment=BottomNavigationHomeFragment.newInstance(craftName[5]);
                break;
        }

        if(fragment!=null){
            fragmentTransaction.replace(R.id.main_container,fragment);
            //fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
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


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main2, menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
////        if (id == R.id.action_settings) {
////            //Add locale for language change
////            Locale current = getResources().getConfiguration().locale;
////
////            if(current.getLanguage().equalsIgnoreCase(Constants.AR)){
////                changeLangLocale(Constants.EN);
////            }
////            else{
////                changeLangLocale(Constants.AR);
////            }
//////            Intent i = getIntent();
//////            finish();
//////            startActivity(i);
////        }
//
//        if(id == R.id.action_about_us){
//            Toast.makeText(getContext(), R.string.about_us, Toast.LENGTH_SHORT).show();
//        }
//
//        if(id == R.id.action_contact_us){
//            Toast.makeText(getContext(), R.string.contact_us, Toast.LENGTH_SHORT).show();
//        }
//
//        if(id == R.id.action_sign_out){
//            final String[] items = {String.valueOf(R.string.signout),String.valueOf(R.string.cancel)};
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Sign out from Herfa app?");
//            builder.setPositiveButton(R.string.sign_out, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    firebaseAuth.signOut();
//                    //finish();
//                }
//            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//
//            builder.show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    public void changeLangLocale(String en) {
//        Locale myLocale = new Locale(en);
//        Locale.setDefault(myLocale);
//        android.content.res.Configuration config = new android.content.res.Configuration();
//        config.locale = myLocale;
//        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LANG_FILE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(Constants.LANG,en);
//        editor.commit();
//    }



//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }


}
