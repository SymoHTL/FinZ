package com.symo.finz.utils;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.time.LocalDateTime;

public class MillisecondEvent extends Event {
    public LocalDateTime dateTime;

    public MillisecondEvent() {
        this.dateTime = LocalDateTime.now();
    }
}


