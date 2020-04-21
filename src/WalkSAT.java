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
			b = b & c.valClause(c.cl[i]);
			i++;
		}
		return b;
	}

	public static Clause choisirClauseFausseAuHasard(CNF c) {
		int i;
		i=(int)(Math.random()*c.NB_CLAUSES-1.0);
		if(!c.valClause(c.cl[i])) {
			return c.cl[i];
		}
		else {
			while(c.valClause(c.cl[i])) {
				i=(int)(Math.random()*c.NB_CLAUSES-1.0);				
			}
			return c.cl[i];
		}
	}		

	public static Clause choisirClauseFausseAuHasard2(CNF c) {
		int i;
		i=(int)(Math.random()*c.nbClauseFausse());
		int j=0;
		int parc=0;
		while(j!=i) {
			while(!c.valClause(c.cl[parc])) {
				parc++;
			}
			parc++;
			j++;
		}
		return c.cl[parc];
	}

	public static int choixDeterministe(CNF c, Clause q) {
		return MIC(c,q);
	}

	/* retourne l'indice ou se trouve le maximum de N valeurs stochées dans un tableau */
	public static int maxN(int t[]) {
		int i=0;
		int max=t[0];
		int indice=0;
		while(i!=t.length) {
			if(max<t[i]) {
				max=t[i];
				indice=i;
			}
			i++;
		}
		return indice;
	}

	public static int minN(int t[]) {
		int i=0;
		int min=t[0];
		int indice=0;
		while(i!=t.length) {
			if(min>t[i]) {
				min=t[i];
				indice=i;
			}
			i++;
		}
		return indice;
	}

	public static int MOMS(CNF c, Clause q) {
		int [] t = new int[q.taille+1]; 
		int i=0;
		while(i!=q.taille) {
			int j=0;
			while(j!=c.NB_CLAUSES) {
				if(c.varEstDansClause(c.cl[j],q.litteraux[i])) {
					t[i]++;
				}
				j++;
			}
			i++;
		}
		return q.litteraux[maxN(t)];
	}

	/* tire une valeur au hasard entre 0 et 1 */
	public static double tirerValeurAuHasard() {
		return Math.random();
	}

	public static int MIC(CNF c, Clause q) {
		int i=0;
		int [] t = new int[q.taille+1];
		while(i!=q.taille) {
			c.inverserValeur(q.litteraux[i]);
			t[i]=c.nbClauseFausse();
			c.inverserValeur(q.litteraux[i]);
			i++;
		}

		return q.litteraux[minN(t)];
	}


	public void algoWalkSAT() {
		int i,y;
		double x;
		Clause cfausse;
		// choisir une assignation uniformément au hasard
		assignationHasard(this.gamma);
		// ws.gamma.imprimerAssignation();
		i=0;
		// tant que v n'est pas modèle et i < MAX_ITERATION FAIRE

		while(!estModele(this.gamma) && i<this.MAX_ITERATION) {
			// choisir une clause fausse au hasard dans C
			cfausse=choisirClauseFausseAuHasard2(this.gamma);
			// tirer une valeur réelle uniformément au hasard
			x=tirerValeurAuHasard();
			// si x<=P
			if(x<=this.P) {
				y=cfausse.litteraux[(int)(Math.random()*(cfausse.taille))];
			}
			else {
				y=choixDeterministe(this.gamma,cfausse);
			}
			this.gamma.inverserValeur(y);
			/*if(i%10000==0) {
				System.out.println(i/10000+"  "+this.gamma.nbClauseFausse());
			} */
			//System.out.println(i);


			i++;
		}
		if(estModele(this.gamma)) {
			System.out.println("SAT");
			this.gamma.imprimerAssignation();
		}
		else {
			System.out.println("UNSAT");
		}
		
	}		

	public void setP(double p) {
		this.P=p;
	}
	/* main : implémentation de l'algo WalkSat */
	public static void main(String args[]) {
		double d=0.01;
//		WalkSAT ws = new WalkSAT(d,1000000, new CNF(args[0]));
		while(d<=1.01) {
		WalkSAT ws = new WalkSAT(d,1000000, new CNF(args[0]));
			try { Thread.sleep(100); }
			catch(Exception e) { System.out.println(e);}
			System.out.println("P = "+d);		
			ws.setP(d);
			ws.algoWalkSAT();
			d+=0.01;
		}
	}
}
