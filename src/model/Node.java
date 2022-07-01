package model;

public class Node<E extends AbstractTask> {
    private AbstractTask item;
    private Node<E> next;
    private Node<E> prev;

    public Node(Node<E> prev, AbstractTask element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }

    public void setItem(AbstractTask item) {
        this.item = item;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public AbstractTask getItem() {
        return item;
    }

    public Node<E> getNext() {
        return next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public boolean hasPrev() {
        return !(prev == null);
    }

    public boolean hasNext() {
        return !(next == null);
    }
}
