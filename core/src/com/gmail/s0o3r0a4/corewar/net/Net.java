package com.gmail.s0o3r0a4.corewar.net;

import com.gmail.s0o3r0a4.corewar.net.node.Node;
import com.gmail.s0o3r0a4.corewar.net.node.Port;
import com.sun.security.auth.NTDomainPrincipal;

import java.awt.Dimension;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class Net
{
    private Graph<Node, Port> graph;
    private FRLayout<Node, Port> layout;
    private Node frontNode;
    private Node enemyFrontNode;
    private Node thirdNode;

    public double x, y;
    public double eneX, eneY;

    public double thirdX, thirdY;

    public Net(float width, float height)
    {
        this.graph = new SparseMultigraph<Node, Port>();
        this.frontNode = new Node();
        this.enemyFrontNode = new Node();
        this.thirdNode = new Node();

        this.graph.addVertex(this.frontNode);
        this.graph.addVertex(this.enemyFrontNode);
        this.graph.addVertex(this.thirdNode);

        Port frontPort = new Port(0);
        Port newPort = new Port(1);

        this.graph.addEdge(frontPort, this.frontNode, this.enemyFrontNode);
        this.graph.addEdge(newPort, this.frontNode, this.thirdNode);


        this.layout = new FRLayout<Node, Port>(graph);
        this.layout.setSize(new Dimension((int)width, (int)height));
        this.layout.initialize();

        this.layout.setLocation(frontNode, width / 2, height / 3);
        this.layout.setLocation(enemyFrontNode, width / 2, height /3 + 100);
    }

    public void update()
    {
        layout.setMaxIterations(1);
        layout.step();
        x = layout.getX(frontNode);
        y = layout.getY(frontNode);

        eneX = layout.getX(enemyFrontNode);
        eneY = layout.getY(enemyFrontNode);

        thirdX = layout.getX(thirdNode);
        thirdY = layout.getY(thirdNode);
    }

    public void addEdge()
    {

    }

    public void removeEdge()
    {

    }

    public void addNode()
    {

    }

    public void removeNode()
    {

    }


}
