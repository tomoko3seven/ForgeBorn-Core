package com.sqvizers.forgeborn.common.data.datagen;

import com.sqvizers.forgeborn.common.data.datagen.lang.FBLangHandler;
import com.sqvizers.forgeborn.common.data.datagen.lang.FBMachineLangHandler;
import com.sqvizers.forgeborn.common.data.datagen.lang.FBMaterialLangHandler;
import com.tterrag.registrate.providers.ProviderType;

import static com.sqvizers.forgeborn.common.registry.FBRegistration.REGISTRATE;

public class FBDatagen {
    // Original Code by Phoenix

    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, FBLangHandler::init);
        REGISTRATE.addDataGenerator(ProviderType.LANG, FBMachineLangHandler::init);
        REGISTRATE.addDataGenerator(ProviderType.LANG, FBMaterialLangHandler::init);
    }
}
