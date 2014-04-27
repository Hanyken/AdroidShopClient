package stx.shopclient;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.ui.common.LoadMoreListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestListFragment extends ListFragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		ListAdapter adapter = new ListAdapter(getActivity(), getListView());
		setListAdapter(adapter);
		getListView().setOnScrollListener(adapter);
		
		adapter.startLoadingData();
	}

	class ListAdapter extends LoadMoreListAdapter {

		List<String> _dataList = new ArrayList<String>();
		Context _context;
		ProgressBar _progressBar;
		ListView _listView;

		public ListAdapter(Context context, ListView listView) {
			super(listView);
			_context = context;
			_progressBar = new ProgressBar(_context);
			_listView = listView;		
			
			_listView.addFooterView(_progressBar);
		}

		public void addData() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int size = _dataList.size();

			for (int i = size; i < size + 30; i++) {
				_dataList.add(Integer.toString(i));
			}
		}

		@Override
		public int getCount() {
			// if (!_isInitialized) {
			// _isInitialized = true;
			// addData();
			// }

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
		public void onLoadMore() {
			addData();
		}
		
		@Override
		public void onBeforeLoadData() {
			_listView.addFooterView(_progressBar);
		}
		
		@Override
		public void onAfterLoadData() {
			_listView.removeFooterView(_progressBar);
		}
	}
}
