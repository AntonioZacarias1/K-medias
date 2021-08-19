package Kmeans;

import tech.tablesaw.api.Table;

public class tabla {
    private int [][] datos;
    private int totalColumnas;
    private int totalFilas;
    private int [] mayores;
    private int [] menores;


    public tabla(int tc, int tf){
        datos = new int [tf][tc];
        totalColumnas = tc;
        totalFilas = tf;
        mayores = new int [totalColumnas];
        menores = new int [totalColumnas];
    }

    public void setDatos(Table tab){
        for(int i = 0; i < tab.rowCount(); i++){
            for (int j = 0; j < tab.columnCount(); j++){
                datos[i][j] = tab.intColumn(j).get(i);
            }
        }
    }

    public int[][] getDatos() {
        return datos;
    }

    public void setDatos(int[][] datos) {
        this.datos = datos;
    }

    public int getTotalColumnas() {
        return totalColumnas;
    }

    public void setTotalColumnas(int totalColumnas) {
        this.totalColumnas = totalColumnas;
    }

    public int getTotalFilas() {
        return totalFilas;
    }

    public void setTotalFilas(int totalFilas) {
        this.totalFilas = totalFilas;
    }

    public void getMayoresyMenores(){
        for (int i = 0 ; i < totalColumnas; i++){
            mayores[i] = Max(i);
            menores[i] = Min(i);
        }
    }

    public int Max(int j){
        int temp = 0;
        for(int i = 0; i < totalFilas; i++){
            if(temp < datos[i][j]){
                temp = datos[i][j];
            }
        }
        return temp;
    }

    public int Min(int j){
        int temp = datos[0][j];
        for(int i = 0; i < totalFilas; i++){
            if(temp > datos[i][j]){
                temp = datos[i][j];
            }
        }
        return temp;
    }

    public int[] getMayores() {
        return mayores;
    }

    public void setMayores(int[] mayores) {
        this.mayores = mayores;
    }

    public int[] getMenores() {
        return menores;
    }

    public void setMenores(int[] menores) {
        this.menores = menores;
    }
}
