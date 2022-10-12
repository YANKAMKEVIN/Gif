package com.kevin.netgemtest.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kevin.netgemtest.DataAdapter;
import com.kevin.netgemtest.Model.DataModel;
import com.kevin.netgemtest.MySingleton;
import com.kevin.netgemtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchGifFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchGifFragment extends BottomSheetDialogFragment {
    ArrayList<DataModel> dataModelArrayListe = new ArrayList<>();

    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    Button searchbt;
    EditText editText;
    String textEditText;

    public static final String API_KEY = "25QLCq6dnp5trFPXDXAclf6AvkSHKgmP";
    public static final String BASE_URL = "https://api.giphy.com/v1/gifs/trending?api_key=";

    String url = BASE_URL + API_KEY;
    public SearchGifFragment() {
        // Required empty public constructor
    }


    public static SearchGifFragment newInstance() {
        SearchGifFragment fragment = new SearchGifFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_blank);
        searchbt = view.findViewById(R.id.searchBtn);
        editText = view.findViewById(R.id.searchInput);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        recyclerView.setHasFixedSize(true);


        //getting the data
        JsonObjectRequest gifsRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i<dataArray.length(); i++){
                        JSONObject object = dataArray.getJSONObject(i);
                        JSONObject object1 = object.getJSONObject("images");
                        JSONObject object2 = object1.getJSONObject("downsized_medium");

                        String imageUrl = object2.getString("url");

                        dataModelArrayListe.add(new DataModel(imageUrl));
                    }
                    dataAdapter = new DataAdapter(getContext(),dataModelArrayListe);
                    recyclerView.setAdapter(dataAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }},new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error" +error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        MySingleton.getInstance(getActivity()).addToRequest(gifsRequest);
        //Volley.newRequestQueue(getContext()).add(gifsRequest);

        //if (editText.s)
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        searchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEditText = editText.getText().toString();
                ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

                //getting the data
                String searchurl = "https://api.giphy.com/v1/gifs/search?api_key=25QLCq6dnp5trFPXDXAclf6AvkSHKgmP&q=" + textEditText;
                JsonObjectRequest gifsRequest = new JsonObjectRequest(Request.Method.GET, searchurl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            for (int i = 0; i<dataArray.length(); i++){
                                JSONObject object = dataArray.getJSONObject(i);
                                JSONObject object1 = object.getJSONObject("images");
                                JSONObject object2 = object1.getJSONObject("downsized_medium");

                                String imageUrl = object2.getString("url");

                                dataModelArrayList.add(new DataModel(imageUrl));
                            }
                            recyclerView.setAdapter(dataAdapter);
                            dataAdapter = new DataAdapter(getContext(),dataModelArrayList);
                            //dataAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error" +error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                MySingleton.getInstance(getActivity()).addToRequest(gifsRequest);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}