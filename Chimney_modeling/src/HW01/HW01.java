package HW01;
import java.util.Scanner;
public class HW01 {//calculate the concentration of the smog in the x direction with Gaussian Plume Model
    public static void main(String[] args){
        double x, C, sigma_y, sigma_z;
        //x:the distance in the x-axis; C:concentration of the pollutants
        //sigma_y:deviation of the pollutants in the y-axis
        //sigma_z:deviation of the pollutants in the z-axis
        int H=20, Q=20, u=3, y=0, z=0;//H:the height of the chimney; Q:pollutants discharge rate; u=wind speed
        Scanner input= new Scanner(System.in);
        System.out.println("Enter the distance");
        x=input.nextDouble();
        sigma_y=0.22*x*Math.pow((1+0.0001*x), -0.5);
        sigma_z=0.001*x;
        C=(Q/(2*Math.PI*u*sigma_y*sigma_z))*
                Math.exp(-Math.pow(y, 2)/(2*Math.pow(sigma_y, 2)))*
                (Math.exp(-Math.pow((z-H), 2)/(2*Math.pow(sigma_z, 2)))+
                        Math.exp(-Math.pow((z+H), 2)/(2*Math.pow(sigma_z, 2))));
        System.out.println("C="+C);
    }
}
