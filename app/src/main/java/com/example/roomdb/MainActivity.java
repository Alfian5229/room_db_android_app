package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.roomdb.databinding.ActivityMainBinding;
import com.example.roomdb.db.AppDatabase;
import com.example.roomdb.db.model.User;

import java.util.ArrayList;
import java.util.Date;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements UserAdapter.Callback {

    AppDatabase database;
    UserAdapter mUserAdapter;
    LinearLayoutManager mLayoutManager;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = AppDatabase.getDatabaseInstance(this);
        setUp();
    }

    private void setUp() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);

        RecyclerView recyclerView = binding.recyclerView;

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mUserAdapter = new UserAdapter(new ArrayList<User>());
        mUserAdapter.setCallback(this);

        prepareDemoContent();
        recyclerView.setAdapter(mUserAdapter);
    }

    private void prepareDemoContent() {
        User mUser1 = new User("Ramesh", "Chandra", "ramesh@gmail.com", null, null, null, new Date(), new Date());
        database.userDao().insertUser(mUser1);

        User mUser2 = new User("Sachin", "Kalal", "chnada@gmail.com", null, null, null, new Date(), new Date());
        database.userDao().insertUser(mUser2);

        User mUser3 = new User("Amit", "Kumar", "arun@gmail.com", null, null, null, new Date(), new Date());
        database.userDao().insertUser(mUser3);

        User mUser4 = new User("Kapil", "sharma", "kapil@gmail.com", null, null, null, new Date(), new Date());
        database.userDao().insertUser(mUser4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserAdapter.addItems(database.userDao().getAll());
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        AddUserActivity.startActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    @Override
    public void onDeleteClick(User mUser) {
        database.userDao().delete(mUser);
        mUserAdapter.addItems(database.userDao().getAll());
    }
}
