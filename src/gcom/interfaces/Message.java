/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcom.interfaces;

import gcom.modules.group.Member;
import java.io.Serializable;

/**
 *
 * @author samtube405
 */
public interface Message extends Serializable{
    public static enum TYPE_MESSAGE {
		APPLICATION, JOINREQUEST, PARTREQUEST, PARTRESPONSE,
		REJECT, WELCOME, GOTMEMBER, LOSTMEMBER,
		ELECTION, CLOSE
	};
    public Serializable getMessage();
    
    public TYPE_MESSAGE getMessageType();
    
    public String getGroupName();

    public Member getSource();
    
    public void setDestination(Member destination);
    
    public Member getDestination();
    
}
