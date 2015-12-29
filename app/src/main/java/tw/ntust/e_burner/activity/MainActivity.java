package tw.ntust.e_burner.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.ntust.e_burner.R;
import tw.ntust.e_burner.flingswipe.SwipeFlingAdapterView;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> al;
    private ArrayList a;
    private ArrayAdapter<String> arrayAdapter;
    private int i;

    @InjectView(R.id.frame)
    SwipeFlingAdapterView flingContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.inject(this);



        // 放在listView中的图片资源id
        int[] imgAry = new int[] { R.drawable.ic_coin};
        // 创建List集合对象
        final List a = new ArrayList();
        // 为List集合添加数据
        for (int i = 0; i < imgAry.length; i++) {
            Map map = new HashMap();
            map.put("image", imgAry[i]);
        // 金紙數量在這
            for (int k = 0; k < 100; k++) {
                a.add(map);
            }
        }
        // 設定item adapter
        final SimpleAdapter adapter = new SimpleAdapter(this, a,
                R.layout.item, new String[] { "image" },
                new int[] { R.id.helloText});


        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
               // Log.d("LIST", "removed object!");
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onUpCardExit(Object dataObject) {
                makeToast(MainActivity.this, "Up!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MainActivity.this, "Clicked!");
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }







}
