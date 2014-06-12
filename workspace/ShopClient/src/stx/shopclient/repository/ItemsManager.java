package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stx.shopclient.entity.AnalogGroup;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Overview;

public class ItemsManager
{
	private Map<Long, CatalogItem> _Items;
	private OverviewsManager _OverviewsManager;

	public ItemsManager(OverviewsManager overviewsManager)
	{
		_Items = new HashMap<Long, CatalogItem>();
		_OverviewsManager = overviewsManager;
	}

	public CatalogItem getItem(long itemId)
	{
		CatalogItem item = null;

		if (_Items.containsKey(itemId))
			return _Items.get(itemId);

		return item;
	}

	public Collection<CatalogItem> getItems(long nodeId)
	{
		ArrayList<CatalogItem> items = new ArrayList<CatalogItem>();

		for (CatalogItem el : _Items.values())
		{
			if (el.getNodeId() == nodeId)
			{
				items.add(el);
			}
		}

		return items;
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
		_Items.put(item.getId(), item);
		for (Overview el : item.getOverviews())
		{
			_OverviewsManager.add(el, item.getId());
		}
	}

	public void addAll(Collection<CatalogItem> items)
	{
		for (CatalogItem item : items)
		{
			_Items.put(item.getId(), item);
		}
	}

}
