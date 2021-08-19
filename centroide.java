package Kmeans;

public class centroide {

    private int cordA;
    private int cordB;
    private int cluster;

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public centroide(int cordA, int codB, int clust) {
        this.cordA = cordA;
        this.cordB = codB;
        this.cluster = clust;
    }

    public int getCordA() {
        return cordA;
    }

    public void setCordA(int cordA) {
        this.cordA = cordA;
    }

    public int getCordB() {
        return cordB;
    }

    public void setCordB(int codB) {
        this.cordB = codB;
    }
}
