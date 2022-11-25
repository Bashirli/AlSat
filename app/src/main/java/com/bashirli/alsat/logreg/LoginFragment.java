package com.bashirli.alsat.logreg;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.bashirli.alsat.view.FeedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    Button button;
    TextView textView,textView2;
    FirebaseAuth auth;
    EditText editText,editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    button=view.findViewById(R.id.button2);
        textView=view.findViewById(R.id.textView);
        textView2=view.findViewById(R.id.textView3);
        editText=view.findViewById(R.id.editTextTextPersonName);
        editText2=view.findViewById(R.id.editTextTextPassword);
         auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            Intent intent=new Intent(requireContext(), FeedActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

    textView2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loginAsAGuest(view);
        }
    });
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login(view);
        }
    });

    textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            register(view);
        }
    });

    }



    public void loginAsAGuest(View view){
        AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
        alert.setTitle("Anonim giriş").setMessage("Anonim giriş etmək istədiyinizə əminsiniz?");
        alert.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProgressDialog progressDialog=new ProgressDialog(requireContext());
                progressDialog.setTitle("Anonim");
                progressDialog.setMessage("Anonim giriş edilir");
                progressDialog.setCancelable(false);
                progressDialog.show();
                auth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.cancel();
                        Toast.makeText(requireContext(),"Uğurla giriş edildi! \n Xoş gəldiniz!",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(requireContext(), FeedActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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


    public int problemTapma(){
        if(editText.getText().toString().equals("")||
                editText2.getText().toString().equals("")){
            Toast.makeText(requireContext(), "Bütün xanalar doldurulmayıb!", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if(editText2.getText().toString().length()<6){
            Toast.makeText(requireContext(), "Parol çox qısadır(Minimum 6 simvol)!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }


    public void login(View view){
        if(problemTapma()==0){
            return;
        }
        ProgressDialog progressDialog=new ProgressDialog(requireContext());
        progressDialog.setTitle("Giriş");
        progressDialog.setMessage("Hesaba giriş edilir");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String email,password;
        email=editText.getText().toString();
        password=editText2.getText().toString();
    auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            progressDialog.cancel();
            Toast.makeText(requireContext(),"Hesaba uğurla giriş edildi! \n Xoş gəldiniz!",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(requireContext(), FeedActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressDialog.cancel();
            Toast.makeText(requireContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            System.out.println(e.getLocalizedMessage());
        }
    });

    }

  public void register(View view){
      NavDirections navDirections=LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
      Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(navDirections);
  }

}