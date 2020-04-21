/* 


*/
import java.io.*;
import java.util.*;
public class CNF {
	
	public Clause [] cl;
	public int [] lgClause;
	public boolean [] ass;

	public int NB_VARS;
	public int NB_CLAUSES;



	public CNF(String nomFic) {
		lectureDimacs(nomFic);
		this.ass = new boolean[this.NB_VARS];
	}


	public void lectureDimacs(String nomFic) {
		Scanner sc=null;
		try {
			FileInputStream fis = new FileInputStream(nomFic);
			BufferedInputStream bis = new BufferedInputStream(fis);
			sc = new Scanner(bis);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		sc.findWithinHorizon("p cnf",0);
		this.NB_VARS=sc.nextInt();
		this.NB_CLAUSES=sc.nextInt();
		this.cl = new Clause[this.NB_CLAUSES];
        	int deb = 0;
        	int j = 0;
        	int fin = 0;
        	int [] tab = new int[this.NB_VARS*this.NB_CLAUSES];
        	for(int i = 0; sc.hasNextInt(); i++) {
           		 tab[i] = sc.nextInt();
            		if(tab[i]==0){
                		this.cl[j] = new Clause(Arrays.copyOfRange(tab, deb, fin));
                		deb = i+1;
                		fin = deb;
               			j++;
            		} 
			else {
               			 fin++;
           		}
       		}
		
	}

	public static int valAbs(int i) {
		if(i<0) {
			return -i;
		}
		else { 
			return i;
		}
	}

	public boolean estNegatif(int i) {
		return i<0;
	}

	
	public boolean valClause(Clause q) {
		int j=0;
		boolean val=false;
		while(j!=q.taille && !val) {
			if(estNegatif(q.litteraux[j])) {
				val = val | !(this.ass[valAbs(q.litteraux[j])-1]);
			}
			else {
				val = val | (this.ass[valAbs(q.litteraux[j])-1]);
			}
			j++;
		}
		return val;	
	}
	/* renvoi vrai si ass[] est modele de cl[] */	
	public boolean estModele() {
		int i=0;
		boolean b = true;
		while(i!=this.cl.length && b) {
			b = b & valClause(this.cl[i]);
			i++;
		}
		return b;
	}

	// inverse la valeur de la variable i dans l'assignation de la CNF
	public void inverserValeur(int i) {
		this.ass[valAbs(i)-1]=!this.ass[valAbs(i)-1];
	}

	// renvoi vrai si var est dans la clause c (negativement ou positivement)	
	public boolean varEstDansClause(Clause c, int var) {
		int i=0;
		while(i!=c.taille & valAbs(c.litteraux[i])!=valAbs(var)) {
			i++;
		}
		return i!=c.taille;
	}
		
	/* affiche l'assignation */	 
	public void imprimerAssignation() {
		int i=0;
		while(i!=this.NB_VARS) {

			if(this.ass[i]==false) {
				System.out.print("-"+(i+1)+" ");
			}
			else {
				System.out.print((i+1)+" ");
			}
			i++;
		}
		System.out.println("0");
	}	
	
	public int nbClauseFausse() {
		int nb=0;
		int i=0;
		while(i!=this.cl.length) {
			if(!valClause(this.cl[i])) {
				nb++;
			}	
			i++;
		}
		return nb;	
	}
				
	


}
