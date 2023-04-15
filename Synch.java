// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.  

import java.util.concurrent.*;

public class Synch {

  public static Semaphore mutex;
  public static Semaphore wrt;
  public static Semaphore rd; // read sempahore

  public static int readcount = 0; //active readers
  public static int waiting_readcount = 0; //waiting readers
  public static int writecount = 0; //active writers
  public static int waiting_writecount = 0; //waiting writers


}  // end of class "Synch"

