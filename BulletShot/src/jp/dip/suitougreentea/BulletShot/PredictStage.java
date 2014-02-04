package jp.dip.suitougreentea.BulletShot;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

//TODO: predictIntegratedTransform?
public class PredictStage extends Stage {

    private Vector3f keepingPlayerPosition;

    public PredictStage(Terrain[][] terrain, int startingX, int startingZ) {
        super(terrain, startingX, startingZ);
    }

    public void setKeepingPlayerPosition(Vector3f pos) {
        keepingPlayerPosition = pos;
        BulletShot.LOGGER.info(String.format("Set position to : %s", pos.toString()));
    }

    /*
     * public void setPlayerPosition(float x, float y, float z){
     * objPlayer.getRigidBody().getMotionState().setWorldTransform(new
     * Transform(new Matrix4f( new Quat4f(0,0,0,1),new Vector3f(x,y,z), 1.0f)));
     * }
     */

    public Transform[] getPredictedPath(int rot, int vrot) {
        Transform[] t = new Transform[300];
        RigidBody playerRigidBody = player.getRigidBody();

        shoot(64, rot, vrot);
        for (int i = 0; i < 300; i++) {
            // dynamicsWorld.stepSimulation(1/60.f,10);
            update();
            Transform nf = new Transform();
            t[i] = playerRigidBody.getWorldTransform(nf);
        }
        playerRigidBody.setLinearVelocity(new Vector3f(0, 0, 0));
        playerRigidBody.setAngularVelocity(new Vector3f(0, 0, 0));
        playerRigidBody.setMotionState(new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), keepingPlayerPosition, 1.0f))));
        playerRigidBody.clearForces();
        return t;
    }
}
