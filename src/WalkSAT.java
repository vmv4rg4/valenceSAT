/* 

	implémentation de l'algorithme WalkSAT

*/
import java.lang.Math.*;
public class WalkSAT {
	
	private double P;		// influe la méthode de choix pour y
	private int MAX_ITERATION;	// nombre d'itération maximal pour la semi-décision
	private CNF gamma;		// ensemble de clauses

	public WalkSAT(double p, int max_iter, CNF c) {
		this.gamma=c;
		this.P=p;
		this.MAX_ITERATION=max_iter;
	}

	/* donne des valeurs au hasard pour l'assignation v de c */
	public static void assignationHasard(CNF c) {
		int i=0;
		while(i!=c.NB_VARS) {
			c.ass[i]=Math.random()>=0.5;
			i++;
		}		
	}

	/* renvoi vrai si l'assignation pour c est modèle de c */
	public static boolean estModele(CNF c) {
		int i=0;
		boolean b = true;
		while(i!=c.cl.length && b) {
			b = b & c.valClause(i);
			i++;
		}
		return b;
	}

	public static int choisirClauseFausseAuHasard(CNF c) {
		int i;
		i=(int)(Math.random()*c.NB_CLAUSES);
		if(!c.valClause(i)) {
			return i;
		}
		else {
			while(c.valClause(i)) {
				i=(int)(Math.random()*c.NB_CLAUSES);				
			}
			return i;
		}
	}		

	public static int choixDeterministe() {
		return 1;
	}

	/* tire une valeur au hasard entre 0 et 1 */
	public static double tirerValeurAuHasard() {
		return Math.random();
	}

	/* main : implémentation de l'algo WalkSat */
	public static void main(String args[]) {
		WalkSAT ws = new WalkSAT(1.01,1000000, new CNF(args[0]));
		int i,cfausse,y;
		double x;
		// choisir une assignation uniformément au hasard
		assignationHasard(ws.gamma);
		// ws.gamma.imprimerAssignation();
		i=0;
		// tant que v n'est pas modèle et i < MAX_ITERATION FAIRE
		while(!estModele(ws.gamma) && i<ws.MAX_ITERATION) {
			// choisir une clause fausse au hasard dans C
			cfausse=choisirClauseFausseAuHasard(ws.gamma);
			// tirer une valeur réelle uniformément au hasard
			x=tirerValeurAuHasard();
			// si x<=P
			if(x<=ws.P) {
				y=ws.gamma.cl[cfausse].litteraux[(int)(Math.random()*(ws.gamma.cl[cfausse].taille))];
			}
			else {
				y=choixDeterministe();
			}
			ws.gamma.inverserValeur(y);
			i++;
		}
		if(estModele(ws.gamma)) {
			System.out.println("SAT");
			ws.gamma.imprimerAssignation();
		}
		else {
			System.out.println("UNSAT");
		}
		
	}
}
