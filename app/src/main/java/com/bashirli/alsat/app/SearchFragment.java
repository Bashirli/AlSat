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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.bashirli.alsat.adapter.SearchAdapter;
import com.bashirli.alsat.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    EditText editText;
    Button button;
    TextView textView;
    ArrayList<Data> arrayList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    SearchAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerSearch);
        button=view.findViewById(R.id.button5);
        editText=view.findViewById(R.id.editTextTextPersonName9);
        textView=view.findViewById(R.id.textView4);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(view);
            }
        });



    }

    public void search(View view) {
        arrayList=new ArrayList<>();
        if (editText.getText().toString().equals("")) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Axtarış cavabı tapılmadı!");
            return;
        }
        textView.setVisibility(View.GONE);


            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new SearchAdapter(arrayList);
            recyclerView.setAdapter(adapter);
        getData();
    }

    public void getData(){
    firestore.collection("Elanlar").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("product",editText.getText().toString()).addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if(error!=null){
                Toast.makeText(requireContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                System.out.println(error.getLocalizedMessage());
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
                if(arrayList.size()!=0) {
                    adapter.notifyDataSetChanged();
                }else{
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Axtarış cavabı tapılmadı!");
                    return;
                }



        }
    });
    }

}