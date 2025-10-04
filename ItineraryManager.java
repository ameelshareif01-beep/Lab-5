import java.util.Iterator;
import java.util.NoSuchElementException;

public class IniteraryManager {

    // ---------- Position Interface ----------
    public interface Position<E> {
        E getElement();
    }

    // ---------- LinkedPositionalList Class ----------
    public static class LinkedPositionalList<E> implements Iterable<E> {

        // ----- Node class -----
        private static class Node<E> implements Position<E> {
            private E element;
            private Node<E> prev;
            private Node<E> next;

            public Node(E e, Node<E> p, Node<E> n) {
                element = e;
                prev = p;
                next = n;
            }

            public E getElement() {
                return element;
            }

            public Node<E> getPrev() {
                return prev;
            }

            public Node<E> getNext() {
                return next;
            }

            public void setPrev(Node<E> p) {
                prev = p;
            }

            public void setNext(Node<E> n) {
                next = n;
            }

            public void setElement(E e) {
                element = e;
            }
        }

        // ----- List instance variables -----
        private Node<E> header;
        private Node<E> trailer;
        private int size = 0;

        // ----- Constructor -----
        public LinkedPositionalList() {
            header = new Node<>(null, null, null);
            trailer = new Node<>(null, header, null);
            header.setNext(trailer);
        }

        // ----- Utility methods -----
        private Node<E> validate(Position<E> p) {
            if (!(p instanceof Node))
                throw new IllegalArgumentException("Invalid position");
            Node<E> node = (Node<E>) p;
            if (node.getNext() == null)
                throw new IllegalArgumentException("Position is no longer in the list");
            return node;
        }

        private Position<E> position(Node<E> node) {
            if (node == header || node == trailer)
                return null; // Do not expose sentinels
            return node;
        }

        // ----- Accessor methods -----
        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public Position<E> first() {
            return position(header.getNext());
        }

        public Position<E> last() {
            return position(trailer.getPrev());
        }

        public Position<E> before(Position<E> p) {
            Node<E> node = validate(p);
            return position(node.getPrev());
        }

        public Position<E> after(Position<E> p) {
            Node<E> node = validate(p);
            return position(node.getNext());
        }

        // ----- Update methods -----
        private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
            Node<E> newest = new Node<>(e, pred, succ);
            pred.setNext(newest);
            succ.setPrev(newest);
            size++;
            return newest;
        }

        public Position<E> addFirst(E e) {
            return addBetween(e, header, header.getNext());
        }

        public Position<E> addLast(E e) {
            return addBetween(e, trailer.getPrev(), trailer);
        }

        public Position<E> addBefore(Position<E> p, E e) {
            Node<E> node = validate(p);
            return addBetween(e, node.getPrev(), node);
        }

        public Position<E> addAfter(Position<E> p, E e) {
            Node<E> node = validate(p);
            return addBetween(e, node, node.getNext());
        }

        public E set(Position<E> p, E e) {
            Node<E> node = validate(p);
            E answer = node.getElement();
            node.setElement(e);
            return answer;
        }

        public E remove(Position<E> p) {
            Node<E> node = validate(p);
            Node<E> predecessor = node.getPrev();
            Node<E> successor = node.getNext();
            predecessor.setNext(successor);
            successor.setPrev(predecessor);
            size--;
            E answer = node.getElement();
            node.setElement(null);
            node.setNext(null);
            node.setPrev(null);
            return answer;
        }

        // ---------- Iterator ----------
        private class ElementIterator implements Iterator<E> {
            private Position<E> cursor = first();

            public boolean hasNext() {
                return cursor != null;
            }

            public E next() {
                if (cursor == null)
                    throw new NoSuchElementException("No more elements");
                E element = cursor.getElement();
                cursor = after(cursor);
                return element;
            }
        }

        @Override
        public Iterator<E> iterator() {
            return new ElementIterator();
        }
    }

    // ---------- Main: Travel Itinerary Manager ----------
    public static void main(String[] args) {
        LinkedPositionalList<String> itinerary = new LinkedPositionalList<>();

        // Add initial stops
        Position<String> p1 = itinerary.addLast("New York");
        Position<String> p2 = itinerary.addLast("Paris");
        Position<String> p3 = itinerary.addLast("Tokyo");

        // Insert a new stop AFTER Paris
        itinerary.addAfter(p2, "Eiffel Tower Museum");

        // Insert a new stop BEFORE Tokyo
        itinerary.addBefore(p3, "Hawaii");

        // Print itinerary
        System.out.println("Your Travel Itinerary:");
        for (String stop : itinerary) {
            System.out.println("- " + stop);
        }
    }
}
