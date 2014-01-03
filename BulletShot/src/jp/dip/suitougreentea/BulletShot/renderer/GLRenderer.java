package jp.dip.suitougreentea.BulletShot.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector3f;

import jp.dip.suitougreentea.BulletShot.Resource;
import jp.dip.suitougreentea.BulletShot.Stage;
import jp.dip.suitougreentea.BulletShot.Terrain;
import jp.dip.suitougreentea.BulletShot.TerrainUtil;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.TextureImpl;

import com.bulletphysics.linearmath.Transform;

/**
 * 
 * TODO: Rewriting.... 反時計回り TODO: switchかifかどっちかに統一
 * テクスチャ座標に関しては、Slick的に気にしなくてよい…？ 参考 / http://www.arakin.dyndns.org/gl_coord.php
 * 
 * @author suitougreentea
 * 
 */
public class GLRenderer {

    private int displayWidth;
    private int displayHeight;
    private float cameraPosX = 2.5f;
    private float cameraPosZ = 2.5f;
    private float zoom = 5.0f;
    //private float viewHeight = 8f; // Default:8

    private Resource gameResource;
    private Image tile, wall, wallRight, wallLeft;

    private int timer;

    public GLRenderer(Resource resource) {
        this.gameResource = resource;
        this.tile = gameResource.getImage(Resource.IMAGEID_TILE);
        this.wall = gameResource.getImage(Resource.IMAGEID_WALL);
        this.wallRight = gameResource.getImage(Resource.IMAGEID_WALL_RIGHT);
        this.wallLeft = gameResource.getImage(Resource.IMAGEID_WALL_LEFT);

        displayWidth = Display.getDisplayMode().getWidth();
        displayHeight = Display.getDisplayMode().getHeight();
        timer = 0;
    }

    public void incrementTimer() {
        timer++;
    }

    public void resetTimer() {
        timer = 0;
    }

    /**
     * Get drawing buffers of floors.
     * @return Arrays of FloatBuffer. [0] is Normal, [1] is TexCoord, [2] is Vertex.
     */
    public Buffers getBuffer(int x, int z, Terrain t){
        FloatBuffer normal = null;
        FloatBuffer texcoord = null;
        FloatBuffer  vertex = null;
        IntBuffer index = null;

        int type = t.getType();
        int height = t.getHeight();
        int direction = t.getDirection();

        if(type==Terrain.TERRAIN_PYRAMID){
            normal = BufferUtils.createFloatBuffer(15);
            texcoord = BufferUtils.createFloatBuffer(10);
            vertex = BufferUtils.createFloatBuffer(15);
            index = BufferUtils.createIntBuffer(12);
        } else {
            normal = BufferUtils.createFloatBuffer(12); //TODO: 頂点法線
            texcoord = BufferUtils.createFloatBuffer(8);
            vertex = BufferUtils.createFloatBuffer(12);
            index = BufferUtils.createIntBuffer(6);

            switch (type) {
            case Terrain.TERRAIN_NORMAL:
            case Terrain.TERRAIN_SINGLESLOPE:
            case Terrain.TERRAIN_DOUBLESLOPE:
            case Terrain.TERRAIN_HIGHSLOPE:
            case Terrain.TERRAIN_LOWSLOPE:
                normal.put(new float[]{
                        0, 1, 0,
                        0, 1, 0,
                        0, 1, 0,
                        0, 1, 0
                });
                break;
            }

            texcoord.put(new float[]{
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0
            });

            vertex.put(new float[]{
                    x, TerrainUtil.getHeight(0, 0, type, height, direction), z,
                    x, TerrainUtil.getHeight(0, 1, type, height, direction), z+1,
                    x+1, TerrainUtil.getHeight(1, 1, type, height, direction), z+1,
                    x+1, TerrainUtil.getHeight(1, 0, type, height, direction), z
            });

            index.put(new int[]{
                    0, 1, 3, 1, 2, 3     
            });
        }

        normal.flip();
        texcoord.flip();
        vertex.flip();
        index.flip();
        return new Buffers(normal, texcoord, vertex, index);
    }

