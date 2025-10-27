public class CalendrierEx2
{
   public static void main (String[] a)
   {
      /*-----------------------------*/
		/* Déclaration des variables   */
		/*-----------------------------*/
      
		int    nbJour;
		int [] tabDate;
		

		/*-----------------------------*/
		/* Instructions                */
		/*-----------------------------*/
		tabDate = new int[3];
		nbJour = 1;
		
		long debut = System.nanoTime();
		
		
		 // On teste le passage de 1900 à 2000
		
			while (nbJour < 36525) //36525 jours pour passer du 1/1/1900 au 1/1/2000
			{
				nbJour++;
			}
			
			tabDate = Outils.jour2Date(nbJour); 
		
			
		
		long fin = System.nanoTime();
		System.out.println((fin-debut) + " nanosecondes");
		
		System.out.println("le " + nbJour + " ème jour correspond au " + tabDate[0] + '/' +
			                   tabDate[1] + '/' + tabDate[2]); //retourne la date à partir du jour
	}
	
	public static boolean estBissextile (int annee)
	{
		if ( (annee % 4 == 0 && annee % 100 != 0) || (annee % 400 == 0) )
		{
			return true;
		}
		return false;
	}
	
	public static int dureeAnnee(int annee)
	{
		//Variable
		int nbJour;
		
		//Instructions
		nbJour = 0;
		if (estBissextile(annee))
		{
			nbJour = 366;
		}
		else
		{
			nbJour = 365;
		}
		
		return nbJour;
	}
	
	public static int dureeMois(int annee, int mois)
	{
		//Varible
		int dureeMois;
		
		//Instructions
		dureeMois = 0;
		if (mois == 4 || mois == 6 || mois == 9 || mois == 11)
		{
			dureeMois = 30;
		}
		else
		{
			if (mois == 2 && estBissextile(annee))
			{
				dureeMois = 29;
			}
			else
			{
				if (mois == 2 && ! estBissextile(annee))
				{
					dureeMois = 28;
				}
				else
				{
					dureeMois = 31;
				}
			}
		}
		
		return dureeMois;
	}
	
	public static int[] jour2Date (int nbJour)
	{
		//Variable
		int[] tabDate;
		int jour, mois,annee;
		
		int cptMois;
		
		//Instructions
		jour = mois = annee = 0;
		tabDate = new int[3];
		
		annee = 1900 + Math.round(nbJour / 365);
		
		while (nbJour > 365)
		{
			nbJour = nbJour - 365;
		}
		
		cptMois = 1;
		while ( nbJour > 30 )
		{
			nbJour = nbJour - dureeMois(cptMois, annee);
			cptMois++;
		}
		
		mois = cptMois;
		jour = nbJour;
		
		tabDate[0] = jour;
		tabDate[1] = mois;
		tabDate[2] = annee;
		
		return tabDate;
	}
}
