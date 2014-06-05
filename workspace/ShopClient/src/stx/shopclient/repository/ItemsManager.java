package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.entity.AnalogGroup;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Overview;

public class ItemsManager
{
	private ArrayList<CatalogItem> _Items;
	private OverviewsManager _OverviewsManager;

	public ItemsManager(OverviewsManager overviewsManager)
	{
		_Items = new ArrayList<CatalogItem>();
		_OverviewsManager = overviewsManager;
	}

	public CatalogItem getItem(long itemId)
	{
		CatalogItem item = null;
		for (CatalogItem el : _Items)
		{
			if (el.getId() == itemId)
			{
				item = el;
				break;
			}
		}
		return item;
	}

	public Collection<CatalogItem> getItems(long nodeId)
	{
		ArrayList<CatalogItem> items = new ArrayList<CatalogItem>();
		for (CatalogItem el : _Items)
		{
			if (el.getNodeId() == nodeId)
			{
				items.add(el);
			}
		}

		return items;
	}

	public Collection<CatalogItem> getFavorits()
	{
		return _Items;
	}

	public Collection<AnalogGroup> getAnalogs(long itemId)
	{
		List<AnalogGroup> items = new ArrayList<AnalogGroup>();
		CatalogItem item = getItem(itemId);
		Collection<CatalogItem> analogs = getItems(item.getNodeId());

		long[] ids = new long[analogs.size() - 1];
		int i = 0;
		for (CatalogItem el : analogs)
		{
			if (el.getId() != itemId)
			{
				ids[i] = el.getId();
				i++;
			}
		}
		AnalogGroup group = new AnalogGroup();
		group.setIds(ids);
		group.setName("Попробуйте так же");
		items.add(group);
		return items;
	}

	public void add(CatalogItem item, long nodeId)
	{
		item.setNodeId(nodeId);
		_Items.add(item);
		for (Overview el : item.getOverviews())
		{
			_OverviewsManager.add(el, item.getId());
		}
	}

}
