package stx.shopclient.entity;

public class CatalogItem {
	private long _id;
	private long _parentId;
	private String _name;
	private String[] _smallImageUrls;
	private String[] _mediumImageUrls;
	private String[] _largeImageUrls;
	private double _price;
	private double _rating;
	private int _overviewsCount;
	private String[] _overviews;
	private String[] _tags;
	private String _description;
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		_id = id;
	}
	public long getParentId() {
		return _parentId;
	}
	public void setParentId(long parentId) {
		_parentId = parentId;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}
	public String[] getSmallImageUrls() {
		return _smallImageUrls;
	}
	public void setSmallImageUrls(String[] smallImageUrls) {
		_smallImageUrls = smallImageUrls;
	}
	public String[] getMediumImageUrls() {
		return _mediumImageUrls;
	}
	public void setMediumImageUrls(String[] mediumImageUrls) {
		_mediumImageUrls = mediumImageUrls;
	}
	public String[] getLargeImageUrls() {
		return _largeImageUrls;
	}
	public void setLargeImageUrls(String[] largeImageUrls) {
		_largeImageUrls = largeImageUrls;
	}
	public double getPrice() {
		return _price;
	}
	public void setPrice(double price) {
		_price = price;
	}
	public double getRating() {
		return _rating;
	}
	public void setRating(double rating) {
		_rating = rating;
	}
	public int getOverviewsCount() {
		return _overviewsCount;
	}
	public void setOverviewsCount(int overviewsCount) {
		_overviewsCount = overviewsCount;
	}
	public String[] getOverviews() {
		return _overviews;
	}
	public void setOverviews(String[] overviews) {
		_overviews = overviews;
	}
	public String[] getTags() {
		return _tags;
	}
	public void setTags(String[] tags) {
		_tags = tags;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		_description = description;
	}
}
