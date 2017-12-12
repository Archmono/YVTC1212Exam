package com.example.auser.yvtc1212exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireChat extends AppCompatActivity {

    DatabaseReference ref;
    EditText etSend;
    String str;
    ListView listView;
    ArrayList arrayList,arrayList2;
    ArrayAdapter adapter;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_chat);
        etSend = (EditText)findViewById(R.id.etSend);
        listView = (ListView) findViewById(R.id.listView_FireChat);

        ref = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    protected void onResume() {
        super.onResume();
        arrayList = new ArrayList();
        arrayList2 = new ArrayList();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                arrayList2.clear();
                for(DataSnapshot ds : dataSnapshot.child("Chat").getChildren()){
                    arrayList.add(ds.getValue());
                }
                if(arrayList.size()!=0) {
                    for (int i = arrayList.size()-1; i > 0; i--) {
                        arrayList2.add(arrayList.get(i));
                    }
                }
                adapter = new ArrayAdapter(FireChat.this,android.R.layout.simple_list_item_1,arrayList2);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void clickSend(View v){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot ds:dataSnapshot.child("Chat").getChildren()){
                    count++;
                }
                str = etSend.getText().toString();
                if("".equals(str)){
                    Toast.makeText(FireChat.this,"請勿空白",Toast.LENGTH_SHORT).show();
                }else{
                    ref.child("Chat").child(count+"").setValue(str);
                    etSend.setText("");
                    count=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
