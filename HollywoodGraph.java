import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
* Creates a graph of movies and actors in Hollywood. 
* Extends the AdjListGraph<T> class.
*
* @author Isabelle Whetsel
* @author Dorrie Peters
* @author Nessa Tong
* @version 12/3/23
*/

public class HollywoodGraph<T> extends AdjListsGraph<T>{
  private ArrayList<Actor> alreadyAdded;
  private ArrayList<Movie> addedIn;

  /**
   * Creates a new Hollywood graph
   * @param file the String name of the file
   */
  public HollywoodGraph(String file){
      super();
      //creates empty graph
      alreadyAdded = new ArrayList<Actor>();
      addedIn = new ArrayList<Movie>();
      readDataFromFile(file);
  }  
      //make movie
      //add actors to movie
      //add movie as node
      //check if actors exists and add new actors as nodes
      //add arcs between movie and actors
      //repeat for each movie
    /**
    * Reads data from input file
    * @param fileName name of file to be read
    */
  private void readDataFromFile(String fileName)
  {
      try{
          Scanner scan = new Scanner(new File(fileName));
          scan.nextLine(); //We want to skip past the header, explaining the lists
          String bigLine;
          Movie mov = new Movie("");
          //need to make first line of the movement
          Actor temp;
          boolean leading;
          while(scan.hasNext()){
              bigLine = scan.nextLine();
              String[] listOfStrs = splitLine(bigLine);
              if(mov.getTitle().equals("")){
                  mov = new Movie(listOfStrs[0]);
              }//Should only iterate for the very first line.
              //System.out.println(mov.getTitle());
              //System.out.println(listOfStrs[0]);
              //System.out.println("---");
              if(!mov.getTitle().equals(listOfStrs[0])){
                  //System.out.println("---");
                  addMovie(mov);
                  addedIn.add(mov);
                  //add the movie into the graph
                  mov = new Movie(listOfStrs[0]);
                  //create a new movie
              }
              //create and associate actor to movie
              leading = listOfStrs[3].equals("Leading");
              temp = new Actor(listOfStrs[1], listOfStrs[5], leading);
              mov.addActor(temp);
          }
          addMovie(mov);
          //This adds the final, final movie after the while loop is finished iterating.
      }catch (FileNotFoundException oops){
          System.out.println(oops);
      }
  }
  
   /**
    * Given a movie, what is the list of all actors who have played in that movie? 
    * Write a method to answer this question, and test this method thoroughly.
    */
  public ArrayList<Actor> findCast(String movieName){
    Movie temp = null;
        for(int i=0; i < addedIn.size(); i++){
            temp = addedIn.get(i);
            if(temp.getTitle().equals(movieName)){
                //if the names are the same.
                return temp.getCastList();
            }
        }
    return null; //hopefully we never get here
  }
    
  /**
   * Helper method that splits each line by title, actor, etc
   * @param line Line to be read
   * @return Separated line in an array
   */
  private String[] splitLine(String line){
      String[] list = line.split(",");
          for(int i = 0; i < 5; i++){
              list[i] = list[i].replace("\"", "");
              //For testing purposes
            
              //System.out.println("Test" + list[i]);
          }
      return list;
  }


  /**
   * Adds a movie to the graph, adds actors from movie to graph
   * @param in new movie added to graph
   */
  private void addMovie(Movie in){
      this.addVertex((T) in.getTitle());
      //^adds in the movie title as a vertex
      ArrayList<Actor> actors = in.getCastList();
      //a list of all the actors.
      Actor actorA = actors.get(0);
      for(int i = 0; i< actors.size(); i++){
          actorA = actors.get(i);
          this.addVertex((T) actorA.getName());
          //adds in the actor's name (string) which should bounce if already in there
          //ie this shouldn't make any repeats
          this.addEdge((T) in.getTitle(), (T) actorA.getName()); //adds an edge between actor and movie
          //repeat for every actor in the movie.
      }
  }
  
  /**
   * Runs our version of the Bechdel Test on every Movie in the HollywoodGraph object.
   * Returns void (nothing) because it prints out the result for each and every movie.
   * At the end, it prints out how many Movies, in the Graph, passed the test.
   */
  public void runBechdelTest(){
      String temp = "";
      Movie temporary = null;
      int numPasses = 0;
      for(int i = 0; i < addedIn.size(); i++){
          temporary = addedIn.get(i);
          temp = "Now testing " + temporary.getTitle() + " . This movie passes the test: ";
          temp += temporary.runOurTest();
          if(temporary.runOurTest()){
              numPasses++;
          }
          System.out.println(temp);
      }
      System.out.println("In this graph, " + numPasses + " movie(s) passed our test");
  }
   
