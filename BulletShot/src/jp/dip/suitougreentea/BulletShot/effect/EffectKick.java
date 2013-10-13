package jp.dip.suitougreentea.BulletShot.effect;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.BulletShot;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;

import com.bulletphysics.dynamics.RigidBody;

/**
 * Change player's angle keeping velocity.
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
    public static final int DIRECTION_NULL = 8;
    
    private int speed;
    public static final int SPEED_SLOW = 0;
    public static final int SPEED_FAST = 1;
    
    public EffectKick(int direction, int speed){
        //super(x,z);
        this.direction = direction;
        this.speed = speed;
    }
    
    @Override
    public void onRegister(){
    }
    @Override
    public boolean isActivatable(){
        return false;
    }
    @Override
    public void activate(int x, int z,ObjectPlayer player){
        if(!player.isFlying()){
            Vector3f v = new Vector3f();
            Vector3f s = new Vector3f();
            player.getRigidBody().getLinearVelocity(v);
            float velocity = (float) Math.sqrt(Math.pow(v.x,2)+Math.pow(v.z,2));
            s.x = (float) Math.cos(90 * Math.PI / 180) * velocity;
            s.y = v.y;
            s.z = (float) Math.sin(90 * Math.PI / 180) * velocity;
            player.getRigidBody().setLinearVelocity(s);
        }
    }
}
