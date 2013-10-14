package jp.dip.suitougreentea.BulletShot.renderer;

import jp.dip.suitougreentea.BulletShot.Terrain;

public class RendererObjectDash extends RendererObjectNormal {

    public RendererObjectDash(int direction) {
        super(-1,direction);
    }
    
    @Override
    public void draw(Terrain t, int timer){
        drawBase(t, (timer/3)%4, texturey);
     }

}
