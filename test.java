package Kmeans;

import org.checkerframework.checker.units.qual.A;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;

import java.io.IOException;
import java.util.ArrayList;

public class test {

    public static void main(String[] args) throws IOException {
        Table cd = Table.read().usingOptions(CsvReadOptions
                .builder("/Users/j.a.z./Desktop/Proyectos/InteligenciaArtificial/src/Kmeans/customer_data.csv")
                .header(true));
        System.out.println(cd.print());
        tabla t = new tabla(cd.columnCount(), cd.rowCount());
        Plot.show(
                ScatterPlot.create("Grafica Inicial",
                        cd, "income", "spend")
        );
        t.setDatos(cd);
        int [] gps = new int [t.getTotalFilas()];
        IntColumn grupos = IntColumn.create("grupo", gps);
        cd.addColumns(grupos);
        kmeans km = new kmeans(10, t);
        km.calcular(10, cd);
        km.mostrarClusters();
        //km.calcularWSS();
        Table datos = Table.create("datos");
        km.mostrarClusters();
        Table wss = Table.create("WSS");
        ArrayList<Integer> WSS = calcularWSS(t, cd, km.getNumeroClusters());
        int [] clases = new int [km.getNumeroClusters()];
        int [] cuadrados = new int [km.getNumeroClusters()];
        km.pasarDatosParaGrafica(clases, cuadrados, WSS);
        IntColumn cls = IntColumn.create("clusters", clases);
        DoubleColumn cds = DoubleColumn.create("cuadrados", cuadrados);
        wss.addColumns(cls, cds);
        Plot.show(
                TimeSeriesPlot.create("Grafica del codo", wss, "clusters", "cuadrados")
        );
    }

    public static ArrayList<Integer> calcularWSS(tabla t, Table cd, int numero_clusters){
        ArrayList<Integer> WSS = new ArrayList<Integer>();
        for(int i = 1; i <= numero_clusters; i++) {
            kmeans km = new kmeans(i, t);
            km.calcularSinGrafico(10, cd);
            km.mostrarClusters();
            km.calcularWSS();
            WSS.add(km.totalWSS);
        }
        return WSS;
    }
}
