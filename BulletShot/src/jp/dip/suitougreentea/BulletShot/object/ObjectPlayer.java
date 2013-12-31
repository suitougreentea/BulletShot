package jp.dip.suitougreentea.BulletShot.object;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;

public class ObjectPlayer extends ObjectBase {
    protected int lastEffectX=-1,lastEffectZ=-1;

    public ObjectPlayer(Vector3f position){
        this.position = position;
        shape = new SphereShape(0.2f);
    }
    
    protected void postRegister(DiscreteDynamicsWorld world){
        this.rigidBody.setRestitution(0.5f);
        this.rigidBody.setDamping(0f,0f);
        this.rigidBody.setActivationState(RigidBody.ISLAND_SLEEPING);

        /*****
         * Proxyでグループ取得できる。これ使ってどうにか
        world.getDispatcher().getManifoldByIndexInternal(0);
        rigidBody.getBroadphaseProxy();
        *****/
    }

    public void setLastEffectPosition(int x, int z) {
        lastEffectX = x;
        lastEffectZ = z;
    }
    
    public int getLastEffectX(){
        return lastEffectX;
    }
    public int getLastEffectZ(){
        return lastEffectZ;
    }
}
