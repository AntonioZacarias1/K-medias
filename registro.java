package Kmeans;

public class registro {
    private int cordA;
    private int cordB;
    private double distancia;
    private int cluster;

    public registro(int ca, int cb){
        cordA = ca;
        cordB = cb;
    }

    public double calcularDistancia(centroide cent){
        double dist = Math.sqrt((Math.pow((cent.getCordA() - cordA), 2)) +
                                (Math.pow((cent.getCordB() - cordB), 2)));
        return dist;
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

    public void setCordB(int cordB) {
        this.cordB = cordB;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
}
