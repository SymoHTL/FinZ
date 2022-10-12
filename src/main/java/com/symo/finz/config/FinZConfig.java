package com.symo.finz.config;

import com.symo.finz.FinZ;
import com.symo.finz.config.modules.ModuleConfig;

import java.util.List;

public final class FinZConfig {

    public static List<ModuleConfig> moduleConfigs;


    public static void readConfig(){
        FinZ.config.load();


    }
}
