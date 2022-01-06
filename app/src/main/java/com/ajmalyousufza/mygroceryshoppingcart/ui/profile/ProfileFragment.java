package com.ajmalyousufza.mygroceryshoppingcart.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.ajmalyousufza.mygroceryshoppingcart.models.UserModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profile_image;
    TextView name,email,phone,address;
    Button updateProfile;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_profile,container,false);

       storage = FirebaseStorage.getInstance();
       auth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();

       profile_image = root.findViewById(R.id.profile_image);
       name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        phone = root.findViewById(R.id.profile_phone);
        address = root.findViewById(R.id.profile_address);
        updateProfile = root.findViewById(R.id.profile_updadte_button);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        Glide.with(getContext()).load(userModel.getProfileImg()).into(profile_image);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        profile_image.setOnClickListener(view -> {
            updateUserProfileImage();
        });

        updateProfile.setOnClickListener(view -> {
            updateUserProfile();
        });

        return root;
    }

    private void updateUserProfile() {



    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        //doSomeOperations();
                        if(data.getData()!=null){
                            Uri profileUri = data.getData();
                            profile_image.setImageURI(profileUri);

                            final StorageReference reference = storage.getReference()
                                    .child("profile_picture")
                                    .child(FirebaseAuth.getInstance().getUid());
                            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        database.getReference().child("Users")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child("profileImg").setValue(uri.toString());

                                        Toast.makeText(getContext(), "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                }
                            });

                        }
                    }
                }
            });

    private void updateUserProfileImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        someActivityResultLauncher.launch(intent);
    }

}