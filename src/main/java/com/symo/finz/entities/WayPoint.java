package com.symo.finz.entities;

import com.symo.finz.utils.RGBColor;
import com.symo.finz.utils.RenderString;
import com.symo.finz.utils.esp.BlockESPUtil;
import net.minecraft.util.BlockPos;
import org.lwjgl.util.Color;

public class WayPoint {

    public BlockPos pos;
    public String name;
    public Color color;

    public WayPoint(BlockPos pos) {
        this.pos = pos;
        this.name = "Waypoint";
        this.color = RGBColor.getRandColor();
    }

    public WayPoint(BlockPos pos, String name) {
        this.pos = pos;
        this.name = name;
        this.color = RGBColor.getRandColor();
    }

    public void Render() {
        RenderString.RenderAtCoords(pos.getX(), pos.getY(), pos.getZ(), name, 100);
        BlockESPUtil.drawBeacon(pos.getX(), pos.getY(), pos.getZ(), color);
    }
}
