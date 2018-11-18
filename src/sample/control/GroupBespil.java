package sample.control;

import sample.model.Bespilotnik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupBespil implements Iterable<Bespilotnik>{
    private List<Bespilotnik> group = new ArrayList<>();


    public void addToGroup(Bespilotnik besp){
        group.add(besp);
    }
    public List<Bespilotnik> getGroup() {
        return group;
    }

    @Override
    public Iterator<Bespilotnik> iterator() {
        return group.iterator();
    }
}
