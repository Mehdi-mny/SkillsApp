package com.example.skillsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class MessagingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText editTextMessage;
    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boitemessagerie);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        TextView textViewUserName = findViewById(R.id.textViewUserName);
        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        // Set the user name
        String userName = getIntent().getStringExtra("USER_NAME");
        textViewUserName.setText(userName);

        // Load messages
        loadMessages();

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void loadMessages() {
        String recipientId = getIntent().getStringExtra("RECIPIENT_ID");

        db.collection("messages")
                .whereEqualTo("recipientId", recipientId)
                .orderBy("timestamp")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(MessagingActivity.this, "Error while loading messages", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Message message = dc.getDocument().toObject(Message.class);
                                messageList.add(message);
                                messageAdapter.notifyItemInserted(messageList.size() - 1);
                                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
                            }
                        }
                    }
                });
    }



    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        String userName = currentUser.getDisplayName();
        String recipientId = getIntent().getStringExtra("RECIPIENT_ID");
        String recipientName = getIntent().getStringExtra("RECIPIENT_NAME");

        Map<String, Object> message = new HashMap<>();
        message.put("userId", userId);
        message.put("userName", userName);
        message.put("recipientId", recipientId);
        message.put("recipientName", recipientName);
        message.put("message", messageText);
        message.put("timestamp", System.currentTimeMillis());


        db.collection("messages")
                .add(message)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            editTextMessage.setText("");
                        } else {
                            Toast.makeText(MessagingActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
