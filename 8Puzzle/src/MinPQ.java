public class MinPQ<Node extends Comparable<Node>> {
    private Node[] nodePQ;
    private int size;

    public MinPQ(Node initialNode) {
        nodePQ = (Node[]) new Object[2];
        nodePQ[0] = null;
        nodePQ[1] = initialNode;
        size = 1;
    }
    public void insert(Node input){
        if(size == nodePQ.length) resize();
        nodePQ[++size] = input;
        swim(size);
    }

    public Node delMin() {
        if (size < 1) throw new NullPointerException();
        if (nodePQ.length / 4 > size) {
            resize();
        }
        exch(1,size);
        Node output = nodePQ[size];
        //below prevents loitering
        nodePQ[size--] = null;
        sink(1);
        return output;
    }

    private void sink(int position) {
        if (position > size || position < 1) throw new IllegalArgumentException();
        while (position * 2 <= size) {
            int j = position * 2;
            if (less(nodePQ[j], nodePQ[j+1])) j++;
            if (less(nodePQ[j], nodePQ[position])) break;
            exch(j, position);
            position = j;
        }
    }
    private void swim(int position){
        if (position > size || position < 1) throw new IllegalArgumentException();
        while(position > 1 && less(nodePQ[position/2],nodePQ[position])){
            exch(position/2,position);
            position = position/2;

        }
    }

    private void exch(int first, int second) {
        Node temp = nodePQ[first];
        nodePQ[first] = nodePQ[second];
        nodePQ[second] = temp;
    }

    private boolean less(Node first, Node second) {
        return true;
    }


    private void resize() {
        Node[] replacement;
        if (nodePQ.length / 4 > size) {
            replacement = (Node[]) new Object[nodePQ.length / 2];
            replacement[0] = null;
            for (int i = 1; i < size; i++) {

                replacement[i] = nodePQ[i];
            }
        } else {
            replacement = (Node[]) new Object[nodePQ.length * 2];
            replacement[0] = null;
            for (int i = 1; i < size; i++) {

                replacement[i] = nodePQ[i];
            }
        }
        nodePQ = replacement;
    }
}