    public void draw(Stage s,Transform[] predict,int frame){
        //if(true)return;
        glEnable(GL_LIGHT0);

        glPushMatrix();
        FloatBuffer transformationBuffer = s.getCircleRot();
        GL11.glMultMatrix(transformationBuffer);
        drawPlayerObject();
        glPopMatrix();

        Vector3f p = s.getCirclePos();
        glPushMatrix();
        glTranslatef(p.x, 0.5f, p.z);
        // drawShadow();
        glPopMatrix();

        if (predict != null) {
            drawPredictedPath(predict, frame);
        }

        drawFloors(s.getTerrain());

        glDisable(GL_LIGHT0);

        glEnable(GL_LIGHT1);

        drawWalls(s.getTerrain());

        glDisable(GL_LIGHT1);
    }

    public void drawFloors(Terrain[][] terrain) {
        for (int iz = 0; iz < terrain.length; iz++) {
            for (int ix = 0; ix < terrain[0].length; ix++) {
                Terrain t = terrain[iz][ix];
                if (t.getHeight() == 0) {
                    continue;
                }
                glPushMatrix();

                //Res.tile.bind();
                /*glBegin(GL_QUADS);
                //glColor3f(1f,1f,1f);
                glNormal3f(0f,1f,0f);
                glTexCoord2f(0,0);
                glVertex3f(0,0,0);
                glTexCoord2f(0,2);
                glVertex3f(0,0,1);
                glTexCoord2f(1,2);
                glVertex3f(1,0,1);
                glTexCoord2f(1,0);
                glVertex3f(1,0,0);
                glEnd();*/

                Buffers buf = getBuffer(ix,iz,t);
                tile.bind();
                glColor3f(1f,1f,1f);

                glEnableClientState(GL_NORMAL_ARRAY);
                glEnableClientState(GL_TEXTURE_COORD_ARRAY);
                glEnableClientState(GL_VERTEX_ARRAY);

                glNormalPointer(0, buf.getNormal());
                glTexCoordPointer(2, 0, buf.getTexcoord());
                glVertexPointer(3, 0, buf.getVertex());
                //glDrawArrays(GL_TRIANGLES,0,6);
                glDrawElements(GL_TRIANGLES, buf.getIndex());
                //TODO: glDrawElements

                glDisableClientState(GL_NORMAL_ARRAY);
                glDisableClientState(GL_TEXTURE_COORD_ARRAY);
                glDisableClientState(GL_VERTEX_ARRAY);

                //buf = null;

                glTranslatef(ix, t.getHeight() * 0.5f + 0.001f, iz);   //TODO: ずらしてるだけ

                if(t.getEffect()!=null)t.getEffect().getRenderer(gameResource).draw(t, timer);

                /*=======
                glTranslatef(ix, t.getHeight() * 0.5f, iz);

                if (t.getType() == Terrain.TERRAIN_NORMAL) {
                    drawNormal();
                }
                if (t.getType() == Terrain.TERRAIN_PYRAMID) {
                    drawPyramid();
                }
                if (t.getType() == Terrain.TERRAIN_LOWSLOPE) {
                    drawLowSlope(t.getDirection());
                }
                if (t.getType() == Terrain.TERRAIN_HIGHSLOPE) {
                    drawHighSlope(t.getDirection());
                }
                if (t.getType() == Terrain.TERRAIN_SINGLESLOPE) {
                    drawSingleSlope(t.getDirection());
                }
                if (t.getType() == Terrain.TERRAIN_DOUBLESLOPE) {
                    drawDoubleSlope(t.getDirection());
                }

                glTranslatef(0, 0.001f, 0); // TODO: ずらしてるだけ

                if (t.getEffect() != null) {
                    t.getEffect().getRenderer(gameResource).draw(t, timer);
                }

>>>>>>> master*/
                glPopMatrix();
            }
        }
    }

