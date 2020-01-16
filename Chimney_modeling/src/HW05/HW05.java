//607238002 2019_5_8
/////Given/////
/*1.the height of the chimney
  2.the discharging rate of the pollutants
  3.the wind speed and direction. In is version, the wind direction is N45E.
  4.the size of grid*/
/////Goal/////
/*1.calculate the concentration at each point in the 3-D area
  2.find the position where the maximum concentration exists*/
/////Constraints/////
/*1.the position of each point should be stored in three 3-D array
  2.scan the grids in the sequence (x-->y-->z)
  !!!!!3.write a class that contains the position, height, discharge rate of the chimney*/

package HW05;
public class HW05 {
    public static void main(String[] args) {
        int x=50000,y=50000,z=1000,gridX,gridY,gridZ,i=0,j=0,k=0;
        gridX=(x/1000)+1;//number of the grids in X-axis
        gridY=(y/1000)+1;//number of the grids in Y-axis
        gridZ=(z/25)+1;//number of the grids in Z-axis
        int[][][] X= new int[gridZ][gridY][gridX];
        int[][][] Y= new int[gridZ][gridY][gridX];
        int[][][] Z= new int[gridZ][gridY][gridX];
        double[][][] C= new double[gridZ][gridY][gridX];
        arrayInitializer(gridX,gridY,gridZ,X,Y,Z);//set the (x,y,z) at each point
        Chimney chi = new Chimney(25000.0, 25000.0, 20.0, 20.0);
        G_model(gridX,gridY,gridZ,X,Y,Z,C,chi);//calculate concentration in all the grids
        maxConcentration(gridX,gridY,gridZ,C);//find the position where the maximum concentration exists
    }

    public static void arrayInitializer(int gridX, int gridY, int gridZ, int[][][] X, int[][][] Y, int[][][] Z){
        int i,j,k,temp;
        //////////X-axis assignment//////////
        for(k=0;k<gridZ;k++){
            for(j=0;j<gridY;j++){
                temp=0;
                for(i=0;i<gridX;i++){//Z[k][j][0] is 0. We don't need to assign a value to it.
                    X[k][j][i]=temp;
                    temp+=1000;
                }
            }
        }
        //////////Y-axis assignment//////////
        for(k=0;k<gridZ;k++){
            temp=0;
            for(j=1;j<gridY;j++){//Y[k][0][i] is 0. We don't need to assign a value to it.
                temp+=1000;
                for(i=0;i<gridX;i++){
                    Y[k][j][i]=temp;
                }
            }
        }
        //////////Z-axis assignment//////////
        temp=0;
        for(k=1;k<gridZ;k++){//Z[0][j][i] is 0. We don't need to assign a value to it.
            temp+=25;
            for(j=0;j<gridY;j++){
                for(i=0;i<gridX;i++){
                    Z[k][j][i]=temp;
                }
            }
        }
    }

    public static void G_model(int gridX, int gridY, int gridZ, int[][][] X, int[][][] Y, int[][][] Z, double[][][] C, Chimney chi){
        double xNew, yNew, zNew, sigmaY, sigmaZ, Concentration, theta;
        int u=3, i, j, k=0;//u=wind speed
        //////////calculate concentration//////////
        while(k<gridZ){
            j=0;//j should be reset in each loop
            while(j<gridY){
                i=0;//i should be reset in each loop
                while(i<gridX){
                    //////////new_axis//////////
                    theta=Math.PI/180*45;//wind direction in this homework is set N45E
                    xNew=(-1)*Math.sin(theta)*(X[k][j][i]-chi.getX())+(-1)*Math.cos(theta)*(Y[k][j][i]-chi.getY());
                    yNew=Math.cos(theta)*(X[k][j][i]-chi.getX())+(-1)*Math.sin(theta)*(Y[k][j][i]-chi.getY());
                    zNew=Z[k][j][i];
                    //////////The downwind points should be calculated.//////////
                    if(xNew > 0){
                        sigmaY=0.22*xNew*Math.pow((1+0.0001*xNew), -0.5);//sigmaY:the deviation of the pollutants in the y-axis
                        sigmaZ=0.001*xNew;//sigmaZ the deviation of the pollutants in the z-axis
                        Concentration=(chi.getQ()/(2*Math.PI*(double)u*sigmaY*sigmaZ))*
                                Math.exp(-Math.pow(yNew, 2)/(2*Math.pow(sigmaY, 2)))*
                                (Math.exp(-Math.pow((zNew-chi.getH()), 2)/(2*Math.pow(sigmaZ, 2)))
                                        +Math.exp(-Math.pow((zNew+chi.getH()), 2)/(2*Math.pow(sigmaZ, 2))));
                        C[k][j][i]=Concentration;
                        //System.out.println("("+X[k][j][i] +","+Y[k][j][i] +","+Z[k][j][i] +")=>" +"("+xNew +","+yNew +","+zNew +")=" +Concentration);
                    }
                    i++;
                }
                j++;
            }
            k++;
        }
    }

    public static void maxConcentration(int gridX, int gridY, int gridZ, double[][][] C){
        int i,j,k,iMax=0,jMax=0,kMax=0,xMax,yMax,zMax;
        double temp,difference;
        //////////find maximum concentration//////////
        temp=C[0][0][0];
        for(k=0;k<gridZ;k++){
            for(j=0;j<gridY;j++){
                for(i=0;i<gridX;i++){
                    difference=C[k][j][i]-temp;
                    if(difference>0){
                        temp=C[k][j][i];
                        iMax=i;
                        jMax=j;
                        kMax=k;
                    }
                }
            }
        }
        //////////the position of maximum concentration//////////
        xMax=iMax*1000;
        yMax=jMax*1000;
        zMax=kMax*25;
        System.out.println("The maximum concentration of pollutants occurs at ("+xMax +","+yMax +","+zMax +") where the concentration is "+temp +"." );
    }
}
class Chimney {
    private double X, Y, H, Q;
    Chimney (){
        X=0.0;
        Y=0.0;
        H=0.0;
        Q=0.0;
    }
    Chimney (double inputX, double inputY, double inputH, double inputQ){
        X=inputX;
        Y=inputY;
        H=inputH;
        Q=inputQ;
    }
    public void setX (double inputX){
        X=inputX;
    }
    public double getX (){
        return X;
    }
    public void setY (double inputY){
        Y=inputY;
    }
    public double getY (){
        return Y;
    }
    public void setH (double inputH){
        H=inputH;
    }
    public double getH (){
        return H;
    }
    public void setQ (double inputQ){
        Q=inputQ;
    }
    public double getQ (){
        return Q;
    }
}