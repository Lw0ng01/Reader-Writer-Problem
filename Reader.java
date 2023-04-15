// This file defines class "Reader".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables 
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Reader extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Reader.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Reader(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();  // Create an instance of RandomSleep.
  }  // end of the constructor for class "Reader"



  public void run () {
    for (int I = 0;  I < 5; I++) {
      System.out.println("Reader " + myName + " wants to read.  "
                         + "Beforehand, readcount is "  + Synch.readcount);

      // Do acquire on the "mutex" semaphore, to get exclusive access to the
      // variable "Synch.readcount". 
      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}

      //increments waiting read if one of conditions is met else increments active readers
      if (Synch.writecount > 0 || Synch.waiting_writecount > 0)
      {
        Synch.waiting_readcount++;
      }
      else
      {
        Synch.rd.release();
        Synch.readcount++;
      }
      Synch.mutex.release();

      try
      {
        Synch.rd.acquire();
      }
      catch (Exception e)
      {

      }
      
      
      // If a writer is active, the first reader waits for "wrt" while still 
      // holding on to  "mutex".  Other readers, who arrive later, will get
      //  held up waiting for "mutex". 
      //Synch.readcount++;
      if (Synch.readcount==0)
      {
	try{
		 Synch.wrt.acquire(); 
	}
	catch(Exception e){}
       }
      // Now we have permission to start reading.  
      // Print a message and release mutex.
      System.out.println("Reader " + myName + " is now reading.  "
                         + "Readcount is " + Synch.readcount);
      //Synch.mutex.release();

      // Simulate the time taken for reading
      rSleep.doSleep(1, 200);   

      // We're finished reading.  Decrement readcount.  If we are the last
      // reader, then signal "wrt".  The signal to "wrt" will either wake up
      // a waiting writer, or set the semaphore value to 1, so that a future
      // writer or reader can go without waiting.
      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}
      Synch.readcount--;
      System.out.println("Reader " + myName + " is finished reading.  " 
                         + "Readcount decremented to " + Synch.readcount);
      if (Synch.readcount == 0 && Synch.waiting_writecount > 0) 
      {
        Synch.wrt.release();
        Synch.writecount++;
        Synch.waiting_writecount--; //decrements waiting writers
      }

      Synch.mutex.release();


      // Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"

