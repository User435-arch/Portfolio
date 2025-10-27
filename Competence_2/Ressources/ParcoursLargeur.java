import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ParcoursLargeur
{
	public static void main (String[] args)
	{
		SingleGraph graph = Generateur.grapheAleatoire(Integer.parseInt(args[0]), Double.parseDouble(args[1]));
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		List<Node> lstNode = new ArrayList<Node>();
		Node n = Toolkit.randomNode(graph);
		
		lstNode.add(n);
		
		int num = 1;
		while(! lstNode.isEmpty())
		{
			System.out.println("\nnum " + num );
			
			Node u = lstNode.remove(0);
			if (!u.hasAttribute("marque"))
			{
				u.setAttribute("marque", true);

				Node pi = (Node) u.getAttribute("predecesseur");
				if(pi!= null)
					u.getEdgeBetween(pi).setAttribute("ui.style", "fill-color:green;");
					
				num++;
				
				Iterator<Node> lesVoisins = u.getNeighborNodeIterator();
				
				while (lesVoisins.hasNext())
				{
					Node s = lesVoisins.next();
					if (! s.hasAttribute("marque"))
					{
						s.setAttribute("ui.style", "fill-color :green;");
						pi = (Node) s.getAttribute("predecesseur");
						
						if(pi == null)
						{
							s.setAttribute("label", "(" + s.getId() + ")");
							s.setAttribute("ui.style", "fill-color:orange;");
						}
					}
				}
			}
		}

		graph.display();

	}
}
