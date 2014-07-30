package stx.shopclient.mainactivity;

import java.util.Collection;

import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Token;
import stx.shopclient.repository.Repository;
import stx.shopclient.webservice.WebClient;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStrip;

public class MainItemsTabFragment extends Fragment
{
	CatalogItemViewPagerFragment _popular;
	CatalogItemViewPagerFragment _favorite;
	CatalogItemViewPagerFragment _recent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.main_activity_items_tabs_fragment, container, false);

		view.setBackgroundColor(Color.WHITE);

		PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);

		tabStrip.setTabNames(new String[]
		{ "Популярное", "Избранное", "Последнее" });

		tabStrip.setIndicatorColor(Repository.get(getActivity())
				.getCatalogManager().getSettings().getBackground());
		tabStrip.setUnderlineColor(Repository.get(getActivity())
				.getCatalogManager().getSettings().getBackground());
		tabStrip.setBackgroundColor(Repository.get(getActivity())
				.getCatalogManager().getSettings().getItemPanelColor());

		FrameLayout frameLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout);
		tabStrip.setFrameLayout(frameLayout);

		_popular = (CatalogItemViewPagerFragment) getFragmentManager()
				.findFragmentById(R.id.popularViewPager);
		_favorite = (CatalogItemViewPagerFragment) getFragmentManager()
				.findFragmentById(R.id.favoritesViewPager);
		_recent = (CatalogItemViewPagerFragment) getFragmentManager()
				.findFragmentById(R.id.recentViewPager);

		LoadItemsTask task = new LoadItemsTask();
		task.execute();

		return view;
	}

	@Override
	public void onStart()
	{
		super.onStart();

		LoadItemsTask task = new LoadItemsTask();
		task.execute();
	}

	class LoadItemsTask extends AsyncTask<Void, Void, Void>
	{
		Collection<CatalogItem> popularItems;
		Collection<CatalogItem> favoriteItems;
		Collection<CatalogItem> recentItems;
		Throwable exception;

		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				WebClient client = new WebClient(getActivity());

				popularItems = client.getPopular(Token.getCurrent(),
						Repository.CatalogId);
				favoriteItems = client.getFavorite(Token.getCurrent(),
						Repository.CatalogId);
				recentItems = client.getLast(Token.getCurrent(),
						Repository.CatalogId);
			}
			catch (Throwable ex)
			{
				exception = ex;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			if (getActivity().isDestroyed())
				return;

			if (popularItems != null)
				_popular.setItems(popularItems);
			if (favoriteItems != null)
				_favorite.setItems(favoriteItems);
			if (recentItems != null)
				_recent.setItems(recentItems);
		}
	}
}
