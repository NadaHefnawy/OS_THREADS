package prime;

import java.io.FileWriter;
import java.io.IOException;

public class Consumer {
	PN pn;
    String fileName;

    Consumer(PN pnn, String fileName)
    {
        this.pn=pnn;
        this.fileName = fileName;
    }

    public int consume() throws InterruptedException, IOException {
        FileWriter myWriter = new FileWriter(fileName);
        int numberOfElements = 0;
        while (true) {
            synchronized (this.pn)
            {
                // consumer thread waits while list
                // is empty
                while(this.pn.list.size() == 0)
                    this.pn.wait();

                // to retrieve the first job in the list
                int val = this.pn.list.removeFirst();
                if(val == -1)break;

                myWriter.write(val + ", ");
                numberOfElements++;

                // Wake up producer thread
                this.pn.notify();
            }
        }
        myWriter.close();
        return numberOfElements;
    }

    public static void setFinished(boolean b) {
        // TODO Auto-generated method stub

    }

}
