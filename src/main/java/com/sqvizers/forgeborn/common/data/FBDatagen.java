package com.sqvizers.forgeborn.common.data;

import com.sqvizers.forgeborn.common.data.lang.FBMLangHandler;
import com.sqvizers.forgeborn.common.registry.FBRegistration;
import com.tterrag.registrate.providers.ProviderType;

public class FBDatagen {

    public static void init() {
        FBRegistration.REGISTRATE.addDataGenerator(ProviderType.LANG, FBMLangHandler::init);
    }
}
