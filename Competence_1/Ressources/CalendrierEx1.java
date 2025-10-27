public class CalendrierEx1
{
   public static void main (String[] a)
   {
      /*-----------------------------*/
		/* Déclaration des variables   */
		/*-----------------------------*/
      
		int    jour;
		int    mois;
		int    annee;
		

		/*-----------------------------*/
		/* Instructions                */
		/*-----------------------------*/

		jour = mois = 1;
		
		long debut = System.nanoTime();
		
		
		 // On teste le passage de 1900 à 2000
		
			annee = 1900;
			while (annee < 2000)
			{
				mois = 1;
				while (mois < 12)
				{
					jour = 1;
					while ( jour < dureeMois(mois, annee) )
					{
						jour++;
					}
							
					if (jour == dureeMois(mois, annee))
					{
						jour = 1;
					}
				
			
					
					mois++;
				}
				
				if (mois == 12)
				{
					mois = 1;
				}
				
				annee++;
			}
			

			
		
		
		long fin = System.nanoTime();
		System.out.println((fin-debut) + " nanosecondes");
		System.out.println("annee : " + annee);
		System.out.println("jour : " + jour);
		System.out.println("mois : " + mois);
		
	}
	
	//Permet d'évaluer un résultat
	public static boolean estBissextile (int annee)
	{
		if ( (annee % 4 == 0 && annee % 100 != 0) || (annee % 400 == 0) )
		{
			return true;
		}
		return false;
	}
	
	//Permet d'évaluer un résultat
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
	
	
}
