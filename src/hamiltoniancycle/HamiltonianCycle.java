package hamiltoniancycle;

public class HamiltonianCycle {
    private int[][] adjcentMatrix;
    private int[] circuit;
    private int numberOfVertices;

    private HamiltonianCycle() {
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
        circuit[0] = 0;
        hamiltonian(1);
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
                System.out.print("Clico proibido: ");
                for(int i = 0; i < numberOfVertices; i++) {
                    if (circuit[i] != 0) {
                        System.out.print((circuit[i]) + " ");
                    }
                }
                System.out.println();
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
        new HamiltonianCycle();
    }
}
