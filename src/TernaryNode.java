public class TernaryNode<T> {
	public char character;
	public T val;
		
	public TernaryNode<T> left;
	public TernaryNode<T> middle;
	public TernaryNode<T> right;
		
	public TernaryNode() {
		this.left = null;
		this.middle = null;
		this.right = null;
	}
	
	public int size() {
		return size(this);
	}
	
	public int size(TernaryNode node) {
		if (node == null) return 0;
		
		return (size(node.left) + size(node.middle) + size(node.right) + 1);
	}
}