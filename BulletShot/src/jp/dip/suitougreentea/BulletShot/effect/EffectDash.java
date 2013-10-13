package jp.dip.suitougreentea.BulletShot.effect;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.BulletShot;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;

import com.bulletphysics.dynamics.RigidBody;

/**
 * Add linear power to player.
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
    public static final int SPEED_SLOW = 0;
    public static final int SPEED_FAST = 1;
    
    public EffectDash(int direction, int speed){
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
            player.getRigidBody().applyCentralForce(new Vector3f(10f,0f,0f));
        }
    }
}
