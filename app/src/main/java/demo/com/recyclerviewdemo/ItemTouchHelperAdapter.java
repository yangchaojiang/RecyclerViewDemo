package demo.com.recyclerviewdemo;

/**
 * Created by yangc on 2017/5/11.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */
public interface ItemTouchHelperAdapter {
    public static final String TAG = "ItemTouchHelperAdapter";
    //数据交换
    void onItemMove(int fromPosition,int toPosition);
    //数据删除
    void onItemDissmiss(int position);
}
