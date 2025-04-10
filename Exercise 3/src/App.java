public class App {
    public static void main(String[] args) throws Exception {

        
        HybridVehicle vehicle1 = new HybridSedan();
        vehicle1.carName = "BYD Seal 5";

        HybridVehicle vehicle2 = new HybridPickup();
        vehicle2.carName = "BYD Shark 6";

        CarWash wash = new CarWash();


        System.out.println(vehicle1.carName + " is");
        vehicle1.chargebattery();
        vehicle1.fillgas();
        wash.carwash();
        
        System.out.println(vehicle2.carName + " is");
        vehicle2.chargebattery();
        vehicle2.fillgas();
        wash.carwash();
    }

}