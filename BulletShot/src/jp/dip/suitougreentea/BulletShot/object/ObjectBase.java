package jp.dip.suitougreentea.BulletShot.object;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class ObjectBase {
    protected CollisionShape shape;
    protected float mass = 1f;
    protected Vector3f inertia = new Vector3f(0f,0f,0f);
    protected Quat4f rotation = new Quat4f(0f,0f,0f,1f);
    protected Vector3f position = new Vector3f(0f,0f,0f);
    protected float scale = 1f;
    
    protected MotionState motionState;
    protected RigidBodyConstructionInfo rigidBodyCI;
    protected RigidBody rigidBody;
    
    protected boolean flying;
    
    public void register(DiscreteDynamicsWorld world){
        preRegister(world);
        motionState = new DefaultMotionState(new Transform(new Matrix4f(rotation,position,scale)));
        shape.calculateLocalInertia(mass,inertia);
        rigidBodyCI = new RigidBodyConstructionInfo(mass,motionState,shape,inertia);
        rigidBody = new RigidBody(rigidBodyCI);
        world.addRigidBody(rigidBody,(short)0x2,(short)0x1);
        postRegister(world);
    }
    
    protected void preRegister(DiscreteDynamicsWorld world) {
    }
    protected void postRegister(DiscreteDynamicsWorld world) {
    }

    public CollisionShape getCollisionShape() {
        return shape;
    }

    public float getMass() {
        return mass;
    }

    public Vector3f getInertia() {
        return inertia;
    }

    public Quat4f getRotation() {
        return rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }

    public MotionState getMotionState() {
        return motionState;
    }

    public RigidBodyConstructionInfo getRigidBodyConstructionInfo() {
        return rigidBodyCI;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }
}
