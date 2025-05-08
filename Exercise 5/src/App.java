public class App {
    public static void main(String[] args) {
        HDMI HDMIone = new HDMI();

        VGA adapter = new VGAToHDMIAdapter(HDMIone);

        Monitor monitor = new Monitor();
        monitor.connect(adapter);
    }
}
