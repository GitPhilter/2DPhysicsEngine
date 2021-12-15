package math;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private double[][] data;
    public final int rows, cols;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public void setData(double[][] newData){
        if(rows != newData.length || cols != newData[0].length){
            System.out.println("Matrix.setData(): newData[][] has invalid size!");
            return;
        }
        for(int r = 0; r < rows; ++r){
            for(int c = 0; c < cols; ++c){
                data[r][c] = newData[r][c];
            }
        }
    }

    public double[][] getData() {
        return data;
    }

    public void add(double scalar) {
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                data[r][c] = data[r][c] + scalar;
            }
        }
    }

    public void add(Matrix m) {
        if (rows != m.rows || cols != m.cols) {
            System.out.println("Matrix.add(Matrix m): matrices are of different sizes!");
            return;
        }
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                data[r][c] = data[r][c] + m.getData()[r][c];
            }
        }
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix resultMatrix = new Matrix(a.rows, a.cols);
        for (int r = 0; r < a.rows; ++r) {
            for (int c = 0; c < a.cols; ++c) {
                resultMatrix.getData()[r][c] = a.getData()[r][c] - b.getData()[r][c];
            }
        }
        return resultMatrix;
    }

    public static Matrix transpose(Matrix a) {
        Matrix resultMatrix = new Matrix(a.cols, a.rows);
        for(int r = 0; r < a.rows; ++r){
            for(int c = 0; c < a.cols; ++c){
                resultMatrix.getData()[c][r] = a.getData()[r][c];
            }
        }
        return resultMatrix;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        //System.out.println("a.size: " + a.rows + " * " + a.cols);
        //System.out.println("b.size: " + b.rows + " * " + b.cols);

        Matrix resultMatrix = new Matrix(a.rows,b.cols);
        for(int i = 0; i < resultMatrix.rows; i++)
        {
            for(int j = 0; j < resultMatrix.cols;j++)
            {
                double sum=0;
                for(int k = 0;k < a.cols ; k++)
                {
                    sum += a.data[i][k] * b.data[k][j];
                }
                resultMatrix.data[i][j]=sum;
            }
        }
        return resultMatrix;
    }

    public void multiply(Matrix a) {
        for(int i=0;i<a.rows;i++)
        {
            for(int j=0;j<a.cols;j++)
            {
                this.data[i][j]*=a.data[i][j];
            }
        }

    }

    public void multiply(double a) {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                this.data[i][j]*=a;
            }
        }

    }

    public void sigmoid() {
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
                this.data[i][j] = 1/(1+Math.exp(-this.data[i][j]));
        }

    }

    public Matrix dsigmoid() {
        Matrix temp=new Matrix(rows,cols);
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
                temp.data[i][j] = this.data[i][j] * (1-this.data[i][j]);
        }
        return temp;

    }

    public static Matrix fromArray(double[]x)
    {
        Matrix temp = new Matrix(x.length,1);
        for(int i =0;i<x.length;i++)
            temp.data[i][0]=x[i];
        return temp;

    }

    public List<Double> toArray() {
        List<Double> temp= new ArrayList<Double>()  ;

        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                temp.add(data[i][j]);
            }
        }
        return temp;
    }

    @Override
    public String toString(){
        String result = "";
        for(int c = 0; c < cols; ++c){
            for(int r = 0; r < rows; ++r){
                result += "[" + data[r][c] + "]";
            }
            result += "\n";
        }
        return result;
    }
}
