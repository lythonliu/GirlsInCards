package com.bankl.womenincards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<MyData> data = new ArrayList();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

        data.add(new MyData("小卡", 12, 1));
        data.add(new MyData("中卡", 22, 2));
        data.add(new MyData("小卡", 12, 3));
        data.add(new MyData("中卡", 22, 4));
        data.add(new MyData("小卡", 12, 5));
        data.add(new MyData("中卡", 22, 6));
        data.add(new MyData("小卡", 12, 7));
        data.add(new MyData("中卡", 22, 8));
        data.add(new MyData("小卡", 12, 0));
        data.add(new MyData("大卡", 24, 9));

        data.add(new MyData("小卡", 12, 11));
        data.add(new MyData("中卡", 22, 12));
        data.add(new MyData("小卡", 12, 13));
        data.add(new MyData("中卡", 22, 14));
        data.add(new MyData("小卡", 12, 15));
        data.add(new MyData("中卡", 22, 16));
        data.add(new MyData("小卡", 12, 17));
        data.add(new MyData("中卡", 22, 18));
        data.add(new MyData("小卡", 12, 10));
        data.add(new MyData("大卡", 24, 19));

        data.add(new MyData("小卡", 12, 21));
        data.add(new MyData("中卡", 22, 22));
        data.add(new MyData("小卡", 12, 23));
        data.add(new MyData("中卡", 22, 24));
        data.add(new MyData("小卡", 12, 25));
        data.add(new MyData("中卡", 22, 26));
        data.add(new MyData("小卡", 12, 27));
        data.add(new MyData("中卡", 22, 28));
        data.add(new MyData("小卡", 12, 20));
        data.add(new MyData("大卡", 24, 29));

    }

    private void initView() {
        rv = findViewById(R.id.rv);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setAdapter(mAdapter = new MyAdapter());
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int childPosition = parent.getChildLayoutPosition(view);
                if (childPosition>-1){
                    int type = data.get(childPosition).type;

                }
                outRect.left = outRect.right = (int) (parent.getContext().getResources().getDisplayMetrics().density*5);
                outRect.top = outRect.bottom = (int) (parent.getContext().getResources().getDisplayMetrics().density*4);


            }
        });
        new MyHelper(new MyHelperInner()).attachToRecyclerView(rv);
        rv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                if (e.getAction()==MotionEvent.ACTION_UP){
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(data.get(holder.getAdapterPosition()).type == 24);
            }
        }

        HashMap<Integer, View> mCache = new HashMap<>();

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(viewType == 12 ? R.layout.item12 : viewType == 22 ? R.layout.item22 : R.layout.item24, parent, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                if (holder.itemView instanceof ViewGroup)((ViewGroup) holder.itemView).removeAllViews();
//                ((MyViewHolder) holder).position.setText(position + "");
//                ((MyViewHolder) holder).position.setTextColor(Color.parseColor("#ff0000"));
                View view = mCache.get(data.get(position).id);
                if (view != null) {
                    if (view.getParent() instanceof ViewGroup ) {
                        if (view.getParent() != holder.itemView) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }else{
                            return;
                        }
                    }
                } else {
                    view = getView(holder.itemView, data.get(position).type, data.get(position).id, mCache);
                }
                if (holder.itemView instanceof ViewGroup) {
                    ((ViewGroup) holder.itemView).addView(view);
                    TextView textView = new TextView(holder.itemView.getContext());
                    textView.setText(position+"");
                    textView.setTextColor(Color.parseColor("#ff0000"));
                    ((ViewGroup) holder.itemView).addView(textView);
                }

            }
        }

        private View getView(View itemView, int type, int id, HashMap<Integer, View> mCache) {
            ImageView imageView = new ImageView(itemView.getContext());
            float dp = 156;
            if (type == 12) dp = 74;
            int typedDp = (int) (itemView.getContext().getResources().getDisplayMetrics().density * dp);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, typedDp);
            imageView.setLayoutParams(layoutParams);
            int drawable = itemView.getContext().getResources().getIdentifier("img_" + id, "drawable", getPackageName());
            if (drawable > 0) {
                imageView.setImageResource(drawable);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mCache.put(id, imageView);
            return imageView;
        }

        @Override
        public int getItemViewType(int position) {
            return data.get(position).type;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void onItemMove(RecyclerView.ViewHolder source,
                               RecyclerView.ViewHolder target) {
            int fromPosition = source.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < data.size() && toPosition < data.size()) {
                if (fromPosition<toPosition){
                    for (int i=fromPosition;i<toPosition;i++){
                        Collections.swap(data,i,i+1);
                        notifyItemMoved(i, i+1);
                    }
                }else{
                    for (int i = fromPosition;i>toPosition;i--){
                        Collections.swap(data,i,i-1);
                        notifyItemMoved(i, i-1);
                    }
                }
//                //交换数据位置
//                Collections.swap(data, fromPosition, toPosition);
//                //刷新位置交换
//                notifyItemMoved(fromPosition, toPosition);
            }
            //移动过程中移除view的放大效果
            onItemClear(source);
        }

        public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
            //当拖拽选中时放大选中的view
            viewHolder.itemView.setScaleX(1.2f);
            viewHolder.itemView.setScaleY(1.2f);
        }

        public void onItemClear(RecyclerView.ViewHolder viewHolder) {

            //拖拽结束后恢复view的状态
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
        }


        private class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView position;

            public MyViewHolder(View inflate) {
                super(inflate);
                this.position = inflate.findViewById(R.id.position);
            }
        }
    }

    private class MyData {
        private final String typeName;
        private final int type;
        private final int id;

        public MyData(String typeName, int type, int id) {
            this.typeName = typeName;
            this.type = type;
            this.id = id;
        }
    }

    private class MyHelper extends ItemTouchHelper {
        /**
         * Creates an ItemTouchHelper that will work with the given Callback.
         * <p>
         * You can attach ItemTouchHelper to a RecyclerView via
         * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
         * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
         *
         * @param callback The Callback which controls the behavior of this touch helper.
         */
        public MyHelper(@NonNull Callback callback) {
            super(callback);
        }
    }

    private class MyHelperInner extends ItemTouchHelper.Callback {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            //int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; //允许上下的拖动
            //int dragFlags =ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; //允许左右的拖动
            //int swipeFlags = ItemTouchHelper.LEFT; //只允许从右向左侧滑
            //int swipeFlags = ItemTouchHelper.DOWN; //只允许从上向下侧滑
            //一般使用makeMovementFlags(int,int)或makeFlag(int, int)来构造我们的返回值
            //makeMovementFlags(dragFlags, swipeFlags)

            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; //允许上下左右的拖动
            return makeMovementFlags(dragFlags, 0);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;//长按启用拖拽
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false; //不启用拖拽删除
        }


        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //通过接口传递拖拽交换数据的起始位置和目标位置的ViewHolder
            mAdapter.onItemMove(viewHolder, target);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                //当滑动或者拖拽view的时候通过接口返回该ViewHolder
                mAdapter.onItemSelect(viewHolder);
            }
        }


        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (!recyclerView.isComputingLayout()) {
                //当需要清除之前在onSelectedChanged或者onChildDraw,onChildDrawOver设置的状态或者动画时通过接口返回该ViewHolder
                mAdapter.onItemClear(viewHolder);
            }
        }
    }
}