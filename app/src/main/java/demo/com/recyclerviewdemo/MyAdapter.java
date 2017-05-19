package demo.com.recyclerviewdemo;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangc on 2017/5/11.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */
public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements ItemTouchHelperAdapter {
    public static final String TAG = "MyAdapter";

    public MyAdapter(@Nullable List<String> data) {
        super(R.layout.item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item, item);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        mData.remove(position);
        notifyItemRemoved(position);
    }
}
