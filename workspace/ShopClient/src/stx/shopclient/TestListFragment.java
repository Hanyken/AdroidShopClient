package stx.shopclient;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.ui.common.LoadMoreListAdapter;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestListFragment extends ListFragment {
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		ListAdapter adapter = new ListAdapter(getActivity());
		setListAdapter(adapter);
		getListView().setOnScrollListener(adapter);
	}

	class ListAdapter extends LoadMoreListAdapter {

		List<String> _dataList = new ArrayList<String>();
		Context _context;
		ProgressBar _progressBar;

		boolean _isLoadingData = false;
		boolean _isInitialized = false;

		public ListAdapter(Context context) {
			_context = context;
			_progressBar = new ProgressBar(_context);

			addData();
		}

		public void addData() {
			if (_isLoadingData)
				return;

			getListView().addFooterView(_progressBar);

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(2000);

						int size = _dataList.size();

						for (int i = size; i < size + 30; i++) {
							_dataList.add(Integer.toString(i));
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						_isLoadingData = false;

						getListView().post(new Runnable() {

							@Override
							public void run() {
								setLoading(false);
								getListView().removeFooterView(_progressBar);
								notifyDataSetChanged();
							}
						});
					}
				}
			});

			thread.start();
		}

		@Override
		public int getCount() {
//			if (!_isInitialized) {
//				_isInitialized = true;
//				addData();
//			}

			return _dataList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup arg2) {
			TextView textView = new TextView(_context);
			textView.setPadding(20, 20, 20, 20);
			textView.setText(_dataList.get(index));
			return textView;
		}

		@Override
		public void onLoadMore(int page, int totalItemsCount) {
			addData();
		}

	}
}
