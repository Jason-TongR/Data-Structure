
/* GTIIT - Data Structures 1 - Spring 2026 - Assignment 1 */

import java.util.Stack;
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
        /*  Worst-case time complexity is O(n)

            Reason : beacause this method will go through all the nodes in the tree.
         */
    }

    static int fastHeight(Node p) {
        if (p == null){
            return 0;
        }else{
        return p.height;
        }
        /*  Worst-case time complexity is O(1)
            
            Reason : acutally it just read the field of the class "node" , so it takes O(1) time. 
        */     
    }

    static int balanceFactor(Node p) {
        if (p == null){
            return 0;
        }else{
            return fastHeight(p.left) - fastHeight(p.right);
        }
        /*  Worst-case time complexity is O(1) 

            Reason : because the the method "fastHeight" take O(1) time , and all the operation in the code also take O(1) time
                     so the Worst-case time complexity is O(1)
         */ 
    }

    static boolean checkInvariant(Node p) {
        // Empty tree satisfies the invariant , for empty tree we have nothing to do
        if (p == null){
            return true;
        }

        /*  as the following , it is the case that the tree is not empty ,
            we will return false once we find some node isn't satisfies the representation invariant. 
        */  
        
        // The key of the root is right(compare with leftC and rightC)
        if (p.left != null && p.left.key >= p.key){
            return false;                               // this is contration to the key of root must greater than the key of the left child
        }
        if (p.right != null && p.right.key <= p.key){
            return false;                               // this is contration to the key of root must less than the key of the right child
        }
        
        // The correctness for the height
        int actualHeight = slowHeight(p);
        if (actualHeight != fastHeight(p)){
            return false;
        }
        
        // BalanceFactor must be 1 0 0r -1
        int num = balanceFactor(p);
        if (num < -1 || num > 1){
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
        p.left =  new Node(2);
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
       //create the subtree
        Node y = p.right;
        Node T = y.left;        // the right child of y doesn't change , so we don;t create it
        // Rotate
        y.left = p;
        p.right = T;
        // Update the height in the class "node"
        p.height = 1 + Math.max(fastHeight(p.left) , fastHeight(p.right));      //we need to upgate node p  first to make sure we have correct height to upgate the height of y
        y.height = 1 + Math.max(fastHeight(y.left) , fastHeight(y.right));
        return y;
    }

    static Node rotateRight(Node p) {
        
        //rotateRight is the symmetric of rotateLeft

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
        Node cur = root;
        while (cur != null){
            if (x == cur.key){
                return true;            // found! retunr true
            }else if ( x < cur.key){
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        return false;                   // this x doesn't in the tree . return false

        /*  Worst-case time complexity is O(logn)
            
            Reason : the number of the step of this method is the height of this tree
                     and we know that Height is belong to O(logn)
                     Therefore , the time complexity of this method is O(logn).

            Precondition : we need the tree to be a binary search tree.
        */

    }

    void add(int x) {
        // Empty tree
        if ( root == null ){
            root = new Node( x );    
            return;
        }
        
        // Find the position to insert
        Node cur = root;
        Stack<Node> path = new Stack<>();
        Node parent = null;
        while ( cur != null ){
            parent = cur;
            path.push(parent);
            if ( x == cur.key ){
                return;
            }else if ( x < cur.key ){
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        
        // Insert!
        Node newNode = new Node(x);
        if ( x < parent.key ){
            parent.left = newNode;
        }else{
            parent.right = newNode;
        }
        
        // Refresh height + Balancing
        while(!path.isEmpty()){
            Node node = path.pop();
            node.height = 1 + Math.max(Node.fastHeight(node.left) , Node.fastHeight(node.right));
            int num = Node.balanceFactor(node);
            
            //Type:LL
            if ( num > 1 && x < node.left.key ){
                Node newRoot = Node.rotateRight(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:RR
            else if ( num < -1 && x > node.right.key ){
                Node newRoot = Node.rotateLeft(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:LR
            else if ( num > 1 && x > node.left.key ){
                Node newRoot = Node.doubleRotateRight(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:RL
            else if ( num < -1 && x < node.right.key ){
                Node newRoot = Node.doubleRotateLeft(node);
                attachToParent (path, newRoot, node);
            }
            // If already balanced,then do nothing.
        }
        // Worst-case time complexity is O(logn)
    }
    //auxiliary private method
    private void attachToParent (Stack<Node> path, Node newSubtree, Node oldNode){
        if (path.isEmpty()){
            root = newSubtree;
        }else{
            Node parentNode = path.peek();
            if (parentNode.left == oldNode){
                parentNode.left = newSubtree;
            }else{
                parentNode.right = newSubtree;
            }                    
        }        
    }
    
    void remove(int x) {
        // Empty tree
        if (root == null){
            return;
        }
        
        // Find the position to remove
        Node cur = root;
        Stack<Node> path = new Stack<>();
        Node parent = null;
        while ( cur != null && cur.key != x ){
            parent = cur;
            path.push (parent);
            if (x < cur.key){
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        // No such x
        if (cur == null){
            return;
        }
        
        // Deleting!
        Node deleteNode = cur;
        Node deleteParent = parent;
        
        // Case 1: No leftChild
        if (cur.left == null){
            replaceNode (parent, cur, cur.right, path);
        }
        // Case 2: No rightChild
        else if (cur.right == null){
            replaceNode (parent, cur, cur.left, path);
        }
        // Case 3: 2 children
        else{
            Node successorParent = cur;
            Node successor = cur.right;
            path.push (successorParent);
            while (successor.left != null){
                successorParent = successor;
                path.push (successorParent);
                successor = successor.left;
            }
            cur.key = successor.key;
            
            replaceNode (successorParent, successor, successor.right, path);
            deleteNode = successor;
            deleteParent = successorParent;
        }
        
        // Refresh height + Balancing
        while(!path.isEmpty()){
            Node node = path.pop();
            node.height = 1 + Math.max(Node.fastHeight(node.left) , Node.fastHeight(node.right));
            int num = Node.balanceFactor(node);
            
            //Type:LL
            if ( num > 1 && Node.balanceFactor (node.left) >= 0 ){
                Node newRoot = Node.rotateRight(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:RR
            else if ( num < -1 && Node.balanceFactor (node.right) <= 0 ){
                Node newRoot = Node.rotateLeft(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:LR
            else if ( num > 1 && Node.balanceFactor (node.left) < 0 ){
                Node newRoot = Node.doubleRotateRight(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:RL
            else if ( num < -1 && Node.balanceFactor (node.right) > 0 ){
                Node newRoot = Node.doubleRotateLeft(node);
                attachToParent (path, newRoot, node);
            }
            // If already balanced,then do nothing.
        }
    }
    //auxiliary private method
    private void replaceNode (Node parent, Node oldNode, Node child, Stack<Node> path){
        if (parent == null){
            root = child;
        }else if (parent.left == oldNode){
            parent.left = child;
        }else{
            parent.right = child;
        }
    }
    // Worst-case time complexity is O(logn)
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

