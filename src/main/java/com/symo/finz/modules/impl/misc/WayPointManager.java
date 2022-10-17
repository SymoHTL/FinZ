package com.symo.finz.modules.impl.misc;

import com.symo.finz.entities.WayPoint;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.ChatUtils;

import java.util.ArrayList;

public class WayPointManager extends Module {

    protected static final ArrayList<WayPoint> waypoints = new ArrayList<>();

    public WayPointManager() {
        super("WayPoints", "FinZ - Misc");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    public void onRender() {
        waypoints.forEach(WayPoint::Render);
    }


    public static void addWayPoint(final WayPoint wayPoint) {
        waypoints.add(wayPoint);
        ChatUtils.sendMessage("Added waypoint " + wayPoint.name);
    }

    public static void removeWayPoint(final WayPoint wayPoint) {
        waypoints.removeIf(wayPoint1 -> wayPoint1.name.equals(wayPoint.name) && wayPoint1.pos.equals(wayPoint.pos));
        ChatUtils.sendMessage("Removed waypoint " + wayPoint.name);
    }

    public static ArrayList<WayPoint> getWayPoints() {
        return waypoints;
    }

}