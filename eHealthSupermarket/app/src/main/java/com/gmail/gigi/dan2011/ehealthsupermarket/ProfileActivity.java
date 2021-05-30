package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfileActivity extends AppCompatActivity {

  private Uri filePath;
  private final int PICK_IMAGE_REQUEST = 71;
  //Firebase
  private FirebaseStorage storage;
  private StorageReference storageReference;
  private ImageView profile_pic;
  private FirebaseUser user;
  private RelativeLayout name_field;
  private RelativeLayout email_field;
  private RelativeLayout mobile_field;
  private RelativeLayout direction_field;
  private TextView email_field_edit;
  private TextView mobile_field_edit;
  private TextView direction_field_edit;
  private TextView name_field_edit;
  private TextView user_arroba;
  private TextView email_profile_principal;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    storage = FirebaseStorage.getInstance();
    storageReference = storage.getReference();
    profile_pic = findViewById(R.id.profile_pic);
    // Create a storage reference from our app
    user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageRef = storage.getReference();
    // Create a reference with an initial file path and name
    StorageReference pathReference = storageRef.child("images/" + user.getUid() + ".jpg");
    final long ONE_MEGABYTE = 1024 * 1024;
    pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
      @Override
      public void onSuccess(byte[] bytes) {
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        profile_pic.setImageBitmap(bmp);
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception exception) {
        profile_pic.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.baseline_face_black_48dp));
      }
    });
    name_field = findViewById(R.id.name_field);
    email_field = findViewById(R.id.email_field);
    mobile_field = findViewById(R.id.mobile_field);
    direction_field = findViewById(R.id.direction_field);

    name_field_edit = findViewById(R.id.name_field_edit);
    email_field_edit = findViewById(R.id.email_field_edit);
    mobile_field_edit = findViewById(R.id.mobile_field_edit);
    direction_field_edit = findViewById(R.id.direction_field_edit);

    user_arroba = findViewById(R.id.user_arroba);
    email_profile_principal = findViewById(R.id.email_profile_principal);

    db.collection("USERS").document(user.getUid()).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @RequiresApi(api = VERSION_CODES.O)
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              Map<String, Object> document = task.getResult().getData();
              User actualUser = mapper.convertValue(document, User.class);
              name_field_edit.setText(actualUser.getName());
              mobile_field_edit.setText(actualUser.getPhone_number());
              email_field_edit.setText(actualUser.getEmail());
              user_arroba.setText("@" + actualUser.getName().replace(" ","_"));
              email_profile_principal.setText(actualUser.getEmail());
              String[] intolerancesList = actualUser.getIntolerances().stream()
                  .map(intolerance -> intolerance.getIntolerance_name()).collect(
                      Collectors.toList()).stream().toArray(String[]::new);
              direction_field_edit.setText(String.join(", ", intolerancesList));
            }
          }
        });


    profile_pic.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        chooseImage();
      }
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  private void chooseImage() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
  }


  private void uploadImage() {

    if (filePath != null) {
      final ProgressDialog progressDialog = new ProgressDialog(this);
      progressDialog.setTitle("Uploading...");
      progressDialog.show();

      StorageReference ref = storageReference.child("images/" + user.getUid() + ".jpg");
      ref.putFile(filePath)
          .addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              progressDialog.dismiss();
              Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              progressDialog.dismiss();
              Toast.makeText(ProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT)
                  .show();
            }
          })
          .addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
              double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                  .getTotalByteCount());
              progressDialog.setMessage("Uploaded " + (int) progress + "%");
            }
          });
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null) {
      filePath = data.getData();
      try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        profile_pic.setImageBitmap(bitmap);
        uploadImage();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}