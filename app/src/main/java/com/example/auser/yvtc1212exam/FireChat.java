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

    ArrayList arrayList1,arrayList2;
    EditText etSend;
    String str;
    ListView listView;
    ArrayAdapter adapter;

    boolean b;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_chat);

        setTitle("Fire Chat");

        etSend = (EditText)findViewById(R.id.etSend);
        listView = (ListView) findViewById(R.id.listView_FireChat);

        ref = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    protected void onResume() {
        super.onResume();
        arrayList1 = new ArrayList();
        arrayList2 = new ArrayList();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList1.clear();
                arrayList2.clear();
                for(DataSnapshot ds : dataSnapshot.child("Chat").getChildren()){
                    arrayList1.add(ds.getValue());
                }
                if(arrayList1.size()!=0) {
                    for (int i = arrayList1.size()-1; i > 0; i--) {
                        arrayList2.add(arrayList1.get(i));
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
        b = false;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot ds:dataSnapshot.child("Chat").getChildren()){
                    count++;
                }
                str = etSend.getText().toString();
                if(!b) {
                    if ("".equals(str)) {
                        Toast.makeText(FireChat.this, "請輸入內文", Toast.LENGTH_SHORT).show();
                    } else {
                        ref.child("Chat").child(count + "").setValue(str);
                        etSend.setText("");
                        count = 0;
                    }
                    b = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
