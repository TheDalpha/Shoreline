/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;
/**
 *
 * @author ollie
 */
public class Loggin
{
    private int traceId;  
    private String username;
    private String action;
    private String date; 
    private String filename;
    private String error;

    // Get the error
    public String getError() {
        return error;
    }

    // Sets the error
    public void setError(String error) {
        this.error = error;
    }
 
    // Get the file name
    public String getFilename() {
        return filename;
    }

    // Sets the file name
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    // Get the id of the trace
    public int getTraceId()
    {
        return traceId;
    }

    // Sets the id of the trace
    public void setTraceId(int traceId)
    {
        this.traceId = traceId;
    }

    // Get the username
    public String getUsername()
    {
        return username;
    }

    // Sets the username
    public void setUsername(String username)
    {
        this.username = username;
    }

    // Get the action
    public String getAction()
    {
        return action;
    }

    // Sets the action
    public void setAction(String action)
    {
        this.action = action;
    }

    // Get the date
    public String getDate()
    {
        return date;
    }

    // Sets the date
    public void setDate(String date)
    {
        this.date = date;
    }
    
}
