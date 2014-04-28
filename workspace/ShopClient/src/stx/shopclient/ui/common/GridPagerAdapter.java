package stx.shopclient.ui.common;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class GridPagerAdapter<TItemModel> extends PagerAdapter implements OnItemClickListener {

	List<TItemModel> _items;
	Context _context;
	private int _gridColumnCount = 3;

	public GridPagerAdapter(List<TItemModel> items, Context context) {
		_items = items;
		_context = context;
	}

	protected abstract View createItemView(TItemModel item);
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		int startIndex = (Integer)parent.getTag();
		
		TItemModel item = _items.get(startIndex + position);
		
		onItemClick(item);		
	}
	
	protected void onItemClick(TItemModel item){
		
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
//		Button button = new Button(_context);
//		button.setText("button");
//		container.addView(button);
//		return button;
		GridView grid = new GridView(_context);
		grid.setOnItemClickListener(this);
		grid.setNumColumns(_gridColumnCount);
		ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.height = LayoutParams.MATCH_PARENT;
		layoutParams.width = LayoutParams.MATCH_PARENT;
		//grid.setGravity(Gravity.CENTER);
		//grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

		int startIndex = position * _gridColumnCount;
		int count = _gridColumnCount;

		if (startIndex + _gridColumnCount > _items.size())
			count = _items.size() - startIndex;

		GridAdapter<TItemModel> gridAdapter = new GridAdapter<TItemModel>(this,
				startIndex, count, _items);

		grid.setTag(Integer.valueOf(startIndex));
		grid.setAdapter(gridAdapter);

		container.addView(grid);

		return grid;

		// GridLayout grid = new GridLayout(_context);
		// grid.setColumnCount(_gridColumnCount);
		// grid.setRowCount(1);
		//
		// ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
		// layoutParams.gravity = Gravity.CENTER;
		// layoutParams.height = LayoutParams.MATCH_PARENT;
		// layoutParams.width = LayoutParams.MATCH_PARENT;
		//
		// grid.setLayoutParams(layoutParams);
		//
		// int startIndex = position * _gridColumnCount;
		// int count = _gridColumnCount;
		//
		// if (startIndex + _gridColumnCount > _items.size())
		// count = _items.size() - startIndex;
		//
		// for (int i = startIndex; i < startIndex + count; i++) {
		// TItemModel item = _items.get(i);
		//
		// View view = createItemView(item);
		//
		// GridLayout.LayoutParams params = new GridLayout.LayoutParams();
		// params.setGravity(Gravity.CENTER);
		// grid.addView(view);
		// }
		//
		// container.addView(grid);
		// return grid;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		int count = _items.size() / _gridColumnCount;

		if (_items.size() % _gridColumnCount > 0)
			count++;

		return count;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view.equals(obj);
	}

	public int getGridColumnCount() {
		return _gridColumnCount;
	}

	public void setGridColumnCount(int gridColumnCount) {
		_gridColumnCount = gridColumnCount;
	}

	static class GridAdapter<TItemModel> extends BaseAdapter {
		GridPagerAdapter<TItemModel> _pagerAdapter;
		List<TItemModel> _items;
		int _startIndex;
		int _count;

		public GridAdapter(GridPagerAdapter<TItemModel> adapter,
				int startIndex, int count, List<TItemModel> items) {
			_startIndex = startIndex;
			_count = count;
			_items = items;
			_pagerAdapter = adapter;
		}

		@Override
		public int getCount() {
			return _count;
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
			TItemModel item = _items.get(_startIndex + index);
			View view = _pagerAdapter.createItemView(item);

			return view;
		}

	}
}
