package jp.dip.suitougreentea.BulletShot.test;

import jp.dip.suitougreentea.BulletShot.Stage;
import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.effect.Effect;
import jp.dip.suitougreentea.BulletShot.effect.EffectDash;

public class StageGeneratorFlat implements StageGenerator {
    private int stageWidth;
    private int stageHeight;
    private int groundHeight;
    
    public StageGeneratorFlat(int width,int height,int groundHeight){
        this.stageWidth = width;
        this.stageHeight = height;
        this.groundHeight = groundHeight;
    }
    
    public Terrain[][] generate(){
        int[][] type = new int[stageHeight][stageWidth];
        int[][] height = new int[stageHeight][stageWidth];
        int[][] direction = new int[stageHeight][stageWidth];
        int[][] bumper  = new int[stageHeight][stageWidth];
        Effect[][] effect  = new Effect[stageHeight][stageWidth];
        int[][] chara = new int[stageHeight][stageWidth];
        
        for(int iz=0;iz<stageHeight;iz++){
            for(int ix=0;ix<stageWidth;ix++){
                type[iz][ix] = Terrain.TERRAIN_NORMAL;
                height[iz][ix] = groundHeight;
                direction[iz][ix] = Terrain.DIRECTION_NULL;
                bumper[iz][ix] = 0;
                effect[iz][ix] = null;
                chara[iz][ix] = 0;
            }
        }
        
        effect[2][2] = new EffectDash(0,0);
       
        return Stage.createTerrain(type, height, direction, bumper, effect, chara);
    }
}
