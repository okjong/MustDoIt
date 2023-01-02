package com.mrhi2022.tp05todomrhi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CalendarView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mrhi2022.tp05todomrhi.databinding.ActivityEditBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;

    int category;
    String[] categoryTitle= new String[]{"ALL","WORK","STUDY","HEALTH","HOBBY","MEETING","ETC","DONE"};

    String date="2022년 09월 30일";

    BottomSheetDialog bottomSheetDialog= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("할일 추가");

        category= getIntent().getIntExtra("category", 0);
        binding.tvCategory.setText(categoryTitle[category]);

        date= new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
        binding.tvDate.setText(date);

        binding.tvDate.setOnClickListener(v-> showBottomSheetDialogCalendar() );
        binding.tvCategory.setOnClickListener(v->showBottomSheetDialogCategory());
        binding.btnComplete.setOnClickListener(v->clickComplete());

    }

    void clickComplete(){

        SQLiteDatabase db= openOrCreateDatabase("Todo", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS todo(num INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, date TEXT, category INTEGER, note TEXT, isDone INTEGER)");


        String title= binding.etTitle.getText().toString();
        String note= binding.etNote.getText().toString();


        db.execSQL("INSERT INTO todo(title, date, category, note, isDone) VALUES(?,?,?,?,?)", new Object[]{title, date, category, note, 0});


        onBackPressed();
    }


    void showBottomSheetDialogCategory(){

        bottomSheetDialog= new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bs_category);
        bottomSheetDialog.show();

        bottomSheetDialog.findViewById(R.id.bsd_category_work).setOnClickListener(v->clickCategory(1));
        bottomSheetDialog.findViewById(R.id.bsd_category_study).setOnClickListener(v->clickCategory(2));
        bottomSheetDialog.findViewById(R.id.bsd_category_health).setOnClickListener(v->clickCategory(3));
        bottomSheetDialog.findViewById(R.id.bsd_category_hobby).setOnClickListener(v->clickCategory(4));
        bottomSheetDialog.findViewById(R.id.bsd_category_meeting).setOnClickListener(v->clickCategory(5));
        bottomSheetDialog.findViewById(R.id.bsd_category_etc).setOnClickListener(v->clickCategory(6));
    }

    void clickCategory(int category){
        this.category= category;
        binding.tvCategory.setText( categoryTitle[category] );

        bottomSheetDialog.dismiss();
    }


    void showBottomSheetDialogCalendar(){

        bottomSheetDialog= new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bs_calendar);
        bottomSheetDialog.show();

        CalendarView calendarView= bottomSheetDialog.findViewById(R.id.bsd_calendar);
        calendarView.setOnDateChangeListener( (view, year, month, day) ->{


            GregorianCalendar calendar= new GregorianCalendar(year,month,day);

            date= new SimpleDateFormat("yyyy년 MM월 dd일").format(calendar.getTime());
            binding.tvDate.setText(date);

            bottomSheetDialog.dismiss();
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}