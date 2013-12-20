/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcom.modules.group;

import gcom.interfaces.IMember;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Praneeth
 */
public class Election {

    private Member initiater;
    private ArrayList<Member> members;
    private boolean[] participation;
    private int electionSize;

    public Election(Member initiater) {
        Group group = initiater.getParentGroup();
        HashMap<String, IMember> membersList = group.getMembersList();

        // make the initiater 1st in the list
        //membersList.remove(initiater);
        //membersList.add(0, initiater);
        electionSize = membersList.size();
        participation = new boolean[electionSize];
    }

    public void startElection() {
    }
}
