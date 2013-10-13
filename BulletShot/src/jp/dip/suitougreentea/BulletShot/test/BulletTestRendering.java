package jp.dip.suitougreentea.BulletShot.test;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.Stage;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.demos.opengl.DemoApplication;
import com.bulletphysics.demos.opengl.GLDebugDrawer;
import com.bulletphysics.demos.opengl.IGL;
import com.bulletphysics.demos.opengl.LWJGL;

public class BulletTestRendering extends DemoApplication {


    //private ObjectArrayList<CollisionShape> collisionShapes = new ObjectArrayList();
    /*private DefaultCollisionConfiguration collisionConfiguration;
    private CollisionDispatcher dispatcher;
    private BroadphaseInterface broadphase;
    private ConstraintSolver solver;*/

    public BulletTestRendering(IGL gl) {
        super(gl);
    }

    public void clientMoveAndDisplay() {
        this.gl.glClear(16640);

        //float ms = getDeltaTimeMicroseconds();

        if (this.dynamicsWorld != null) {
            stage.update();

            this.dynamicsWorld.debugDrawWorld();
        }

        renderme();
    }

    public void displayCallback() {
        this.gl.glClear(16640);

        renderme();

        if (this.dynamicsWorld != null)
            this.dynamicsWorld.debugDrawWorld();
    }
    
    Stage stage = new Stage(new StageGeneratorFlat(20,20,1).generate(),0,0);
    
    public void initPhysics(){
        stage.initPhysics();
        this.dynamicsWorld = stage.getDynamicsWorld();
    }
    

    public static void main(String[] args) throws LWJGLException {
        BulletTestRendering ccdDemo = new BulletTestRendering(LWJGL.getGL());
        ccdDemo.ShootBoxInitialSpeed = 10f;
        ccdDemo.shootBoxShape = new BoxShape(new Vector3f(0.25f,0.25f,0.25f));
        ccdDemo.setCameraDistance(10.0F);
        ccdDemo.initPhysics();
        ccdDemo.clientResetScene();
        ccdDemo.azi = 225;
        ccdDemo.getDynamicsWorld().setDebugDrawer(
                new GLDebugDrawer(LWJGL.getGL()));
        
        LWJGL.main(args, 800, 600, "Bullet Test Rendering",
                ccdDemo);
    }
    @Override
    public void renderme() {
        // TODO 自動生成されたメソッド・スタブ
        super.renderme();
        setOrthographicProjection();
        Vector3f cp = stage.getCirclePos();
        drawString(String.format("%5f, %5f, %5f", cp.x,cp.y,cp.z), 16, 512, TEXT_COLOR);
        resetPerspectiveProjection();
        Display.sync(60);
    }
}
