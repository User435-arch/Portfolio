import org.graphstream.graph.implementations.SingleGraph;


public class Generateur
{
    public static SingleGraph grapheAleatoire (int nbSommets, double proba)
    {
        SingleGraph graph = new SingleGraph("Graphe Complet");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

			for(int i=0; i<nbSommets; i++)
				graph.addNode(""+i);
			
        for(int i=0; i < nbSommets; i++)
			for(int j=0; j < nbSommets; j++)
				if(Math.random() < proba && !graph.getNode(""+i).hasEdgeBetween(""+j) )
				{
					graph.addEdge("("+i+","+j+")", ""+i, ""+j);
				}
		
        return graph;
    }
}
