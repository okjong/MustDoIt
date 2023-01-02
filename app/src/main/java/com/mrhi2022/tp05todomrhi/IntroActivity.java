package com.mrhi2022.tp05todomrhi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.mrhi2022.tp05todomrhi.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {


    ActivityIntroBinding binding;


    String profileImage="";
    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btn.setOnClickListener(v->{
            saveData();
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.civProfile.setOnClickListener(v->{

            Intent intent= new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            resultLauncher.launch(intent);
        });

        loadData();

    }


    void loadData(){
        SharedPreferences pref= getSharedPreferences("account", MODE_PRIVATE);
        profileImage= pref.getString("profile", "");
        name= pref.getString("name", "");

        Glide.with(this).load(profileImage).error(R.drawable.profle).into(binding.civProfile);
        binding.etName.setText(name);
    }


    void saveData(){
        name= binding.etName.getText().toString();

        SharedPreferences pref= getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();

        editor.putString("profile", profileImage);
        editor.putString("name", name);

        editor.commit();
    }


    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if( result.getResultCode() != RESULT_CANCELED ){
                Intent intent= result.getData();
                Uri uri= intent.getData();

                profileImage= uri.toString();
                Glide.with(IntroActivity.this).load(uri).error(R.drawable.profle).into(binding.civProfile);
            }
        }
    });

}