package com.e.uploadfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;

    //MenuItem Implement... code
    private ValueEventListener mDBListener;



    //MenuItem Implement... code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();

        //MenuItem Implement... code
        mImageAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(ImagesActivity.this);
        mStorage = FirebaseStorage.getInstance();
        //MenuItem Implement... code

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");



        //Old Version
        //mDatabaseRef.addValueEventListener(new ValueEventListener() {

        //Updated Version
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //MenuItem Implement... code
                mUploads.clear();
                //MenuItem Implement... code


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);

                    //MenuItem Implement... code
                    upload.setKey(postSnapshot.getKey());
                    //MenuItem Implement... code

                    mUploads.add(upload);

                }

                //MenuItem Implement... code
                mImageAdapter.notifyDataSetChanged();
                //MenuItem Implement... code


                mProgressCircle.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });



    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"Normal click at position: " + position,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(this,"Whatever click at position: " + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {


//        Toast.makeText(this,"Delete click at position: " + position,Toast.LENGTH_SHORT).show();
        //MenuItem Implement... code
        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());

      //  StorageReference imageRef = storageRef.child("upload_images");
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ImagesActivity.this,"Delete Successful ",Toast.LENGTH_SHORT).show();

            }
        });
        //MenuItem Implement... code

        //Toast.makeText(this,"Delete click at position: " + position,Toast.LENGTH_SHORT).show();
    }

    //MenuItem Implement... code
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
    //MenuItem Implement... code


}
