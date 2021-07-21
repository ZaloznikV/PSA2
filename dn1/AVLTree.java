public class AVLTree {

    public class Node {
        int key;
        int height;
        int value;
        Node left;
        Node right;

        Node(int key, int value) {
            this.value = value;
            this.key = key;
        }
    }

    private Node root;



    public void insert(int key, int value) {

        root = insert(root, key, value);
    }


    public Node getRoot() {
        return root;
    }

    public void delete(int key, int value) {
        root = delete(root, key, value);
    }

    private Node insert(Node node, int key, int value) {
        if (node == null) {
            return new Node(key, value);
        } else if (node.key > key) {
            node.left = insert(node.left, key, value);
        } else if (node.key < key) {
            node.right = insert(node.right, key, value);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }



    private Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            } else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node delete(Node node, int key, int value) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key, value);
        } else if (node.key < key) {
            node.right = delete(node.right, key, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.value = mostLeftChild.value;
                node.right = delete(node.right, node.key, node.value);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node mostLeftChild(Node node) {
        Node current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(Node n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }
}