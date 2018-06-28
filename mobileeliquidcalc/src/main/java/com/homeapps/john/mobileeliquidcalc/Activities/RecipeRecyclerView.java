package com.homeapps.john.mobileeliquidcalc.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class RecipeRecyclerView extends RecyclerView {
    private View mEmptyView;

    private RecyclerView.AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateEmptyView();
        }
    };

    public RecipeRecyclerView(Context context) {
        super(context);
    }

    public RecipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecipeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Designate a view as the empty view. When the backing adapter has no
     * data this view will be made visible and the recycler view hidden.
     *
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(mDataObserver);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mDataObserver);
        }
        super.setAdapter(adapter);
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (mEmptyView != null && getAdapter() != null) {
            boolean showEmptyView = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
            setVisibility(showEmptyView ? GONE : VISIBLE);
        }
    }
}