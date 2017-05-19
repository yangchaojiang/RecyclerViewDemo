package demo.com.recyclerviewdemo;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by yangc on 2017/5/11.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    public static final String TAG = "SimpleItemTouchHelperCallback";
    private MyAdapter mAdapter;

    public SimpleItemTouchHelperCallback(MyAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 不需要长按拖拽功能  我们手动控制
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 不需要滑动功能
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 不同Type之间不可移动
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        if (recyclerView.getAdapter() instanceof ItemTouchHelperAdapter) {
            ItemTouchHelperAdapter listener = ((ItemTouchHelperAdapter) recyclerView.getAdapter());
            listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    //限制ImageView长度所能增加的最大值
    private double ICON_MAX_SIZE = 150;
    //ImageView的初始长宽
    private int fixedWidth = 250;

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //重置改变，防止由于复用而导致的显示问题
        viewHolder.itemView.setScrollX(0);
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        baseViewHolder.setText(R.id.tv_text, "左滑删除");
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) baseViewHolder.getView(R.id.tv_text).getLayoutParams();
        params.width = 250;
        params.height = 150;
        baseViewHolder.getView(R.id.tv_text).setLayoutParams(params);
        baseViewHolder.getView(R.id.tv_text).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //仅对侧滑状态下的效果做出改变
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
            if (Math.abs(dX) <= getSlideLimitation(viewHolder)) {
                viewHolder.itemView.scrollTo(-(int) dX, 0);
            }
            //如果dX还未达到能删除的距离，此时慢慢增加“眼睛”的大小，增加的最大值为ICON_MAX_SIZE
            else if (Math.abs(dX) <= recyclerView.getWidth() / 2) {
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                double distance = (recyclerView.getWidth() / 2 - getSlideLimitation(viewHolder));
                double factor = ICON_MAX_SIZE / distance;
                double diff = (Math.abs(dX) - getSlideLimitation(viewHolder)) * factor;
                if (diff >= ICON_MAX_SIZE)
                    diff = ICON_MAX_SIZE;
                baseViewHolder.setText(R.id.tv_text, "左滑删除"); //把文字去掉
                baseViewHolder.getView(R.id.tv_text).setVisibility(View.VISIBLE);  //显示眼睛
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) baseViewHolder.getView(R.id.tv_text).getLayoutParams();
                params.width = (int) (fixedWidth + diff);
                params.height = (int) (fixedWidth + diff);
                baseViewHolder.getView(R.id.tv_text).setLayoutParams(params);
            }
        } else {
            //拖拽状态下不做改变，需要调用父类的方法
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    /**
     * 获取删除方块的宽度
     */
    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder) {
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(1).getLayoutParams().width;
    }
}
