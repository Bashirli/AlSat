package com.bashirli.alsat.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.bashirli.alsat.adapter.Adapter;
import com.bashirli.alsat.adapter.SearchAdapter;
import com.bashirli.alsat.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

public class MyPostsFragment extends Fragment {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ArrayList<Data> arrayList;
    RecyclerView recyclerView;
    SearchAdapter adapter;
    TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_my_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    recyclerView=view.findViewById(R.id.myPostRecycler);
    arrayList=new ArrayList<>();
    textView=view.findViewById(R.id.textView8);
    auth=FirebaseAuth.getInstance();
    firestore=FirebaseFirestore.getInstance();

    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    adapter=new SearchAdapter(arrayList);
    recyclerView.setAdapter(adapter);
    getData();


    }

    public void getData(){
        firestore.collection("Elanlar")
                .orderBy("date", Query.Direction.DESCENDING)
                .whereEqualTo("email",auth.getCurrentUser().getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getLocalizedMessage());
                return;
                }
                if(value!=null){
                    for(DocumentSnapshot snapshot:value.getDocuments()){
                        Map<String,Object> elanData=snapshot.getData();
                        String email,name,Value, about, connection, product, image;
                        email=auth.getCurrentUser().getEmail();
                        name=(String) elanData.get("name");
                        Value=(String) elanData.get("value");
                        about=(String) elanData.get("about");
                        connection=(String) elanData.get("connection");
                        product=(String) elanData.get("product");
                        image=(String) elanData.get("image");
                        Data oldData=new Data(email,name,Value,about,connection,product,image);
                arrayList.add(oldData);
                    }
                }
                if(arrayList.size()!=0) {
                textView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();

                }else{
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}