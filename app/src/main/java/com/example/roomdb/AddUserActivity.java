package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.roomdb.databinding.ActivityAddUserBinding;
import com.example.roomdb.db.AppDatabase;
import com.example.roomdb.db.model.User;

import java.util.Date;
import java.util.Objects;

import butterknife.OnClick;

public class AddUserActivity extends AppCompatActivity {

    AppDatabase database;

    private ActivityAddUserBinding binding;

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, AddUserActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = AppDatabase.getDatabaseInstance(this);
    }

    @OnClick(R.id.buttonAddUser)
    public void onViewClicked() {
        if (Objects.requireNonNull(binding.textFirstName.getText()).toString().trim().isEmpty()) {
            binding.textInputLayoutFirstName.setError(getString(R.string.error_msg_firstname));
            return;
        }

        if (Objects.requireNonNull(binding.textEmail.getText()).toString().trim().isEmpty()) {
            binding.textInputLayoutEmail.setError(getString(R.string.error_msg_email));
            return;
        }
        User mUser = new User(
                binding.textFirstName.getText().toString(),
                Objects.requireNonNull(binding.textLastName.getText()).toString(),
                binding.textEmail.getText().toString(),
                Objects.requireNonNull(binding.textPhoneNo.getText()).toString(),
                Objects.requireNonNull(binding.textAddress.getText()).toString(),
                null,
                new Date(),
                new Date());
        database.userDao().insertUser(mUser);
        finish();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();

    }
}
