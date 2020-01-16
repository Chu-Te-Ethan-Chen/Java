//607238002 2019_3_19
package HW02;
import java.util.Scanner;
public class HW02 {
    public static void main(String[] args) {
        int i, interval;//i:counter;
        double start, end, segment, concentration;//segment:unit length
        Scanner scanner= new Scanner(System.in);
        while(true){
            System.out.println("Enter the starting point");
            start= scanner.nextDouble();
            System.out.println("Enter the end point");
            end= scanner.nextDouble();
            if (start<end) {
                break;
            }
            System.out.println("Error! Enter the value again.\n");
        }
        System.out.println("Enter the interval you want");
        interval=scanner.nextInt();
        segment=(end-start)/(double)interval;//there will be n segments when a line is divided by n interval
        double[] arrayPositions=new double[interval+1];//arrayPositions stores the Positions that should be calculated
        for(i=0;i<=interval;i++){
            arrayPositions[i]=start+segment*i;
        }
        for(double e: arrayPositions){//to calculate every position
            concentration=G_model(e);
            System.out.println("The concentration of pollutants at "+e +" is "+concentration);
        }
    }
    public static double G_model(double x){
        double sigmaY, sigmaZ, C;//C: the concentration of the pollutants
        int H=20, Q=20, u=3, y=0, z=0;//H:the height of the chimney; Q:pollutants discharge rate; u=wind speed
        sigmaY=0.22*x*Math.pow((1+0.0001*x), -0.5);//sigmaY:the deviation of the pollutants in the y-axis
        sigmaZ=0.001*x;//sigmaZ the deviation of the pollutants in the z-axis
        C=((double)Q/(2*Math.PI*(double)u*sigmaY*sigmaZ))*
                Math.exp(-Math.pow(y, 2)/(2*Math.pow(sigmaY, 2)))*
                (Math.exp(-Math.pow((z-H), 2)/(2*Math.pow(sigmaZ, 2)))+
                        Math.exp(-Math.pow((z+H), 2)/(2*Math.pow(sigmaZ, 2))));
        return C;
        }
}