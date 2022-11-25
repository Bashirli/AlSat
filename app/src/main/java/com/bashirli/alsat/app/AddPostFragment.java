package com.bashirli.alsat.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.bashirli.alsat.model.Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.UUID;

public class AddPostFragment extends Fragment {
    ImageView imageView;
    EditText ad,qiymet,haqqinda,elaqe,mehsul;
    Button button,button2;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Uri selectedUri=null;
    Bitmap selectedBitmap;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;
    String info;
    Data oldData=null;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    imageView=view.findViewById(R.id.imageView);
    ad=view.findViewById(R.id.editTextTextPersonName3);
    qiymet=view.findViewById(R.id.editTextTextPersonName4);
    haqqinda=view.findViewById(R.id.editTextTextMultiLine);
    elaqe=view.findViewById(R.id.editTextTextPersonName5);
    button=view.findViewById(R.id.button6);
        button2=view.findViewById(R.id.button7);
        mehsul=view.findViewById(R.id.editTextTextPersonName8);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect(view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paylas(view);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geriDon(view);
            }
        });
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        launchActivity();

        info=AddPostFragmentArgs.fromBundle(getArguments()).getInfo();
        if(info.equals("old")){
            oldData=AddPostFragmentArgs.fromBundle(getArguments()).getMainData();
            Picasso.get().load(oldData.getImage()).fit().into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    return;
                }
            });
            button.setVisibility(View.GONE);
            ad.setInputType(InputType.TYPE_NULL);
            mehsul.setInputType(InputType.TYPE_NULL);
            elaqe.setInputType(InputType.TYPE_NULL);
            qiymet.setInputType(InputType.TYPE_NULL);
           ad.setText(oldData.getName());
           mehsul.setText(oldData.getProduct());
           qiymet.setText(oldData.getValue());
            haqqinda.setText(oldData.getAbout());
            elaqe.setText(oldData.getConnection());

        }else if(info.equals("new")){
            oldData=null;
            button.setVisibility(View.VISIBLE);

            ad.setInputType(InputType.TYPE_CLASS_TEXT);
            mehsul.setInputType(InputType.TYPE_CLASS_TEXT);
            elaqe.setInputType(InputType.TYPE_CLASS_TEXT);
            qiymet.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ad.setText("");
            mehsul.setText("");
            qiymet.setText("");
            elaqe.setText("");
            haqqinda.setText("");
            imageView.setImageResource(R.drawable.sekilsec);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageSelect(view);
                }
            });

        }

    }



    public void imageSelect(View view){
    if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
            Snackbar.make(view,"Şəkil seçmək üçün icazə yoxdur!",Snackbar.LENGTH_INDEFINITE).setAction("İcazə ver", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }).show();
        }else {
            //permission
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }else{
        //activity
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }

    }


    public void geriDon(View view) {
        String geriInfo=AddPostFragmentArgs.fromBundle(getArguments()).getGeri();
        if (info.equals("old")&&!geriInfo.equals("true")) {
            NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2).navigate(navDirections);
        }else if(info.equals("new")){
            AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
            alert.setTitle("Geri dönmək?").setMessage("Geri döndükdə bu səhifədəki məlumatlar itəcək. \n Geri dönməyə əminsiniz?")
                    .setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2).navigate(navDirections);

                        }
                    }).setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).create().show();


        } else if(geriInfo.equals("true")){
            NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToSearchFragment();
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2).navigate(navDirections);
        }else if(geriInfo.equals("trueM")){
            NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMyPostsFragment();
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2).navigate(navDirections);

        }
    }

    public int problemTapma(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(requireContext().CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo()==null){
            Toast.makeText(requireContext(), "İnternetə qoşulu deyilsiniz!", Toast.LENGTH_LONG).show();
        return 0;
        }


        if(selectedUri==null){
            Toast.makeText(requireContext(), "Şəkil seçilməyib!", Toast.LENGTH_SHORT).show();
        return 0;
        }
    if(ad.getText().toString().equals("")||
            mehsul.getText().toString().equals("")||
        qiymet.getText().toString().equals("")||
        haqqinda.getText().toString().equals("")||
        elaqe.getText().toString().equals("")){
        Toast.makeText(requireContext(), "Məlumatlar tam doldurulmayıb!", Toast.LENGTH_SHORT).show();
    return 0;
    }




    return 1;
    }

    public void paylas(View view){
    if(problemTapma()==0){
        return;
    }
        AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
    alert.setTitle("DİQQƏT!").setMessage("Elan paylaşıldıqdan sonra silə və ya dəyişdirə bilməzsiniz.\n Elanı paylaşmağa əminsiniz?")
            .setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ProgressDialog progressDialog=new ProgressDialog(requireContext());
                    progressDialog.setTitle("Elan");
                    progressDialog.setMessage("Elan paylaşılır...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    UUID uuid=UUID.randomUUID();
                    String imageData="images/"+uuid+".jpg";
                    storageReference.child(imageData).putFile(selectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            storage.getReference(imageData).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                 String uriLink=uri.toString();
                 String email=auth.getCurrentUser().getEmail();
                    HashMap<String,Object> data=new HashMap<>();
                    data.put("email",email);
                    data.put("date", FieldValue.serverTimestamp());
                    data.put("name",ad.getText().toString());
                    data.put("value",qiymet.getText().toString());
                    data.put("about",haqqinda.getText().toString());
                    data.put("product",mehsul.getText().toString());
                    data.put("connection",elaqe.getText().toString());
                    data.put("image",uriLink);
                    firestore.collection("Elanlar").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.cancel();
                            Toast.makeText(requireContext(), "Elan uğurla paylaşıldı!", Toast.LENGTH_SHORT).show();
                            NavDirections navDirections=AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                            Navigation.findNavController(requireActivity(),R.id.fragmentContainerView2).navigate(navDirections);
                        }
                    });



                }
            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            return;
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

    public void launchActivity(){
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==Activity.RESULT_OK){
                Intent intent=result.getData();
                if(intent.getData()!=null){
                    selectedUri=intent.getData();
                    try {
                        if(Build.VERSION.SDK_INT>=28){
                            ImageDecoder.Source source=ImageDecoder.createSource(requireContext().getContentResolver(),selectedUri);
                            selectedBitmap=ImageDecoder.decodeBitmap(source);
                            imageView.setImageBitmap(selectedBitmap);
                        }else{
                            selectedBitmap=MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),selectedUri);
                            imageView.setImageBitmap(selectedBitmap);
                        }
                    }catch (Exception e){
                        Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            }
        });

        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }else{
                    Toast.makeText(requireContext(), "İcazə verilmədi.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}