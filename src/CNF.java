/* 


*/
import java.io.*;

public class CNF {
	
	public Clause [] cl;
	public int [] lgClause;
	public boolean [] ass;

	public int NB_VARS;
	public int NB_CLAUSES;



	public CNF(String nomFic) {
		lectureDimacs(nomFic);
	}


	public void lectureDimacs(String nomFic) {
		try {
			Reader r = new FileReader(nomFic);
			BufferedReader br = new BufferedReader(r);
			String l = br.readLine();
			// tant que ligne de commentaires : avancer lecture
			while(l.charAt(0)=='c')	{
				l = br.readLine();
			}
			lectureLignePCNF(l);
			this.cl = new Clause[this.NB_CLAUSES];
			this.ass = new boolean[this.NB_VARS];
			l = br.readLine();
			int k=0;
			while(l != null) {
				cl[k] = new Clause(lectureLigne(l));
				k++;
				l = br.readLine();
			}

		}
		

		catch(Exception e) {
			System.out.println(e);
		}

	}

	// lis un entier dans une chaine a partir d'une certaine position
	public int lireEntier(String s, int i) {
		int k=i;
		int sig=1;
		int val=0;
		if(s.charAt(k)=='-') {
			sig=-1;
			k++;
		}
		while(k!=s.length() && s.charAt(k)>=48 && s.charAt(k)<=57 ) {
			val=10*val+(s.charAt(k)-48);
			k++;
		}
		return sig*val;
	}




	public void lectureLignePCNF(String l) {	
		int j=6;
		this.NB_VARS=lireEntier(l,j);		
		while(l.charAt(j)!=' ') {
			j++;
		}
		j++;
		this.NB_CLAUSES=lireEntier(l,j);
	}

	public int [] lectureLigne(String l) {
		int t [] = new int[l.length()-2];
		int k=0;
		int i=0;
		while(l.charAt(i)!='0') {
			t[k]=lireEntier(l,i);
			while(l.charAt(i)!=' ') {
				i++;
			}
			i++;
			k++;
		}
		return t;
	}

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

	/* renvoi vrai si la clause est valide */
	public boolean valClause(int n_clause) {
		int j=0;
		boolean val=false;
		while(j!=(this.cl[n_clause].taille) && !val) {
			if(estNegatif(this.cl[n_clause].litteraux[j])) {
				val = val | !(this.ass[valAbs(this.cl[n_clause].litteraux[j])-1]);
			}
			else {
				val = val | (this.ass[valAbs(this.cl[n_clause].litteraux[j])-1]);
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
			b = b & valClause(i);
			i++;
		}
		return b;
	}

	public void inverserValeur(int i) {
		this.ass[valAbs(i)-1]=!this.ass[valAbs(i)-1];
	}
	


}
