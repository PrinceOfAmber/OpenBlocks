package openblocks.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import openblocks.client.model.ModelLuggage;
import openblocks.common.entity.EntityLuggage;

public class EntityLuggageRenderer extends RenderLiving {

	private static ModelBase luggageModel = new ModelLuggage();
	public EntityLuggageRenderer() {
		super(luggageModel, 0.5F);
	}
	
	private int renderSpecial(EntityLuggage luggage, int p, float m) {
		if(luggage.isSpecial() && luggage.getOwnerName() != null){
			if (p == 1)
            {
                float f1 = (float)luggage.ticksExisted + m;
                this.loadTexture("/armor/power.png");
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float f2 = f1 * 0.01F;
                float f3 = f1 * 0.01F;
                GL11.glTranslatef(f2, f3, 0.0F);
                this.setRenderPassModel(EntityLuggageRenderer.luggageModel);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float f4 = 0.5F;
                GL11.glColor4f(f4, f4, f4, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                return 1;
            }
            if (p == 2)
            {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
		}
		return -1;
	}
	
	@Override
	protected void preRenderCallback(EntityLiving entity, float par2) {
		EntityLuggage luggage = (EntityLuggage)entity;
		if(luggage.isSpecial()) {
			float oscMagnitude = (float)Math.abs(Math.sin((entity.ticksExisted + par2) * 0.05));
			GL11.glColor3f(oscMagnitude * 0.4f + 0.6f,  oscMagnitude* 0.4f + 0.6f,  1f);
		}
	}

	protected int inheritRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return -1;
    }
	
	protected int shouldRenderPass(EntityLiving entity, int par2, float par3)
    {
        return renderSpecial((EntityLuggage)entity, par2, par3);
    }

}
