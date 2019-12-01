package hamiltoniancycle;

import static java.lang.Integer.min;

public class AcademyMethods {
    private static int[][] adjcentMatrix;
    private static int[] circuit, verticesDegree;
    private static int numberOfVertices, time;
    private boolean[] vis;
    private int[] discoveredNodes, lowerNodes, parents;

    private AcademyMethods() {
        initializeAdjcentMatrix();
        numberOfVertices = adjcentMatrix.length;
        circuit = new int[numberOfVertices];
        verticesDegree = new int[numberOfVertices];
        vis = new boolean[numberOfVertices];
        discoveredNodes = new int[numberOfVertices];
        lowerNodes = new int[numberOfVertices];
        parents = new int[numberOfVertices];
        circuit[0] = 0;
        countVerticesDegrees();
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

//        Complete graph example
//        adjcentMatrix = new int[][]{
//                {0,1,1,1},
//                {1,0,1,1},
//                {1,1,0,1},
//                {1,1,1,0}
//        };

        //Disconex graph exmaple
//        adjcentMatrix = new int[][]{
//                {0,1,0,1},
//                {1,0,0,1},
//                {0,0,0,0},
//                {1,1,0,0}
//        };
    }

    private void countVerticesDegrees() {
        for (int i = 0; i < numberOfVertices; i++){
            int count = 0;
            for (int j = 0; j < numberOfVertices ; j++){
                count += adjcentMatrix[i][j];
            }
            verticesDegree[i] = count;
        }
    }

    private boolean oreMethod() {
        if(numberOfVertices < 3) return false;
        for (int i = 0; i < numberOfVertices ; i++){
            for (int j = 0; j < numberOfVertices ; j++) {
                if (adjcentMatrix[i][j] == 0 && i != j && verticesDegree[i] + verticesDegree[j] < numberOfVertices) return false;
            }
        }
        return true;
    }

    private boolean diracMethod() {
        if(numberOfVertices < 3) return false;
        double half = numberOfVertices / 2.0;
        for (Integer i: verticesDegree) {
            if(i < half)
                return false;
        }
        return true;
    }

    private boolean completeGraph() {
        for (int i = 0; i < numberOfVertices ; i++){
            for (int j = 0; j < numberOfVertices ; j++) {
                if (adjcentMatrix[i][j] == 0 && i != j) return false;
            }
        }
        return true;
    }

    private boolean conexGraph() {
        boolean verticeHasEdge;
        boolean isConex = true;
        for (int i = 0; i < numberOfVertices ; i++){
            verticeHasEdge = false;
            for (int j = 0; j < numberOfVertices ; j++) {
                if (adjcentMatrix[i][j] > 0 && i != j) {
                    verticeHasEdge = true;
                    break;
                }
            }
            isConex = isConex && verticeHasEdge;
        }
        return isConex;
    }

    public boolean biconectedMethod(){

        for(int i = 0; i < numberOfVertices; i++) {
            vis[i] = false; parents[i] = -1;
        }

        if(findArticulationPointMethod(0))  return false;  //No articulation Point
        for(int i = 0; i < numberOfVertices; i++)   if (!vis[i]) return false;    //Exists nodes not visited

        return true;
    }

    public boolean findArticulationPointMethod(int start){
        int dfsChild = 0;
        time = 0;
        vis[start] = true;
        discoveredNodes[start] = lowerNodes[start] = time++;

        for(int v = 0; v < numberOfVertices; v++) {
            boolean check = (adjcentMatrix[start][v] == 1)?true:false;
            if(check){
                if(!vis[v]) {
                    dfsChild++; parents[v] = start;
                    if(findArticulationPointMethod(v))  return true;
                    lowerNodes[start] = min(lowerNodes[start], lowerNodes[v]);
                    if(parents[start] == -1 && dfsChild > 1) return true;
                } else if(v != parents[start])   lowerNodes[start] = min(lowerNodes[start], discoveredNodes[v]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        AcademyMethods academyMethods = new AcademyMethods();
        System.out.println("Conex Graph: " + academyMethods.conexGraph());
        System.out.println("Biconnected Graph: " + academyMethods.biconectedMethod());
        System.out.println("Ore`s Method: " + academyMethods.oreMethod());
        System.out.println("Dirac`s Method: " + academyMethods.diracMethod());
        System.out.println("Complete Graph: " + academyMethods.completeGraph());
    }

}
