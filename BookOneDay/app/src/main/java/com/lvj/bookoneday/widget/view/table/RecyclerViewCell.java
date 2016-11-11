package com.lvj.bookoneday.widget.view.table;

import android.content.Context;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created Description
 *
 * @Author: qiugaoying
 * @createTime 2015/11/12,11:44
 */
public abstract class RecyclerViewCell<T> {

    private T cellEntity;
    private int layout;
    private Context context; //方便取resource

    public RecyclerViewCell(int layout,Context context) {
        this.layout = layout;
        this.context = context;
    }

    //刷新表格数据
    protected abstract void refreshCellData(BGAViewHolderHelper viewHolderHelper, int position, T model);

    //绑定item事件:子类要绑定就要覆盖该方法
    protected  void setItemChildListener(BGAViewHolderHelper viewHolderHelper){
    }

    //get,set
    public T getCellEntity() {
        return cellEntity;
    }
    public void setCellEntity(T cellEntity) {
        this.cellEntity = cellEntity;
    }
    public int getLayout() {
        return layout;
    }
    public void setLayout(int layout) {
        this.layout = layout;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
}
