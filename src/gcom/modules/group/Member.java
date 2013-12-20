/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcom.modules.group;

import gcom.interfaces.IMember;
import gcom.interfaces.IMessage;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author praneeth
 */
public class Member implements IMember{

    private String name;
    private int identifier;
    private Group parentGroup;
    private boolean isElectionParticipant;
    private boolean isGroupLeader;
    private HashMap<String, Member> members;
    
    

    public Member(String name, Group parent) {
        this.parentGroup = parent;
        this.name = name;
        identifier = new Random().nextInt(100) + 1;
    }

   

    public void callElection() {
        parentGroup.initiateElection(this);
        ElectionMessage em = new ElectionMessage(this);
    }

    public void vote() {
    }

    public void start() {
    }

    /**
     * @return the parentGroup
     */
    public Group getParentGroup() {
        return parentGroup;
    }

    /**
     * @param parentGroup the parentGroup to set
     */
    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
        //System.out.println(this.getName()+" Mem count: "+this.parentGroup.getMemberCount());
    }

    /**
     * @return the id
     */
    public String getName() {
        return name;
    }

    /**
     * @param id the id to set
     */
    public void setName(String id) {
        this.name = id;
    }

    /**
     * @return the identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the isElectionParticipant
     */
    public boolean isElectionParticipant() {
        return isElectionParticipant;
    }

    /**
     * @param isElectionParticipant the isElectionParticipant to set
     */
    public void setElectionParticipant(boolean isElectionParticipant) {
        this.isElectionParticipant = isElectionParticipant;
    }

    /**
     * @return the isGroupLeader
     */
    public boolean isGroupLeader() {
        return isGroupLeader;
    }

    /**
     * @param isGroupLeader the isGroupLeader to set
     */
    public void setGroupLeader(boolean isGroupLeader) {
        this.isGroupLeader = isGroupLeader;
    }

    public IMember sendRequest(Message message) throws RemoteException, AccessException {
        //Member m = new Member(message.getParams().get(1), this.parentGroup);
        //m.setParentGroup(this.parentGroup);
        IMember m = message.getSource();

        if (message.getMessageType() == IMessage.TYPE_MESSAGE.JOINREQUEST) {
            try {
                this.parentGroup.addMember(m);
                m.setParentGroup(this.parentGroup);

                multicast();

            } catch (GroupManagementException ex) {
                Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return m;
    }

    public void multicast() throws RemoteException, AccessException, NotBoundException {
        HashMap<String, IMember> membersList = this.parentGroup.getMembersList();
        for (String key : membersList.keySet()) {
            //RMIServer srv = new RMIServer("localhost", 1099);
            //srv.start();
            //IMember imem = srv.regMemLookUp(m.getName());
            //m.updateGroup(this.parentGroup);
            //System.out.println("MEM OBJECT TYPE: "+(m instanceof Remote));
            //System.out.println("I AM LEADER PROCESS: "+this.getName());
            //m.setParentGroup(this.parentGroup);
            IMember m=membersList.get(key);             
            m.updateGroup(this.parentGroup);
            //System.out.println(m.getName()+" My Parent count: "+m.getParentGroup().getMemberCount());
           
        }
    }

    public void updateGroup(Group parentGroup) throws RemoteException {
        this.parentGroup = parentGroup;        
        System.out.println(this.getName() + " -> Update Group Message: New member count: " + this.parentGroup.getMemberCount());
    }

    public void updateMember(Member member) throws RemoteException {
        member.setParentGroup(this.parentGroup);
    }

 
}
