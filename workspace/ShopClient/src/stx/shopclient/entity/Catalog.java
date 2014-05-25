package stx.shopclient.entity;

public class Catalog {
	private long _id;
	private String _name;
	private int _nodeCount;
	
	public long getId() {
		return _id;
	
	}
	public void setId(long id) {
		_id = id;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public int getNodeCount() {
		return _nodeCount;
	}
	
	public void setNodeCount(int nodeCount) {
		_nodeCount = nodeCount;
	}
}
