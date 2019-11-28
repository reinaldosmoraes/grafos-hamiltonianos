package hamiltoniancycle;

public class AcademyMethods {
    private int[][] adjcentMatrix;
    private int[] circuit, degree;
    private int numberOfVertices;

    private AcademyMethods() {
        adjcentMatrix = new int[][]{
        //       0 1 2 3 4 5 6 7
                {0,1,1,0,0,0,1,0},// 0
                {1,0,1,0,0,0,0,1},// 1
                {1,1,0,1,0,1,0,0},// 2
                {0,0,1,0,1,0,0,0},// 3
                {0,0,0,1,0,1,0,0},// 4
                {0,0,1,0,1,0,1,0},// 5
                {1,0,0,0,0,1,0,1},// 6
                {0,1,0,0,0,0,1,0} // 7
        };
        numberOfVertices = adjcentMatrix.length;
        circuit = new int[numberOfVertices];
        degree = new int[numberOfVertices];
        circuit[0] = 0;
        countVerticesDegrees(adjcentMatrix);
        System.out.println(oreMethod(adjcentMatrix));
        diracMethod();
    }

    private void countVerticesDegrees(int[][] m){
        for (int i =0; i<numberOfVertices ;i++){
            int count = 0;
            for (int j =0; j < numberOfVertices ; j++){
                count += m[i][j];
            }
            degree[i] = count;
        }
        //printDegree();
    }

    private boolean oreMethod(int[][] m){
        if(numberOfVertices < 3) return false;
        for (int i =0; i < numberOfVertices ;i++){
            for (int j =0; j < numberOfVertices ; j++) {
                if (m[i][j] == 0 && i != j && degree[i] + degree[j] < numberOfVertices) return false;
            }
        }
        return true;
    }

    private boolean diracMethod(){
        if(numberOfVertices < 3) return false;
        double half = numberOfVertices/2;
        for (Integer i: degree) {
            if(i < half) return false;
        }
        return true;
    }

    private void printDegree(){
        for (Integer i: degree
        ) {
            System.out.print(i.toString() + ' ');
        }
    }

    public static void main(String[] args) {
        new AcademyMethods();
    }
}
