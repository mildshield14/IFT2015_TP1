import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math.*;
public class GestionCamion {

        private static ArrayList<Integer> arrayBox;
        private static Queue<Double> arrayDist;
        private static ArrayList<Coordinates> pairList = new ArrayList<>();
        private int contentTruck;


    public void setPairList(ArrayList<Coordinates> pairList) {
        this.pairList = pairList;
    }

    public void setContentTruck(int contentTruck) {
        this.contentTruck = contentTruck;
    }

    public  void setArrayBox(ArrayList<Integer> arrayBox) {
        GestionCamion.arrayBox = arrayBox;
    }

    public static ArrayList<Integer> getArrayBox() {
        return arrayBox;
    }


    public ArrayList<Coordinates> getPairList() {
        return pairList;
    }

    public static Queue<Double> getArrayDist() {
        return arrayDist;
    }

    public int getContentTruck() {
        return contentTruck;
    }

    public static void setArrayDist(Queue<Double> arrayDist) {
        GestionCamion.arrayDist = arrayDist;
    }

    public static void main(String[] args) {

        int maxBoxIndex =0;
        int maxBox=0;

        GestionCamion c1 = new GestionCamion();
        c1.setContentTruck(15);

        ArrayList<Integer> box = new ArrayList();
        box.add(4);
        box.add(8);
        box.add(2);
        box.add(5);
        c1.setArrayBox(box);

        ArrayList<Coordinates> positions = new ArrayList();
        Coordinates ob1 = new Coordinates(-73.714,45.4977);
        Coordinates ob2 = new Coordinates(-73.8205,45.4383);
        Coordinates ob3 = new Coordinates(-73.561996,45.515399);
        Coordinates ob4 = new Coordinates(-73.5682,45.5092);
        positions.add(ob1);
        positions.add(ob2);
        positions.add(ob3);
        positions.add(ob4);
        c1.setPairList(positions);


        maxBoxIndex = maxmin("max",c1.getArrayBox());
        maxBox = c1.getArrayBox().get(maxBoxIndex);


        Coordinates firstPos = c1.getPairList().get(maxBoxIndex);

        c1.getPairList().remove(maxBoxIndex);
        System.out.println("Truck position: " +"(" + firstPos.getLatitude() + "," + firstPos.getLongitude() + ")");
        if (c1.getArrayBox().get(maxBoxIndex) < c1.getContentTruck()) {

            System.out.println("Distance:0" + "\t" + "Number of boxes:0" + "\t" + "Position:" + "(" + firstPos.getLatitude() + "," + firstPos.getLongitude() + ")");

            c1.setContentTruck(c1.getContentTruck() - (c1.getArrayBox().get(maxBoxIndex)));

            c1.getArrayBox().remove(maxBoxIndex);

            // output (pos initial)
            int i = 0;

            ArrayList<Double> tableau = new ArrayList();
            double dist = 0;

            for (int j = 0; j < c1.getPairList().size(); j++) {
                dist = calculeDistance(firstPos, c1.getPairList().get(j));
                tableau.add(dist);
            }

            int lengthArrBox = c1.getArrayBox().size();
            Queue<Double> queue = new LinkedList<Double>();
            for (int k = 0; k < lengthArrBox; k++) {
                int minDistIndex = maxmind("min", tableau);


                queue.add(tableau.get(minDistIndex));
                tableau.remove(minDistIndex);




               // int temp1 = c1.getArrayBox().get(k);
              //  Coordinates temp2 = c1.getPairList().get(k);

                ArrayList<Integer> t1;
                ArrayList<Coordinates> t2 = null;

                t1=c1.getArrayBox();
                t2=c1.getPairList();

                int temp1 = t1.get(k);
                Coordinates temp2 = t2.get(k);

                t1.set(k,getArrayBox().get((minDistIndex+k)));
                t2.set(k, c1.getPairList().get(minDistIndex+k));

               // c1.getArrayBox().set(k, c1.getArrayBox().get(minDistIndex));  // TO-DO /maybe (create an arraylist and assing)
              //  c1.getPairList().set(k, c1.getPairList().get(minDistIndex));

t1.set(minDistIndex+k, temp1);
t2.set(minDistIndex+k, temp2);

               // c1.getArrayBox().set(minDistIndex, temp1);
               // c1.getPairList().set(minDistIndex, temp2);

                c1.setPairList(t2);
                c1.setArrayBox(t1);
            }

                firstPos = c1.getPairList().get(0);
                c1.setArrayDist(queue);

                for (i = 0; i < c1.getPairList().size(); i++) {
                 //   if (i>0) {
                       // if ((c1.getArrayBox().get(i - 1)) - c1.getContentTruck() > 0) {
                            firstPos = calculation(c1, firstPos);
                       // }
                  //  }
                }


            } else if (c1.getArrayBox().get(maxBoxIndex) >= c1.getContentTruck()) {
                System.out.println("Distance:0" + "\t" + "Number of boxes:" + ((c1.getArrayBox().get(maxBoxIndex))-c1.getContentTruck()) + "\t" + "Position:" + "(" + positions.get(maxBoxIndex).getLatitude() + "," + positions.get(maxBoxIndex).getLongitude() + ")" );
            }

    }

