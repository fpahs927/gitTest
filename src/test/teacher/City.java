package test.teacher;

import test.teacher.Building;

import java.util.ArrayList;

public class City {
    private ArrayList<Building> buildings = new ArrayList<>(0);
    public void addBuilding(Building building) {
        buildings.add(building);
    }


    public <T extends  Building>T getBuilding(String address) { //generic 빌딩을 가져올 때 Building interface를 받는다
        T _building =null;
        for(Building b:buildings) {
            if (b.checkAddress(address)) {
                try {
                    _building = b.toResult();
                    if (_building == null) {
                        continue;
                    }
                    return _building;
                } catch (Exception ex) {

                }
            }
        }
        return null;
    }

    public void clearAll() {
        buildings.clear();
    }
}
