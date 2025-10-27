import java.io.FileInputStream;
import java.util.Scanner;


public class Plateforme
{
	private  int     posLig;
	private  int     posCol;
	private char[][] grille = {};
	private boolean cleBleue = false; // Déclaration de cleBleue
	private boolean cleVerte = false; // Déclaration de cleVerte
	private boolean cleRouge = false; // Déclaration de cleRouge
	private int      numNiveau = 1;

	public Plateforme() 
    {
        this.posLig = 0;
        this.posCol = 0;
        chargerNiveau();  // Charger le niveau au début
    }

	
	public void setnumNiveau(int numNiveau)                   {this.numNiveau = numNiveau;}

	public int getnumNiveau()
    {
        return this.numNiveau;
    }

    // Méthode appelée pour recommencer
    public void recommencer() 
    {
        chargerNiveau();

    }

    // Méthode pour charger le niveau à partir du fichier
    private void chargerNiveau() 
    {
        try 
        {
            Scanner sc = new Scanner(new FileInputStream("data/niveau_0" + numNiveau + ".data"), "UTF8");

            int cptLig = 0;
            int cptCol = 0;

            while (sc.hasNextLine()) 
            {
                String p = sc.nextLine();
                cptLig++;
                cptCol = p.length();
            }

 
            this.grille = new char[cptLig][cptCol];
            sc.close();

            // Recharger le contenu du fichier dans la grille et initialiser la position du robot
            sc = new Scanner(new FileInputStream("data/niveau_0" + numNiveau + ".data"), "UTF8");
            int numLigne = 0;
            
            while (sc.hasNextLine()) 
            {
                String ligneActuel = sc.nextLine();

                for (int cpt = 0; cpt < this.grille[0].length; cpt++) 
                {
                    grille[numLigne][cpt] = ligneActuel.charAt(cpt);

    
                    if (ligneActuel.charAt(cpt) == '+') 
                    {
                        this.posLig = numLigne;
                        this.posCol = cpt;
                        grille[numLigne][cpt] = ' ';  // Remplacer le '+' par un espace vide
                    }
                }
                numLigne++;
            }
            sc.close();

        } 
        catch (Exception e) {e.printStackTrace();}
		cleBleue = false;
		cleRouge = false;
		cleVerte = false;
    }

			
	public void deplacer (char direction)
	{
		int deltaLig = 0;
		int deltaCol = 0;

		if (estSortie()) 
		{
			
			try{ Thread.sleep(50); } catch (InterruptedException ex){}
			System.out.println("Félicitations, vous avez atteint la sortie !");
			System.exit(0); // Arrête le programme
		}

		switch (direction) 
		{
			case 'O': deltaCol = -1; break;
			case 'E': deltaCol = +1; break;
			case 'P': deltaCol =  0; break;  
		}

		if (this.grille[this.posLig][this.posCol] == '#')
		{
			switch (direction) 
			{
				case 'N': deltaLig = - 1; break;
				case 'S': deltaLig = + 1; break;
			
				default:
					break;
			}
		}



		/*if ((direction == 'E') && this.posCol > 0 && this.grille[this.posLig - 1][this.posCol + 1] == ' ')
		{
			deltaLig = -1;
			deltaCol = -1;
		}*/ 

		int futurLig = this.posLig + deltaLig; 
		int futurCol = this.posCol + deltaCol;


		
		if (futurLig < 0 ) 
		{
			futurLig = this.grille.length - 1;
		}

		if ( futurLig > this.grille.length - 1 )
		{
			futurLig = 0;
			System.out.println ("iohuhihuhui");
		}


		if ( futurCol < 0 ) 
		{
			futurCol = this.grille[0].length - 1;
		}

		if ( futurCol >= this.grille[0].length  )
		{
			futurCol = 0;  // Revenir à la première colonne
		}
		
		
		
		// Vérifier si on peut escalader un obstacle de 1 de hauteur

		/*
		 * Si le déplacement est horizontal ('O' ou 'E') et qu'il y a un
		 * obstacle de 1 de hauteur ('='), le personnage peut escalader
		 * l'obstacle si la case au-dessus est vide (' '). 
		 */

		if ((direction == 'O' || direction == 'E')            && 
			 this.grille[this.posLig][futurCol] == '='        && 
			 this.posLig > 0                                  &&
			 this.grille[this.posLig - 1][this.posCol] == ' ' &&  
			 this.grille[this.posLig - 1][futurCol]    == ' '    )
			
		{
			futurLig = this.posLig - 1;
		}
		
		
		if ( this.grille[futurLig][futurCol] == ' '               ||
			 this.grille[futurLig][futurCol] == '@'               ||
			 this.grille[futurLig][futurCol] == '#'               ||  
			 Character.isLowerCase(this.grille[futurLig][futurCol] ))
		{
			this.posLig = futurLig;
			this.posCol = futurCol;

			
			
		}
		
	}

	public int getPosLig () { return this.posLig; }
    public int getPosCol () { return this.posCol; }
    public void setPosLig (int posLig) { this.posLig = posLig; }


	public int getNbLignes ()
	{
		return this.grille.length;

	}

	public int getNbColonnes ()
	{
		return this.grille[0].length;
	} 

	public char getCase (int lig, int col )
	{
		return this.grille[lig][col];
	}

	public boolean estSortie()
	{
		return this.grille[this.posLig][this.posCol] == '@';  // retourne vrai si l'objet est sur la case de sortie
	}

	/*public void tomber()
    {
        // Répète la chute tant qu'il y a de l'espace vide ou une lettre en
        // minuscule sous le personnage
        while (true)
        {
            int futurLig = this.posLig + 1;

            // Vérifie si le personnage peut descendre
            if (futurLig < this.grille.length && (this.grille[futurLig][this.posCol] == ' '
                    || Character.isLowerCase(this.grille[futurLig][this.posCol])))
            {
                System.out.println("Chute: de " + this.posLig + " à " + futurLig); // Message
                                                                                    // de
                                                                                    // débogage
                this.posLig = futurLig; // Descend d'une case
            }
            else
            {
                System.out.println("Arrêt de la chute à la ligne " + this.posLig); // Message de débogage
                // Arrête la chute si on atteint un sol ou une échelle
                break;
            }
        }
    }*/

	
	
	public boolean getCleBleu()
	{
		return cleBleue;
	}

	public boolean getCleVerte()
	{
		return cleVerte;
	}

	public boolean getCleRouge()
	{
		return cleRouge;
	}
	
	public void setCase(int ligne, int colonne)
	{
		if (ligne >= 0 && ligne < this.grille.length && colonne >= 0 && colonne < grille[0].length)
		{

			grille[ligne][colonne] = ' ';
		}
	}

	public boolean enleverCleBleue()
	{
		if (this.grille[this.posLig][this.posCol] == 'b')
		{
			cleBleue = true; // Marque que la clé bleue est collectée
			return true;
		}
		return false;
	}

	public boolean enleverCleRouge()
	{
		if (this.grille[this.posLig][this.posCol] == 'r')
		{
			cleRouge = true; // Marque que la clé rouge est collectée
			return true;
		}
		return false;

	}

	public boolean enleverCleVerte()
	{
		if (this.grille[this.posLig][this.posCol] == 'v')
		{
			cleVerte = true; // Marque que la clé verte est collectée
			return true;
		}
		return false;
	}

	public boolean presencePersonnage (int lig, int col) //Indique si le personnage est présent à cette case
	{
		if (lig == this.posLig && col == this.posCol)
		{
			return true;
		}
		return false; 
	}
}
