package hamiltoniancycle;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.min;

public class AcademyMethods {
    private static int[][] adjcentMatrix;
    private static int[] circuit, verticesDegree;
    private static int numberOfVertices;
    private boolean[] visited;
    private int[] discoveredNodes, lowerNodes, parents;
    private static Instant start = Instant.now();
    private static Instant end = Instant.now();
    private static Runtime runtime = Runtime.getRuntime();

    private AcademyMethods() {
        initializeAdjcentMatrix();
        numberOfVertices = adjcentMatrix.length;
        circuit = new int[numberOfVertices];
        verticesDegree = new int[numberOfVertices];
        visited = new boolean[numberOfVertices];
        discoveredNodes = new int[numberOfVertices];
        lowerNodes = new int[numberOfVertices];
        parents = new int[numberOfVertices];
        circuit[0] = 0;
        countVerticesDegrees();
    }

    private void initializeAdjcentMatrix() {
//        adjcentMatrix = new int[][]{
//        //       0 1 2 3 4 5 6 7
//                {0,1,1,0,0,0,1,0},// 0
//                {1,0,1,0,0,0,0,1},// 1
//                {1,1,0,1,0,1,0,0},// 2
//                {0,0,1,0,1,0,0,0},// 3
//                {0,0,0,1,0,1,0,0},// 4
//                {0,0,1,0,1,0,1,0},// 5
//                {1,0,0,0,0,1,0,1},// 6
//                {0,1,0,0,0,0,1,0} // 7
//        };

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

        //presentation exmaple
        adjcentMatrix = new int[][]{
                {0,1,1,1,1,1,1}, //0
                {1,0,1,1,0,1,0}, //1
                {1,1,0,0,0,0,0}, //2
                {0,1,0,0,1,1,0}, //3
                {1,0,0,1,0,0,0}, //4
                {1,1,0,1,0,1,1}, //5
                {1,0,0,0,0,5,0}, //6
        };
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

    private boolean biconectedMethod(){
        for(int i = 0; i < numberOfVertices; i++) {
            visited[i] = false; parents[i] = -1;
        }

        if(findArticulationPointMethod(0))  return false;  //No articulation Point

        for(int i = 0; i < numberOfVertices; i++) {
            if (!visited[i]) return false;    //Exists nodes not visited
        }
        return true;
    }

    private boolean findArticulationPointMethod(int start){
        int dfsChild = 0;
        int time = 0;
        visited[start] = true;
        discoveredNodes[start] = lowerNodes[start] = time++;

        for(int v = 0; v < numberOfVertices; v++) {
            boolean check = adjcentMatrix[start][v] == 1;
            if(check){
                if(!visited[v]) {
                    dfsChild++; parents[v] = start;
                    if(findArticulationPointMethod(v))  return true;
                    lowerNodes[start] = min(lowerNodes[start], lowerNodes[v]);
                    if(parents[start] == -1 && dfsChild > 1) return true;
                } else if(v != parents[start])   lowerNodes[start] = min(lowerNodes[start], discoveredNodes[v]);
            }
        }
        return false;
    }

    private long initMetrics() {
        start = Instant.now();
        runtime = Runtime.getRuntime();
        return runtime.maxMemory() - runtime.freeMemory();
    }

    private void printMetrics(long initUsedMemory) {
        // Time
        end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");

        // Memory
        System.out.println("Used memory: " + (runtime.maxMemory() - runtime.freeMemory() - initUsedMemory) + "\n");
    }

    private void nextValue(int k){
        while(true){
            circuit[k] = (circuit[k] + 1) % numberOfVertices;
            if(circuit[k] == 0) return;
            if(adjcentMatrix[circuit[k - 1]][circuit[k]] != 0){
                int j;
                for(j = 0; j < k; j++){
                    if(circuit[k] == circuit[j]){
                        break;
                    }
                }
                if(j == k){
                    if(k < numberOfVertices - 1 ){
                        return;
                    }
                    if((k == numberOfVertices - 1) && (adjcentMatrix[circuit[k]][circuit[0]] != 0)){
                        return;
                    }
                }
            }
        }
    }

    //TODO: imprimir grafo proibido
    private void hamiltonian(int k){
        while(true){
            nextValue(k);
            if(circuit[k] == 0) {
//                System.out.print("Clico proibido: ");
                for(int i = 0; i < numberOfVertices; i++) {
                    if (circuit[i] != 0) {
//                        System.out.print((circuit[i]) + " ");
                    }
                }
//                System.out.println();
                return;
            }
            if(k == numberOfVertices - 1){
                System.out.print("Hamiltonian Cycle: ");
                for(int i = 0; i < numberOfVertices; i++) {
                    System.out.print((circuit[i]) + " ");
                }
                System.out.println();
            } else {
                hamiltonian(k + 1);
            }
        }
    }

    public static void main(String[] args) {
        AcademyMethods academyMethods = new AcademyMethods();

        long initTime = academyMethods.initMetrics();

        if (!academyMethods.conexGraph() || !academyMethods.biconectedMethod()) {
            System.out.println("O grafo NÃO é hamiltoniano.");
        } else if (academyMethods.diracMethod() || academyMethods.oreMethod() || academyMethods.completeGraph()) {
            System.out.println("O grafo é hamiltoniano.");
        } else {
            System.out.println("Não pode-se afirmar se é hamiltoniano");
        }

        academyMethods.hamiltonian(1);

        academyMethods.printMetrics(initTime);

    }

}
