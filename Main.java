
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
        
        // First , we need to find the correct position to add
        
        // CASE 1 : Empty tree . just add this node to be the root
        if ( root == null ){
            root = new Node( x );    
            return;
        }
        
        // CASE 2 : the tree isn't empty
        Node cur = root;
        Stack<Node> path = new Stack<>();
        Node parent = null;
        while ( cur != null ){          // intersection is always add the node to the lowest bounded of the tree , so the condition is that the current node must go to null at some

            parent = cur;               // let the parent node be the current node , and we will consider three cases in the follow
            
            path.push(parent);          // add the parent node into the top of the stack , because the "correct position" will be assign to the 'parent'

            if ( x == cur.key ){
                return;                 // in this case , the number that we want to add exits in the tree , so just return
            }
            else{ 
                if ( x < cur.key ){
                    cur = cur.left;     // let the current node be the left node of the current node
                }
                else{
                    cur = cur.right;    // let the current node be the right node of the current node
                }
            }
        }                                
        

        // Second , insert!
        Node newNode = new Node(x);
        if ( x < parent.key ){
            parent.left = newNode;
        }else{
            parent.right = newNode;
        }
        
        
        // Third , Refresh height + Balancing
        while(!path.isEmpty()){
            Node node = path.pop();         // we need to operate the node that in the stack "path" one by one.

            node.height = 1 + Math.max(Node.fastHeight(node.left) , Node.fastHeight(node.right));   // upgate the height of the node which in the path
            int node_balance_factor = Node.balanceFactor(node);
            
            //Type:LL
            if ( node_balance_factor > 1 && x < node.left.key ){
                Node newRoot = Node.rotateRight(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:RR
            else if ( node_balance_factor < -1 && x > node.right.key ){
                Node newRoot = Node.rotateLeft(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:LR
            else if ( node_balance_factor > 1 && x > node.left.key ){
                Node newRoot = Node.doubleRotateRight(node);
                attachToParent (path, newRoot, node);
            }
            
            //Type:RL
            else if ( node_balance_factor < -1 && x < node.right.key ){
                Node newRoot = Node.doubleRotateLeft(node);
                attachToParent (path, newRoot, node);
            }
            // If already balanced,then do nothing.
        }
        /*  Worst-case time complexity is O(logn)
            Reason:
                1. The First step is to find the correct position to add, in the worst case this step take O(logn) , because h is in O(logn)
                2. The Second step is insert , this step take O(1) time in the worst case
                3. The Third step is upgate height and balancing , this step take O(logn) in the worst case , because h is in O(logn) 
                4. The auiliary private method also take O(1)
                
                Therefore , the worst case time complexity is O(logn)
        */  
            
    }
    
    
    /*  this is a auxiliary private method

        The function of this private auxiliary method :
        this method is used to connect the new subtree to the parent node after we do the rotation in the method "add"
    */
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
        
        // First , we need to find the position to remove
        
        // CASE 1 : Empty tree . nothing to do . return that is.
        if (root == null){
            return;
        }
        
        //  CASE 2 : non-empty tree
        Node cur = root;                            // we need current node
        Stack<Node> path = new Stack<>();           // we need to use Stack to do this Algorithm
        Node parent = null;                         // we need the parent node of the current node
        while ( cur != null && cur.key != x ){      // the condition that the while loop stop is "the current node is null (that is we have reached to the leaf)" or "the value of the curren node is x (that is : we have found the value x)"    
            parent = cur;
            path.push (parent);                     // this step is in the while loop , so the Stack "path" can record the path that we find the value x
            if (x < cur.key){
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }



        /*  Second , we need to delelet the node
            No such x
            Reason:
            this is the while loop stop case that "the current node is null (that is we have reached to the leaf)" , 
            then it is trival that the value of the parent is also not x (if not , the current node should stop at the parent position , then it is the contraditon)
        */
        if (cur == null){                   
            return;             // since no such x , so we remove nothing , return that is.
        }
        


        /*  There exists such x , the current node cur is the aim node that we want to delete*/
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
        

        /*  Third , Refresh height + Balancing  */
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


    /*  auxiliary private method
        
        The function of this auxiliary method :
            to replace the oldNode to the child.

    */
    private void replaceNode (Node parent, Node oldNode, Node child, Stack<Node> path){
        if (parent == null){                // in this case is that the tree is only one node (that is the root) , so we just need to let the root be the child            
            root = child;
        }
        else{

            if(parent.left == oldNode)
                parent.left = child;
            else
                parent.right = child;

        }
    }
    /*  Worst-case time complexity is O(logn)
        Reason:
            1. In the first step , we need to find the position to remove , this step take O(logn) time int the worst case
            2. In the second step , it also take O(logn) time in the worse case , because the Case 3 take O(logn) in the worst case
            3. In the third step , this step take O(logn) in the worst case , because h is in O(logn)
            4. In the auxiliary step , it takes O(1)
            Therefore , the method remove take O(logn) in the worst case.
    */
}

class SetTest {

    static void runAllTests() {
        test0();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
    }

    static void test0() {
        Set s = new Set();
        assert !s.contains(1) : "Empty set must not contain 1";
        assert s.checkInvariant() : "Invariant check fails";
        s.add(1);
        assert s.contains(1) : "Set must contain 1";
        assert s.checkInvariant() : "Invariant check fails";
    }

    
    /*
        test 1 is Exercise 11 (a)
    */
    static void test1() {
        //Arrange
        Set s = new Set();

        // Act , Assert
        for( int i = 1 ; i < 101 ; i++){
            s.add(i);                                                                           // add the element i

            for( int j = 1 ; j < i + 1 ; j++ ){                                                 // this for loop is to check that all the inserted elment are in the set    
                assert (s.contains(j)) : " element " + j + "should be in the set";
            }
            
            for( int j = i + 1  ; j < 101 ; j++ ){                                              // this for loop is to check that the other elements are not in the set
                assert (!s.contains(j)) : " elment " + j + "should not be in the set";     
            }
            
            assert (s.checkInvariant()) : "this tree should be Invariant";                      // this is to check the invariant preserved
        }
    }

    
    /*
        test 2 is Exercise 11 (b)
    */
    static void test2(){
        //Arrange
        Set s = new Set();

        // Act , Assert
        for( int i = 100 ; i > 0  ; i--){
            s.add(i);                                                                           // add the element i

            for( int j = 100 ; j > i - 1 ; j-- ){                                               // this for loop is to check that all the inserted elment are in the set    
                assert (s.contains(j)) : " element " + j + "should be in the set";
            }
            
            for( int j = i - 1  ; j > 0 ; j-- ){                                                // this for loop is to check that the other elements are not in the set
                assert (!s.contains(j)) : " elment " + j + "should not be in the set";     
            }
            
            assert (s.checkInvariant()) : "this tree should be Invariant";                      // this is to check the invariant preserved
        }
    }



    /*
        test 3 is Exercise 11 (c)
    */
    static void test3(){
        /*  Arrange
            our sequence is {2 , 1 , 5 , 4 , 3 , 6}
            our test_tree is like : 

                             2
                           /   \
                          1     5
                               / \
                              4   6
                             /
                            3                         
        */
        Node test_tree = new Node(2);       // this is the root
        test_tree.height = 4;

        test_tree.left = new Node(1);
        test_tree.left.height = 1;

        test_tree.right = new Node(5);
        test_tree.right.height = 3;

        test_tree.right.right = new Node(6);
        test_tree.right.right.height = 1;

        test_tree.right.left = new Node(4);
        test_tree.right.left.height = 2;

        test_tree.right.left.left = new Node(3);
        test_tree.right.left.left.height = 1;

        //Act
        Node after_rotation_tree = Node.doubleRotateLeft(test_tree);
        
        //Assert
        
        assert (Node.checkInvariant(after_rotation_tree)) : "the after_rotation_tree should be Invariant";
        
    }


    /*
        test 4 is Exercise 11 (d)
    */
    static void test4(){
        /*
            Arrange
            our sequence is {6 , 2 , 1 , 4 , 5 , 7}
            our test_tree is like :
                                6
                               / \
                              2   7
                             / \ 
                            1   4
                                 \
                                  5
        */
        Node test_tree = new Node(6);
        test_tree.height = 4;

        test_tree.right = new Node(7);
        test_tree.right.height = 1;

        test_tree.left = new Node(2);
        test_tree.left.height = 3;

        test_tree.left.left = new Node(1);
        test_tree.left.left.height = 1;

        test_tree.left.right = new Node(4);
        test_tree.left.right.height = 2;
        
        test_tree.left.right.right = new Node(5);
        test_tree.left.right.right.height = 1;

        //Act
        Node after_rotation_tree = Node.doubleRotateRight(test_tree);

        //Assert
        
        assert (Node.checkInvariant(after_rotation_tree)) : "the after_rotation tree should be Invariant";

    }


    /*
        test 5 is Exercise 11 (e)
    */
    static void test5(){
        //Arrange
        Set s = new Set();
        for( int i = 1 ; i < 101 ; i++ ){       // this for loop is to construct the tree
            s.add(i);
        }


        //Act , Assert
        for( int i = 1 ; i < 101 ; i++ ){       
            s.remove(i);                        // remove i

            /*
                These two for loop is to check that the correspond element wheather is in the set
            */
            for( int j = 1 ; j < i + 1 ; j++ ){ 
                assert (!s.contains(j)) : j + " should not be in the set after deleting";
            }

            for( int j = i + 1 ; j < 101 ; j++ ){
                assert (s.contains(j)) : j + " should be in the set when it is not be deleted";
            }

            assert (s.checkInvariant()) : "s should be invariant";              // to check the invariant

        }
    }


    /*
        test 7 is Exercise 11 (f)
    */
    static void test6(){
         //Arrange
        Set s = new Set();
        for( int i = 1 ; i < 101 ; i++ ){       // this for loop is to construct the tree
            s.add(i);
        }


        //Act , Assert
        for( int i = 100 ; i > 0 ; i-- ){       
            s.remove(i);                        // remove i

            /*
                These two for loop is to check that the correspond element wheather is in the set
            */
            for( int j = 100 ; j > i - 1 ; j-- ){ 
                assert (!s.contains(j)) : j + " should not be in the set after deleting";
            }

            for( int j = i - 1 ; j > 0 ; j-- ){
                assert (s.contains(j)) : j + " should be in the set when it is not be deleted";
            }

            assert (s.checkInvariant()) : "s should be invariant";              // to check the invariant

        }
        
    }
}

