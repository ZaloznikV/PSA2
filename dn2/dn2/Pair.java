package dn2;

public class Pair implements Comparable<Pair>{
    private int key;
    private boolean value;

    public Pair(int k, boolean b){
        this.key = k;
        this.value = b;
    }

    public int getKey() {
        return key;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public int compareTo(Pair pair) { //popravljeno
        int comparekey = pair.getKey();
        boolean comparevalue = pair.getValue();
        if (this.key - comparekey == 0){
            if (comparevalue == false && this.value == true){
                return -1;
            }
            else if (comparevalue == true && this.value == false){
                return 1;
            }
            else if (comparevalue == this.value){
                return 0;
            }

        }
        return (this.key - comparekey);
    }
}
