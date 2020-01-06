public class UnionFind {
    private int[] id;
    private int[] size;
    public UnionFind(int n){
        id = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++){
            id[i] = i;
        }
    }
    public boolean connected(int p, int q){
        return find(p) == find(q);
    }
    public int find(int i){
        validate(i);
        while(i != id[i]){
            i = id[i];
        }
        return i;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }
    public void union(int p, int q){
        int rootp= find(p);
        int rootq=find(q);
        if(rootp == rootq) return;
        if(size[rootp] < size[rootq]) {id[rootp] = rootq; size[rootq]  += size[rootp];}
        else id[rootq] = rootp; size[rootq] += rootp;
    }

    public int count(int i){
        return size[i];
    }
    public void print(){
        int counter = 0;
        for (int i = 0; i < id.length; i++){
            System.out.print(id[i] + "\t");
            counter ++;
            if(counter == Math.sqrt(id.length-2)){
                counter = 0;
                System.out.print("\n");
            }

        }
    }
}
