package jp.dip.suitougreentea.BulletShot.test;

import javax.vecmath.Vector3f;

import org.lwjgl.LWJGLException;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.demos.opengl.DemoApplication;
import com.bulletphysics.demos.opengl.GLDebugDrawer;
import com.bulletphysics.demos.opengl.IGL;
import com.bulletphysics.demos.opengl.LWJGL;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

public class BulletTest extends DemoApplication {

    private ObjectArrayList<CollisionShape> collisionShapes = new ObjectArrayList<CollisionShape>();
    private DefaultCollisionConfiguration collisionConfiguration;
    private CollisionDispatcher dispatcher;
    private BroadphaseInterface broadphase;
    private ConstraintSolver solver;

    public BulletTest(IGL gl) {
        super(gl);
    }

    public void clientMoveAndDisplay() {
        this.gl.glClear(16640);

        float ms = getDeltaTimeMicroseconds();

        if (this.dynamicsWorld != null) {
            this.dynamicsWorld.stepSimulation(ms / 1000000.0F);

            this.dynamicsWorld.debugDrawWorld();
        }

        renderme();
    }

    public void displayCallback() {
        this.gl.glClear(16640);

        renderme();

        if (this.dynamicsWorld != null) {
            this.dynamicsWorld.debugDrawWorld();
        }
    }

    public void initPhysics() {
        setCameraDistance(50.0F);

        this.collisionConfiguration = new DefaultCollisionConfiguration();

        this.dispatcher = new CollisionDispatcher(this.collisionConfiguration);

        this.broadphase = new DbvtBroadphase();

        SequentialImpulseConstraintSolver sol = new SequentialImpulseConstraintSolver();
        this.solver = sol;

        this.dynamicsWorld = new DiscreteDynamicsWorld(this.dispatcher,
                this.broadphase, this.solver, this.collisionConfiguration);

        this.dynamicsWorld.setGravity(new Vector3f(0.0F, -10.0F, 0.0F));
        // CollisionShape groundShape = new BoxShape(new Vector3f(50.0F, 50.0F,
        // 50.0F));

        int width = 20;
        int length = 20;
        float[] heightfieldData = new float[width * length];
        for (int i = 0; i < width * length; i++) {
            heightfieldData[i] = 0;
        }
        // heightfieldData [10*20+10]=5;
        // btHeightfieldTerrainShape* heightFieldShape = new
        // btHeightfieldTerrainShape(width, length, heightfieldData, maxHeight,
        // 1, useFloatDatam, flipQuadEdges);
        ObjectArrayList<Vector3f> points = new ObjectArrayList<Vector3f>();
        points.add(new Vector3f(-5f, 2.5f, -5f));
        points.add(new Vector3f(0f, 0f, 0f));
        points.add(new Vector3f(-10f, 0f, 0f));
        points.add(new Vector3f(-10f, 0f, -10f));
        points.add(new Vector3f(0f, 0f, -10f));

        CollisionShape groundShape = new ConvexHullShape(points);

        this.collisionShapes.add(groundShape);

        Transform groundTransform = new Transform();
        groundTransform.setIdentity();

        float mass = 0.0F;

        boolean isDynamic = mass != 0.0F;

        Vector3f localInertia = new Vector3f(0.0F, 0.0F, 0.0F);
        if (isDynamic) {
            groundShape.calculateLocalInertia(mass, localInertia);

        }

        DefaultMotionState myMotionState = new DefaultMotionState(
                groundTransform);
        RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass,
                myMotionState, groundShape, localInertia);
        RigidBody body = new RigidBody(rbInfo);

        this.dynamicsWorld.addRigidBody(body);

        CollisionShape colShape = new BoxShape(new Vector3f(1.0F, 1.0F, 1.0F));

        this.collisionShapes.add(colShape);

        Transform startTransform = new Transform();
        startTransform.setIdentity();

        mass = 1.0F;

        isDynamic = mass != 0.0F;

        localInertia = new Vector3f(0.0F, 0.0F, 0.0F);
        if (isDynamic) {
            colShape.calculateLocalInertia(mass, localInertia);
        }

        float startX = -5.0F;
        float startY = -5.0F;
        float startZ = -5.0F;

        for (int k = 0; k < 3; ++k) {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    startTransform.origin.set(2.0F * i + startX, 10.0F + 2.0F
                            * k + startY, 2.0F * j + startZ);

                    myMotionState = new DefaultMotionState(startTransform);
                    rbInfo = new RigidBodyConstructionInfo(mass, myMotionState,
                            colShape, localInertia);
                    body = new RigidBody(rbInfo);
                    body.setActivationState(2);

                    this.dynamicsWorld.addRigidBody(body);
                    body.setActivationState(2);
                }
            }

        }

        clientResetScene();
    }

    public static void main(String[] args) throws LWJGLException {
        BulletTest ccdDemo = new BulletTest(LWJGL.getGL());
        ccdDemo.initPhysics();
        ccdDemo.getDynamicsWorld().setDebugDrawer(
                new GLDebugDrawer(LWJGL.getGL()));

        LWJGL.main(args, 800, 600, "Bullet Test Rendering",
                ccdDemo);
    }
}
