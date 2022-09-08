package service;

import model.AbstractTask;
import model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<AbstractTask> history = new ArrayList<>();
    private final Map<Integer, Node<AbstractTask>> nodeMap = new HashMap<>();

    @Override
    public void add(AbstractTask task) {
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (null != nodeMap.get(id)) {
            removeNode(nodeMap.get(id));
            nodeMap.remove(id);
        }
    }

    @Override
    public List<AbstractTask> getHistory() {
        getTasks();
        return history;
    }

    private void removeNode(Node<AbstractTask> node) {
        if (node.hasPrev()) {
            node.getPrev().setNext((node.hasNext()) ? node.getNext() : null);
        }
        if (node.hasNext()) {
            node.getNext().setPrev((node.hasPrev()) ? node.getPrev() : null);
        }
    }

    private void linkLast(AbstractTask task) {
        if (nodeMap.isEmpty()) {
            Node<AbstractTask> node = new Node(null, task, null);
            nodeMap.put(task.getId(), node);
        } else {
            if (nodeMap.containsKey(task.getId())) {
                removeNode(nodeMap.get(task.getId()));
                nodeMap.values().remove(task.getId());
            }
            for (Node<AbstractTask> nodeFirst : nodeMap.values()) {
                if (nodeFirst.getNext() == null) {
                    Node<AbstractTask> node = new Node(nodeFirst, task, null);
                    nodeMap.put(task.getId(), node);
                    nodeFirst.setNext(node);
                    break;
                }
            }

        }

    }

    private void getTasks() {
        Node<AbstractTask> node = new Node(null, null, null);
        history.clear();
        for (Node<AbstractTask> nodeFirst : nodeMap.values()) {
            if (nodeFirst.getPrev() == null) {
                history.add(nodeFirst.getItem());
                node = nodeFirst;
                break;
            }
        }

        while (node.getNext() != null) {
            node = node.getNext();
            history.add(node.getItem());
        }
    }
}
