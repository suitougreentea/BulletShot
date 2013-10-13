package jp.dip.suitougreentea.BulletShot;

import java.nio.FloatBuffer;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.effect.Effect;
import jp.dip.suitougreentea.BulletShot.object.ObjectPlayer;

import org.lwjgl.BufferUtils;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.dispatch.NearCallback;
import com.bulletphysics.collision.narrowphase.PersistentManifold;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.Transform;

public class Stage {
    Terrain[][] terrain;
    int startingX, startingZ;
    ObjectPlayer player;

    public ObjectPlayer getPlayer() {
        return player;
    }
    
    public Stage(Terrain[][] terrain, int startingX, int startingZ){
        this.terrain = terrain;
        this.startingX = startingX;
        this.startingZ = startingZ;
    }
    

    public static Terrain[][] createTerrain(int[][] type,int[][] height,int[][] direction,int[][] bumper,Effect[][] effect,int[][] chara) {
        Terrain[][] terrain = new Terrain[type.length][type[0].length];

        for(int iz=0;iz<type.length;iz++){
            for(int ix=0;ix<type[0].length;ix++){
                terrain[iz][ix] = new Terrain(type[iz][ix],height[iz][ix],direction[iz][ix],bumper[iz][ix],effect[iz][ix],chara[iz][ix]);
            }
        }
       
        return terrain;
    }

    public Terrain getTerrain(int x, int z){
        return terrain[z][x];
    }
    
    public Vector3f getCirclePos(){
        Transform t = new Transform();
        getPlayer().getMotionState().getWorldTransform(t);
        return t.origin;
    }
    
    public FloatBuffer getCircleRot(){
        float[] matrix = new float[16];
        FloatBuffer transformBuffer = BufferUtils.createFloatBuffer(16);
        Transform t = new Transform();
        
        getPlayer().getMotionState().getWorldTransform(t);
        t.getOpenGLMatrix(matrix);

        transformBuffer.clear();
        transformBuffer.put(matrix);
        transformBuffer.flip();
        return transformBuffer;
    }
    
    public DiscreteDynamicsWorld getDynamicsWorld() {
        return dynamicsWorld;
    }
    
    public int getWidth(){
        return terrain[0].length;
    }
    
    public int getHeight(){
        return terrain.length;
    }

    AxisSweep3 broadphase;
    //DbvtBroadphase broadphase;
    DefaultCollisionConfiguration collisionConfiguration;
    CollisionDispatcher dispatcher;
    SequentialImpulseConstraintSolver solver;
    DiscreteDynamicsWorld dynamicsWorld;
    
    public void initPhysics(){
        //broadphase = new DbvtBroadphase();
        Vector3f minAABB = new Vector3f(-10,-10,-10);
        Vector3f maxAABB = new Vector3f(100,100,100);
        int maxProxies = 10000;
        broadphase = new AxisSweep3(minAABB, maxAABB, maxProxies);
        collisionConfiguration = new DefaultCollisionConfiguration();
        //dispatcher = new CollisionDispatcher(collisionConfiguration);
        dispatcher = new BulletShotDispatcher(collisionConfiguration);
        solver = new SequentialImpulseConstraintSolver();
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher,broadphase,solver,collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0,-5f,0));
        
        for(int iz=0;iz<terrain.length;iz++){
            for(int ix=0;ix<terrain[0].length;ix++){
                Terrain t = terrain[iz][ix];
                t.generateRigidBody(ix, iz);
                dynamicsWorld.addRigidBody(t.getFloorRigidBody(),(short)0x1,(short)0x2);
                if(t.getSlopeRigidBody() != null) dynamicsWorld.addRigidBody(t.getSlopeRigidBody(),(short)0x1,(short)0x2);
            }
        }
        //BulletTerrainBuilder.generate(dynamicsWorld, terrain);
        float y = getTerrain(startingX,startingZ).getHeight()*0.5f+0.2f;    //radius of the player
        switch(getTerrain(startingX,startingZ).getType()){
        case Terrain.TERRAIN_DOUBLESLOPE:
        case Terrain.TERRAIN_SINGLESLOPE:
        case Terrain.TERRAIN_LOWSLOPE:
        case Terrain.TERRAIN_PYRAMID:
            y+=0.25;
            break;
        case Terrain.TERRAIN_HIGHSLOPE:
            y+=0.5;
            break;
        }
        player = new ObjectPlayer(new Vector3f(startingX+0.5f,y, startingZ+0.5f));
        player.setFlying(false);
        player.register(dynamicsWorld);
    }
    
    public void update(){
          dynamicsWorld.stepSimulation(1/60.f,10);
          
          //player.getRigidBody().setDamping(0f, 0f);
          player.setFlying(true);
          outside:for(int i=0;i<dispatcher.getNumManifolds();i++){
              PersistentManifold manifold = dispatcher.getManifoldByIndexInternal(i);
              for(int m=0;m<4;m++){
                  if(manifold.getContactPoint(m).getDistance()<=0.01f){
                      //BulletShot.getLogger().warning(String.valueOf(manifold.getContactPoint(m).getDistance()));
                      player.getRigidBody().setDamping(0f, 0.8f);
                      player.setFlying(false);
                      break outside;
                  }else{
                      player.setFlying(true);
                  }
              }
          }
          
          Transform t = new Transform();
          player.getRigidBody().getMotionState().getWorldTransform(t);
          
          int x = (int) Math.floor(t.origin.x);
          if(x<0)x=0;
          if(x>=getWidth())x=getWidth()-1;
          int z = (int) Math.floor(t.origin.z);
          if(z<0)z=0;
          if(z>=getHeight())z=getHeight()-1;
          if(getTerrain(x,z).getEffect()!=null)getTerrain(x,z).getEffect().activate(x,z,player);
    }
    

    //TODO: CalculateVelocityの仕様も検討
    public void shoot(int poweri,int rot,int vrot) {
        RigidBody playerRigidBody = player.getRigidBody();
        playerRigidBody.activate();
        final float HEIGHT_COEFFICIENT = 1.340625f;
        
        //float power = 4f;    //Default:4 Half:2.75,*0.6875
        float power = (float) (Math.sqrt(poweri/64f)*4f);
        float spin = 0f;
        
        float x = (float) Math.cos(rot * Math.PI / 180);
        float z = (float) Math.sin(rot * Math.PI / 180);
        float vp = (float) Math.cos(vrot * Math.PI / 180);
        float vy = (float) Math.sin(vrot * Math.PI / 180);
        
        //fallRigidBody.applyForce(new Vector3f(x,325,y),new Vector3f(x,325,y));
        //playerRigidBody.setLinearVelocity(new Vector3f(x,5,z));
        playerRigidBody.setLinearVelocity(new Vector3f(x*power*vp,HEIGHT_COEFFICIENT*power*vy,z*power*vp));
        playerRigidBody.setAngularVelocity(new Vector3f(z*spin,0,-x*spin));
    }

    public Terrain[][] getTerrain() {
        return terrain;
    }

    public int getStartingX() {
        return startingX;
    }

    public int getStartingY() {
        return startingZ;
    }


    
}
