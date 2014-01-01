package jp.dip.suitougreentea.BulletShot.test.junit;

import static org.junit.Assert.*;
import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.TerrainUtil;

import org.junit.Test;

public class TerrainUtilTest {

    @Test
    public void test() {
        testTerrain(0, 0, 0, 0, 0, 0);
        testTerrain(0.5f, 0.5f, 0, 1, 0, 0.5f);
        testTerrain(0.75f, 0.25f, Terrain.TERRAIN_PYRAMID, 0, 0, 0.125f);
        testTerrain(0.5f, 1f, Terrain.TERRAIN_HIGHSLOPE, 0, Terrain.DIRECTION_TOPLEFT, 0.25f);
        testTerrain(0.5f, 0.75f, Terrain.TERRAIN_SINGLESLOPE, 0, Terrain.DIRECTION_TOPRIGHT, 0);
        testTerrain(0.5f, 0.5f, Terrain.TERRAIN_DOUBLESLOPE, 0, Terrain.DIRECTION_TOPRIGHT, 0.25f);
        testTerrain(0.25f, 0f, Terrain.TERRAIN_LOWSLOPE, 0, Terrain.DIRECTION_RIGHT, 0.125f);
    }

    private void testTerrain(float x, float z, int type, int height, int direction, float expectedY) {
        float y = TerrainUtil.getHeight(x, z, type, height, direction);
        assertTrue(String.format("Expected: %f, Returned: %f", expectedY, y), y == expectedY);
    }
}
