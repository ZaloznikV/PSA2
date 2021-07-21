package dn2;

class AVLTree {

    public static class Node {
        double key;
        double height;
        Node left;
        Node right;

        Node(double key) {
            this.key = key;
        }
    }

    private Node root = null;



    public Node find(double key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.right : current.left;
        }
        return current;
    }

    public void insert(double key) {
        root = insert(root, key);
    }

    public void delete(double key) {
        root = delete(root, key);
    }

    public Node getRoot() {
        return root;
    }

    public double height() {
        return root == null ? -1 : root.height;
    }

    private Node insert(Node node, double key) {
        if (node == null) {
            return new Node(key);
        } else if (node.key > key) {
            node.left = insert(node.left, key);
        } else if (node.key < key) {
            node.right = insert(node.right, key);
        } else {
            //System.out.println(key);
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    private Node delete(Node node, double key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node mostLeftChild(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node rebalance(Node z) {
        updateHeight(z);
        double balance = getBalance(z);
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

    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private double height(Node n) {
        return n == null ? -1 : n.height;
    }

    public double getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }
}