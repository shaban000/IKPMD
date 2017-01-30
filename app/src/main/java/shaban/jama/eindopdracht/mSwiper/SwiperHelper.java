package shaban.jama.eindopdracht.mSwiper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import shaban.jama.eindopdracht.Adapter.SubdoelAdapter;

/**
 * Created by sangam on 10/01/2017.
 */

public class SwiperHelper extends ItemTouchHelper.SimpleCallback{

    private SubdoelAdapter adapter;
    private Boolean voldaan;

    public SwiperHelper(SubdoelAdapter adapter, boolean voldaan) {
        super(ItemTouchHelper.UP| ItemTouchHelper.DOWN,ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.voldaan = voldaan;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.updateModel(viewHolder.getAdapterPosition(), voldaan);

    }
}
