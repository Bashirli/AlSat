package com.bashirli.alsat.app;

import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.bashirli.alsat.adapter.Adapter;
import com.bashirli.alsat.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;


public class MainFragment extends Fragment {

    TextView textView;
    Adapter adapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ArrayList<Data> arrayList;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=view.findViewById(R.id.textView6);
         startAnimation();
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(requireContext().CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo()==null){
            Toast.makeText(requireContext(), "İnternetə qoşulu deyilsiniz!", Toast.LENGTH_LONG).show();
        }

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        arrayList=new ArrayList<>();

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter=new Adapter(arrayList);
        recyclerView.setAdapter(adapter);
        getData();

    }

    public void startAnimation(){
        Animation animation=AnimationUtils.loadAnimation(requireContext(),R.anim.anim);
        textView.startAnimation(animation);
    }

    public void getData(){
    firestore.collection("Elanlar").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if(error!=null){
                Toast.makeText(requireContext(), error.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
            if(value!=null){
                for(DocumentSnapshot snapshot:value.getDocuments()){
                    Map<String,Object> data=snapshot.getData();
                            String about= (String) data.get("about");
                            String connection=(String) data.get("connection");
                            String email=(String) data.get("email");
                            String name=(String) data.get("name");
                            String product=(String) data.get("product");
                            String Value=(String) data.get("value");
                            String image=(String) data.get("image");
                             Data postData=new Data(email,name,Value,about,connection,product,image);

                        arrayList.add(postData);
                }

            }
            adapter.notifyDataSetChanged();
        }
    });
    }
}