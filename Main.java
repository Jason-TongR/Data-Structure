
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
        // TODO: COMPLETE
        return 0;
    }

    static int fastHeight(Node p) {
        // TODO: COMPLETE
        return 0;
    }

    static int balanceFactor(Node p) {
        // TODO: COMPLETE
        return 0;
    }

    static boolean checkInvariant(Node p) {
        // TODO: COMPLETE
        return false;
    }

    static void testInvariant() {
        Node p = new Node(5);
        p.height = 3;
        p.left = new Node(2);
        // p.left.height = ...
        // p.right = ...
        //
        // TODO: continue building the tree...
 
        if (checkInvariant(p)) {
            System.out.println("Invariant is fulfilled.");
        } else {
            System.out.println("The invariant is not fulfilled.");
        }
    }

    static Node rotateLeft(Node p) {
        // TODO: COMPLETE
        return null;
    }

    static Node rotateRight(Node p) {
        // TODO: COMPLETE
        return null;
    }

    static Node doubleRotateLeft(Node p) {
        // TODO: COMPLETE
        return null;
    }

    static Node doubleRotateRight(Node p) {
        // TODO: COMPLETE
        return null;
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

