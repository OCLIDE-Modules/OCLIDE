package ru.VladG24.ocide.tester;

/**
 * Configurable OpenComputers 1.7.5 architectures' emulator
 *
 * TODO: 1.) Finish "config" section
 *
 * @author VladG24YT
 */
public class Emulator extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private javax.swing.JPanel console = new javax.swing.JPanel();

    private final int MIN_WIDTH = 50, MIN_HEIGHT = 16, MID_WIDTH = 80, MID_HEIGHT = 25, MAX_WIDTH = 160, MAX_HEIGHT = 50;
    private final int CHAR_WIDTH = 7, CHAR_HEIGHT = 10;

    //config
    //Computer Case
    public final int CASE_1 = 1, CASE_2 = 2, CASE_3 = 3;
    //CPU
    public final int CPU_1 = 1, CPU_2 = 2, CPU_3 = 3;
    //Graphics card
    public final int GPU_1 = 1, GPU_2 = 2, GPU_3 = 3;
    //APU
    public final int APU_2 = 1, APU_3 = 2, APU_C = 3;

    public Emulator(int screenType, int caseType, int CPU_type, int GPU_type) {
        //TODO
        this();
    }

    public Emulator(int screenType, int caseType, int APU_type) {
        //TODO
        this();
    }

    public Emulator() {
        //Panel
        console.setBackground(java.awt.Color.BLACK);
        console.setLayout(new javax.swing.BoxLayout(console, javax.swing.BoxLayout.Y_AXIS));
        //lines
        javax.swing.JTextField[] lines = new javax.swing.JTextField[MIN_HEIGHT];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new javax.swing.JTextField(String.valueOf(i));
            lines[i].setEditable(false);
            lines[i].setBackground(java.awt.Color.BLACK);
            lines[i].setForeground(java.awt.Color.WHITE);
            lines[i].setFont(new java.awt.Font(/*"Lucida Console"*//*"Monospaced"*/"Consolas", java.awt.Font.BOLD, 16));
            lines[i].setBounds(0, CHAR_HEIGHT * i, MIN_WIDTH * CHAR_WIDTH, CHAR_HEIGHT);
            lines[i].setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
            console.add(lines[i]);
        }
        for (int i = 0; i < MIN_WIDTH / 10; i++) {
            lines[0].setText(lines[0].getText() + "1234567890");
        }
        //Window
        this.setTitle("OpenComputers Emulator");
        //this.setBounds(0, 0, MIN_WIDTH * 14, MIN_HEIGHT * 20);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(console);
        this.setResizable(false);
        this.pack();

        javax.swing.Timer t = new javax.swing.Timer(300, (java.awt.event.ActionEvent e) -> {
            this.revalidate();
        });
        t.start();
    }
}
