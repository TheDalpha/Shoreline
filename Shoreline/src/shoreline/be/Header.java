/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

/**
 *
 * @author Jesper Riis
 */
public class Header {
    
    String headerName;
    int headerIndex;
    int headerId;
    int listIndex;
    String attName;

    // Get the header id
    public int getHeaderId() {
        return headerId;
    }

    // Set the header id
    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    // Get the index of where it is in the list
    public int getListIndex() {
        return listIndex;
    }

    // Sets the index of where it is in the list
    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    // Get the attribute name
    public String getAttName() {
        return attName;
    }

    // Sets the attribute name
    public void setAttName(String attName) {
        this.attName = attName;
    }

    // Get header name
    public String getHeaderName() {
        return headerName;
    }

    // Sets the header name
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    // Get the header index of where it is in the file
    public int getHeaderIndex() {
        return headerIndex;
    }

    // Sets the header index of where it is in the file
    public void setHeaderIndex(int headerIndex) {
        this.headerIndex = headerIndex;
    }

    @Override
    public String toString() {
        return headerName + headerIndex;
    }
    
}
