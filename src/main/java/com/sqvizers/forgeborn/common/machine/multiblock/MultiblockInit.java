package com.sqvizers.forgeborn.common.machine.multiblock;

import com.sqvizers.forgeborn.common.machine.multiblock.part.SteamFluidHatchPartMachine;
import com.sqvizers.forgeborn.common.machine.multiblock.steam.SteamMixingVessel;

public class MultiblockInit {

    public static void init() {
        // Steam
        SteamMixingVessel.init();
        SteamFluidHatchPartMachine.init();
        // Electric
    }
}
