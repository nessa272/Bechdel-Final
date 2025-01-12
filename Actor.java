/**
 * Class representing an actor in a movie.
 *
 * @author Isabelle Whetsel
 * @author Dorrie Peters
 * @author Nessa Tong
 * @version 12/3/23
 */

 public class Actor{
    private String name;
    private String gender;
    private boolean leading;
    //A new Actor is created for every Movie, this variables tracks if
    //they are leading in that particular Movie
    //The redundancy of this is avoided by using Strings as the vertices
    //in the HollywoodGraph implementation

    /**
     * constructor for the actor class
     * @param name the name of the actor
     * @param gender the gender of the actor
     * @param leading whether the actor is leading or supporting in this Movie
     */
    public Actor(String name, String gender, boolean leading){
        this.name = name;
        this.gender = gender;
        this.leading = leading;
    }

    /**
     * getter for the name attribute
     * @return the name of the actor
     */
    public String getName(){
        return this.name;
    }

    /**
     * getter for the gender attribute
     * @return the gender of the actor
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * getter for the leading attribute
     * @return true if the actor is leading, false if not
     */
    public boolean getLeading(){
        return this.leading;
    }

    /**
     * compares two actors to see if they are the same
     * @param other the actor to compare to
     * @return true if they have the same name, false if they have different names
     */
    public boolean compareTo(Actor other){
        return (this.getName().equals(other.getName()));
    }

    /**
    * A public tester method.
    */
    public static void main(String [] args){
        System.out.println("Now testing the Actor class");
        Actor takis1 = new Actor("Takis", "male", true);
        System.out.println("Testing getName(), should print Takis. Output: " + takis1.getName());
        System.out.println("Testing getGender(), should print male. Output: " + takis1.getGender());
        System.out.println("Testing getLeading(), should print true. Output: " + takis1.getLeading());
        Actor takis2 = new Actor("Takis", "male", false);
        Actor stella = new Actor("Stella", "female", true);
        System.out.println("Testing compareTo(), should print true. Output: " + takis1.compareTo(takis2)); //who is the true Takis
        System.out.println("Testing compareTo(), should print false. Output: " + takis1.compareTo(stella));
    }
}
