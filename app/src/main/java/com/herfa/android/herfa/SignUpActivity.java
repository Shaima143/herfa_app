package com.herfa.android.herfa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.icu.util.ULocale.getDisplayName;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private CircleImageView profileImage;
    private ImageView cameraIcon;
    private final int IMAGE_CAPTURE_REQUEST_CODE = 1;
    //private final int SELECT_FILE =2;
    //private static final String IMAGE_DIRECTORY = "/demonuts";
    //private final int RESULT_LOAD_IMAGE = 5;

    private final static int RESULT_LOAD_IMAGE = 6;


    private int PICK_IMAGE_REQUEST = 2;
    private Bitmap bmp, profile_photo;

    private FirebaseStorage userProfileImage;
    private StorageReference storageReference;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference newUser;
    FirebaseUser firebaseUser;

    private EditText signUpEmail, signUpPwd, signUpReenterPwd, signUpUsername;
    private Switch isCraftsmanUser, isBuyerUser, isDeliveryUser;
    BlurImageView blurImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.sign_up);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();   //node
        firebaseDatabase = FirebaseDatabase.getInstance();
        newUser = firebaseDatabase.getReference("UserInfo");   //branch

        signUpEmail = findViewById(R.id.editTextEmailSignUp);
        signUpPwd = findViewById(R.id.editTextPasswordSignUp);
        signUpReenterPwd = findViewById(R.id.editTextReEnterPasswordSignUp);
        signUpUsername = findViewById(R.id.editTextUsernameSignUp);
        isCraftsmanUser = findViewById(R.id.switch1);
        isBuyerUser = findViewById(R.id.switch2);
        isDeliveryUser = findViewById(R.id.switch3);

        Button btnAlreadyRegistered = findViewById(R.id.buttonAlreadyRegistered);
        Button btnCreateAccount = findViewById(R.id.buttonCreateAccount);

        profileImage = findViewById(R.id.profile_image);

        blurImageView=findViewById(R.id.BlurImageView);
        blurImageView.setBlur(5);
        //cameraIcon = (ImageView)findViewById(R.id.camIcon);



        //validations
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUpUsername.getText().toString().length() == 0) {
                    signUpUsername.setError(getString(R.string.enter_name));
                    return;
                }
                if (signUpEmail.getText().toString().length() == 0) {
                    signUpEmail.setError(getString(R.string.enter_email));
                    return;
                }

                if (signUpPwd.getText().toString().length() == 0) {
                    signUpPwd.setError(getString(R.string.enter_pwd));
                    return;
                }
                if (profile_photo == null) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.select_or_capture_image), Toast.LENGTH_LONG).show();
                    return;
                }

                if (signUpPwd.getText().toString().length() < 7) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.pwd_should_be_more_that_7), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isCraftsmanUser.isChecked()==false && isBuyerUser.isChecked()==false && isDeliveryUser.isChecked()==false){
                    Toast.makeText(SignUpActivity.this, R.string.please_select_at_least_one_user_type,Toast.LENGTH_SHORT).show();
                    //isCraftsmanUser.setError(getString(R.string.please_select_at_least_one));
                    return;

                }


                if (signUpPwd.getText().toString().equals(signUpReenterPwd.getText().toString())) {

                    firebaseAuth.createUserWithEmailAndPassword(signUpEmail.getText().toString(),
                            signUpPwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //final String uid = user.getUid();
                                final String str = firebaseAuth.getCurrentUser().getUid();
                                //Toast.makeText(SignUpActivity.this, str, Toast.LENGTH_SHORT).show();

                                userProfileImage = FirebaseStorage.getInstance();
                                storageReference = userProfileImage.getReference();

                                //StorageReference imageRef = storageReference.child("user_profile_image").child(getSaltString());
                                StorageReference imageRef = storageReference.child("users/" + str + "/profileImage.png");

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();  //convert photo
                                profile_photo.compress(Bitmap.CompressFormat.PNG, 20, baos);
                                byte[] data1 = baos.toByteArray();


                               // firebaseUser.getPhotoUrl();

                                UploadTask uploadTask = imageRef.putBytes(data1);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        Log.d("image URL", taskSnapshot.getDownloadUrl().toString()); //testing

                                        String key = newUser.push().getKey();


                                        SignUpInfo signUpInfo = new SignUpInfo();
                                        signUpInfo.setUsername(signUpUsername.getText().toString());
                                        signUpInfo.setUserImageURL(taskSnapshot.getDownloadUrl().toString());

                                        if (isCraftsmanUser.isChecked()) {
                                            signUpInfo.setCraftmanUser(true);
                                        } else {
                                            signUpInfo.setCraftmanUser(false);
                                        }

                                        if (isBuyerUser.isChecked()) {
                                            signUpInfo.setBuyerUser(true);
                                        } else {
                                            signUpInfo.setBuyerUser(false);
                                        }

                                        if (isDeliveryUser.isChecked()) {
                                            signUpInfo.setDeliveryUser(true);
                                        } else {
                                            signUpInfo.setDeliveryUser(false);
                                        }


                                        //signUpInfo.setIsCraftmanUser(isCraftsmanUser.getText().toString());
                                        // signUpInfo.setIsBuyerUser(isBuyerUser.getText().toString());
                                        // signUpInfo.setIsDeliveryUser(isDeliveryUser.getText().toString());

                                        newUser.child(str).setValue(signUpInfo);
                                        //newUser.setValue(signUpInfo);

                                        Toast.makeText(SignUpActivity.this, getString(R.string.account_created_successfully), Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(i);

//                                firebaseUser = firebaseAuth.getCurrentUser();
//                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            //Log.d(TAG, "Email sent.");
//                                            Toast.makeText(SignUpActivity.this, "Verification Email sent", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                });






                            } else {
                                Toast.makeText(SignUpActivity.this, R.string.user_reg_failed, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, R.string.passwords_dont_match, Toast.LENGTH_LONG).show();
                }

            }


        });





        btnAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bmp = (Bitmap) bundle.get("data");
            profile_photo = Bitmap.createScaledBitmap(bmp, 512, 512, true);
            profileImage.setImageBitmap(profile_photo);

        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
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
            profileImage.setImageBitmap(profile_photo);

        }



        //Uri selectedImageURI = data.getData();

