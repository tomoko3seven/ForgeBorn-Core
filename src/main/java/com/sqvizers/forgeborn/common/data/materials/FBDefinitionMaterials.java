package com.sqvizers.forgeborn.common.data.materials;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.FluidState;

import com.sqvizers.forgeborn.ForgeBorn;
import com.sqvizers.forgeborn.common.data.FBMaterials;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;

public class FBDefinitionMaterials {

    public static void init() {
        // Nature`s Aura
        FBMaterials.InfusedIron = new Material.Builder(ForgeBorn.id("infusediron"))
                .ingot()
                .color(0xdb510d).secondaryColor(0x792906)
                .buildAndRegister();

        // Ars Noveau
        FBMaterials.SourceMetall = new Material.Builder(ForgeBorn.id("sourcemetall"))
                .ingot()
                .color(0x9B5FA9).iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();

        // ForgeBorn GTM
        FBMaterials.AmmoniaResidue = new Material.Builder(ForgeBorn.id("ammonia_residue"))
                .fluid()
                .color(0x86684f)
                .buildAndRegister();

        // ForgeBorn Magic
        FBMaterials.EarthCrystal = new Material.Builder(ForgeBorn.id("earth_crystal"))
                .ingot()
                .color(0x8a735a).iconSet(MaterialIconSet.CERTUS)
                .flags(GENERATE_PLATE)
                .buildAndRegister();
        FBMaterials.WaterCrystal = new Material.Builder(ForgeBorn.id("water_crystal"))
                .ingot()
                .color(0x8a735a).iconSet(MaterialIconSet.CERTUS)
                .flags(GENERATE_PLATE)
                .buildAndRegister();
        FBMaterials.FireCrystal = new Material.Builder(ForgeBorn.id("fire_crystal"))
                .ingot()
                .color(0x8a735a).iconSet(MaterialIconSet.CERTUS)
                .flags(GENERATE_PLATE)
                .buildAndRegister();
        FBMaterials.AeroCrystal = new Material.Builder(ForgeBorn.id("aero_crystal"))
                .ingot()
                .color(0x8a735a).iconSet(MaterialIconSet.CERTUS)
                .flags(GENERATE_PLATE)
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
        FBMaterials.Draconium = new Material.Builder(ForgeBorn.id("draconium"))
                .ingot()
                .ore()
                .color(0x6f42cf).secondaryColor(0x701068).iconSet(MaterialIconSet.BRIGHT)
                .liquid(new FluidBuilder().state(FluidState.LIQUID).customStill())
                .toolStats(ToolProperty.Builder.of(4.0F, 2.0F, 130, 1)
                        .attackSpeed(0.2F).enchantability(3).build())
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_FINE_WIRE, GENERATE_ROD, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister().setFormula("Dc");

        FBMaterials.Adamantium = new Material.Builder(ForgeBorn.id("adamantium"))
                .ingot()
                .iconSet(FBMaterialSet.ADAMANTIUM)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();
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
        FBMaterials.Livingwood = new Material.Builder(ForgeBorn.id("livingwood"))
                .color(0x34140c).iconSet(MaterialIconSet.ROUGH)
                .flags(GENERATE_PLATE, GENERATE_BOLT_SCREW)
                .buildAndRegister();
        FBMaterials.Livingrock = new Material.Builder(ForgeBorn.id("livingrock"))
                .color(0xd7d7c8).iconSet(MaterialIconSet.ROUGH)
                .flags(GENERATE_PLATE)
                .buildAndRegister();

        FBMaterials.ManaDiamond = new Material.Builder(ForgeBorn.id("mana_diamond"))
                .color(0x3BAFEA).secondaryColor(0x1E7FCB).iconSet(MaterialIconSet.BRIGHT)
                .flags(GENERATE_PLATE)
                .buildAndRegister();

        FBMaterials.Manasteel = new Material.Builder(ForgeBorn.id("manasteel"))
                .ingot()
                .ore()
                .color(0x3BAFEA).secondaryColor(0x1E7FCB).iconSet(MaterialIconSet.BRIGHT)
                .liquid(new FluidBuilder().state(FluidState.LIQUID).customStill())
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_FINE_WIRE, GENERATE_ROD, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR, GENERATE_PLATE)
                .toolStats(ToolProperty.Builder.of(4.0F, 2.0F, 1300, 4)
                        .attackSpeed(0.2F).enchantability(5).build())
                .buildAndRegister().setFormula("Ms");
        FBMaterials.AnnealedManasteel = new Material.Builder(ForgeBorn.id("annealed_manasteel"))
                .ingot()
                .color(0x6A8FA4).secondaryColor(0x4F6D84).iconSet(MaterialIconSet.BRIGHT)
                .cableProperties(GTValues.V[GTValues.LV], 8, 0, true)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_FINE_WIRE, GENERATE_ROD, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR, GENERATE_PLATE)
                .buildAndRegister().setFormula("Ms");
        FBMaterials.Elementium = new Material.Builder(ForgeBorn.id("elementium"))

                .color(0xE04AC0).secondaryColor(0xC038A0).iconSet(MaterialIconSet.BRIGHT)
                .liquid(new FluidBuilder().state(FluidState.LIQUID).customStill())
                .cableProperties(GTValues.V[GTValues.EV], 3, 4, false)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_FINE_WIRE, GENERATE_ROD, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister().setFormula("Em");
        FBMaterials.Terrasteel = new Material.Builder(ForgeBorn.id("terrasteel"))

                .color(0x47FF8D).secondaryColor(0x2ECF78).iconSet(MaterialIconSet.BRIGHT)
                .liquid(new FluidBuilder().state(FluidState.LIQUID).customStill())
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_FINE_WIRE, /* GENERATE_NANITES, */ GENERATE_ROD,
                        GENERATE_DENSE, GENERATE_LONG_ROD, /* FBFlags.GENERATE_REINFORCED, FBFlags.GENERATE_INFUSED, */
                        GENERATE_SMALL_GEAR)
                .buildAndRegister().setFormula("Ts");

        // Undergarden
        FBMaterials.CloggrumSteel = new Material.Builder(ForgeBorn.id("cloggrum_steel"))
                .ingot()
                .color(0x8a735a).iconSet(MaterialIconSet.BRIGHT)
                .flags(GENERATE_GEAR, GENERATE_RING, GENERATE_ROD, GENERATE_PLATE, GENERATE_LONG_ROD,
                        GENERATE_SMALL_GEAR)
                .buildAndRegister();

        FBMaterials.DitchBulbResin = new Material.Builder(ForgeBorn.id("ditchbulb_resin"))
                .ingot()
                .color(0x8a735a).iconSet(MaterialIconSet.BRIGHT)
                .flags(GENERATE_RING, GENERATE_ROD, GENERATE_PLATE)
                .buildAndRegister();
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
