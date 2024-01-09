package Main;

public class Greetings
{
     private String greetings; 

     public Greetings(String greeting)
     {
          greetings = greeting;
          System.out.println(greetings);
     } 

     public void hello()
     {
          System.out.println("Hello");
     } 

     public void translate()
     {
          greetings = "Hola";
     } 

     public void changeGreeting(String greeting)
     {
          greetings = greeting;
     } 

     public void greeting()
     {
          System.out.println(greetings);
     }
}