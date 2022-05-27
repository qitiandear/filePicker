package com.tq.pldk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.permissions.XXPermissions;
import com.tq.pldk.filepicker.FilePickerActivity;
import com.tq.pldk.filepicker.PickerManager;
import com.tq.pldk.filepicker.adapter.FilePickerShowAdapter;
import com.tq.pldk.filepicker.adapter.OnFileItemClickListener;
import com.tq.pldk.filepicker.util.OpenFile;

public class MainActivity extends AppCompatActivity {
    private static int REQ_CODE = 0X01;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_file);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    public void goFilePicker(View view) {
        PickerManager.getInstance().setMaxCount(4);
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent,REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            FilePickerShowAdapter adapter = new FilePickerShowAdapter(this,PickerManager.getInstance().files);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnFileItemClickListener() {
                @Override
                public void click(int position) {
                    startActivity(Intent.createChooser(OpenFile.openFile(PickerManager.getInstance().files.get(position).getPath(), getApplicationContext()), "选择程序"));
                }
            });
        }
    }
}
