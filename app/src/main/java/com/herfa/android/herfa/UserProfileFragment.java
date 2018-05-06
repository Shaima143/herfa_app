package com.herfa.android.herfa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CircleImageView profileImage;
    private EditText usernameEditText;
    private Switch switchEdit;
    private TextView userProfileEmail;
    private Button updateProfile;

    private final int IMAGE_CAPTURE_REQUEST_CODE=1;
    private final int SELECT_FILE =2;
    private Bitmap bmp, profile_photo;

    private FirebaseStorage userProfileImage;
    private StorageReference storageReference;


    //BottomNavigationView navigation;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    private OnFragmentInteractionListener mListener;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        View view= inflater.inflate(R.layout.fragment_user_profile, container, false);

        getActivity().setTitle("Profile");

        profileImage = view.findViewById(R.id.user_profile_image);
        usernameEditText = view.findViewById(R.id.editTextNameProfile);
        switchEdit = view.findViewById(R.id.switchEditProfile);
        userProfileEmail=view.findViewById(R.id.tvUserProfileEmail);
        updateProfile=view.findViewById(R.id.buttonUpdateProfile);



        //navigation = view.findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
                                Picasso.with(getContext()).load(info.getUserImageURL()).resize(450,450).into(profileImage);
                                usernameEditText.setText(info.getUsername());
                                userProfileEmail.setText(info.getEmail());
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

        usernameEditText.setEnabled(false);
        updateProfile.setVisibility(View.INVISIBLE);

        switchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchEdit.isChecked()){
                    usernameEditText.setEnabled(true);
                    updateProfile.setVisibility(View.VISIBLE);
                }
                else{
                    usernameEditText.setEnabled(false);
                    updateProfile.setVisibility(View.INVISIBLE);
                }
            }
        });



        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().toString().length()==0){
                    usernameEditText.setError("Please enter a new username");
                    return;
                }

               UserProfileChangeRequest.Builder profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(usernameEditText.getText().toString());
                        //.setPhotoUri();


            }
        });




//        switchEdit.setChecked(false);
//
//        if(switchEdit.isChecked()){
//            usernameEditText.setEnabled(true);
//            updateProfile.setVisibility(View.VISIBLE);
//        }
//        else {
//            usernameEditText.setEnabled(true);
//            updateProfile.setVisibility(View.VISIBLE);
//        }


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
//                     fragment=new LevelsFragment();
//                    break;
//
//                case R.id.navigation_market:
//                    fragment = new MarketFragment();
//                    break;
//
//                case R.id.navigation_achievements:
//                     fragment=new AchievementsFragment();
//                   break;
//
//                case R.id.navigation_profile:
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            bmp = (Bitmap) bundle.get("data");
            profile_photo = Bitmap.createScaledBitmap(bmp,512,512,true);
            profileImage.setImageBitmap(profile_photo);


        }
    }


    private void selectImage(){
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose profile photo");
        AlertDialog.Builder builder1 = builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result = Utility.checkPermission(SignUpActivity.this);

                if (items[item].equals("Take Photo")) {
                    //userChoosenTask = "Take photo";
                    //if(result)
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    // userChoosenTask = "Choose from Library";
                    //if(result)
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });
        builder.show();

    }


    private void cameraIntent(){
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, IMAGE_CAPTURE_REQUEST_CODE);

            }
        });
    }



    private void galleryIntent(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select photo"), SELECT_FILE);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sign_out_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_sign_out_sign_out){
            final String[] items = {String.valueOf(R.string.signout),String.valueOf(R.string.cancel)};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sign out from Herfa app?");
            builder.setPositiveButton(R.string.sign_out, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    firebaseAuth.signOut();
                    //finish();
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }



}
