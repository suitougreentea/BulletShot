package jp.dip.suitougreentea.BulletShot.effect;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObject;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObjectNormal;

public class Effect {
    /*
     * int x, z;
     * 
     * public Effect(int x, int z) { this.x = x; this.z = z; }
     */
    public void onRegister() {

    }

    public boolean isActivatable(int x, int z, ObjectPlayer player) {
        return false;
    }

    public void activate(int x, int z, ObjectPlayer player) {

    }

    public RendererObject getRenderer(Resource gameResource) {
        return new RendererObjectNormal(gameResource, 0, 0);
    }
}
