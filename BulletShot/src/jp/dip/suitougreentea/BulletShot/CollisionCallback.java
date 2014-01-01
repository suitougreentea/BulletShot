package jp.dip.suitougreentea.BulletShot;

import com.bulletphysics.collision.broadphase.BroadphasePair;
import com.bulletphysics.collision.broadphase.DispatcherInfo;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultNearCallback;
import com.bulletphysics.collision.dispatch.NearCallback;

public class CollisionCallback extends NearCallback {

    private DefaultNearCallback dCallBack = new DefaultNearCallback();

    @Override
    public void handleCollision(BroadphasePair collisionPair, CollisionDispatcher dispatcher, DispatcherInfo dispatchInfo) {
        dCallBack.handleCollision(collisionPair, dispatcher, dispatchInfo);
        BulletShot.LOGGER.warning("Collide!");

    }

}
