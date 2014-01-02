package jp.dip.suitougreentea.BulletShot.effect;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObject;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObjectGrass;
import com.bulletphysics.linearmath.Transform;

/**
 * Change player's angle gradually decreasing velocity.
 * 
 * @author suitougreentea
 * 
 */
public class EffectGrass extends Effect {

    private int direction;
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_TOPLEFT = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_BOTTOMLEFT = 3;
    public static final int DIRECTION_BOTTOM = 4;
    public static final int DIRECTION_BOTTOMRIGHT = 5;
    public static final int DIRECTION_RIGHT = 6;
    public static final int DIRECTION_TOPRIGHT = 7;

    private int power;

    public EffectGrass(int direction, int power) {
        // super(x,z);
        this.direction = direction;
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
        float velocity = (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.z, 2));
        s.x = v.x * 0.985f;
        s.y = v.y;
        s.z = v.z * 0.985f;
        player.getRigidBody().setLinearVelocity(s);

        Vector3f s1 = new Vector3f();
        s1.x = (float) Math.cos((270 - direction * 45) * Math.PI / 180) * velocity * 0.5f;
        s1.z = (float) Math.sin((270 - direction * 45) * Math.PI / 180) * velocity * 0.5f;
        player.getRigidBody().applyCentralForce(new Vector3f(s1.x, 0f, s1.z)); //TODO: 加速的な
    }

    @Override
    public RendererObject getRenderer(Resource gameResource) {
        return new RendererObjectGrass(gameResource, direction, power);
    }
}
