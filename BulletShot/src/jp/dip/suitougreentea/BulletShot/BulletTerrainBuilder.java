package jp.dip.suitougreentea.BulletShot;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

//TODO: static?

public class BulletTerrainBuilder {
    
    @Deprecated
    public static void generate(DiscreteDynamicsWorld dynamicsWorld, Terrain[][] terrain){
        for(int iz=0;iz<terrain.length;iz++){
            for(int ix=0;ix<terrain[0].length;ix++){
                Terrain t = terrain[iz][ix];
                
                if(t.getType() != Terrain.TERRAIN_NORMAL){
                    ObjectArrayList<Vector3f> points = BulletTerrainBuilder.getPoints(t);
                    CollisionShape slopeShape = new ConvexHullShape(points);
                    DefaultMotionState slopeMotionState = new DefaultMotionState(new Transform(new Matrix4f(
                      new Quat4f(0,0,0,1f),new Vector3f(ix,t.getHeight()*0.5f,iz), 1f)));
                    RigidBodyConstructionInfo slopeRigidBodyCI = new RigidBodyConstructionInfo(0,slopeMotionState,slopeShape,new Vector3f(0,0,0));
                    RigidBody slopeRigidBody = new RigidBody(slopeRigidBodyCI);
                    //dynamicsWorld.addRigidBody(slopeRigidBody);
                    dynamicsWorld.addRigidBody(slopeRigidBody,(short)0x1,(short)0x2);
                    //slopeRigidBody.setActivationState(RigidBody.ISLAND_SLEEPING);
                    slopeRigidBody.setRestitution(1.0f);
                }
                
                CollisionShape boxShape = new BoxShape(new Vector3f(0.5f,t.getHeight()*0.25f,0.5f));
                DefaultMotionState boxMotionState = 
                        new DefaultMotionState(new Transform(
                                new Matrix4f(new Quat4f(0,0,0,1),new Vector3f(ix+0.5f,t.getHeight()*0.25f,iz+0.5f), 1f)
                        ));
                RigidBodyConstructionInfo boxRigidBodyCI = new RigidBodyConstructionInfo(0,boxMotionState,boxShape,new Vector3f(0,0,0));
                RigidBody boxRigidBody = new RigidBody(boxRigidBodyCI);
                dynamicsWorld.addRigidBody(boxRigidBody,(short)0x1,(short)0x2);
                //boxRigidBody.setActivationState(RigidBody.ISLAND_SLEEPING);
                boxRigidBody.setRestitution(1.0f);
            }
        }
    }
    
    public static ObjectArrayList<Vector3f> getPoints(Terrain terrain){
        Terrain t = terrain;
        ObjectArrayList<Vector3f> points = new ObjectArrayList<Vector3f>();
        if(t.getType() == Terrain.TERRAIN_PYRAMID){
            points.add(new Vector3f(0.5f, 0.25f, 0.5f));
            points.add(new Vector3f(0f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 1f));
            points.add(new Vector3f(0f, 0f, 1f));
        }else if(t.getType() == Terrain.TERRAIN_LOWSLOPE){
            int d = t.getDirection();
            points.add(new Vector3f(((d==Terrain.DIRECTION_BOTTOM||d==Terrain.DIRECTION_RIGHT)?1f:0f), 0.5f, ((d==Terrain.DIRECTION_BOTTOM||d==Terrain.DIRECTION_LEFT)?1f:0f)));
            points.add(new Vector3f(((d==Terrain.DIRECTION_TOP||d==Terrain.DIRECTION_RIGHT)?1f:0f), 0.5f, ((d==Terrain.DIRECTION_BOTTOM||d==Terrain.DIRECTION_RIGHT)?1f:0f)));
            //points.add(new Vector3f(0f, 0.5f, 0f));
            //points.add(new Vector3f(1f, 0.5f, 0f));
            points.add(new Vector3f(0f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 1f));
            points.add(new Vector3f(0f, 0f, 1f));
        }else if(t.getType() == Terrain.TERRAIN_HIGHSLOPE){
            int d = t.getDirection();
            points.add(new Vector3f(((d==Terrain.DIRECTION_TOPRIGHT||d==Terrain.DIRECTION_BOTTOMRIGHT)?1f:0f), 1f, ((d==Terrain.DIRECTION_BOTTOMRIGHT||d==Terrain.DIRECTION_BOTTOMLEFT)?1f:0f)));
            points.add(new Vector3f(((d==Terrain.DIRECTION_TOPLEFT||d==Terrain.DIRECTION_TOPRIGHT)?1f:0f), 0.5f, ((d==Terrain.DIRECTION_TOPRIGHT||d==Terrain.DIRECTION_BOTTOMRIGHT)?1f:0f)));
            points.add(new Vector3f(((d==Terrain.DIRECTION_BOTTOMLEFT||d==Terrain.DIRECTION_BOTTOMRIGHT)?1f:0f), 0.5f, ((d==Terrain.DIRECTION_TOPLEFT||d==Terrain.DIRECTION_BOTTOMLEFT)?1f:0f)));
            points.add(new Vector3f(0f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 1f));
            points.add(new Vector3f(0f, 0f, 1f));
        }else if(t.getType() == Terrain.TERRAIN_DOUBLESLOPE){
            int d = t.getDirection();
            points.add(new Vector3f(((d==Terrain.DIRECTION_TOPRIGHT||d==Terrain.DIRECTION_BOTTOMRIGHT)?1f:0f), 0.5f, ((d==Terrain.DIRECTION_BOTTOMRIGHT||d==Terrain.DIRECTION_BOTTOMLEFT)?1f:0f)));
            points.add(new Vector3f(0f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 0f));
            points.add(new Vector3f(1f, 0f, 1f));
            points.add(new Vector3f(0f, 0f, 1f));
        }else if(t.getType() == Terrain.TERRAIN_SINGLESLOPE){
            int d = t.getDirection();
            points.add(new Vector3f(((d==Terrain.DIRECTION_TOPRIGHT||d==Terrain.DIRECTION_BOTTOMRIGHT)?1f:0f), 0.5f, ((d==Terrain.DIRECTION_BOTTOMRIGHT||d==Terrain.DIRECTION_BOTTOMLEFT)?1f:0f)));
            if(d!=Terrain.DIRECTION_BOTTOMRIGHT)points.add(new Vector3f(0f, 0f, 0f));
            if(d!=Terrain.DIRECTION_BOTTOMLEFT)points.add(new Vector3f(1f, 0f, 0f));
            if(d!=Terrain.DIRECTION_TOPLEFT)points.add(new Vector3f(1f, 0f, 1f));
            if(d!=Terrain.DIRECTION_TOPRIGHT)points.add(new Vector3f(0f, 0f, 1f));
        }
        return points;
    }
}
