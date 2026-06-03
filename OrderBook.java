import java.util.*;

class OrderNode {
    long orderId;
    double price;
    OrderNode left, right;

    OrderNode(long orderId, double price) {
        this.orderId = orderId;
        this.price = price;
    }
}

public class OrderBook {
    OrderNode root;
    Map<Long, OrderNode> byId = new HashMap<>();

    OrderNode insert(OrderNode root, long id, double price) {
        if (root == null) {
            OrderNode node = new OrderNode(id, price);
            byId.put(id, node);
            return node;
        }

        if (price > root.price)
            root.left = insert(root.left, id, price);
        else
            root.right = insert(root.right, id, price);

        return root;
    }

    void insertOrder(long id, double price) {
        root = insert(root, id, price);
    }

    boolean cancelById(long id) {
        if (!byId.containsKey(id))
            return false;

        byId.remove(id);
        return true;
    }

    OrderNode peekBestBid() {
        OrderNode curr = root;
        while (curr != null && curr.left != null)
            curr = curr.left;
        return curr;
    }

    public static void main(String[] args) {
        OrderBook ob = new OrderBook();

        ob.insertOrder(101, 2980);
        ob.insertOrder(102, 2965);
        ob.insertOrder(104, 2985);

        OrderNode best = ob.peekBestBid();
        System.out.println("Best Bid: " + best.price);

        ob.cancelById(102);
    }
}