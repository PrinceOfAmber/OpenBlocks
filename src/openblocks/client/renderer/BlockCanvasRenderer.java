package openblocks.client.renderer;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import openblocks.common.block.BlockCanvas;
import openblocks.common.tileentity.TileEntityCanvas;
import openblocks.sync.SyncableBlockLayers;
import openblocks.sync.SyncableBlockLayers.Layer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BlockCanvasRenderer implements IBlockRenderer {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		BlockRenderingHandler.renderInventoryBlock(renderer, block, ForgeDirection.EAST);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileEntityCanvas) {
			BlockCanvas clayBlock = (BlockCanvas)block;
			TileEntityCanvas clayTile = (TileEntityCanvas)tile;
			for (int i = 0; i < 6; i++) {
				clayBlock.setLayerForRender(-1);
				clayBlock.setSideForRender(i);
				renderer.renderStandardBlock(block, x, y, z);
				SyncableBlockLayers sideLayersContainer = clayTile.getLayersForSide(i);
				ArrayList<Layer> layers = sideLayersContainer.getAllLayers();
				for (int l = 0; l < layers.size(); l++) {
					clayBlock.setLayerForRender(l);
					byte rot = layers.get(l).getRotation();
					if (rot == 2) {
						rot = 3;
					} else if (rot == 3) {
						rot = 2;
					}
					renderer.uvRotateTop = rot;
					renderer.uvRotateBottom = rot;
					renderer.uvRotateNorth = rot;
					renderer.uvRotateSouth = rot;
					renderer.uvRotateEast = rot;
					renderer.uvRotateWest = rot;
					renderer.renderStandardBlock(block, x, y, z);
					BlockRenderingHandler.resetFacesOnRenderer(renderer);
				}
			}
			BlockRenderingHandler.resetFacesOnRenderer(renderer);
		}
		return false;
	}
}
