package jp.dip.suitougreentea.BulletShot.effect;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObject;
import jp.dip.suitougreentea.BulletShot.renderer.RendererObjectNormal;

import com.bulletphysics.linearmath.Transform;

/**
 * Change player's angle keeping velocity.
 * 
 * @author suitougreentea
 * 
 */
public class EffectKick extends Effect {

    private int direction;
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_TOPLEFT = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_BOTTOMLEFT = 3;
    public static final int DIRECTION_BOTTOM = 4;
    public static final int DIRECTION_BOTTOMRIGHT = 5;
    public static final int DIRECTION_RIGHT = 6;
    public static final int DIRECTION_TOPRIGHT = 7;

    public static final int SPEED_SLOW = 0;
    public static final int SPEED_FAST = 1;

    public EffectKick(int direction) {
        // super(x,z);
        this.direction = direction;
    }

    @Override
    public void onRegister() {
    }

    @Override
    public boolean isActivatable(int x, int z, ObjectPlayer player) {
        Transform t = new Transform();
        player.getMotionState().getWorldTransform(t);
        if (player.getLastEffectX() == x && player.getLastEffectZ() == z) {
            return false;
        }
        /*if (t.origin.x - x > 0.25f && t.origin.x - x < 0.75f && t.origin.z - z > 0.25f && t.origin.z - z < 0.75f) {
            return true;
        }*/

        float rx = t.origin.x - x;
        float rz = t.origin.z - z;

        if (Math.sqrt((rx - 0.5f) * (rx - 0.5f) + (rz - 0.5f) * (rz - 0.5f)) < 0.375f) {
            if ((direction == DIRECTION_TOP || direction == DIRECTION_BOTTOM) && rx > 0.4375f && rx < 0.5625f) {
                return true;
            }
            if ((direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) && rz > 0.4375f && rz < 0.5625f) {
                return true;
            }
            if ((direction == DIRECTION_TOPRIGHT || direction == DIRECTION_BOTTOMLEFT) && rx + rz > 0.9558f && rx + rz < 1.0442f) { //0.0625/sqrt(2)=0.0442
                return true;
            }
            if ((direction == DIRECTION_TOPLEFT || direction == DIRECTION_BOTTOMRIGHT) && rx - rz > -0.0442f && rx - rz < 0.0442f) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void activate(int x, int z, ObjectPlayer player) {
        if (!player.isFlying()) {
            /*Transform t = new Transform();
            player.getRigidBody().getWorldTransform(t);
            // t.transform(new Vector3f(x+0.5f,t.origin.y,z+0.5f));
            t.origin.x = x + 0.5f;
            t.origin.y = t.origin.y - 0.001f;
            t.origin.z = z + 0.5f;
            player.getRigidBody().setWorldTransform(t);*/

            Vector3f v = new Vector3f();
            Vector3f s = new Vector3f();
            player.getRigidBody().getLinearVelocity(v);
            float velocity = (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.z, 2));
            s.x = (float) Math.cos((270 - direction * 45) * Math.PI / 180) * velocity;
            s.y = v.y;
            s.z = (float) Math.sin((270 - direction * 45) * Math.PI / 180) * velocity;
            player.getRigidBody().setLinearVelocity(s);
            //player.getRigidBody().setAngularVelocity(s);
        }
    }

    @Override
    public RendererObject getRenderer(Resource gameResource) {
        return new RendererObjectNormal(gameResource, 4, direction);
    }
}
