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
    
    

    public int getTraceId()
    {
        return traceId;
    }

    public void setTraceId(int traceId)
    {
        this.traceId = traceId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
    
}
