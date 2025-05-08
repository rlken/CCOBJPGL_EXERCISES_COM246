class Monitor {
    public void connect(VGA cable) {
        System.out.println("\nDisplay 1");
        cable.connectWithVGA();
        System.out.println("\nHDMI To VGA Connected\n");
    }
}