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
	
	long getId() {
		return _id;
	}
	void setId(long id) {
		_id = id;
	}
	long getParentId() {
		return _parentId;
	}
	void setParentId(long parentId) {
		_parentId = parentId;
	}
	String getName() {
		return _name;
	}
	void setName(String name) {
		_name = name;
	}
	String[] getSmallImageUrls() {
		return _smallImageUrls;
	}
	void setSmallImageUrls(String[] smallImageUrls) {
		_smallImageUrls = smallImageUrls;
	}
	String[] getMediumImageUrls() {
		return _mediumImageUrls;
	}
	void setMediumImageUrls(String[] mediumImageUrls) {
		_mediumImageUrls = mediumImageUrls;
	}
	String[] getLargeImageUrls() {
		return _largeImageUrls;
	}
	void setLargeImageUrls(String[] largeImageUrls) {
		_largeImageUrls = largeImageUrls;
	}
	double getPrice() {
		return _price;
	}
	void setPrice(double price) {
		_price = price;
	}
	double getRating() {
		return _rating;
	}
	void setRating(double rating) {
		_rating = rating;
	}
	int getOverviewsCount() {
		return _overviewsCount;
	}
	void setOverviewsCount(int overviewsCount) {
		_overviewsCount = overviewsCount;
	}
	String[] getOverviews() {
		return _overviews;
	}
	void setOverviews(String[] overviews) {
		_overviews = overviews;
	}
	String[] getTags() {
		return _tags;
	}
	void setTags(String[] tags) {
		_tags = tags;
	}
	String getDescription() {
		return _description;
	}
	void setDescription(String description) {
		_description = description;
	}
}
