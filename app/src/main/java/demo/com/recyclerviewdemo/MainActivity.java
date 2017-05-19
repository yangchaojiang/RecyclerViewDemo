package demo.com.recyclerviewdemo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.jaychang.srv.behavior.SnapAlignment;
import com.jaychang.srv.behavior.StartSnapHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DrawerArrowDrawable drawerArrowDrawable;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
        for (int i = 0; i < 10; i++) {
            list.add("test:" + i);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setSupportActionBar((Toolbar) findViewById(R.id.Toolbar));
        imageView= (ImageView) findViewById(R.id.action_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerArrowDrawable.setColor(getColor(R.color.white));
                drawerArrowDrawable.setDirection(DrawerArrowDrawable.ARROW_DIRECTION_RIGHT);
                imageView.setImageDrawable(drawerArrowDrawable);
            }
        });

        drawerArrowDrawable = new DrawerArrowDrawable(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        PagerSnapHelper snapHelper =  new PagerSnapHelper();
        snapHelper.findSnapView(linearLayoutManager);
        snapHelper.attachToRecyclerView(recyclerView);
//        SimpleItemTouchHelperCallback simpleItemTouchHelperCallback=new SimpleItemTouchHelperCallback(adapter);
//        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleItemTouchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    private void add() {
        List<String> list = new ArrayList<>();
        for (int i = adapter.getItemCount(); i < adapter.getItemCount()+10; i++) {
            list.add("test:" + i);
        }
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new MYDiffCallBack<>(adapter.getData(), list), true);
        diffResult.dispatchUpdatesTo(adapter);
        adapter.addData(5,list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                add();
                break;
            case R.id.update:
                break;
            case R.id.remove:
                break;
        }

    }
}
