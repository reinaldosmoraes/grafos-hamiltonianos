package hamiltoniancycle;

public class AcademyMethods {
    private static int[][] adjcentMatrix;
    private static int[] circuit, verticesDegree;
    private static int numberOfVertices;

    private AcademyMethods() {
        initializeAdjcentMatrix();
        numberOfVertices = adjcentMatrix.length;
        circuit = new int[numberOfVertices];
        verticesDegree = new int[numberOfVertices];
        circuit[0] = 0;
        countVerticesDegrees(adjcentMatrix);
    }

    private void initializeAdjcentMatrix() {
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
    }

    private void countVerticesDegrees(int[][] adjcentMatrix){
        for (int i =0; i<numberOfVertices ;i++){
            int count = 0;
            for (int j =0; j < numberOfVertices ; j++){
                count += adjcentMatrix[i][j];
            }
            verticesDegree[i] = count;
        }
    }

    private boolean oreMethod(int[][] adjcentMatrix){
        if(numberOfVertices < 3) return false;
        for (int i = 0; i < numberOfVertices ; i++){
            for (int j = 0; j < numberOfVertices ; j++) {
                if (adjcentMatrix[i][j] == 0 && i != j && verticesDegree[i] + verticesDegree[j] < numberOfVertices) return false;
            }
        }
        return true;
    }

    private boolean diracMethod(int[] verticesDegree, int numberOfVertices){
        if(numberOfVertices < 3) return false;
        double half = numberOfVertices / 2.0;
        for (Integer i: verticesDegree) {
            if(i < half)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        AcademyMethods academyMethods = new AcademyMethods();
        System.out.println("Ore`s Method: " + academyMethods.oreMethod(adjcentMatrix));
        System.out.println("Dirac`s Method: " + academyMethods.diracMethod(verticesDegree, numberOfVertices));
    }

}
