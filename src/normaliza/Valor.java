package normaliza;

import java.util.ArrayList;

public class Valor {

    private double max = 0;
    private double min = 0;

    public Valor(double array[]) {

        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                min = array[i];
                max = array[i];
            }
            if (min >= array[i]) {
                min = array[i];
            }
            if (max <= array[i]) {
                max = array[i];
            }

        }
    }

    public Valor(double array[][]) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (i == 0 && j == 0) {
                    min = array[i][j];
                    max = array[i][j];
                    continue;
                }
                if (min >= array[i][j]) {
                    min = array[i][j];
                }
                if (max <= array[i][j]) {
                    max = array[i][j];
                }
            }
        }
    }

    
    public Valor(ArrayList<Double> array){
    	for (int i = 0; i < array.size(); i++) {
            if (i == 0) {
                min = array.get(i);
                max = array.get(i);
            }
            if (min >= array.get(i)) {
                min = array.get(i);
            }
            if (max <= array.get(i)) {
                max = array.get(i);
            }

        }
    }
    
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
    
    
    
}
