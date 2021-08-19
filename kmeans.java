package Kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;

public class kmeans {
    private centroide [] clusters;
    private int numeroClusters;
    private tabla Datos;
    private registro [] registros;
    private double [] wss;
    public int totalWSS;

    public kmeans(int nc, tabla datos){
        Datos = datos;
        numeroClusters = nc;
        clusters = new centroide [nc];
        registros = new registro[datos.getTotalFilas()];
        wss = new double[numeroClusters];
        totalWSS = 0;
    }

    public void inicializarCentroides(){
        Datos.getMayoresyMenores();
        int [] max = Datos.getMayores();
        int [] min = Datos.getMenores();
        for(int i = 0; i < numeroClusters; i++){
            Random r = new Random();
            int cordA = r.nextInt(max[0] - min[0]) + min[0];
            int cordB = r.nextInt(max[1] - min[1]) + min[1];
            clusters[i] = new centroide(cordA, cordB, i);
        }
    }

    public void initRegistros(Table cd){
        int [] gps = new int [Datos.getTotalFilas()];
        int [][] arr = Datos.getDatos();
        for(int i = 0; i < Datos.getTotalFilas(); i++){
            int ca = arr[i][0];
            int cb = arr[i][1];
            registros[i] = new registro(ca, cb);
            registros[i].setDistancia(registros[i].calcularDistancia(clusters[0]));
            registros[i].setCluster(clusters[0].getCluster());
            for(int j = 1; j < numeroClusters; j++){
                if(registros[i].getDistancia() > registros[i].calcularDistancia(clusters[j])){
                    registros[i].setDistancia(registros[i].calcularDistancia(clusters[j]));
                    registros[i].setCluster(clusters[j].getCluster());
                    gps[i] = clusters[j].getCluster();
                }
            }
        }
        cd.removeColumns("grupo");
        IntColumn grupos = IntColumn.create("grupo", gps);
        cd.addColumns(grupos);
    }

    public void reCalcularCentroides(){
        for(int i = 0; i < numeroClusters; i++){
            int a = 0;
            int b = 0;
            int c = 0;
            for (int j = 0; j < Datos.getTotalFilas(); j++){
                if(clusters[i].getCluster() == registros[j].getCluster()) {
                    a += registros[j].getCordA();
                    b += registros[j].getCordB();
                    c++;
                }
            }
            if(a > 0 && c > 0 && b > 0) {
                clusters[i].setCordA(a / c);
                clusters[i].setCordB(b / c);
            }
        }
    }

    public void calcular(int iteraciones, Table cd){
        int a = 1;
        inicializarCentroides();
        for(int i = 0; i < iteraciones; i++){
            centroide [] anteriores = new centroide[numeroClusters];
            for (int j = 0; j < numeroClusters; j++){
                anteriores[j] = new centroide(clusters[j].getCordA(), clusters[j].getCordB(), clusters[j].getCluster());
            }
            initRegistros(cd);

            Plot.show(
                    ScatterPlot.create("datos",
                            cd, "income", "spend", "grupo")
            );

            reCalcularCentroides();
            System.out.printf("Iteracion %d\n", i);
            for(int j = 0; j < numeroClusters; j++){
                System.out.printf("Cluster %d = [%d, %d]\n", clusters[j].getCluster(), clusters[j].getCordA(), clusters[j].getCordB());
            }
        }
    }

    public void calcularSinGrafico(int iteraciones, Table cd){
        int a = 1;
        inicializarCentroides();
        for(int i = 0; i < iteraciones; i++){
            centroide [] anteriores = new centroide[numeroClusters];
            for (int j = 0; j < numeroClusters; j++){
                anteriores[j] = new centroide(clusters[j].getCordA(), clusters[j].getCordB(), clusters[j].getCluster());
            }
            initRegistros(cd);
            reCalcularCentroides();
            System.out.printf("Iteracion %d\n", i);
            for(int j = 0; j < numeroClusters; j++){
                System.out.printf("Cluster %d = [%d, %d]\n", clusters[j].getCluster(), clusters[j].getCordA(), clusters[j].getCordB());
            }
        }
    }

    public void calcularWSS(){
        int total = 0;
        for(int j = 0; j < numeroClusters; j++){
            int sum = 0;
            for (int i = 0; i < Datos.getTotalFilas(); i++){
                if(registros[i].getCluster() == clusters[j].getCluster()) {
                    int a = (int) Math.pow(registros[i].getCordA() - clusters[j].getCordA(), 2);
                    int b = (int) Math.pow(registros[i].getCordB() - clusters[j].getCordB(), 2);
                    a += b;
                    sum += a;
                }
            }
            total += sum;
            //wss[j] = sum;
        }
        totalWSS = total;
    }

    public void mostrarClusters(){
        for(int i = 0; i < registros.length; i++){
            System.out.printf("%d - [ %d , %d, %d ]\n", i, registros[i].getCordA(), registros[i].getCordB(), registros[i].getCluster());
        }
    }

    public void pasarDatosParaGrafica(int [] a, int [] b, ArrayList<Integer> WSS){
        for(int i = 0; i < numeroClusters; i++){
            a[i] = clusters[i].getCluster() + 1;
            b[i] = WSS.get(i);
        }
    }

    public centroide[] getClusters() {
        return clusters;
    }

    public void setClusters(centroide[] clusters) {
        this.clusters = clusters;
    }

    public int getNumeroClusters() {
        return numeroClusters;
    }

    public void setNumeroClusters(int numeroClusters) {
        this.numeroClusters = numeroClusters;
    }

    public tabla getDatos() {
        return Datos;
    }

    public void setDatos(tabla datos) {
        Datos = datos;
    }

    public registro[] getRegistros() {
        return registros;
    }

    public void setRegistros(registro[] registros) {
        this.registros = registros;
    }
}
