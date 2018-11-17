package sample.control;

import sample.model.Bespilotnik;

import java.util.ArrayList;
import java.util.List;

public class GroupBespil {
    private static List<Bespilotnik> group = new ArrayList<>();


    public static void addToGroup(Bespilotnik besp){
        group.add(besp);
    }
    public static List<Bespilotnik> getGroup() {
        return group;
    }
}