  /**
     * retrieves the list of movies that an actor has played in
     * This method assumes that Movie exists and is in the graph
     * @param actor the specific actor
     * @return a linked list of all the adjacent vertices (movies)
     */
    public LinkedList<T> getActorAdjList(Movie m)
    {
        String title = m.getTitle();
        int index = super.vertices.indexOf(title);
        return super.arcs.get(index);
    }
    
  /**
     * retrieves the list of movies that an actor has played in
     * This method assumes that Actor exists and is in the graph.
     * @param actor the specific actor
     * @return a linked list of all the adjacent vertices (movies)
     */
    public LinkedList<T> getMovieAdjList(Actor a)
    {
        String name = a.getName();
        int index = super.vertices.indexOf(name);
        return super.arcs.get(index);
    }
  
  /**
   * Tests the degrees (Movies) of separation between actors, if they starred in the same movie, it'll return 1. 
   * Otherwise it counts every movie in between the two actors. If the actors have the same name, 0 is returned
   * If either Actor a or b have no movies, returns -1.
   * @param Actor   a   an Actor instance
   * @param Actor   b   an Actor instance
   * @return    int     the number of movies separating the actors
   */
  public int getSeparation(Actor a, Actor b)
    {
        //CHANGE EVERYTHING TO STRINGS
        String aN = a.getName();
        String bN = b.getName();
        //Assume they are in the graph, 
        //Breadth First Search
        // edge case: actors are the same
        if(aN.equals(bN))
        {
            return 0;
        }
        // edge case: actor a or b have no movies
        if(getMovieAdjList(a).isEmpty() || getMovieAdjList(a).isEmpty())
        {
            return -1;
        }

        LinkedQueue<String> queue = new LinkedQueue(); // queue to search graph
        LinkedList<String> visited = new LinkedList(); // keeps track of visited actors
        LinkedList<String> moviesVisited = new LinkedList(); // keeps track of visited movies
        LinkedList<LinkedList<Actor>> connections = new LinkedList(); // keeps track of how actors are connected to each other
        // each linkedlist in connections will contain previous actor and new actor

        queue.enqueue(aN); // adds current actor *name* to the queue to be searched
        visited.add(aN); // adds current actor *name* to list of already visisted actors

        // each loop goes through the movies of one actor, and adds the actors of each movie to the queue to be checked later
        while(!queue.isEmpty())
        {
            String currentActor = queue.dequeue(); //this is the current actor's name
            LinkedList<T> movieList = getMovieAdjList(new Actor(currentActor, "f", true)); 
            //we create a fake actor based on the name becuase we only care about the name and this method has an Actor param
            // retrives all the movies that this actor has starred in
            
            // iterates through movie list
            for(int i = 0; i < movieList.size(); i++)
            {
                String mov1 = (String)(movieList.get(i)); //
                // checks if movie has been visited to prevent a loop
                boolean movieAlreadyVisited = false;
                for(int j = 0; j < moviesVisited.size(); j++)
                {
                    String mov2 = moviesVisited.get(j);
                    if(mov1.equals(mov2))
                    {
                        movieAlreadyVisited = true;
                        break; 
                        //if we've already visited thisn movie than we leave and pick the next mov1, to check through that.
                    }
                }

                if(!movieAlreadyVisited)
                {
                    String temp = (String)(movieList.get(i)); 
                    //changes it into a movie to get the title to get the String
                    moviesVisited.add(temp); // adds to list of movies visited

                    // iterates through actors in the movie
                    LinkedList<T> actorsInMovie = getActorAdjList(new Movie((String)(movieList.get(i))));
                    for(int j = 0; j < actorsInMovie.size(); j++)
                    {
                        // checks if actor has already been traversed through to prevent loop
                        boolean alreadyVisited = false;
                        for(int k = 0; k < visited.size(); k++)
                        {
                            String superTemp = (String) actorsInMovie.get(j);
                            //(String) ((Actor)()).getName(); //gets their name
                            if((visited.get(k).equals(superTemp)))
                            {
                                // actor has already been visited
                                alreadyVisited = true;
                                break;
                            }
                        }

                        if(!alreadyVisited)
                        {
                            // if actor has the same name
                            //((Actor)actorsInMovie.get(j)).getName().equals(bN)
                            if(((String)actorsInMovie.get(j)).equals(bN))
                            {
                                // find connections back to origin
                                int count = 0;
                                for(int k = connections.size() - 1; k >= 0; k--)
                                {
                                    // previous connection found
                                    if(connections.get(k).peekLast().getName().equals(currentActor))
                                    {
                                        count++;
                                        currentActor = connections.get(k).peek().getName(); 
                                        // will now search for connection to previous actor's *name*
                                    }
                                }
                                return count+1;
                            }
                            else
                            {
                                // add new actor to queue, to be checked after program iterates through all actors connected to current actor
                                //queue.enqueue(((Actor)actorsInMovie.get(j)).getName());
                                queue.enqueue((String)actorsInMovie.get(j));
                                // keeps track of visited actors
                                visited.add((String)actorsInMovie.get(j));

                                // adds a connection between the current actor and new actor
                                LinkedList<Actor> newConnection = new LinkedList();
                                newConnection.add(new Actor(currentActor, "f", true)); 
                                //we create a fake actor based on the name becuase we only care about the name and this method has an Actor param
                                newConnection.add(new Actor(((String) actorsInMovie.get(j)), "f", false));
                                connections.add(newConnection);
                            }
                        }
                    }
                }
            }
        }

        // if queue is empty, actor b was not found after traversing the graph
        return -1;
    }
  
