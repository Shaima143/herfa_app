package com.herfa.android.herfa;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuInflater;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


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

    private CircleImageView UserprofileImage;
    private EditText usernameEditText, emailEditText;
    private Switch switchEdit;
    private TextView userProfileEmail, logout, status;
    private Button updateProfile, deleteProfile, changePass, changeEmail;
    private ProgressBar progressBar;
    private ImageView settingsBtn, back, edit, iconChangeProfileImage;;
    private TextView title;

    Fragment fragment;

    private final int IMAGE_CAPTURE_REQUEST_CODE=1;
   // private final int SELECT_FILE =2;
    private final static int RESULT_LOAD_IMAGE = 6;
    private Bitmap bmp, profile_photo;

    private FirebaseStorage userProfileImage;
    private StorageReference storageReference;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private Boolean presssed = false;
    int count =0;


    //BottomNavigationView navigation;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

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

        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_user_profile, container, false);

        title = view.findViewById(R.id.titleProfile);
        title.setText(getString(R.string.profile));

        settingsBtn = view.findViewById(R.id.settingsButton);

        //usernameEditText.setBackgroundColor(Color.TRANSPARENT);

        getActivity().setTitle(getString(R.string.profile));



        UserprofileImage = view.findViewById(R.id.user_profile_image);
        usernameEditText = view.findViewById(R.id.editTextNameProfile);
        emailEditText = view.findViewById(R.id.editTextUserEmail);
        //switchEdit = view.findViewById(R.id.switchEditProfile);
        //userProfileEmail=view.findViewById(R.id.tvUserProfileEmail);
        updateProfile=view.findViewById(R.id.buttonUpdateProfile);
        deleteProfile = view.findViewById(R.id.buttonDeleteProfile);
        changePass = view.findViewById(R.id.BtnProfilechangePass);
        changeEmail = view.findViewById(R.id.BtnProfilechangeEmail);
        progressBar = view.findViewById(R.id.progressBarProfile);
        status = view.findViewById(R.id.tvProfileStatus);
        edit = view.findViewById(R.id.editIcon);
        iconChangeProfileImage = view.findViewById(R.id.iconAdd);

        UserprofileImage.setClickable(false);


        usernameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        deleteProfile.setVisibility(view.INVISIBLE);
        updateProfile.setVisibility(View.INVISIBLE);
        iconChangeProfileImage.setVisibility(view.INVISIBLE);
        UserprofileImage.setClickable(false);


        UserprofileImage.setImageAlpha(204);


        if(count==0){
            edit.setColorFilter(ContextCompat.getColor(getContext(),
                    R.color.grey));
        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    edit.setColorFilter(ContextCompat.getColor(getContext(),
                    R.color.grey));
                    usernameEditText.setEnabled(false);
                    emailEditText.setEnabled(false);
                    updateProfile.setVisibility(View.INVISIBLE);
                    deleteProfile.setVisibility(View.INVISIBLE);
                    usernameEditText.setBackground(null);
                    iconChangeProfileImage.setVisibility(View.INVISIBLE);

                    UserprofileImage.setClickable(false);
                   // UserprofileImage.setImageAlpha((int) 1.0);
                    count++;

                }

               // count=count+1;

                else if(count==1){
                    edit.setColorFilter(ContextCompat.getColor(getContext(),
                            R.color.lightBrown));
                    usernameEditText.setEnabled(true);
                    emailEditText.setEnabled(true);
                    updateProfile.setVisibility(View.VISIBLE);
                    deleteProfile.setVisibility(View.VISIBLE);
                    iconChangeProfileImage.setVisibility(View.VISIBLE);
                    usernameEditText.setBackground(Drawable.createFromPath(String.valueOf(R.drawable.edittextshape)));

                    UserprofileImage.setClickable(true);
                   // UserprofileImage.setImageAlpha((int) 0.5);
                    count=0;
                }



            }
        });



        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getContext(), settingsBtn); //you can use image button
                // as btnSettings on your GUI after
                //clicking this button pop up menu will be shown

                popup.getMenuInflater().inflate(R.menu.main2, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Fragment fragment = null;
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        switch (item.getItemId()) {
                            case R.id.action_about_us:
                                fragment = new AboutUsFragment();
                                fragmentTransaction.replace(R.id.frame_container,fragment);
                                fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
                                fragmentTransaction.commit();

                                break;

//                            case R.id.action_contact_us:
//                                fragment = new ContactUsFragment();
//                                fragmentTransaction.replace(R.id.main_container,fragment);
//                                fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
//                                fragmentTransaction.commit();
//                                Toast.makeText(getContext(), "contact", Toast.LENGTH_SHORT).show();
//                                break;

                            case R.id.action_sign_out:
                                final String[] items = {String.valueOf(R.string.signout),String.valueOf(R.string.cancel)};

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Sign out from Herfa app?");
                                builder.setPositiveButton(R.string.sign_out, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseAuth.getInstance().signOut();
                                        getActivity().finish();
                                        System.exit(0);
                                    }
                                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.show();

                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });

                popup.show();

            }
        });



        String pr = String.valueOf(progressBar.getProgress());
        status.setText(pr+" %");


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        String Useremail = firebaseUser.getEmail();
        emailEditText.setText(Useremail);

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
                                Picasso.with(getContext()).load(info.getUserImageURL()).resize(400,400).into(UserprofileImage);
                                usernameEditText.setText(info.getUsername());
                                //emailEditText.setText(info.getEmail());
                                //userProfileEmail.setText(info.getEmail());
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

        //usernameEditText.setBackground(null);



