package eblo.study.springboot.controller;

public class Singleton {

    private final static Singleton instance = new Singleton();
    
    private Singleton() {
        super();
    }
    
    public static Singleton getInstance() {
        return instance;
    }
    
    public String test() {
        return "test";
    }
}
