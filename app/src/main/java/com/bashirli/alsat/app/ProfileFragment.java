package com.bashirli.alsat.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
        EditText ad,nomre,bio,email,age;
        TextView textView;
        Button button;
        FirebaseAuth auth;
        FirebaseFirestore firestore;
    String password,name,number,biog,yas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    ad=view.findViewById(R.id.editTextTextPersonName10);
    nomre=view.findViewById(R.id.editTextTextPersonName13);
   email=view.findViewById(R.id.editTextTextPersonName11);

   age=view.findViewById(R.id.editText);

        age.setInputType(InputType.TYPE_CLASS_NUMBER);
    bio=view.findViewById(R.id.editTextTextMultiLine2);
    button=view.findViewById(R.id.button8);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            yenile(view);
        }
    });

    auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
email.setInputType(InputType.TYPE_NULL);
        textView=view.findViewById(R.id.textView7);

    getData();
    }
    public void getData(){
    firestore.collection("UserData").document(auth.getCurrentUser().getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            if(error!=null){
                Toast.makeText(requireContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
            email.setText((String)value.get("email"));
            bio.setText((String)value.get("bio"));
            age.setText((String)value.get("age"));
            ad.setText((String)value.get("name"));
            nomre.setText((String)value.get("number"));
            if((String)value.get("name")==null) {
                textView.setText("Xoş gəldiniz \n " + auth.getCurrentUser().getEmail() + "!");
            }else{
                textView.setText("Xoş gəldiniz \n "+ad.getText().toString()+"!");

            }
        }
    });

    }



    public void fun(){
        if(age.getText().toString().equals("")){
            yas=null;
        }else{
            yas=age.getText().toString();
        }
        if(ad.getText().toString().equals("")){
            name=null;
        }else{
            name=ad.getText().toString();
        }
        if(bio.getText().toString().equals("")){
            biog=null;
        }else{
            biog=bio.getText().toString();
        }
        if(nomre.getText().toString().equals("")){
            number=null;
        }else{
            number=nomre.getText().toString();
        }

    }


    public void yenile(View view){
        AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
        alert.setTitle("Diqqət!").setMessage("Məlumatlarınızı yeniləməyə əminsiniz?")
                        .setPositiveButton("Bəli", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ProgressDialog progressDialog=new ProgressDialog(requireContext());
                                progressDialog.setTitle("Profilim");

                                progressDialog.setMessage("Profil yenilənir");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                fun();

                                Map<String,Object> data=new HashMap<>();
                                data.put("number",number);
                                data.put("age",yas);
                                data.put("name",name);
                                data.put("bio",biog);
                                firestore.collection("UserData").document(auth.getCurrentUser().getEmail()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.cancel();
                                        Toast.makeText(requireContext(),"Məlumatlar uğurla yeniləndi!",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.cancel();
                                        Toast.makeText(requireContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).create().show();


    }


}