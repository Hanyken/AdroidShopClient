package stx.shopclient.ui.common;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class LoadMoreListAdapter extends BaseAdapter implements
		OnScrollListener {
	// The minimum amount of items to have below your current scroll position
	// before loading more.
	private int visibleThreshold = 0;
	// The total number of items in the dataset after the last load
	private int previousTotalItemCount = 0;
	// True if we are still waiting for the last set of data to load.
	private boolean loading = true;
	private boolean _allDataLoaded = false;

	private ListView listView;

	public LoadMoreListAdapter(ListView listView) {
		this.listView = listView;
	}

	public LoadMoreListAdapter(int visibleThreshold, ListView listView) {
		this(listView);
		this.visibleThreshold = visibleThreshold;
	}

	public void setLoading(boolean value) {
		loading = value;
	}

	// This happens many times a second during a scroll, so be wary of the code
	// you place here.
	// We are given a few useful parameters to help us work out if we need to
	// load some more data,
	// but first we check if we are waiting for the previous load to finish.
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// If the total item count is zero and the previous isn't, assume the
		// list is invalidated and should be reset back to initial state
		// If there are no items in the list, assume that initial items are
		// loading
		if (!loading && (totalItemCount < previousTotalItemCount)) {
			this.previousTotalItemCount = totalItemCount;
			if (totalItemCount == 0) {
				this.loading = true;
			}
		}

		// If it isn’t currently loading, we check to see if we have breached
		// the visibleThreshold and need to reload more data.
		// If we do need to reload some more data, we execute onLoadMore to
		// fetch the data.
		if (!_allDataLoaded && !loading
				&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			startLoadingData();
			loading = true;
		}
	}

	public void startLoadingData() {
		
		onBeforeLoadData();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
				 	_allDataLoaded = onLoadMore();
				} catch (Throwable e) {
					Log.e("LoadMoreListAdapter.LoadingThread", e.getMessage(),
							e);
				} finally {
					loading = false;
					
					listView.post(new Runnable() {
						@Override
						public void run() {			
							onAfterLoadData();
							notifyDataSetChanged();							
						}
					});
				}
			}
		});

		thread.start();
	}

	// Defines the process for actually loading more data based on page
	public abstract boolean onLoadMore();
	
	public void onBeforeLoadData(){
		
	}
	
	public void onAfterLoadData(){
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Don't take any action on changed
	}
}
