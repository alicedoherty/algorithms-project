import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TernarySearchTree<T> {

	private TernaryNode<T> root;
	private List<T> possibleVals;
	public static final String[] STOP_KEYWORDS = new String[] {"FLAGSTOP", "WB", "NB", "SB", "EB"};
	public List<String> inputKeywords; 
	
	public int size() {
		return root.size();
	}
	
	public boolean contains(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Inputted key for contains() is null");
		}
		
		return get(key) != null;
	}
	
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
	
	private void findChildVals (TernaryNode<T> x) {
		if (x != null) {
			findChildVals(x.left);
			findChildVals(x.middle);
			findChildVals(x.right);
			
			if (x.val != null) possibleVals.add(x.val);
		}
		

	}
	
    public void put(String key, T val) {
        if (key == null) {
            throw new IllegalArgumentException("Inputted key for put() is null");
        }
        
        root = put(root, key, val, 0);
    }

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
