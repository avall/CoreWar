package com.gmail.s0o3r0a4.corewar.net;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.gmail.s0o3r0a4.corewar.net.node.Node;
import com.gmail.s0o3r0a4.corewar.net.node.NodeAddr;
import com.gmail.s0o3r0a4.corewar.net.node.Port;

import java.util.Comparator;
import java.util.HashMap;

public class NodesManager {
//    private Array<Node> nodes; // TODO: From now on most of the array should be the array from LibGdx
    private HashMap<NodeAddr, Node> nodes;
    private int shownNodeIndex;

    public NodesManager() {
    }

    public NodesManager(HashMap<NodeAddr, Node> nodes, int shownNodeIndex) {
        this.nodes = nodes;
        this.shownNodeIndex = shownNodeIndex;
        sort();
    }

    public void initNodes() {

    }

    public void initGame() {

    }

    public void cycle() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).cycle();
        }

        send();
        flush();
    }

    public void newNode(NodeAddr nodeAddr, Node node) {
        nodes.put(nodeAddr, node);
    }

    public void save(String filename) {

    }

    public void load(String filename) {

    }
    
    public Node getShownNode() {
        return nodes.get(shownNodeIndex);
    }

    public void sort() {
//        nodes.sort(new Comparator<Node>() {
//            @Override
//            public int compare(Node node1, Node node2) {
//                if (node1.getStartupTime() > node2.getStartupTime())
//                    return 1;
//                else
//                    return -1;
//            }
//        });

    }

    public void flush() {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            if (node.isReceving()) {
                int j = node.getReceivingPort();
                node.getPort(j).flush();
                node.closeReceiving();
            }
        }
    }

    public void send() {
        for (int i = 0; i < nodes.size(); i++) {
            Node node1 = nodes.get(i);
            if (node1.isSending()) {
                Port port = node1.getPort(node1.getSendingPort());
                Node node2 = port.getConnectedNode(node1);

                int node2PortID = port.getPortID(node2);

//                int j = nodes.indexOf(node2, false);

                node2 = nodes.get(node2.getAddr());
//                int j = nodes.get(nodeAddr.toString());

                node2.getPort(node2PortID).writeOut(node2, port.readIn(node1));
                node2.openReceiving(node2PortID);
                node1.closeSending();
            }
        }
    }

    public void invite() {
        for (int i = 0; i < nodes.size(); i++) {
            Node node1 = nodes.get(i);
            if (node1.isInviting()) {
                NodeAddr nodeAddr = node1.getInvitingAddr();
                Node node2 = nodes.get(nodeAddr);
                node1.invite(node2);
                node2.invitedBy(node1);
            }
        }
    }
}
