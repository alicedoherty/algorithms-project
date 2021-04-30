/**
 * Class TernarySearchTree: implements a Ternary Search Tree.
 * @param <T> This is a type parameter.
 *
 * For example to create a new TernarySearchTree class containing Integer data: 
 *    TernarySearchTree<Integer> TST = new TernarySearchTree<Integer>();
 *
 * This class offers the methods to "get" a list of values for all nodes which could possibly
 * be valid for the search query and also to "put" a new node into the tree with a specific 
 * character and value.
 */


import java.util.ArrayList;
import java.util.List;

public class TernarySearchTree<T> {

	private TernaryNode<T> root; 		// Root node of TST
	private List<T> possibleVals; 		// List containing all values from nodes possibly satisfying search query
	
	private class TernaryNode<T> {
		public char character;
		public T val;
			
		// Left, middle, and right subtries
		public TernaryNode<T> left;
		public TernaryNode<T> middle;
		public TernaryNode<T> right;
			
		public TernaryNode() {
			this.left = null;
			this.middle = null;
			this.right = null;
		}
	}
	
	/*
	 * Returns a list of all values associated with nodes in the trie found with the key and all of its subtries
	 * @param key the search key
	 * @return list of all values from nodes contained in the subtries of the node associated with the key, or in that node.
	 * @throws IllegalArgumentException if key is null or if key's length is 0 
	 */
	
	public List<T> get (String key) {
		if (key == null) {
			throw new IllegalArgumentException("Inputted key for get() is null");
		}
		
		if (key.length() == 0) {
			throw new IllegalArgumentException("Inputted key must have length >= 1");
		}

		TernaryNode<T> x = get(root, key, 0);
		if (x == null) return null;
		
		possibleVals = new ArrayList<T>();
		if (x.middle.val != null) possibleVals.add(x.val);
		findChildVals(x.middle);
		
		return possibleVals;
	}
	
	// Returns the subtrie corresponding to the inputted key
	private TernaryNode<T> get(TernaryNode<T> x, String key, int d) {
		if (x == null) return null;                                    
		if (key.length() == 0) {
			throw new IllegalArgumentException("Inputted key must have length >= 1");
		}
		
		char c = key.charAt(d);
		if (c < x.character)			return get(x.left, key, d);
		else if (c > x.character)		return get(x.right, key, d);
		else if (d < key.length() - 1)  return get(x.middle, key, d+1);
		else							return x;
	}
	
	// Recursively searches each subtrie for the inputted node
	// If the current node's value if not null, it adds it to the list of possible values
	private void findChildVals (TernaryNode<T> x) {
		if (x != null) {
			findChildVals(x.left);
			findChildVals(x.middle);
			findChildVals(x.right);
			
			if (x.val != null) possibleVals.add(x.val);
		}
	}
	
	/*
	 * Inserts a node containing the key-value pair into the Ternary Search Tree.
	 * If a node already exists for this key, it will overwrite its value with the new value.
	 * If the value is null, it will essentially delete the key from the search tree.
	 * @param key the search key
	 * @param val the value
	 * @throws IllegalArgumentException if key is null or if key's length is 0 
	 */
    public void put(String key, T val) {
        if (key == null) {
            throw new IllegalArgumentException("Inputted key for put() is null");
        }
        
        root = put(root, key, val, 0);
    }

    /*
     *  If the current character is the final character in the key, it will set the current node's
     *  value to the inputted value. Otherwise, it will go left, right, or down the middle, depending
     *  on the result of the comparison between the key's current character and the node's character.
     */
    private TernaryNode<T> put(TernaryNode<T> x, String key, T val, int d) {
        char c = key.charAt(d);
        
        if (x == null) {
            x = new TernaryNode<T>();
            x.character = c;
        }
        
        if      (c < x.character)		x.left  = put(x.left,  key, val, d);
        else if (c > x.character)		x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)  x.middle   = put(x.middle,   key, val, d+1);
        else                            x.val   = val;
        return x;
    }
}
