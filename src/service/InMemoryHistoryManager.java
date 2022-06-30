package service;

import model.AbstractTask;

import model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<AbstractTask> history = new ArrayList<>();
    private final Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(AbstractTask task) {
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
        nodeMap.remove(id);
    }

    @Override
    public List<AbstractTask> getHistory() {
        getTasks();
        return history;
    }

    private void removeNode(Node node) {
        if (!(node.getPrev() == null)) {
            node.getPrev().setNext((node.getNext() == null) ? null : node.getNext());
        }
        if (!(node.getNext() == null)) {
            node.getNext().setPrev((node.getPrev() == null) ? null : node.getPrev());
        }
    }

    private void linkLast(AbstractTask task) {
        if (nodeMap.isEmpty()) {
            Node node = new Node(null, task, null);
            nodeMap.put(task.getId(), node);
        } else {
            if (nodeMap.containsKey(task.getId())) {
                removeNode(nodeMap.get(task.getId()));
                nodeMap.remove(task.getId());
            }
            for (Node nodeFirst : nodeMap.values()) {
                if (nodeFirst.getNext() == null) {
                    Node node = new Node(nodeFirst, task, null);
                    nodeMap.put(task.getId(), node);
                    nodeFirst.setNext(node);
                    break;
                }
            }

        }

    }

    public void getTasks() {
        Node node = new Node(null, null, null);
        history.clear();
        for (Node nodeFirst : nodeMap.values()) {
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
