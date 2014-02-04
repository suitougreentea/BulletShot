package jp.dip.suitougreentea.BulletShot;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class Bumper {
    protected CollisionShape shape;
    protected Vector3f position = new Vector3f(0f, 0f, 0f);

    protected MotionState motionState;
    protected RigidBodyConstructionInfo rigidBodyCI;
    protected RigidBody rigidBody;

    protected boolean flying;

    //public static final short 

    public Bumper(Vector3f position) {
        this.position = position;
        shape = new BoxShape(new Vector3f(0.1f, 0.1f, 0.5f));
    }

    public void register(DiscreteDynamicsWorld world) {
        preRegister(world);
        motionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0f, 0f, 0f, 1f), position, 1f)));
        //shape.calculateLocalInertia(mass, inertia);
        rigidBodyCI = new RigidBodyConstructionInfo(0, motionState, shape, new Vector3f(0, 0, 0));
        rigidBody = new RigidBody(rigidBodyCI);
        world.addRigidBody(rigidBody, (short) 0x1, (short) 0x2);
        rigidBody.setActivationState(RigidBody.ISLAND_SLEEPING);
        postRegister(world);
    }

    protected void preRegister(DiscreteDynamicsWorld world) {

    }

    protected void postRegister(DiscreteDynamicsWorld world) {

    }

    public CollisionShape getCollisionShape() {
        return shape;
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
}
