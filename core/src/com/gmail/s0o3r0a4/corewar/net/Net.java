package com.gmail.s0o3r0a4.corewar.net;

//import edu.uci.ics.jung.algorithms.layout.FRLayout;
//import edu.uci.ics.jung.graph.Graph;

public class Net {
//    private Graph<Node, Port> graph;
//    private FRLayout<Node, Port> layout;
//    JGraphModelAdapter<Node, Port> adapter = new JGraphModelFacade((GraphModel) graph);
//    JGraph jgraph = new JGraph(adapter);

//    private ArrayList<Node> nodes;

//    private Node frontNode;
//    private Node enemyFrontNode;
//    private Node thirdNode;
//
//    private ArrayList<Port> ports;
//
//    private Port frontPort;
//    private Port newPort;

    public float x[], y[];

    public Net(float width, float height) {

//        this.graph = new SparseMultigraph<Node, Port>();
//
//        this.frontNode = new Node();
//        this.enemyFrontNode = new Node();
//        this.thirdNode = new Node();
//
//        this.nodes = new ArrayList<Node>();
//
//        this.nodes.add(this.frontNode);
//        this.nodes.add(this.enemyFrontNode);
//        this.nodes.add(this.thirdNode);
//
//        x = new float[3];
//        y = new float[3];
//
//        this.graph.addVertex(this.frontNode); // 0
//        this.graph.addVertex(this.enemyFrontNode); // 1
//        this.graph.addVertex(this.thirdNode); // 2
//
//        this.ports = new ArrayList<Port>();
//
//        this.ports.add(new Port(1, 1, 2));
//
//        this.graph.addEdge(this.ports.get(0), this.frontNode, this.enemyFrontNode);
//        this.graph.addEdge(this.ports.get(1), this.enemyFrontNode, this.thirdNode);
//        this.ports.add(graph.addEdge(this.frontNode, this.enemyFrontNode));
//        this.ports.add(this.graph.addEdge(this.enemyFrontNode, this.thirdNode));
//
//        this.layout = new FRLayout<Node, Port>(this.graph);
//        this.layout.setSize();
//
//        this.layout.initialize();
//
//        this.layout.setLocation(frontNode, width / 2, height / 3);
//        this.layout.setLocation(enemyFrontNode, width / 2, height * 2 /3);
    }

    public void update() {
//        layout.setMaxIterations(1);
//        layout.step();

//        for (int i = 0; i < x.length; i++)
//        {
//            x[i] = (float)layout.getX(nodes.get(i));
//            y[i] = (float)layout.getY(nodes.get(i));
//        }
//        x = layout.getX(frontNode);
//        y = layout.getY(frontNode);
//
//        eneX = layout.getX(enemyFrontNode);
//        eneY = layout.getY(enemyFrontNode);
//
//        thirdX = layout.getX(thirdNode);
//        thirdY = layout.getY(thirdNode);
    }

    public void initialize() {
//        layout.initialize();
    }

//    public Port getPort(int portID)
//    {
//        return ports.get(portID);
//    }

//    public int getPortsSize()
//    {
//        return ports.size();
//    }

//    public double getNodeX(int nodeID)
//    {
//        return layout.getX(nodes.get(nodeID));
//    }
//
//    public double getNodeY(int nodeID)
//    {
//        return layout.getY(nodes.get(nodeID));
//    }
//
//    public void setLocation(int nodeID, float x, float y)
//    {
//        this.layout.setLocation(nodes.get(nodeID), (double) x, (double) y);
//    }

    public void addEdge() {

    }

    public void removeEdge() {

    }

    public void addNode() {

    }

    public void removeNode() {

    }


}
