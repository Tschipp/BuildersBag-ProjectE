package tschipp.bbprojecte;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tschipp.buildersbag.api.ModuleRegistry;

@EventBusSubscriber
@Mod(modid = BBProjectE.MODID, name = BBProjectE.NAME, version = BBProjectE.VERSION, dependencies = BBProjectE.DEPENDENCIES, acceptedMinecraftVersions = BBProjectE.ACCEPTED_VERSIONS, guiFactory = "tschipp.buildersbag.client.gui.GuiFactoryBuildersBag", certificateFingerprint = BBProjectE.CERTIFICATE)
public class BBProjectE
{
	public static final String MODID = "bbprojecte";
	public static final String VERSION = "GRADLE:VERSION";
	public static final String NAME = "Builder's Bag - ProjectE Addon";
	public static final String ACCEPTED_VERSIONS = "[1.12.2,1.13)";
	//It's important that we register our modules BEFORE Builder's Bag.
	public static final String DEPENDENCIES = "required-after:forge@[13.20.1.2386,);required-after:projecte;required-before:buildersbag@[1.0.1.7,);";
	public static final Logger LOGGER = LogManager.getFormatterLogger(MODID.toUpperCase());
	public static final String CERTIFICATE = "fd21553434f4905f2f73ea7838147ac4ea07bd88";

	private static final String curseSlug = "builders-bag-projecte-addon";
	
	public static boolean FINGERPRINT_VIOLATED = false;


	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModuleRegistry.registerModule(MODID, new ResourceLocation(MODID, "projecte"), ProjectEBagModule::new, 5);
	}


	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event)
	{
		LOGGER.error("WARNING! Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been tampered with! If you didn't download the file from https://minecraft.curseforge.com/projects/" + curseSlug + " or through any kind of mod launcher, immediately delete the file and re-download it from https://minecraft.curseforge.com/projects/" + curseSlug);
		FINGERPRINT_VIOLATED = true;
	}
}