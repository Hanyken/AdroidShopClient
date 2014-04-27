package stx.shopclient.entity;

public class Catalog {
	private long _id;
	private String _name;
	private int _nodeCount;
	
	long getId() {
		return _id;
	
	}
	void setId(long id) {
		_id = id;
	}
	
	String getName() {
		return _name;
	}
	
	void setName(String name) {
		_name = name;
	}
	
	int getNodeCount() {
		return _nodeCount;
	}
	
	void setNodeCount(int nodeCount) {
		_nodeCount = nodeCount;
	}
}
