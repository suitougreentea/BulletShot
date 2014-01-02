package jp.dip.suitougreentea.BulletShot.effect;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObject;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObjectNormal;

import com.bulletphysics.linearmath.Transform;

/**
 * Change player's angle gradually decreasing velocity.
 * 
 * @author suitougreentea
 * 
 */
//TODO: Rendering and motion
public class EffectNeedle extends Effect {

    private int power;

    public EffectNeedle(int power) {
        // super(x,z);
        this.power = power;
    }

    @Override
    public void onRegister() {
    }

    @Override
    public boolean isActivatable(int x, int z, ObjectPlayer player) {
        if (player.isFlying()) {
            return false;
        }
        Transform t = new Transform();
        player.getMotionState().getWorldTransform(t);

        if (t.origin.x - x > 0f && t.origin.x - x < 1f && t.origin.z - z > 0f && t.origin.z - z < 1f) {
            return true;
        }

        return false;
    }

    @Override
    public void activate(int x, int z, ObjectPlayer player) {
        Vector3f v = new Vector3f();
        Vector3f s = new Vector3f();
        player.getRigidBody().getLinearVelocity(v);
        //v.normalize();
        s.x = v.x;
        s.y = 0;
        s.z = v.z;
        player.getRigidBody().setLinearVelocity(s);

        Vector3f s1 = new Vector3f();
        s1.y = 100;
        player.getRigidBody().applyCentralForce(s1);
    }

    @Override
    public RendererObject getRenderer(Resource gameResource) {
        return new RendererObjectNormal(gameResource, 1, 9);
    }
}
