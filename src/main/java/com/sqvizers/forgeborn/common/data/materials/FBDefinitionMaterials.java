package com.sqvizers.forgeborn.common.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.FluidState;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBMaterials;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;

public class FBDefinitionMaterials {

    public static void init() {
        // ForgeBorn GTM
        FBMaterials.AmmoniaResidue = new Material.Builder(ForgeBorn.id("ammonia_residue"))
                .fluid()
                .color(0x86684f)
                .buildAndRegister();

        // ForgeBorn Magic
        /*
         * FBMaterials.SpiritSteel = new Material.Builder(ForgeBorn.id("spiritsteel"))
         * .ingot()
         * .color(0x86684f).iconSet(MaterialIconSet.BRIGHT)
         * .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
         * GENERATE_SMALL_GEAR)
         * .buildAndRegister();
         */
        FBMaterials.Mythril = new Material.Builder(ForgeBorn.id("mythril"))
                .ingot()
                .iconSet(FBMaterialSet.MYTHRIL)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();
        FBMaterials.Blooming = new Material.Builder(ForgeBorn.id("blooming"))
                .ingot()
                // .iconSet(FBMaterialSet.BLOOMING)
                .liquid(new FluidBuilder().state(FluidState.LIQUID).customStill())
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_FINE_WIRE, GENERATE_ROD, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister().setFormula("?");

        FBMaterials.EtheriumAdamantium = new Material.Builder(ForgeBorn.id("etherium_adamantium"))
                .ingot()
                .iconSet(FBMaterialSet.ETHERIUM_ADAMANTIUM)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();
        FBMaterials.SpiritAdamantium = new Material.Builder(ForgeBorn.id("spirit_adamantium"))
                .ingot()
                .iconSet(FBMaterialSet.SPIRIT_ADAMANTIUM)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();
        FBMaterials.WizardsAdamantium = new Material.Builder(ForgeBorn.id("wizards_adamantium"))
                .ingot()
                .iconSet(FBMaterialSet.WIZARD_ADAMANTIUM)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();
        FBMaterials.DraconicAdamantium = new Material.Builder(ForgeBorn.id("draconic_adamantium"))
                .ingot()
                .iconSet(FBMaterialSet.DRACONIC_ADAMANTIUM)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();

        // Botania

        // Undergarden

        /*
         * FBMaterials.Froststeel = new Material.Builder(ForgeBorn.id("froststeel"))
         * .ingot()
         * .ore()
         * .addOreByproducts(Iron, Iron, Aluminium)
         * //.washedIn(ForgeMaterials.Cryotheum)
         * .color(0xb3d0e2).iconSet(MaterialIconSet.METALLIC)
         * .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
         * GENERATE_SMALL_GEAR)
         * .buildAndRegister();
         * FBMaterials.Forgotten = new Material.Builder(ForgeBorn.id("forgotten"))
         * .ingot()
         * .color(0x3dd5a2).iconSet(MaterialIconSet.SHINY)
         * .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
         * GENERATE_SMALL_GEAR)
         * .buildAndRegister();
         * FBMaterials.Utherium = new Material.Builder(ForgeBorn.id("utherium"))
         * .gem()
         * .ore()
         * .color(0xA4433B).iconSet(MaterialIconSet.DIAMOND)
         * .flags(GENERATE_LENS)
         * .buildAndRegister();
         */
    }
}
