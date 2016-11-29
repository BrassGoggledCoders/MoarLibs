package xyz.brassgoggledcoders.opentransport.modules.vanilla;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.opentransport.OpenTransport;

@Module(OpenTransport.MODID)
public class VanillaModule extends ModuleBase {
    @Override
    public String getName() {
        return "Vanilla";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(new VanillaBlockWrappers());
    }
}
