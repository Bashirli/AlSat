package com.bashirli.alsat.logreg;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.bashirli.alsat.view.ScreenActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class RegisterFragment extends Fragment {
    Button button;
    TextView textView;
    EditText editText,editText2,editText3;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=view.findViewById(R.id.button3); 
        textView=view.findViewById(R.id.textView2);
        editText=view.findViewById(R.id.editTextTextPersonName2);
        editText2=view.findViewById(R.id.editTextTextPassword4);
        editText3=view.findViewById(R.id.editTextTextPassword5);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
      
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }


    public void login(View view){
        AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
        alert.setTitle("Geri dön").setMessage("Giriş səhifəsinə qayıtmaq istədiyinizə əminsiniz?");
        alert.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NavDirections navDirections=RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(navDirections);
            }
        }).setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        }).create().show();
    }
    

    public int problemTapma(){
        if(editText.getText().toString().equals("")||
                editText2.getText().toString().equals("")||
                editText3.getText().toString().equals("")){
            Toast.makeText(requireContext(), "Bütün xanalar doldurulmayıb!", Toast.LENGTH_SHORT).show();
        return 0;
        }
        if(!editText2.getText().toString().equals(editText3.getText().toString())){
            Toast.makeText(requireContext(), "Parollar bir-biri ilə uyuşmur!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(editText2.getText().toString().length()<6){
            Toast.makeText(requireContext(), "Parol çox qısadır(Minimum 6 simvol)!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    public void register(View view){
    if(problemTapma()==0){
        return;
    }
        ProgressDialog progressDialog=new ProgressDialog(requireContext());
        progressDialog.setTitle("Qeydiyyat");
        progressDialog.setMessage("Hesab yaradılır");
        progressDialog.setCancelable(false);
        progressDialog.show();
    String email=editText.getText().toString();
    String parol=editText2.getText().toString();

    auth.createUserWithEmailAndPassword(email,parol).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            HashMap<String,Object> data=new HashMap<>();
            data.put("email",email);
            data.put("password",parol);
            data.put("number",null);
            data.put("age",null);
            data.put("name",null);
            data.put("bio",null);
      firestore.collection("UserData").document(email).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void unused) {
          progressDialog.cancel();
            Toast.makeText(requireContext(),"Hesab uğurla yaradıldı!",Toast.LENGTH_LONG).show();
            NavDirections navDirections=RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
            Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(navDirections);
          }
      });
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
        Toast.makeText(requireContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
    });

    }
}