    public void drawWalls(Terrain[][] terrain) {
        for (int iz = 0; iz < terrain.length; iz++) {
            for (int ix = 0; ix < terrain[0].length; ix++) {
                Terrain t = terrain[iz][ix];
                if (t.getHeight() == 0) {
                    continue;
                }
                glPushMatrix();
                glTranslatef(ix, 0, iz);
                for (int h = 0; h < t.getHeight(); h++) {
                    glTranslatef(0, 0.5f, 0);
                    drawWall(1, 1, 1, 1);
                }
                if (t.getType() == Terrain.TERRAIN_LOWSLOPE) {

                    drawWallLowSlope(t.getDirection());
                }
                if (t.getType() == Terrain.TERRAIN_HIGHSLOPE) {
                    drawWallHighSlope(t.getDirection());
                }
                if (t.getType() == Terrain.TERRAIN_SINGLESLOPE) {
                    drawWallSlope(t.getDirection());
                }
                if (t.getType() == Terrain.TERRAIN_DOUBLESLOPE) {
                    drawWallSlope(t.getDirection());
                }
                glPopMatrix();
            }
        }
    }

    public static final int WALL_NULL = -1;
    public static final int WALL_NORMAL = 1;
    public static final int WALL_RIGHT = 0;
    public static final int WALL_LEFT = 2;

    public void drawWall(int t, int l, int b, int r) {
        glPushMatrix();
        glRotatef(180, 0, 1, 0);
        glTranslatef(-1, 0, 0);
        drawWallBase(t);
        glPopMatrix();

        glPushMatrix();
        glRotatef(-90, 0, 1, 0);
        glTranslatef(0, 0, 0);
        drawWallBase(l);
        glPopMatrix();

        glPushMatrix();
        glTranslatef(0, 0, 1);
        drawWallBase(b);
        glPopMatrix();

        glPushMatrix();
        glRotatef(90, 0, 1, 0);
        glTranslatef(-1, 0, 1);
        drawWallBase(r);
        glPopMatrix();
    }

