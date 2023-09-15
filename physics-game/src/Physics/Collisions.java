package Physics;
//A class to do physics math
//For nathan seeeeebeeee to do do :)

//make functions that accept two velocities, masses, and:
//-return velocity for a perfectly elastic collision
//-return velocity a perfectly inelastic collision
//-return velocity for an elastic collsision that disappates a percentage of energy
//-given a certain amount of force from these situtations, return the time (impulse equation)
//-and given a certain amount of time, return the force
//etc, do research

public class Collisions {

    public static double momentum(double v1,double m1 ){
        return v1*m1;
    }

    public static double KE(double m1, double v1){
        return m1*v1*v1/2;
    }

    public static double leftElasticVelocity(double v1, double m1, double v2, double m2, double v2f) {
        return ((m1 - m2)/(m1 + m2))*v1 + ((2*m2)/(m1+m2))*v2;
    }

    public static double rightElasticVelocity(double v1, double m1, double v2, double m2, double v1f) {
        return ((m2 - m1)/(m1 + m2))*v2 + ((2*m1)/(m1+m2))*v1;
    }

    public static double stickyVelocity(double v1, double v2, double m1, double m2) {
        return (m1*v1 + m2*v2)/(m1+m2);
    }

}
