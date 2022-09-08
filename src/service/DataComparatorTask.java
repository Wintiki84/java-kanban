package service;

import model.AbstractTask;

import java.util.Comparator;

public class DataComparatorTask implements Comparator<AbstractTask> {
    @Override
    public int compare(AbstractTask o1, AbstractTask o2) {
        return o1.getStartTime().isBefore(o2.getStartTime()) ? -1 :
                ((o1.getStartTime().equals(o2.getStartTime())) ? 0 : 1);
    }
}
