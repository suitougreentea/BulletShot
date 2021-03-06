package jp.dip.suitougreentea.BulletShot.test;

import jp.dip.suitougreentea.BulletShot.Stage;
import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.effect.Effect;
import jp.dip.suitougreentea.BulletShot.effect.EffectDash;
import jp.dip.suitougreentea.BulletShot.effect.EffectGrass;
import jp.dip.suitougreentea.BulletShot.effect.EffectKick;
import jp.dip.suitougreentea.BulletShot.effect.EffectNeedle;
import jp.dip.suitougreentea.BulletShot.effect.EffectSand;

public class StageGeneratorFlat implements StageGenerator {
    private int stageWidth;
    private int stageHeight;
    private int groundHeight;

    public StageGeneratorFlat(int width, int height, int groundHeight) {
        this.stageWidth = width;
        this.stageHeight = height;
        this.groundHeight = groundHeight;
    }

    @Override
    public Terrain[][] generate() {
        int[][] type = new int[stageHeight][stageWidth];
        int[][] height = new int[stageHeight][stageWidth];
        int[][] direction = new int[stageHeight][stageWidth];
        int[][] bumper = new int[stageHeight][stageWidth];
        Effect[][] effect = new Effect[stageHeight][stageWidth];
        int[][] chara = new int[stageHeight][stageWidth];

        for (int iz = 0; iz < stageHeight; iz++) {
            for (int ix = 0; ix < stageWidth; ix++) {
                type[iz][ix] = Terrain.TERRAIN_NORMAL;
                height[iz][ix] = groundHeight;
                direction[iz][ix] = Terrain.DIRECTION_NULL;
                bumper[iz][ix] = 0;
                effect[iz][ix] = null;
                chara[iz][ix] = 0;
            }
        }

        effect[2][2] = new EffectKick(EffectKick.DIRECTION_RIGHT);
        effect[2][4] = new EffectKick(EffectKick.DIRECTION_BOTTOMRIGHT);
        effect[3][5] = new EffectKick(EffectKick.DIRECTION_BOTTOM);
        effect[5][5] = new EffectKick(EffectKick.DIRECTION_BOTTOMLEFT);
        effect[6][4] = new EffectKick(EffectKick.DIRECTION_LEFT);
        effect[6][2] = new EffectKick(EffectKick.DIRECTION_TOPLEFT);
        effect[5][1] = new EffectKick(EffectKick.DIRECTION_TOP);
        effect[3][1] = new EffectKick(EffectKick.DIRECTION_TOPRIGHT);

        effect[2][3] = new EffectDash(EffectDash.DIRECTION_NULL, 6);
        effect[6][3] = new EffectDash(EffectDash.DIRECTION_LEFT, 6);

        effect[1][0] = new EffectGrass(EffectGrass.DIRECTION_BOTTOM, 6);
        effect[2][0] = new EffectGrass(EffectGrass.DIRECTION_BOTTOM, 6);
        effect[3][0] = new EffectGrass(EffectGrass.DIRECTION_BOTTOM, 6);

        effect[0][1] = new EffectSand(6);
        effect[0][2] = new EffectSand(6);
        effect[0][3] = new EffectNeedle(6);

        return Stage.createTerrain(type, height, direction, bumper, effect, chara);
    }
}
