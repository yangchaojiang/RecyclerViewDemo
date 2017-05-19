package demo.com.recyclerviewdemo;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by yangc on 2017/5/11.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */
public class MYDiffCallBack<T> extends DiffUtil.Callback {
    public static final String TAG = "MYDiffCallBack";
    private List<T> mOldDatas, mNewDatas;

    public MYDiffCallBack(List<T> data, List<T> list) {
        this.mOldDatas=data;
        this.mNewDatas=list;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas==null?0:mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas==null?0:mNewDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        /**
         *这边根据你的需求去判断到底是刷新整个item，还是该item只有一点点数据的变化，从而去下面的方法判断是否需要刷新
         *我的理解就是该item从旧数据到新数据我们用到的layout都变了，就是多布局，那就这边直接返回false，刷新整个item。如果返回true，讲继续执行下面的方法去判断。
         **/
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //这边也是一样的，如果返回true，则不刷新该item，如果返回false，则只刷新该item，并且是一个动作非常小的刷新
        return false;
    }
}
