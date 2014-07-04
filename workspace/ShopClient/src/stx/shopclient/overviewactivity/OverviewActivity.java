package stx.shopclient.overviewactivity;

import java.util.Collection;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Overview;
import stx.shopclient.entity.Token;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.Repository;
import stx.shopclient.styles.ColorButtonDrawable;
import stx.shopclient.styles.ImageButtonDrawable;
import stx.shopclient.webservice.WebClient;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class OverviewActivity extends BaseActivity implements OnClickListener
{
	public static final int OVERVIEW_REQUEST = 1;

	private final int MI_COMMENT = 1;

	private LinearLayout llOverview;
	private RatingBar rtgRaiting;
	private TextView txtOverview;
	private long _ItemId;
	private ProgressDialog _progressDialog;
	private OverviewAdapter _adapter;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.overview_activity,
				parent, false);

		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();
		Intent intent = getIntent();
		_ItemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);

		PullToRefreshListView lstMain = (PullToRefreshListView) view
				.findViewById(R.id.lstMain);
		lstMain.setMode(Mode.PULL_FROM_END);

		llOverview = (LinearLayout) view.findViewById(R.id.llOverviw);
		txtOverview = (TextView) view.findViewById(R.id.txtOverview);
		rtgRaiting = (RatingBar) view.findViewById(R.id.rtgRating);

		Button btnOk = (Button) view.findViewById(R.id.btnOk);
		Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		setRatingBarColor(rtgRaiting, settings.getRatingColor());

		btnOk.setBackground(getBueButtonDrawable(settings));
		btnCancel.setBackground(getBueButtonDrawable(settings));

		_adapter = new OverviewAdapter(this, lstMain, _ItemId);

		lstMain.setAdapter(_adapter);
		lstMain.setOnRefreshListener(new OnRefreshListener<ListView>()
		{
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				_adapter.loadData(false);
			}

		});

		LoadTask task = new LoadTask();
		task.execute();

		return view;
	}

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	@Override
	protected void onDestroy()
	{
		_adapter.setActivityDestroyed(true);

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.clear();
		MenuItem commentItem = menu.add(0, MI_COMMENT, 0, "Коментарий");
		
		CatalogSettings settings =
				 Repository.get(this).getCatalogManager().getSettings(); 
		Bitmap bmp = settings.getImageFromPath(getResources(), "Comment");		
		 
		//BitmapFactory.decodeResource(getResources(), R.drawable.img_share);
		ImageButtonDrawable normal = new ImageButtonDrawable(bmp);
		normal.setSize(30, 30);
		 
		commentItem.setIcon(normal);
		commentItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case MI_COMMENT:
			if (llOverview.getVisibility() == View.GONE)
				ShowOverview();
			else
				HideOverview();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void ShowOverview()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llOverview, "y",
				-150, 0);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				llOverview.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, 0);
				txtOverview.requestFocus();
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
			}
		});
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}

	private void HideOverview()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llOverview, "y",
				0, -150);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtOverview.getWindowToken(), 0);

				llOverview.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
			}
		});
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btnOk:

			EditOverviewTask task = new EditOverviewTask();
			task.rating = (double) rtgRaiting.getRating();
			task.description = txtOverview.getText().toString();
			task.execute();

			HideOverview();
			break;
		case R.id.btnCancel:
			readUserOverview();
			HideOverview();
			break;
		}
	}

	private void readUserOverview()
	{
		Overview item = Repository.get(this).getOverviewsManager()
				.getUserOverview(_ItemId);
		txtOverview.setText(item.getDescription());
		rtgRaiting.setRating((float) item.getRating());
	}

	private Drawable getBueButtonDrawable(CatalogSettings settings)
	{
		StateListDrawable drawable = new StateListDrawable();
		Drawable normal = new ColorButtonDrawable(settings.getBackground());
		Drawable press = new ColorButtonDrawable(settings.getPressedColor());
		Drawable disable = new ColorButtonDrawable(settings.getDisableColor());

		drawable.addState(new int[]
		{ android.R.attr.state_pressed }, press);
		drawable.addState(new int[]
		{ -android.R.attr.state_enabled }, disable);
		drawable.addState(new int[0], normal);
		return drawable;
	}

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		Overview overview;

		@Override
		protected void onPreExecute()
		{
			_progressDialog = ProgressDialog.show(OverviewActivity.this,
					"Загрузка", "Выполняется загрузка отзывов");
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				WebClient client = createWebClient();
				overview = client.getUserOverview(Token.getCurrent(), _ItemId);
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
			_progressDialog.dismiss();

			if (exception != null)
			{
				Toast.makeText(OverviewActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			}
			else
			{
				if (overview != null)
				{
					Repository
							.get(null)
							.getOverviewsManager()
							.addUserOverview(_ItemId, overview.getRating(),
									overview.getDescription());

					readUserOverview();
				}
			}
		}
	}

	class EditOverviewTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		public double rating;
		public String description;

		@Override
		protected void onPreExecute()
		{
			_progressDialog = ProgressDialog.show(OverviewActivity.this,
					"Загрузка", "Отправка отзыва");
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				WebClient client = createWebClient();
				client.editOverview(Token.getCurrent(), _ItemId, rating,
						description);
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
			_progressDialog.dismiss();

			if (exception != null)
			{
				Toast.makeText(OverviewActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			}
			else
			{
				Repository.get(null).getOverviewsManager()
						.addUserOverview(_ItemId, rating, description);

				Toast.makeText(OverviewActivity.this,
						getString(R.string.overview_add_message),
						Toast.LENGTH_SHORT).show();

				_adapter.refreshData();
				setResult(1);
			}
		}
	}
}
