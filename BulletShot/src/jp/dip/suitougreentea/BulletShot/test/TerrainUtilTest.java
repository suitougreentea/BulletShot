package jp.dip.suitougreentea.BulletShot.test;

import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.TerrainUtil;

public class TerrainUtilTest {

    public static void main(String[] args) {
        getHeight(0,0,0,0,0);
        getHeight(0.5f,0.5f,0,1,0);
        getHeight(0.75f,0.25f,Terrain.TERRAIN_PYRAMID,0,0);
        getHeight(0.5f,1f,Terrain.TERRAIN_HIGHSLOPE,0,Terrain.DIRECTION_TOPLEFT);
        getHeight(0.5f,0.75f,Terrain.TERRAIN_SINGLESLOPE,0,Terrain.DIRECTION_TOPRIGHT);
        getHeight(0.5f,0.5f,Terrain.TERRAIN_DOUBLESLOPE,0,Terrain.DIRECTION_TOPRIGHT);
        getHeight(0.25f,0f,Terrain.TERRAIN_LOWSLOPE,0,Terrain.DIRECTION_RIGHT);
    }
    
    public static void getHeight(float x, float z, int type, int height, int direction){
        float y = TerrainUtil.getHeight(x, z, type, height, direction);
        
        System.out.println(String.format("x:%f, Y:%f, z:%f (t:%d, h:%d, d:%d)", x,y,z,type,height,direction));
    }
}
