
public class CompitoRiprova {
	
	public static void main(String[] args) throws InterruptedException {
        //int N=100;
        int M=3;
        //int H=10;
        
        SharedBuffer sb = new SharedBuffer(1);
        Controller[] ct=new Controller[M];
        for(int i=1; i<ct.length; i++) {
            ct[i]=new Controller(sb);
            ct[i].start();
        }
        
        Worker[] w=new Worker[M];
        for(int i=1; i<w.length; i++){
            w[i]=new Worker(sb);
            w[i].start();
        }
        
        /*Object[] message=new Object[H];
        for(int j=0; j<H; j++)
                message[j]=j;*/
        
        //sb.getMessage(message); mi da un messaggio in piu
        
       /*for(int i=0;i<ct.length-1; i++)
            ct[i].interrupt();*/
    }
    
}

class SharedBuffer{
    protected Object[] message;
    private int addMsg;
    //private int remMsg=0;
    private int count;
    
    public SharedBuffer(int H){
        message = new Object[H];
    }
    
    /*public synchronized void processData(Object[] message) throws InterruptedException{// rende usabile il metodo processData
        while(nDisponibili!=0)
            wait();
        //nDisponibili=0;
        notifyAll();
    }*/
    
    public synchronized void getMessage(Object msg) throws InterruptedException{
        while(count==message.length){
            wait();
        }
        message[addMsg] = msg;
        //addMsg++;
        count--;
        notify();
    }
    
    public synchronized Object putMessage() throws InterruptedException{
        while(count==0){
            break;
        }
        
        Object s=message[addMsg];
        //addMsg--;
        //count--;
        notify();
        return s;
    }
    
}

class Controller extends Thread{
    //private int id;
    private SharedBuffer sb;
    private boolean msg=false;
    
    public Controller(SharedBuffer sb){
        this.sb=sb;
        
    }
    
    public void run(){
        try{
            int i=100;
            msg=true;
            while(true && msg==true){
                System.out.println(getName()+": prende "+i);
                sb.getMessage(i--);
            }
            
        }catch(InterruptedException e){
            
        }
        
    }
    
}

class Worker extends Thread{
        //private int id;
        private SharedBuffer sb;
        
        public Worker(SharedBuffer sb){
            this.sb=sb;
        }
        
        public void run(){
            try{
                while(true) {
                Object msg = sb.putMessage();
                System.out.println(getName()+": consuma "+msg.toString());
                //sleep(100);
            }
                
            }catch(InterruptedException e){
                
            }
        }

}
