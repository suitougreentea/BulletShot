package jp.dip.suitougreentea.BulletShot.test;

import jp.dip.suitougreentea.BulletShot.Stage;
import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.effect.Effect;

public class StageGeneratorTest implements StageGenerator {

    @Override
    public Terrain[][] generate() {
        int[][] type = {
                {2,1,1,1,1,1,2},
                {1,3,0,0,0,3,1},
                {1,0,4,0,4,0,1},
                {1,0,0,5,0,0,1},
                {1,0,4,0,4,0,1},
                {1,3,0,0,0,3,1},
                {2,1,1,1,1,1,2},
        };
        int[][] height = {
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1}
        };
        int[][] direction = {
                {0,0,0,0,0,0,3},
                {1,0,0,0,0,3,3},
                {1,0,0,0,3,0,3},
                {1,0,0,0,0,0,3},
                {1,0,1,0,2,0,3},
                {1,1,0,0,0,2,3},
                {1,2,2,2,2,2,2},
        };
        int[][] bumper = new int[7][7];
        Effect[][] effect = new Effect[7][7];
        int[][] chara = new int[7][7];
        return Stage.createTerrain(type, height, direction, bumper, effect, chara);
    }

}