//            Uri imageUri = data.getData();
//            profileImage.setImageURI(imageUri);

//            Picasso.with(this).load(data.getData()).resize(200, 200)
//                    .into(profileImage);


//            Uri contentURI = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                String path = saveImage(bitmap);
//                Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                profileImage.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
        }


//             Uri selectedImage = data.getData();
//             String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//             Cursor cursor = getContentResolver().query(selectedImage,
//                     filePathColumn, null, null, null);
//             cursor.moveToFirst();
//
//             int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//             String picturePath = cursor.getString(columnIndex);
//             cursor.close();
//
//             //ImageView imageView = (ImageView) findViewById(R.id.imgView);
//             profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));





//            Bundle bundle = data.getExtras();
//            bmp = (Bitmap) bundle.get("data");
//            profile_photo = Bitmap.createScaledBitmap(bmp,512,512,true);
//            profileImage.setImageBitmap(profile_photo);
    //}


//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }
//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Add locale for language change
            Locale current = getResources().getConfiguration().locale;

            if(current.getLanguage().equalsIgnoreCase(Constants.AR)){
                changeLangLocale(Constants.EN);
            }
            else{
                changeLangLocale(Constants.AR);
            }
            Intent i = getIntent();
            finish();
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    private void selectImage(){
        //final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.select_from_gallery),
                getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle(getString(R.string.choose_profile_photo));
        AlertDialog.Builder builder1 = builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result = Utility.checkPermission(SignUpActivity.this);

                if (items[item].equals(getString(R.string.take_photo))) {
                    //cameraIntent();
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
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(i,"Select photo"), SELECT_FILE);
    }



    public void changeLangLocale(String en) {
        Locale myLocale = new Locale(en);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LANG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LANG,en);
        editor.commit();
    }



    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }





    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }



}
