package shaban.jama.eindopdracht.mSwiper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import shaban.jama.eindopdracht.Adapter.SubdoelAdapter;
import shaban.jama.eindopdracht.Model.Subdoel;
import shaban.jama.eindopdracht.Subdoelen;
import shaban.jama.eindopdracht.Subdoelen_done;

/**
 * Created by sangam on 10/01/2017.
 */

public class SwiperHelper extends ItemTouchHelper.SimpleCallback{

    private SubdoelAdapter adapter;
    private Boolean voldaan;
    private Context c;
    public SwiperHelper(SubdoelAdapter adapter, Context c, boolean voldaan) {
        super(ItemTouchHelper.UP| ItemTouchHelper.DOWN,ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.voldaan = voldaan;
        this.c =c;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.updateModel(viewHolder.getAdapterPosition(), voldaan);
        String tekst;
        if (voldaan){
            tekst = "Subdoel is gezet in DONE";
        }else{
            tekst = "Subdoel is gezet in TODO";
        }
        Toast.makeText(c,tekst,Toast.LENGTH_LONG).show();
    }
}
