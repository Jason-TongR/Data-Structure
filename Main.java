
/* GTIIT - Data Structures 1 - Spring 2026 - Assignment 1 */

public class Main {

    public static void main(String[] args) {
        SetTest.runAllTests();
    }

}

class Node {
    int key;
    Node left;
    Node right;
    int height;

    Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    static int slowHeight(Node p) {
        if (p == null){
            return 0;
        }else{
            return 1 + Math.max(slowHeight(p.left) , slowHeight(p.right));
        }
        // Worst-case time complexity is O(n)
    }

    static int fastHeight(Node p) {
        if (p == null){
            return 0;
        }else{
        return p.height;
        }
        // Worst-case time complexity is O(1)    
    }

    static int balanceFactor(Node p) {
        if (p == null){
            return 0;
        }else{
            return fastHeight(p.left) - fastHeight(p.right);
        }
        // Worst-case time complexity is O(1)  
    }

    static boolean checkInvariant(Node p) {
        // Empty tree satisfies the invariant
        if (p == null){
            return true;
        }
        
        // The key of the root is right(compare with leftC and rightC)
        if (p.left != null && p.left.key >= p.key){
            return false;
        }
        if (p.right != null && p.right.key <= p.key){
            return false;
        }
        
        // The correctness for the height
        int actualHeight = slowHeight(p);
        if (actualHeight != fastHeight(p)){
            return false;
        }
        
        // BalanceFactor must be 1 0 0r -1
        int num = balanceFactor(p);
        if (num < -1 || num > -1){
            return false;
        }
        
        // Checking left and right subtree recursively
        return checkInvariant(p.left) && checkInvariant(p.right);
    }

    static void testInvariant() {
        // Construct the root
        Node p = new Node(5);
        p.height = 3;
        
        // Construct the left-subtree
        p.left = new Node(2);
        p.left.height = 1;
        
        // Construct the right-subtree
        p.right = new Node(7);
        p.right.height = 2;
        p.right.left = new Node(6);
        p.right.left.height = 1;
        p.right.right = new Node(9);
        p.right.right.height = 1;
 
        if (checkInvariant(p)) {
            System.out.println("Invariant is fulfilled.");
        } else {
            System.out.println("The invariant is not fulfilled.");
        }
    }

    static Node rotateLeft(Node p) {
        Node y = p.right;
        Node T = y.left;
        // Rotate
        y.left = p;
        p.right = T;
        // Update height
        p.height = 1 + Math.max(fastHeight(p.left) , fastHeight(p.right));
        y.height = 1 + Math.max(fastHeight(y.left) , fastHeight(y.right));
        return y;
    }

    static Node rotateRight(Node p) {
        Node y = p.left;
        Node T = y.right;
        // Rotate
        y.right = p;
        p.left = T;
        // Update height
        p.height = 1 + Math.max(fastHeight(p.left) , fastHeight(p.right));
        y.height = 1 + Math.max(fastHeight(y.left) , fastHeight(y.right));
        return y;
    }

    static Node doubleRotateLeft(Node p) {
        p.right = rotateRight(p.right);
        return rotateLeft(p);
    }

    static Node doubleRotateRight(Node p) {
        p.left = rotateLeft(p.left);
        return rotateRight(p);
    }

}

class Set {
    private Node root;

    Set() {
        this.root = null;
    }

    boolean checkInvariant() {
        return Node.checkInvariant(this.root);
    }

    boolean contains(int x) {
        // TODO: COMPLETE
        return false;
    }

    void add(int x) {
        // TODO: COMPLETE
    }

    void remove(int x) {
        // TODO: COMPLETE
    }
}

class SetTest {

    static void runAllTests() {
        test0();
        test1();
        // TODO: add more tests
    }

    static void test0() {
        Set s = new Set();
        assert !s.contains(1) : "Empty set must not contain 1";
        assert s.checkInvariant() : "Invariant check fails";
        s.add(1);
        assert s.contains(1) : "Set must contain 1";
        assert s.checkInvariant() : "Invariant check fails";
    }

    static void test1() {
        // TODO: COMPLETE
    }

    // TODO: add more tests
}

