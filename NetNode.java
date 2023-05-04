/**
 * This is the Node class
 */
public class NetNode {
    /**
     * This is the id variable for the node
     */
    private int id;
    /**
     * This is the name variable for the node
     */
    private String name;
    /**
     * This is the x_coordinate variable for the node
     */
    private double x_coordinate;
    /**
     * This is the y_coordinate variable for the node
     */
    private double y_coordinate;

    /**
     * This is the constructor for a node in the graph.
     * @param id This is the id of the node in the graph.
     * @param name This is the name of the node in the graph.
     * @param x_coordinate This is the x_coordinate of the node in the graph.
     * @param y_coordinate This is the y_coordinate of the node in teh graph.
     */
    public NetNode(int id,String name,double x_coordinate,double y_coordinate){
        this.id=id;
        this.name=name;
        this.x_coordinate=x_coordinate;
        this.y_coordinate=y_coordinate;  
    }
    
    /**
     * This is the setter method for Id variable.
     * @param id This is the id that is passed.
     */
    public void setId(int id){
        this.id=id;
    }
    
    /**
     * This is the setter moethod for name variable.
     * @param name The name variable that is passed.
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * This is the setter method for the x_coordinate.
     * @param x_coordinate The x coordiatne that is passed.
     */
    public void setX_coordinate(double x_coordinate){
        this.x_coordinate=x_coordinate;
    }
    /**
     * This is the setter method for the y_coordinate. 
     * @param y_coordinate This is the y_coordinate that is passed.
     */
     public void setY_coordinate(double y_coordinate){
        this.y_coordinate=y_coordinate;
    }

    /**
     * This is the getter for Id.
     * @return This is the id variable.
     */
    public int getId(){
        return id;
    }
    
    /**
     * This is the getter for name variable.
     * @return The name is returned.
     */
    public String getName(){
        return name;
    }
    /**
     * This is the getter for the x_coordiante.
     * @return The x_coordinate for the node is returned.
     */
    public double getX_coordinate(){
        return x_coordinate;
    }
    /**
     * This is the getter for the Y coordinate.
     * @return The y_coordinate for the node is returned.
     */
     public double getY_coordinate(){
        return y_coordinate;
    }
 }
