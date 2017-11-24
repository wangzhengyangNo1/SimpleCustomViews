package com.wzhy.simplecustomviews.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wzhy.simplecustomviews.R;

import java.util.ArrayList;
import java.util.Random;

public class ActivityTest extends AppCompatActivity {

    private RecyclerView mPersonRv;
    private ArrayList<Person> mPersonList;
    private PersonAdapter mPersonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mPersonRv = (RecyclerView) findViewById(R.id.person_rv);

        mPersonRv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        mPersonRv.setLayoutManager(layoutManager);

        initData();

        mPersonAdapter = new PersonAdapter(mPersonList);
        mPersonAdapter.setOnRvItemClickListener(mRvItemClickListener);
        mPersonRv.setAdapter(mPersonAdapter);

    }


    private void initData() {
        mPersonList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mPersonList.add(new Person("wzhy_" + i, 15 + i));
        }
    }

    public void onClick(View view) {
        int n = mPersonAdapter.getItemCount();
        Random random = new Random();
        int index;
        switch (view.getId()) {
            case R.id.add_item_btn:
                index = random.nextInt(n + 1);
                mPersonAdapter.insertPerson(index);
                break;
            case R.id.del_item_btn:
                if (n <= 0) {
                    Toast.makeText(getApplicationContext(), "没有数据了！", Toast.LENGTH_SHORT).show();
                    return;
                }
                index = random.nextInt(n);
                mPersonAdapter.deletePerson(index);
                break;
        }
    }


    private PersonAdapter.OnRvItemClickListener mRvItemClickListener = new PersonAdapter.OnRvItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Toast.makeText(getApplicationContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClick(View itemView, int position) {
            Toast.makeText(getApplicationContext(), "长按了" + position, Toast.LENGTH_SHORT).show();
            mPersonAdapter.deletePerson(position);
            return false;
        }
    };

}
