package jp.dip.suitougreentea.BulletShot;

import com.bulletphysics.collision.broadphase.Dispatcher;
import com.bulletphysics.collision.broadphase.DispatcherInfo;
import com.bulletphysics.collision.broadphase.OverlappingPairCache;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;

public class BulletShotDispatcher extends CollisionDispatcher {

    public BulletShotDispatcher(CollisionConfiguration collisionConfiguration) {
        super(collisionConfiguration);
        // TODO 自動生成されたコンストラクター・スタブ
    }

    @Override
    public boolean needsCollision(CollisionObject body0,CollisionObject body1){
        //super.needsCollision(body0, body1);)
        //BulletShot.getLogger().warning("Collide!");
        return true;
    }
    
    @Override
    public boolean    needsResponse(CollisionObject body0,CollisionObject body1){
        
        //BulletShot.getLogger().warning("Response!");
        return super.needsResponse(body0, body1);
    }
    
    @Override
    public void  dispatchAllCollisionPairs(OverlappingPairCache pairCache,DispatcherInfo dispatchInfo,Dispatcher dispatcher){
        super.dispatchAllCollisionPairs(pairCache, dispatchInfo, dispatcher);
    }
}
