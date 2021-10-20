/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISParticleFilter;

/**
 *
 * @author seanh
 */
public class Particle {
    private float x;    //x pos of particle
    private float y;    //y pos of particle
    private float yew;  //angle the particle is facing in degrees
    private float weight = 1f;
    
    public Particle(float x, float y, float yew, float minWorldX, float minWorldY, float maxWorldX, float maxWorldY) {
        this.x = x;
        this.y = y;
        this.yew = yew;
    }
    
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    
}
