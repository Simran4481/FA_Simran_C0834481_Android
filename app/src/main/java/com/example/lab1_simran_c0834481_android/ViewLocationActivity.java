package com.example.lab1_simran_c0834481_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewLocationActivity extends AppCompatActivity {
private RecyclerView mRecylerView;
private ArrayList<LocationModel> mList =new ArrayList<>();
private LocationViewAdapter mAdapter;
private LinearLayoutManager mLinearLayoutManager;
    private DBHandler database;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_places);
        mRecylerView = findViewById(R.id.list);
        mContext = this;
        database = new DBHandler(mContext);
        getLocationList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mList.clear();
        mList = database.getFavouritesLocation();
        mAdapter.notifyDataSetChanged();
    }

    public void setAdapter() {
        mAdapter = new LocationViewAdapter(this, mList, new OnItemClickListener() {
            @Override
            public void deleteAddress(LocationModel location) {
                mList.remove(location);
                database.deleteCourse(location.getAddress());
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void editAddress(LocationModel editLocation) {
                Intent it = new Intent(ViewLocationActivity.this, UpdateLocationActivity.class);
                it.putExtra(Constants.LOCATION_NAME, editLocation);
                startActivity(it);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(mLinearLayoutManager);
    }
    public void getLocationList() {
       mList = database.getFavouritesLocation();
        setAdapter();
    }
}