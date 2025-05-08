class VGAToHDMIAdapter implements VGA {
    private HDMI hdmi;

    public VGAToHDMIAdapter(HDMI hdmi) {
        this.hdmi = hdmi;
    }

@Override
public void connectWithVGA() {
    System.out.println("\nAdapting HDMI to VGA...");
    hdmi.connectWithHDMI();
    }
}