package com.gmail.s0o3r0a4.corewar.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.gmail.s0o3r0a4.corewar.net.node.Node;
import com.gmail.s0o3r0a4.corewar.net.node.NodeAddr;
import com.gmail.s0o3r0a4.corewar.net.node.Port;
import com.gmail.s0o3r0a4.corewar.net.node.core.Instruction;

import java.util.Collections;
import java.util.Comparator;

import static javax.swing.UIManager.get;

public class NodeManager {
    private Array<Node> nodes; // TODO: From now on most of the array should be the array from LibGdx
    private int shownNodeIndex;

    public NodeManager(Array<Node> nodes, int shownNodeIndex) {
        this.nodes = nodes;
        this.shownNodeIndex = shownNodeIndex;
        sort();
    }

    public void cycle() {
        for (int i = 0; i < nodes.size; i++) {
            nodes.get(i).cycle();
        }

        send();
        flush();
    }


    public Node getShownNode() {
        return nodes.get(shownNodeIndex);
    }

    public void sort() {
        nodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1.getStartupTime() > node2.getStartupTime())
                    return 1;
                else
                    return -1;
            }
        });
    }

    public void flush() {
        for (int i = 0; i < nodes.size; i++) {
            for (int j = 0; j < nodes.get(i).getPortsSize(); j++) {
                nodes.get(i).getPort(j).flush();
            }
        }
    }

    public void send() {
        for (int i = 0; i < nodes.size; i++) {
            Node node1 = nodes.get(i);
            if (node1.isSending()) {
                Port port = node1.getPort(node1.getSendingPort());
                Node node2 = port.getConnectedNode(node1);

                int node2PortID = port.getPortID(node2);

                int j = nodes.indexOf(node2, false);

                nodes.get(j).getPort(node2PortID).writeOut(node2, port.readIn(node1));
            }
        }
    }
}
