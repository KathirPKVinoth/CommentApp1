package com.example.commentapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {

    Button mAddPostBtn;
    EditText mMsgText;
    ProgressBar mProgresBar;

    StorageReference storageReference;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mAddPostBtn = findViewById(R.id.SubmitBtn);
        mMsgText = findViewById(R.id.MsgText);
        mProgresBar = findViewById(R.id.PostProgressBar);
        mProgresBar.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getEmail();

        mAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgresBar.setVisibility(View.VISIBLE);
                String blog = mMsgText.getText().toString();
                if (!blog.isEmpty()) {
                    HashMap<String, Object> postMap = new HashMap<>();

                    postMap.put("Email", currentUserId);
                    postMap.put("caption", blog);
                    postMap.put("time", FieldValue.serverTimestamp());

                    firestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                mProgresBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MessageActivity.this, "Post Added Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MessageActivity.this, MainActivity2.class));
                                finish();
                            }else {
                                Toast.makeText(MessageActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    mProgresBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MessageActivity.this, "Please type some content", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}