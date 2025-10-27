import ihmgui.FrameGrille;
import ihmgui.Controle;

public class Controleur extends Controle 
{
	private Plateforme   metier   ;
	private FrameGrille  frame    ;
	private String	   sensAncien = "droite";
	private boolean    enChute    = false;

	private String       sens = "";

	public Controleur()
	{
		metier      = new Plateforme();
		frame       = new FrameGrille ( this );
		

		frame.setSize    ( 1200, 1060 );
		frame.setLocation( 100, 10             );
		frame.setTitle   ("Platforme"           );
		frame.setVisible (true                   ); 
	}
	
	
	public String setBouton(int numBtn)
    {
        String lib;

        switch ( numBtn )
        {
            case 0  :  lib = "Recommencer"      ; break ; 
            case 1  :  lib = "Level suivant"   ; break ;
            case 2  :  lib = "Level précédent" ; break ;
            default :  lib = null; 
        }

        return lib;
    }

	public void bouton(int numBtn) 
    {

        if (numBtn == 0)              // Bouton "Recommencer"
        {                     
            metier.recommencer();   // Réinitialise le niveau
            frame.majIHM();        // Met à jour l'interface graphique
        }
        if (numBtn == 1)
        {
            if (metier.getnumNiveau() < 5)
            {
                metier.setnumNiveau(metier.getnumNiveau() + 1 );
                metier.recommencer();
                frame.majIHM(); 
            }
            else
            {
                System.out.println("Vous avez atteint le niveau maximum !!!");
            }
        }
        if (numBtn == 2 )
        {
            if (metier.getnumNiveau() > 1)
            {
                metier.setnumNiveau(metier.getnumNiveau() - 1 );
                metier.recommencer();
                frame.majIHM(); 
            }
            else
            {
                System.out.println("Vous avez atteint le niveau 0 !!!");
            }

        }
    }

	public void jouer (String touche)
	{
		if (touche.equals("FL-H")) { metier.deplacer('N'); }
		
		if (touche.equals("FL-G"))
		{
			
			this.sens = "gauche";
		
		/*--------------------------------------------------------------------------------------*/
		/*
		 * Ne change pas l'orientation image du perso si SensAncien n'est pas
		 * différent de sens
		 */
		/*--------------------------------------------------------------------------------------*/

			if (this.sensAncien != this.sens)
				{
					this.sensAncien = estTourner(this.sensAncien , this.sens);
					
				}
				else
				{
					metier.deplacer('O');
					
				}
			}
        

		if (touche.equals("FL-B")) { metier.deplacer('S'); }

		if (touche.equals("FL-D"))
		{
			this.sens = "droite";

			/*--------------------------------------------------------------------------------------*/
			/*
			 * Ne change pas l'orientation image du perso si SensAncien n'est
			 * pas différent de sens
			 */
			/*--------------------------------------------------------------------------------------*/
			if (this.sensAncien != this.sens)
			{
				this.sensAncien = estTourner(this.sensAncien, this.sens);
				
			}
			else
			{
				metier.deplacer('E');
				
			}
		}
		
    
		chute();
		
		frame.majIHM();
	}


