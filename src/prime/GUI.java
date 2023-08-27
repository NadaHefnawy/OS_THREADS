package prime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class GUI implements ActionListener, Runnable{
    private static JPanel panel;
    private static JLabel nsLabel, BufferSizeLabel, outputFileLabel, messageLabel, largestLabel1,largestLabel2, elementsGeneratedLabel1, elementsGeneratedLabel2;
    private static JLabel processTimeLabel1, processTimeLabel2;
    private static JTextField nsText, outputFileText,bufferSizeTxt;
    private static JButton startButton;
    public void open()
    {
        JFrame frame = new JFrame();
        frame.setTitle("Processing  Numbers");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBackground(Color.white);
        frame.add(panel);
        panel.setLayout(null);


        nsLabel = new JLabel("Number of s:");
        nsLabel.setBounds(10, 20, 120, 25);
        panel.add(nsLabel);

        nsText = new JTextField(20);

        nsText.setBounds(150, 20, 165, 25);
        nsText.setForeground(Color.DARK_GRAY);
        nsText.setBackground(Color.WHITE);
        panel.add(nsText);

        BufferSizeLabel = new JLabel("BufferSize: ");
        BufferSizeLabel.setBounds(10,60,120,25);
        panel.add(BufferSizeLabel);

        bufferSizeTxt = new JTextField(10000);
        bufferSizeTxt.setBounds(150,60,165,25);
        bufferSizeTxt.setForeground(Color.DARK_GRAY);
        bufferSizeTxt.setBackground(Color.WHITE);
        panel.add(bufferSizeTxt);

        outputFileLabel = new JLabel("Output File:");
        outputFileLabel.setBounds(10, 100, 120, 25);
        panel.add(outputFileLabel);

        outputFileText = new JTextField(1000);
        outputFileText.setBounds(150, 100, 165, 25);
        outputFileText.setForeground(Color.DARK_GRAY);
        outputFileText.setBackground(Color.WHITE);
        panel.add(outputFileText);


        startButton = new JButton();
        startButton.setBounds(250, 150, 100, 25);
        startButton.setForeground(Color.DARK_GRAY);
        startButton.setBackground(Color.WHITE);
        startButton.setText("Start");
        startButton.addActionListener(new GUI());
        panel.add(startButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(10, 150, 300, 25);
        panel.add(messageLabel);

        largestLabel1 = new JLabel("Largest  Number:");
        largestLabel1.setBounds(10, 200, 200, 25);
        panel.add(largestLabel1);

        largestLabel2 = new JLabel("");
        largestLabel2.setBounds(180, 200, 300, 25);
        panel.add(largestLabel2);

        elementsGeneratedLabel1 = new JLabel("# of s Generated:");
        elementsGeneratedLabel1.setBounds(10, 250, 200, 25);
        panel.add(elementsGeneratedLabel1);

        elementsGeneratedLabel2 = new JLabel("");
        elementsGeneratedLabel2.setBounds(180, 250, 300, 25);
        panel.add(elementsGeneratedLabel2);

        processTimeLabel1 = new JLabel("Processing Time:");
        processTimeLabel1.setBounds(10, 300, 300, 25);
        panel.add(processTimeLabel1);

        processTimeLabel2 = new JLabel("");
        processTimeLabel2.setBounds(180, 300, 120, 25);
        panel.add(processTimeLabel2);

        frame.setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == startButton)
        {
            if(!nsText.getText().matches("-?\\d+(\\.\\d+)?") || outputFileText.getText() == "")
            {
                messageLabel.setText("Please Enter The Fields Above Correctly.");
                return;
            }else
            {

                messageLabel.setText("Processing...");
            }
            int numbers = Integer.parseInt(nsText.getText());
            int bSize = Integer.parseInt(bufferSizeTxt.getText());
            String file = outputFileText.getText();
            final PN pnn=new PN(bSize);


            final Producer p = new Producer(pnn, numbers);
            final Consumer c = new Consumer(pnn, file);
            final int[] largetNumber = {2};
            final int[] numberOfElements = {0};
            Thread t1 = new Thread(new Runnable() {
            	public void run()
            	{
                try {
                    largetNumber[0] = p.produce();
                }
                catch (InterruptedException e13) {
                    e13.printStackTrace();
                }}
            });

            // Create consumer thread
            Thread t2 = new Thread(new Runnable()  {
            	public void run()
            	{
                try {
                    numberOfElements[0] = c.consume();
                }
                catch (InterruptedException e12) {
                    e12.printStackTrace();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }}
            });
            long start = System.currentTimeMillis();




                // Start both threads
                t1.start();
                t2.start();
                try {
                    t1.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    t2.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            long end = System.currentTimeMillis();
            processTimeLabel2.setText(end - start + " ms");
            largestLabel2.setText(String.valueOf(largetNumber[0]));
            elementsGeneratedLabel2.setText(String.valueOf(numberOfElements[0]));
            messageLabel.setText("Finished");
        }
    }

    public void run() {
        this.open();
    }

}