package prime;

public class prime {
	public static void main(String[] args) throws InterruptedException
    {
        GUI myGUI = new GUI();
        Thread thread1 = new Thread(myGUI);
        thread1.start();

        // t1 finishes before t2

    }

}
