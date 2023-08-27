package prime;

public class Producer {
	
        PN pn;
        int n;
        Producer(PN pnn,int n)
        {
            this.pn=pnn;
            this.n = n;
        }


        public int produce() throws InterruptedException
        {
            int largestPrimeNumber = 2;
            boolean[] notPrime = new boolean[n + 20];


                // producer thread waits while list
                // is full
            for(int i = 2; i <= n; i++){
                if(notPrime[i])continue;
                for(int j = i + i; j <= n; j += i) notPrime[j] = true;
                synchronized (this.pn){
                    while(this.pn.list.size() == this.pn.capacity)
                        this.pn.wait();

                    this.pn.list.add(i);
                    this.pn.notify();
                    largestPrimeNumber = i;
                }
            }
            synchronized (this.pn){
                this.pn.list.add(-1);
                this.pn.notify();
            }
            return largestPrimeNumber;
        }
    

}
