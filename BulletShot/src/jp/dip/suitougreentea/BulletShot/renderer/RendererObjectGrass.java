package jp.dip.suitougreentea.BulletShot.renderer;

import jp.dip.suitougreentea.BulletShot.Resource;

//TODO: power
public class RendererObjectGrass extends RendererObjectNormal {
    private int power;

    public RendererObjectGrass(Resource gameResource, int direction, int power) {
        super(gameResource, 5, direction);
        this.power = power;
    }

    /*@Override
    public void draw(Terrain t, int timer) {
        drawBase(t, (timer / 3) % 4, texturey);
    }*/
}
