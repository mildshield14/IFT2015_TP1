import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class GestionCamion {

        private static ArrayList<int> arrayBox;
        ArrayList<Coordinates> pairList = new ArrayList<>();

        private int contentTruck;


    public void setPairList(ArrayList<Coordinates> pairList) {
        this.pairList = pairList;
    }

    public void setContentTruck(int contentTruck) {
        this.contentTruck = contentTruck;
    }

    public void setArrayBox(ArrayList<int> arrayBox) {
        this.arrayBox = arrayBox;
    }

    public static ArrayList<int> getArrayBox() {
        return arrayBox;
    }


    public ArrayList<Coordinates> getPairList() {
        return pairList;
    }

    public int getContentTruck() {
        return contentTruck;
    }

    public static void main(String[] args) {

        int maxBoxIndex;
        int maxBox;

        Coordinates firstPos;

        GestionCamion capacity = null;
        capacity.setContentTruck(15);
        
        GestionCamion boitesEntrepots = null;
        ArrayList<int> box = new ArrayList();
        box.add(8);
        box.add(4);
        box.add(6);

        ArrayList<Coordinates> positions = new ArrayList();
        Coordinates ob1 = new Coordinates(1,2);
        Coordinates ob2 = new Coordinates(3,5);
        Coordinates ob3 = new Coordinates(8,1);
        positions.add(ob1);
        positions.add(ob2);
        positions.add(ob3);

        GestionCamion pos = null;
        pos.setPairList(positions);

        boitesEntrepots.setArrayBox(box);
        
        maxBoxIndex = maxmin("max",boitesEntrepots.getArrayBox());
        maxBox = boitesEntrepots.getArrayBox().get(maxBoxIndex);

        firstPos = pos.getPairList().get(maxBoxIndex);
        pos.getPairList().remove(maxBoxIndex);

        capacity.setContentTruck(capacity.getContentTruck() - (pos.getArrayBox().get(maxBoxIndex)));

        // output (pos initial)
        int i=0;
        for (i=0; i<pos.getPairList().size(); i++){
            firstPos = calculation(pos, firstPos, pos.getPairList(), boitesEntrepots.getArrayBox(), capacity.getContentTruck());
        }



}

public static Coordinates calculation(GestionCamion ob, Coordinates firstPos, ArrayList<Coordinates> positions, ArrayList<int> boxx, int capacity ){
        int dist =0;
        int minDistIndex=0;

        Queue<int> queue = new LinkedList<int>();

        ArrayList<int> tableau = new ArrayList();

        for (int i=0; i<positions.size(); i++){
            dist = calculeDistance(firstPos, positions.get(i));
            tableau.add(dist);
        }

        for (int j=0; j<tableau.size();j++){
            minDistIndex = maxmin("min",tableau);

            if (j==0){

                while (capacity>=0 || boxx.get(minDistIndex) >= 0){
                    capacity= capacity-1;
                    boxx.set(minDistIndex, boxx.get(minDistIndex)-1);

                }
                // output everything boxx.get(minDistIndex)
                // pos.get(minDistIndex)
                // tableau.get(minDistIndex)

            }


            queue.add(minDistIndex);


    }
        firstPos= positions.get(queue.remove());

        ob.getArrayBox().remove(minDistIndex);
        ob.getPairList().remove(minDistIndex);

        return firstPos;
}

    }


