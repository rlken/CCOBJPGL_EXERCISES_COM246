public class Hybrid implements InternalCombustion, Electric {
    
        String carName;

    public void chargebattery() {
        System.out.println("being charged");
    }
    public void fillgas() {
        System.out.println("being refilled");
    }
}