    public static double calculeDistance(Coordinates posInit, Coordinates entrepot) {

        double latitudePos = Math.toRadians(posInit.getLatitude());
        double latitudeEntrepot = Math.toRadians(entrepot.getLatitude());
        double longitudePos = Math.toRadians(posInit.getLongitude());
        double longitudeEntrepot = Math.toRadians(entrepot.getLongitude());

        double phi=(latitudeEntrepot-latitudePos)/2;
        double lambda=(longitudeEntrepot-longitudePos)/2;

        double sincarre1= Math.sin(phi) * Math.sin(phi);

        double sincarre2= Math.sin(lambda) * Math.sin(lambda);
        double cossincarre2= Math.cos(latitudePos) * Math.cos(latitudeEntrepot) * sincarre2;
        double sincarre1ajoutcossincarre2 = sincarre1 + cossincarre2;

        double rootsincarre1ajoutcossincarre2=Math.sqrt(sincarre1ajoutcossincarre2);
        double distance= 2 * 6371000 * Math.asin(rootsincarre1ajoutcossincarre2);


       /*// double distance = (2*6371000)*Math.asin((Math.sqrt(Math.pow((latitudeEntrepot-latitudePos)/2,2)+
        //        Math.cos(latitudePos)*Math.cos(latitudeEntrepot)*(Math.pow(Math.sin(((longitudeEntrepot-longitudePos)/2)),2))
    )));*/

        double scale = Math.pow(10,1);
        return Math.round(distance*scale)/scale;
    }


    public static int maxmin(String what, ArrayList<Integer> tab){

        int comp=tab.get(0);
        int ind=0;
       // type returnVal = tab.get(0);
        for (int i=1; i<tab.size(); i++){

              if (what=="max"){
                  if (comp<tab.get(i)){
                      comp=tab.get(i);
                      ind=i;
                  }
            }else if (what=="min"){

                  if (comp>tab.get(i)){
                      comp=tab.get(i);
                      ind=i;
                  }

            }

        }

        return ind;

    }

    public static int maxmind(String what, ArrayList<Double> tab){

        double comp=tab.get(0);
        int ind=0;
        // type returnVal = tab.get(0);
        for (int i=1; i<tab.size(); i++){

            if (what=="max"){
                if (comp<tab.get(i)){
                    comp=tab.get(i);
                    ind=i;
                }
            }else if (what=="min"){

                if (comp>tab.get(i)){
                    comp=tab.get(i);
                    ind=i;
                }

            }

        }

        return ind;

    }

public static Coordinates calculation(GestionCamion ob, Coordinates firstPos ){

        int minDistIndex=0;




            double minDist=ob.getArrayDist().remove();
            
            int box= ob.getArrayBox().get(0);
            
            Coordinates pos = ob.getPairList().get(0);


                while (ob.getContentTruck() >0 && ob.getArrayBox().get(0) > 0){
                    ob.setContentTruck((ob.getContentTruck()-1));
                    ob.getArrayBox().set(0, ob.getArrayBox().get(0)-1);

                }


                System.out.println("Distance:" + minDist + "\t" + "Number of boxes:" + ob.getArrayBox().get(0) + "\t" + "Position:" + "(" + ob.getPairList().get(0).getLatitude() +"," + ob.getPairList().get(0).getLongitude() + ")");




        firstPos = ob.getPairList().get(0);

        if (ob.getArrayBox().size() > 0){
        ob.getArrayBox().remove(0);}

        if (ob.getPairList().size() > 0){
        ob.getPairList().remove(0);}

        return firstPos;
}

    }






