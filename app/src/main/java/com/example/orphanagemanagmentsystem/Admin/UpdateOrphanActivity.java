    package com.example.orphanagemanagmentsystem.Admin;

    import androidx.activity.result.ActivityResult;
    import androidx.activity.result.ActivityResultCallback;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.ProgressDialog;
    import android.content.ContentResolver;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.View;
    import android.webkit.MimeTypeMap;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.orphanagemanagmentsystem.Model.Model_Orphan;
    import com.example.orphanagemanagmentsystem.R;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;

    import java.util.HashMap;
    import java.util.Map;

    public class UpdateOrphanActivity extends AppCompatActivity {
    String key;
    EditText name,age,gender,addr,dob;
    TextView chooseImg;
    Button btnUpdate;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri uri;
    String imageUrl,dbImg;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_orphan);
    name=findViewById(R.id.OrphanNameUp);
    age=findViewById(R.id.OrphanAgeUp);
    gender=findViewById(R.id.OrphanGenderUp);
    addr=findViewById(R.id.OrphanADDRUp);
    dob=findViewById(R.id.OrphanDOBUp);
    btnUpdate=findViewById(R.id.btnUpdateOrphan);
    imageView=findViewById(R.id.viewImgUp);
    chooseImg=findViewById(R.id.OrphanIMGUp);
    progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    storageReference = FirebaseStorage.getInstance().getReference("Assets");
    chooseImg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/");
    activityResultLauncher.launch(intent);
    }
    });
    activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
    try {
    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
    uri = result.getData().getData();
    }
    } catch (Exception e){
    e.fillInStackTrace();
    Log.d("imgError","Error\n"+e.getMessage());
    }

    }
    });

    Intent intent=getIntent();
    if (intent != null){
    key=intent.getStringExtra("OrphanKey");
    databaseReference = FirebaseDatabase.getInstance().getReference().child("Orphans").child(key);
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
    Model_Orphan model_orphan=snapshot.getValue(Model_Orphan.class);
    Picasso.get().load(model_orphan.getImage())
    .fit().centerCrop()
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.placeholder)
    .into(imageView);
    name.setText(model_orphan.getName());
    age.setText(model_orphan.getAge());
    gender.setText(model_orphan.getGender());
    dob.setText(model_orphan.getDOB());
    addr.setText(model_orphan.getAddress());
    dbImg= model_orphan.getImage();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
    Toast.makeText(UpdateOrphanActivity.this, "Database Error\t"+ error.getMessage(), Toast.LENGTH_SHORT).show();
    }
    });
    }

    btnUpdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    String Name =name.getText().toString().trim();
    String Age =age.getText().toString().trim();
    String Gender =gender.getText().toString().trim();
    String DOB =dob.getText().toString().trim();
    String Address =addr.getText().toString().trim();
    if (TextUtils.isEmpty(Name)){
    name.setError("Required");
    } else if (TextUtils.isEmpty(Age)){
    addr.setError("Required");
    } else if (TextUtils.isEmpty(Gender)){
    gender.setError("Required");
    } else if (TextUtils.isEmpty(DOB)){
    dob.setError("Required");
    } else if (TextUtils.isEmpty(Address)){
    addr.setError("Required");
    } else {
    progressDialog.setMessage("Please Wait!.....");
    progressDialog.show();

    if (uri == null && imageUrl == null){
        updateOrphan(key,Name,Age,Gender,DOB,Address,dbImg);
        progressDialog.dismiss();

    } else {
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    StorageReference delstorageReference = firebaseStorage.getReferenceFromUrl(dbImg);
    delstorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
    StorageReference storageReference2 = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
    storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
    Task<Uri> task=taskSnapshot.getMetadata().getReference().getDownloadUrl();
    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri imaguri) {
            imageUrl=imaguri.toString();
progressDialog.dismiss();
            updateOrphan(key,Name,Age,Gender,DOB,Address,imageUrl);
        }
    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateOrphanActivity.this, "OnDownloadAssetException"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    });

    }
    });
    }


    }
    }
    });
    }

        private void updateOrphan(String Key,String name, String age, String gender, String dob, String address, String imageUrl) {
            Map<String ,Object> map=new HashMap<String ,Object>();
map.put("name",name);
map.put("age",age);
map.put("gender",gender);
map.put("dob",dob);
map.put("address",address);
map.put("image",imageUrl);
databaseReference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
        Toast.makeText(UpdateOrphanActivity.this, "Updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UpdateOrphanActivity.this,ManageOrphanActivity.class));
        finish();
        progressDialog.dismiss();
    }
})
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UpdateOrphanActivity.this, "onUpdateException\t"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }

        private String getFileExtension(Uri uri){
    ContentResolver contentResolver = getContentResolver();
    MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
    return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    }