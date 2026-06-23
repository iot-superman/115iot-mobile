package org.example;

class Tshape{
    private double upline = 0;
    private double downline = 0;
    private double height = 0;
    public double getUpline() {
        return upline;
    }
    public void setUpline(double upline) {
        this.upline = upline;
    }
    public double getDownline() {
        return downline;
    }
    public void setDownline(double downline) {
        this.downline = downline;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
}
class TshapeArea extends Tshape{
    public double area(){
        return ((getUpline()+getDownline())*getHeight()/2);
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        TshapeArea t = new TshapeArea();
        //t.upline=100;//false
        t.setDownline(20);
        t.setHeight(33);
        t.setUpline(40);
        System.out.println("面積:"+t.area());

    }
}