//        iconChangeProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent();
////                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_CAPTURE_REQUEST_CODE);
//
//            }
        //});

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(emailEditText.getText().toString().length()==0){
                    emailEditText.setError("Please enter a new username");
                    return;
                }

                showDialog();
            }
        });


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = usernameEditText.getText().toString();
                updateProfile(uname);

//                v.startAnimation(buttonClick);
//                if(usernameEditText.getText().toString().length()==0){
//                    usernameEditText.setError("Please enter a new username");
//                    return;
//                }
//
//                showDialog();

//                final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
//                        .getReference("UserInfo").child("username");
//
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String newUsername = usernameEditText.getText().toString();
//                        databaseReference.setValue(newUsername);
//                        Toast.makeText(getContext(), "updated", Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });





//                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(
//                        usernameEditText.getText().toString()).build();
//
//                firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                            Toast.makeText(getContext(), "updated", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
           }
        });






        firebaseAuth = FirebaseAuth.getInstance();

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = emailEditText.getText().toString();
               // Toast.makeText(getContext(), UserEmail, Toast.LENGTH_SHORT).show();

                firebaseAuth.sendPasswordResetEmail(UserEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Check your email to reset password",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Failed to send reset password email",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });


  deleteProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Account from Herfa app?");
        builder.setPositiveButton(R.string.delete_account, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //delete picture from the storage
                storageReference=FirebaseStorage.getInstance().getReference();
                //Toast.makeText(getContext(),user.getUid(),Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Your Herfa account was deleted", Toast.LENGTH_LONG).show();
                StorageReference ref=storageReference.child("users/"+user.getUid()+"/profileImage.png");
                //Toast.makeText(getContext(),ref.getPath(),Toast.LENGTH_LONG).show();
                Log.d("path",user.getUid());
                //storageReference=FirebaseStorage.getInstance().getReferenceFromUrl("gs://herfa-app.appspot.com/users/J3CA61Ux40ZyTobpm8gpJ1sDpLr2/profileImage.png");
                ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Profile photo deleted", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess: deleted file");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: did not delete file");
                    }
                });
                //delete user info from Authintication
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("TAG","OK! Account Deleted!");
                        //delete info of user from the realtime
                        DatabaseReference refr = FirebaseDatabase.getInstance().getReference();
                        refr.child("UserInfo").child(user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        getActivity().finish();
                        System.exit(0);
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"An error occurred", e);
                    }
                });
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();


    }
});



     UserprofileImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             selectImage();
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




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            bmp = (Bitmap) bundle.get("data");
            profile_photo = Bitmap.createScaledBitmap(bmp,512,512,true);
            UserprofileImage.setImageBitmap(profile_photo);
        }

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                //bmp = getBitmapFromUri(selectedImage);
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            profile_photo = Bitmap.createScaledBitmap(bmp, 512, 512, true);
            UserprofileImage.setImageBitmap(profile_photo);

        }
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main2, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

//        if(id == R.id.action_sign_out_sign_out){
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

        return super.onOptionsItemSelected(item);
    }




    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.show();

        final EditText currentEmailEdittext = dialog.findViewById(R.id.et_dialog_email);
        final EditText editPwd = dialog.findViewById(R.id.et_dialog_password);
        Button dialogBtnCancel = dialog.findViewById(R.id.btn_cancel);
        Button dialogBtnOk = dialog.findViewById(R.id.btn_ok);

        dialogBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialogBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Query query=databaseReference.child("UserInfo").orderByChild("email").
                        equalTo(currentEmailEdittext.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap:dataSnapshot.getChildren()){
                            SignUpInfo signUpInfo=snap.getValue(SignUpInfo.class);
                           signUpInfo.setEmail(emailEditText.getText().toString());
                           databaseReference.setValue(signUpInfo);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                AuthCredential credential = EmailAuthProvider.getCredential(currentEmailEdittext.getText().toString(),
                        editPwd.getText().toString());

                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            firebaseUser.updateEmail(emailEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Email updated", Toast.LENGTH_SHORT).show();


                                        dialog.cancel();
                                    } else {
                                        // Should log in recently to change the email
                                        Toast.makeText(getContext(), task.getException() + "", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//
                        } else {
                            Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }


    private void selectImage(){
        //final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.select_from_gallery),
                getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.choose_profile_photo));
        AlertDialog.Builder builder1 = builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result = Utility.checkPermission(SignUpActivity.this);

                if (items[item].equals(getString(R.string.take_photo))) {

                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, IMAGE_CAPTURE_REQUEST_CODE);
                }
                else if (items[item].equals(getString(R.string.select_from_gallery))) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                }
                else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();

                }
            }
        });
        builder.show();

    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    private boolean updateProfile (String username){
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo").child(username);
        databaseReference.setValue(username);

        Toast.makeText(getContext(), "Updated",Toast.LENGTH_LONG).show();
        return  true;
    }
}