	public void chute()
    {
        new Thread(() -> {
            enChute = true; // Indique que le personnage est en chute
            frame.majIHM();

            // Appelle la méthode tomber de Plateforme
            while (true)
            {
                int futurLig = metier.getPosLig() + 1;

                // Vérifie si le personnage peut descendre
                if (futurLig < metier.getNbLignes() && (metier.getCase(futurLig, metier.getPosCol()) == ' '
                        || Character.isLowerCase(metier.getCase(futurLig, metier.getPosCol()))))
                {
                    metier.setPosLig(futurLig); // Descend d'une case
                    frame.majIHM(); // Met à jour l'IHM pour montrer la chute
                    try
                    {
                        Thread.sleep(150); // Délai pour une meilleure
                                            // visibilité
                    } catch (Exception ex)
                    {
                    }
                }
                else
                {
                    // Arrête la chute si on atteint un sol ou une échelle
                    break;
                }
            }

            enChute = false; // Indique que le personnage a terminé sa chute
            frame.majIHM();
        }).start();
    }
	
	
	public String setImage(int ligne, int colonne, int couche)
	{
		char symbole;

		String rep = "./images/asterix/"; // raccourci pour le répertoire image.
		String nom = "";
		String sImage = null;
		symbole = metier.getCase(ligne, colonne); //Vérification du symbole de la case

		

			if (couche == 0)
			{

				symbole = metier.getCase(ligne, colonne);

				if (symbole == 'v' && metier.enleverCleVerte())
				{
					metier.setCase(ligne, colonne);
					sImage = rep + "cleverte.png"; // Remplace la cle par un espace
				}
				else 
					if (symbole == 'v')
					{
						sImage = rep + "cleverte.png"; // Affiche la cle
					}

				// Si la clé bleue est collectée, remplacer la porte par un
				// espace
				if (symbole == 'V' && metier.getCleVerte())
				{
					metier.setCase(ligne, colonne); // Remplace la porte par un
													// espace
				}
				else 
					if (symbole == 'V')
					{
						sImage = rep + "porteverte.png"; // Affiche la porte
					}

				
				
				
				
				
					if (symbole == 'r' && metier.enleverCleRouge())
				{
					metier.setCase(ligne, colonne);
					sImage = rep + "clerouge.png"; // Remplace la cle par un
													// espace
				}
				else 
					if (symbole == 'r')
					{
						sImage = rep + "clerouge.png"; // Affiche la cle
					}

				// Si la clé bleue est collectée, remplacer la porte par un
				// espace
				if (symbole == 'R' && metier.getCleRouge())
				{
					metier.setCase(ligne, colonne); // Remplace la porte par un
													// espace
				}
				else 
					if (symbole == 'R')
					{
						sImage = rep + "porterouge.png"; // Affiche la porte
					}

				// Remplacement de la clé bleue par un espace dans l'image si
				// elle est récupérée
				if (symbole == 'b' && metier.enleverCleBleue())
				{
					metier.setCase(ligne, colonne);
					sImage = rep + "clebleue.png"; // Remplace la cle par un
													// espace
				}
				
				else if (symbole == 'b')
					{
						sImage = rep + "clebleue.png"; // Affiche la cle
					}

				// Si la clé bleue est collectée, remplacer la porte par un
				// espace
				if (symbole == 'B' && metier.getCleBleu())
				{
					metier.setCase(ligne, colonne); // Remplace la porte par un
													// espace
				}
				else 
					if (symbole == 'B')
					{
						sImage = rep + "portebleue.png"; // Affiche la porte
					}

					
				if (symbole == '=')
				{
					if (colonne - 1 >= 0 && metier.getCase(ligne, colonne - 1) == '=')
					{
						nom += "o";
					}

					if (colonne + 1 <= setNbColonne() - 1 && metier.getCase(ligne, colonne + 1) == '=')
					{
						nom += "e";
					}

					switch (nom)
					{
					case "oe" -> sImage = rep + "sol.png";
					case "o"  -> sImage = rep + "sol_droit.png";
					case "e"  -> sImage = rep + "sol_gauche.png";
					default   -> sImage = rep + "sol_gauche_droit.png";
					}
				}

				if (symbole == '@')
					sImage = rep + "sortie.png";

				if (symbole == '#')
					sImage = rep + "echelle.png";

				if ((ligne - 1 >= 0 && ( metier.getCase(ligne - 1, colonne) == '=' ||
					 metier.getCase(ligne - 1, colonne) == ' ' ) && symbole == '#') )
				{
					sImage = rep + "echelle_haut.png";
				}

				if (ligne - 1 < 0 && symbole == '#')
				{
					sImage = rep + "echelle_haut.png";
				}

				if (symbole == ' ')
				{
					sImage = rep + "vide52.png";
				}

			}

		

		

	

		if (couche == 1)
		{
			

			if (metier.presencePersonnage(ligne, colonne))
            {
                if (enChute)
                {
                    sImage = rep + "pers_tombe.png"; // Image du personnage
                                                        // pendant la chute
                }
                else
                {
                    if (sens.equals("droite"))
                        sImage = rep + "pers_droit.png";
                    else if (sens.equals("gauche"))
                        sImage = rep + "pers_gauche.png";
                    else
                        sImage = rep + "pers_droit.png";

                    if (metier.getCase(ligne, colonne) == '#')
                    {
                        if (ligne % 2 == 0)
                        {
                            sImage = rep + "pers_monte1.png";
                        }
                        else if (ligne % 2 != 0)
                        {
                            sImage = rep + "pers_monte2.png";
                        }
                    }
                }
            }

			
			else 
				sImage = "";

			

			

		}

		return sImage;
	}

	// Retourne le nouveau sensAncien et qui ne modifie pas le DeltaCol (0) dans
	// la methode metier.deplacer
	public String estTourner(String sensAncien, String sens)
	{

		if (sensAncien.equals("gauche") && sens.equals("droite"))
		{
			sens = "droite";
			metier.deplacer('P');
		}
		else if (sensAncien.equals("droit") && sens.equals("gauche"))
		{
			sens = "gauche";
			metier.deplacer('P');
		}

		return sens;
	}

	public int setNbLigne()                    {return metier.getNbLignes();}

	public int setNbColonne()                  {return metier.getNbColonnes();}
 
	public boolean setNumLigneColonne()        {return true;}

	public int setLargeurImg()                 {return 52;}

	public String setFondGrille()              {return "./images/asterix/fond.png";}

	public static void main(String[] arg)
	{
		Controleur exemple = new Controleur();
	}
	
}
