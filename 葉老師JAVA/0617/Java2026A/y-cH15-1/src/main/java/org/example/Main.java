package org.example;

interface Bird {
    void birdFly();
}

interface Airplane{
    void airplaneFly();
}

class Fly implements Bird,Airplane{
    @Override
    public void airplaneFly() {
        System.out.println("飛機用引擎飛");
    }
    @Override
    public void birdFly() {
        System.out.println("鳥用翅膀飛");
    }
    public void pediaFly(){
        System.out.println("飛行百科");
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Fly f = new Fly();
        f.airplaneFly();
        f.birdFly();
        f.pediaFly();
    }
}
