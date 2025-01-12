
import java.util.*;

/**
 * A Class that creates a representation of a Movie Object
 *
 * @author Isabelle Whetsel
 * @author Dorrie Peters
 * @author Nessa Tong
 * @version 12/3/23
 */

public class Movie
{
    private String title;
    private ArrayList<Actor> castList;
    
    /**
     * The constructor for the Movie Project
     */
    public Movie(String title)
    {
        this.title = title;
        castList = new ArrayList<Actor>();
    }
    
    /**
     * A method that adds an Actor to the castList of the Movie
     * @param Actor the Actor object that is being added to the cast
     */
    public void addActor(Actor a)
    {
        castList.add(a);
    }

    /**
     * A getter method that returns the title of the Movie
     * @return  String  the title of the Movie
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * A getter method that returns the castList
     * @return  ArrayList   the list of the cast of the Movie 
     */
    public ArrayList<Actor> getCastList()
    {
        return this.castList;
    }

    /**
     * A setter method for the title of the Movie
     * @param   the title of the movie
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * Our version of the Bechdel Test, a Movie passes IFF 50% of the 
     * supporting cast is female and 50% of the leading cast is female.
     * @return  boolean true IFF the conditions are passed
     */
    public boolean runOurTest(){
        Actor temp = null;
        ArrayList<Actor> cL = this.getCastList();
        int leading=0; int fLeading=0; int supporting=0; int fSupporting=0;
        for(int i = 0; i < cL.size(); i++){
            temp = cL.get(i);
            if(temp.getLeading()){//this returns a boolean 1 for leading, 0 for supporting
                leading++;
                if(temp.getGender().equals("female")){
                    fLeading++;
                }
            }else{
                supporting++;
                if(temp.getGender().equals("female")){
                    fSupporting++;
                }
            }
        }
        if(fLeading >= (leading - fLeading) && (fSupporting >= (supporting -fSupporting))){
            return true;
        }
        return false;
    }
    
    /**
    * A public tester method.
    */
    public static void main(String [] args){
        System.out.println("Now testing the Movie Class");
        Movie tester = new Movie("Takis' Baby Videos");
        tester.addActor(new Actor("Takis","male",true));
        tester.addActor(new Actor("Takis' Dad","male",false));
        tester.addActor(new Actor("Takis' Mom","female",false));
        System.out.println("Should print false: " + tester.runOurTest());
        tester.addActor(new Actor("Takis' friend","female",true));
        System.out.println("Should print true: " + tester.runOurTest());
        
    }

}