    public void drawWallBase(int type) {
        switch (type) {
        case WALL_NORMAL:
            wall.bind();
            glBegin(GL_QUADS);
            glNormal3f(0f, 0f, 1f);
            // glColor3f(1f,1f,1f);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, -0.5f, 0);
            glTexCoord2f(1, 1);
            glVertex3f(1, -0.5f, 0);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glEnd();
            break;
        case WALL_LEFT:
            wallLeft.bind();
            glBegin(GL_TRIANGLES);
            glNormal3f(0f, 0f, 1f);
            // glColor3f(1f,1f,1f);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, -0.5f, 0);
            glTexCoord2f(1, 1);
            glVertex3f(1, -0.5f, 0);
            glEnd();
            break;
        case WALL_RIGHT:
            wallRight.bind();
            glBegin(GL_TRIANGLES);
            glNormal3f(0f, 0f, 1f);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, -0.5f, 0);
            glTexCoord2f(1, 1);
            glVertex3f(1, -0.5f, 0);
            glEnd();
            break;
        }
    }

    public void drawWallLowSlope(int d) {
        glTranslatef(0, 0.5f, 0);
        if (d == Terrain.DIRECTION_TOP) {
            drawWall(WALL_NORMAL, WALL_LEFT, WALL_NULL, WALL_RIGHT);
        } else if (d == Terrain.DIRECTION_LEFT) {
            drawWall(WALL_RIGHT, WALL_NORMAL, WALL_LEFT, WALL_NULL);
        } else if (d == Terrain.DIRECTION_BOTTOM) {
            drawWall(WALL_NULL, WALL_RIGHT, WALL_NORMAL, WALL_LEFT);
        } else if (d == Terrain.DIRECTION_RIGHT) {
            drawWall(WALL_LEFT, WALL_NULL, WALL_RIGHT, WALL_NORMAL);
        }
        glTranslatef(0, -0.5f, 0);
    }

    public void drawWallHighSlope(int d) {
        glTranslatef(0, 0.5f, 0);
        if (d == Terrain.DIRECTION_TOPLEFT) {
            drawWall(WALL_NORMAL, WALL_NORMAL, WALL_LEFT, WALL_RIGHT);
        } else if (d == Terrain.DIRECTION_LEFT) {
            drawWall(WALL_RIGHT, WALL_NORMAL, WALL_NORMAL, WALL_LEFT);
        } else if (d == Terrain.DIRECTION_BOTTOM) {
            drawWall(WALL_LEFT, WALL_RIGHT, WALL_NORMAL, WALL_NORMAL);
        } else if (d == Terrain.DIRECTION_RIGHT) {
            drawWall(WALL_NORMAL, WALL_LEFT, WALL_RIGHT, WALL_NORMAL);
        }
        glTranslatef(0, 0.5f, 0);
        if (d == Terrain.DIRECTION_TOPLEFT) {
            drawWall(WALL_RIGHT, WALL_LEFT, WALL_NULL, WALL_NULL);
        } else if (d == Terrain.DIRECTION_LEFT) {
            drawWall(WALL_NULL, WALL_RIGHT, WALL_LEFT, WALL_NULL);
        } else if (d == Terrain.DIRECTION_BOTTOM) {
            drawWall(WALL_NULL, WALL_NULL, WALL_RIGHT, WALL_LEFT);
        } else if (d == Terrain.DIRECTION_RIGHT) {
            drawWall(WALL_LEFT, WALL_NULL, WALL_NULL, WALL_RIGHT);
        }
        glTranslatef(0, -1f, 0);
    }

    public void drawWallSlope(int d) {
        glTranslatef(0, 0.5f, 0);
        if (d == Terrain.DIRECTION_TOPLEFT) {
            drawWall(WALL_RIGHT, WALL_LEFT, WALL_NULL, WALL_NULL);
        }
        if (d == Terrain.DIRECTION_BOTTOMLEFT) {
            drawWall(WALL_NULL, WALL_RIGHT, WALL_LEFT, WALL_NULL);
        }
        if (d == Terrain.DIRECTION_BOTTOMRIGHT) {
            drawWall(WALL_NULL, WALL_NULL, WALL_RIGHT, WALL_LEFT);
        }
        if (d == Terrain.DIRECTION_TOPRIGHT) {
            drawWall(WALL_LEFT, WALL_NULL, WALL_NULL, WALL_RIGHT);
        }
        glTranslatef(0, -0.5f, 0);
    }

    public void drawNormal() {
        tile.bind();
        glBegin(GL_QUADS);
        //<<<<<<< HEAD
        //glColor3f(1f,1f,1f);
        glNormal3f(0f,1f,0f);
        glTexCoord2f(0,0);
        glVertex3f(0,0,0);
        glTexCoord2f(0,2);
        glVertex3f(0,0,1);
        glTexCoord2f(1,2);
        glVertex3f(1,0,1);
        glTexCoord2f(1,0);
        glVertex3f(1,0,0);
        /*=======
        // glColor3f(1f,1f,1f);
        glNormal3f(0f, 1f, 0f);
        glTexCoord2f(0, 0);
        glVertex3f(0, 0, 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 1);
        glTexCoord2f(1, 1);
        glVertex3f(1, 0, 1);
        glTexCoord2f(1, 0);
        glVertex3f(1, 0, 0);
>>>>>>> master*/
        glEnd();
    }

    public void drawPyramid() {
        tile.bind();
        // glColor3f(1f,1f,1f);

        /* 1 */
        glBegin(GL_TRIANGLES);
        glNormal3f(0f, 2f, -1f);
        glTexCoord2f(1, 0);
        glVertex3f(1, 0, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0, 0, 0);
        glTexCoord2f(0.5f, 0.5f);
        glVertex3f(0.5f, 0.25f, 0.5f);
        glEnd();

        /* 2 */
        glBegin(GL_TRIANGLES);
        glNormal3f(-1f, 2f, 0f);
        glTexCoord2f(0, 0);
        glVertex3f(0, 0, 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 1);
        glTexCoord2f(0.5f, 0.5f);
        glVertex3f(0.5f, 0.25f, 0.5f);
        glEnd();

        /* 3 */
        glBegin(GL_TRIANGLES);
        glNormal3f(0f, 2f, 1f);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 1);
        glTexCoord2f(1, 1);
        glVertex3f(1, 0, 1);
        glTexCoord2f(0.5f, 0.5f);
        glVertex3f(0.5f, 0.25f, 0.5f);
        glEnd();

        /* 4 */
        glBegin(GL_TRIANGLES);
        glNormal3f(1f, 2f, 0f);
        glTexCoord2f(1, 1);
        glVertex3f(1, 0, 1);
        glTexCoord2f(1, 0);
        glVertex3f(1, 0, 0);
        glTexCoord2f(0.5f, 0.5f);
        glVertex3f(0.5f, 0.25f, 0.5f);
        glEnd();

    }

    public void drawLowSlope(int d) {
        tile.bind();
        glBegin(GL_QUADS);
        // glColor3f(1f,1f,1f);
        if (d == Terrain.DIRECTION_TOP) {
            glNormal3f(0f, 2f, 1f);
        } else if (d == Terrain.DIRECTION_LEFT) {
            glNormal3f(1f, 2f, 0f);
        } else if (d == Terrain.DIRECTION_BOTTOM) {
            glNormal3f(0f, 2f, -1f);
        } else if (d == Terrain.DIRECTION_RIGHT) {
            glNormal3f(-1f, 2f, 0f);
        }
        glTexCoord2f(0, 0);
        glVertex3f(0, ((d == Terrain.DIRECTION_TOP || d == Terrain.DIRECTION_LEFT) ? 0.5f : 0f), 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, ((d == Terrain.DIRECTION_LEFT || d == Terrain.DIRECTION_BOTTOM) ? 0.5f : 0f), 1);
        glTexCoord2f(1, 1);
        glVertex3f(1, ((d == Terrain.DIRECTION_BOTTOM || d == Terrain.DIRECTION_RIGHT) ? 0.5f : 0f), 1);
        glTexCoord2f(1, 0);
        glVertex3f(1, ((d == Terrain.DIRECTION_RIGHT || d == Terrain.DIRECTION_TOP) ? 0.5f : 0f), 0);
        glEnd();
        // if(d==Terrain.DIRECTION_TOP)drawSingleWall(h+1,1,0,-1,2);
    }

    public void drawHighSlope(int d) {
        tile.bind();
        glBegin(GL_QUADS);
        // glColor3f(1f,1f,1f);
        if (d == Terrain.DIRECTION_TOP) {
            glNormal3f(1f, 1f, 1f);
        } else if (d == Terrain.DIRECTION_LEFT) {
            glNormal3f(1f, 1f, -1f);
        } else if (d == Terrain.DIRECTION_BOTTOM) {
            glNormal3f(-1f, 1f, -1f);
        } else if (d == Terrain.DIRECTION_RIGHT) {
            glNormal3f(-1f, 1f, 1f);
        }
        glTexCoord2f(0, 0);
        glVertex3f(0, ((d == Terrain.DIRECTION_TOP) ? 1f : (d == Terrain.DIRECTION_RIGHT || d == Terrain.DIRECTION_LEFT) ? 0.5f : 0f), 0);
        glTexCoord2f(0, 1);
        glVertex3f(0, ((d == Terrain.DIRECTION_LEFT) ? 1f : (d == Terrain.DIRECTION_TOP || d == Terrain.DIRECTION_BOTTOM) ? 0.5f : 0f), 1);
        glTexCoord2f(1, 1);
        glVertex3f(1, ((d == Terrain.DIRECTION_BOTTOM) ? 1f : (d == Terrain.DIRECTION_LEFT || d == Terrain.DIRECTION_RIGHT) ? 0.5f : 0f), 1);
        glTexCoord2f(1, 0);
        glVertex3f(1, ((d == Terrain.DIRECTION_RIGHT) ? 1f : (d == Terrain.DIRECTION_BOTTOM || d == Terrain.DIRECTION_TOP) ? 0.5f : 0f), 0);

        glEnd();
        // if(d==Terrain.DIRECTION_TOP)drawSingleWall(h+1,1,0,-1,2);
    }

    public void drawSingleSlope(int d) {
        tile.bind();
        glBegin(GL_TRIANGLES);
        // glColor3f(1f,1f,1f);
        switch (d) {
        case Terrain.DIRECTION_TOP:
            glNormal3f(1f, 1f, 1f);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0.5f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glNormal3f(0f, 1f, 0f);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            break;
        case Terrain.DIRECTION_LEFT:
            glNormal3f(1f, 1f, -1f);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0.5f, 1);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glNormal3f(0f, 1f, 0f);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            break;
        case Terrain.DIRECTION_BOTTOM:
            glNormal3f(-1f, 1f, -1f);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0.5f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glNormal3f(0f, 1f, 0f);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            break;
        case Terrain.DIRECTION_RIGHT:
            glNormal3f(-1f, 1f, 1f);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0.5f, 0);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            glNormal3f(0f, 1f, 0f);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            break;
        }
        glEnd();
        // if(d==Terrain.DIRECTION_TOP)drawSingleWall(h+1,1,0,-1,2);
    }

    public void drawDoubleSlope(int d) {
        tile.bind();
        glBegin(GL_TRIANGLES);
        // glColor3f(1f,1f,1f);
        switch (d) {
        case Terrain.DIRECTION_TOP:
            glNormal3f(1f, 2f, 0f);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0.5f, 0);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glNormal3f(0f, 2f, 1f);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0.5f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            break;
        case Terrain.DIRECTION_BOTTOM:
            glNormal3f(0f, 2f, -1f);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0.5f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glNormal3f(-1f, 2f, 0f);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0.5f, 1);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            break;
        case Terrain.DIRECTION_RIGHT:
            glNormal3f(-1f, 2f, 0f);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0.5f, 0);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glNormal3f(0f, 2f, 1f);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0.5f, 0);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0f, 1);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            break;
        case Terrain.DIRECTION_LEFT:
            glNormal3f(0f, 2f, -1f);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0.5f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            glTexCoord2f(0, 0);
            glVertex3f(0, 0f, 0);
            glNormal3f(1f, 2f, 0f);
            glTexCoord2f(0, 1);
            glVertex3f(0, 0.5f, 1);
            glTexCoord2f(1, 1);
            glVertex3f(1, 0f, 1);
            glTexCoord2f(1, 0);
            glVertex3f(1, 0f, 0);
            break;
        }
        glEnd();
    }

    public void drawPlayerObject() {
        TextureImpl.bindNone();
        // glColor3f(0.2f,0.2f,0.2f);
        new Sphere().draw(0.2f, 20, 20);
        /*
         * drawDummyPlayerObject(); glRotatef(45f,0,1,0);
         * drawDummyPlayerObject();
         */
    }

    public void drawDummyPlayerObject() {
        float r = 0.2f;
        TextureImpl.bindNone();
        glBegin(GL_TRIANGLES);

        glColor3f(0.2f, 0.2f, 0.2f);
        glVertex3f(0, r, 0);
        glVertex3f(0, 0, -r);
        glVertex3f(r, 0, 0);

        glColor3f(1f, 0.2f, 0.2f);
        glVertex3f(0, r, 0);
        glVertex3f(r, 0, 0);
        glVertex3f(0, 0, r);

        glColor3f(0.2f, 1f, 0.2f);
        glVertex3f(0, r, 0);
        glVertex3f(0, 0, r);
        glVertex3f(-r, 0, 0);

        glColor3f(1f, 1f, 0.2f);
        glVertex3f(0, r, 0);
        glVertex3f(-r, 0, 0);
        glVertex3f(0, 0, -r);

        glColor3f(0.2f, 0.2f, 1f);
        glVertex3f(0, -r, 0);
        glVertex3f(0, 0, -r);
        glVertex3f(r, 0, 0);

        glColor3f(1f, 0.2f, 1f);
        glVertex3f(0, -r, 0);
        glVertex3f(r, 0, 0);
        glVertex3f(0, 0, r);

        glColor3f(1f, 1f, 1f);
        glVertex3f(0, -r, 0);
        glVertex3f(0, 0, r);
        glVertex3f(-r, 0, 0);

        glColor3f(0.2f, 1f, 1f);
        glVertex3f(0, -r, 0);
        glVertex3f(-r, 0, 0);
        glVertex3f(0, 0, -r);
        glEnd();
    }

    public void drawShadow() {
        TextureImpl.bindNone();
        float r = 0.15f;
        float h = 0.001f;
        glBegin(GL_QUADS);
        // glColor3f(0f,0f,0f);
        glVertex3f(-r, h, -r);
        glVertex3f(-r, h, r);
        glVertex3f(r, h, r);
        glVertex3f(r, h, -r);
        glEnd();
    }

    public void drawPredictedPath(Transform[] predict, int frame) {
        for (int i = 0; i < predict.length; i++) {
            if (i % 6 != frame) {
                continue;
            }
            glPushMatrix();
            Transform t = predict[i];
            glTranslatef(t.origin.x, t.origin.y - 0.2f, t.origin.z);
            /*
             * glBegin(GL_QUADS); TextureImpl.bindNone(); glColor3f(1f,0f,0f);
             * glVertex3f(0,0f,0); glVertex3f(0,0.1f,0);
             * glVertex3f(0.1f,0.1f,0); glVertex3f(0.1f,0,0); glEnd();
             */
            new Sphere().draw(0.05f, 4, 4);
            glPopMatrix();
            glPushMatrix();
            /*
             * glTranslatef(t.origin.x,0.501f,t.origin.z); glBegin(GL_QUADS);
             * TextureImpl.bindNone(); glColor3f(0f,0f,0f);
             * glVertex3f(-0.05f,0,-0.05f); glVertex3f(-0.05f,0,0.05f);
             * glVertex3f(0.05f,0,0.05f); glVertex3f(0.05f,0,-0.05f); glEnd();
             */
            glPopMatrix();
        }
    }

    public void init3D() {
        // glShadeModel(GL_SMOOTH); //今のところいらない
        glClearColor(0.0f, 0.0f, 0.0f, 1f);
        glClearDepth(1.0f);

        glDepthFunc(GL_LEQUAL);

        updateCamera();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_NORMALIZE);

        float ambient0 = 0.9f;
        float diffuse0 = 1f;
        float specular0 = 0.5f;
        glLight(GL_LIGHT0, GL_POSITION, FloatBuffer.wrap(new float[] {-1, 2, 0, 0 }));
        glLight(GL_LIGHT0, GL_AMBIENT, FloatBuffer.wrap(new float[] {ambient0, ambient0, ambient0, ambient0 }));
        glLight(GL_LIGHT0, GL_DIFFUSE, FloatBuffer.wrap(new float[] {diffuse0, diffuse0, diffuse0, diffuse0 }));
        glLight(GL_LIGHT0, GL_SPECULAR, FloatBuffer.wrap(new float[] {specular0, specular0, specular0, specular0 }));

        float ambient1 = 0.9f;
        float diffuse1 = 1f;
        float specular1 = 0.5f;
        glLight(GL_LIGHT1, GL_POSITION, FloatBuffer.wrap(new float[] {1, 0, 1.5f, 0 }));
        glLight(GL_LIGHT1, GL_AMBIENT, FloatBuffer.wrap(new float[] {ambient1, ambient1, ambient1, ambient1 }));
        glLight(GL_LIGHT1, GL_DIFFUSE, FloatBuffer.wrap(new float[] {diffuse1, diffuse1, diffuse1, diffuse1 }));
        glLight(GL_LIGHT1, GL_SPECULAR, FloatBuffer.wrap(new float[] {specular1, specular1, specular1, specular1 }));

        /*
         * float gold_amb[] = { 0.5f,0.5f,0.5f,1f }; float gold_diff[] = {
         * 0.5f,0.5f,0.5f,1f }; float gold_spe[] = { 0.5f,0.5f,0.5f,1f }; float
         * gold_shin[] = { 100.0f,100.0f,100.0f,100.0f };
         * 
         * glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT,
         * FloatBuffer.wrap(gold_amb)); glMaterial(GL_FRONT_AND_BACK,
         * GL_DIFFUSE, FloatBuffer.wrap(gold_diff));
         * glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR,
         * FloatBuffer.wrap(gold_spe)); glMaterial(GL_FRONT_AND_BACK,
         * GL_SHININESS, FloatBuffer.wrap(gold_shin));
         */
    }

    public void view2D() {
        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, displayWidth, displayHeight, 0, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
    }

    public void view3D() {
        glEnable(GL_LIGHTING);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();

        updateCamera();

        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();

        glLoadIdentity();

        // TextureImpl.bindNone(); //必要?
    }

    public void cameraLeft() {
        cameraPosX -= 0.07f;
        cameraPosZ += 0.07f;
    }

    public void cameraRight() {
        cameraPosX += 0.07f;
        cameraPosZ -= 0.07f;
    }

    public void cameraFront() {
        cameraPosX -= 0.07f;
        cameraPosZ -= 0.07f;
    }

    public void cameraBack() {
        cameraPosX += 0.07f;
        cameraPosZ += 0.07f;
    }

    public void cameraIn() {
        zoom += 0.05f;
    }

    public void cameraOut() {
        zoom -= 0.05f;
    }

    public void updateCamera() {
        final float h = (float) displayWidth / (float) (/* 50+ */displayHeight); // TODO:縦横比やっつけ調整
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-zoom * h, zoom * h, -zoom, zoom, -5, 30);
        if (camerapos == 0) {
            GLU.gluLookAt(10f + cameraPosX, 12f, 10f + cameraPosZ, 0.0f + cameraPosX, 0.0f, 0.0f + cameraPosZ, 0.0f, 1.0f, 0.0f);
        }
        if (camerapos == 1) {
            GLU.gluLookAt(10f + cameraPosX, 12f, -10f + cameraPosZ, 0.0f + cameraPosX, 0.0f, 0.0f + cameraPosZ, 0.0f, 1.0f, 0.0f);
        }
        if (camerapos == 2) {
            GLU.gluLookAt(-10f + cameraPosX, 12f, -10f + cameraPosZ, 0.0f + cameraPosX, 0.0f, 0.0f + cameraPosZ, 0.0f, 1.0f, 0.0f);
        }
        if (camerapos == 3) {
            GLU.gluLookAt(-10f + cameraPosX, 12f, 10f + cameraPosZ, 0.0f + cameraPosX, 0.0f, 0.0f + cameraPosZ, 0.0f, 1.0f, 0.0f);
        }

        // GLU.gluLookAt(0,2,0, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);//シャドウマッピング用
    }

    private int camerapos = 0;

    public void changeCameraPos() {
        camerapos = (camerapos + 1) % 4;
    }

    public float getCameraPosX() {
        return cameraPosX;
    }

    public void setCameraPosX(float cameraPosX) {
        this.cameraPosX = cameraPosX;
    }

    public float getCameraPosZ() {
        return cameraPosZ;
    }

    public void setCameraPosZ(float cameraPosZ) {
        this.cameraPosZ = cameraPosZ;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}

class Buffers {
    private FloatBuffer normal, vertex, texcoord;
    private IntBuffer index;
    public Buffers(FloatBuffer normal, FloatBuffer texcoord, FloatBuffer vertex, IntBuffer index) {
        super();
        this.normal = normal;
        this.texcoord = texcoord;
        this.vertex = vertex;
        this.index = index;
    }
    public FloatBuffer getNormal() {
        return normal;
    }
    public FloatBuffer getVertex() {
        return vertex;
    }
    public FloatBuffer getTexcoord() {
        return texcoord;
    }
    public IntBuffer getIndex() {
        return index;
    }
}