  /**
   * A public tester method.
   */
  public static void main(String[] args)
  {
      //HollywoodGraph tester = new HollywoodGraph("nextBechdel_castGender.txt");
      /*System.out.println("Now testing our test:");
      Actor tP = new Actor("Tyler Perry", "male", true);
      Actor lK = new Actor("Liza Koshy", "female", false);
      Actor jK = new Actor("J.K. Simmons", "ef", true);
      System.out.println("Now testing getMovieAdjList for Tyler Perry");
      System.out.println(tester.getMovieAdjList(tP));
      System.out.println("Now testing getMovieAdjList for Liza Koshy");
      System.out.println(tester.getMovieAdjList(jK));
      System.out.println("Now testing getActorAdjList for Zootopia");
      System.out.println(tester.getActorAdjList(new Movie("Zootopia")));
      System.out.println("Now testing getSeparation with Tyler Perry and Kristine Johnson, should print 2");
      System.out.println(tester.getSeparation(tP,new Actor("Kristine Johnson", "blah", false)));
      Actor rDJ = new Actor("Robert Downey Jr.", "male", true);
      Actor sW = new Actor("Sigourney Weaver", "female", true);
      System.out.println("Now testing getSeparation with Robert Downey Jr. and Sigourney Weaver, should print 3");
      System.out.println(tester.getSeparation(rDJ, sW));
      System.out.println("Now testing getSeparation with Tyler Perry and Liza Koshy, should print 1");
      System.out.println(tester.getSeparation(tP, lK));
      System.out.println("Now running our Bechdel test for our graph");
      tester.runBechdelTest(); 
      System.out.println("Now testing toString for our graph");*/
      //System.out.println(tester);
      //tester.saveTGF("testingtesting.tgf");
      
      System.out.println("Now testing the HollywoodGraph Class");
      HollywoodGraph tester = new HollywoodGraph("nextBechdel_castGender.txt");
      System.out.println("Now testing Task 2.0 for The Jungle Book.");
      System.out.println(tester.getActorAdjList(new Movie("The Jungle Book")));
      System.out.println("Now testing Task 2.1 for Jennifer Lawrence.");
      System.out.println(tester.getMovieAdjList(new Actor("Jennifer Lawrence", "female", true)));
      Actor tP = new Actor("Tyler Perry", "male", true);
      Actor mF = new Actor("Megan Fox", "female", true);
      Actor nA = new Actor("Nick Arapoglou", "male", false);
      //We create new Actors because our project, graph, and code is based on Strings as opposed to objects.
      System.out.println("Now testing Task 2.2 for Tyler Perry and Megan Fox.");
      System.out.println(tester.getSeparation(mF, tP));
      System.out.println("Now testing Task 2.2 for Tyler Perry and Nick Arapoglou.");
      System.out.println(tester.getSeparation(nA, tP));
      System.out.println("Now testing Task 2.3 for our test.");
      tester.runBechdelTest();
  }
}
