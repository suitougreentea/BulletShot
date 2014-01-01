package jp.dip.suitougreentea.BulletShot.effect;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObject;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObjectDash;

import com.bulletphysics.linearmath.Transform;

/**
 * Add linear power to player.
 * 
 * @author suitougreentea
 * 
 */
public class EffectDash extends Effect {

    private int direction;
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_TOPLEFT = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_BOTTOMLEFT = 3;
    public static final int DIRECTION_BOTTOM = 4;
    public static final int DIRECTION_BOTTOMRIGHT = 5;
    public static final int DIRECTION_RIGHT = 6;
    public static final int DIRECTION_TOPRIGHT = 7;
    public static final int DIRECTION_NULL = 8;

    private int speed;

    public EffectDash(int direction, int speed) {
        // super(x,z);
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void onRegister() {
    }

    @Override
    public boolean isActivatable(int x, int z, ObjectPlayer player) {
        if (player.isFlying()) {
            return false;
        }
        if (player.getLastEffectX() == x && player.getLastEffectZ() == z) {
            return false;
        }

        Transform t = new Transform();
        player.getMotionState().getWorldTransform(t);
        if (t.origin.x - x > 0.125f && t.origin.x - x < 0.875f && t.origin.z - z > 0.125f && t.origin.z - z < 0.875f) {
            return true;
        }
        return false;
    }

    @Override
    public void activate(int x, int z, ObjectPlayer player) {
        Vector3f s = new Vector3f();
        s.x = (float) Math.cos((270 - direction * 45) * Math.PI / 180) * 25 * speed;
        s.z = (float) Math.sin((270 - direction * 45) * Math.PI / 180) * 25 * speed;
        player.getRigidBody().applyCentralForce(new Vector3f(s.x, 0f, s.z)); //TODO: 加速的な
    }

    @Override
    public RendererObject getRenderer(Resource gameResource) {
        return new RendererObjectDash(gameResource, direction);
    }
}
