/* 


*/
import java.io.*;

public class CNF {
	
	private Clause [] gamma;
	private int [] longueurDesClauses;
	private boolean [] assignation;

	private int NB_VARS;
	private int NB_CLAUSES;



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
			this.gamma = new Clause[this.NB_CLAUSES];
			this.assignation = new boolean[this.NB_VARS];
			l = br.readLine();
			int k=0;
			while(l != null) {
				gamma[k] = new Clause(lectureLigne(l));
				k++;
				l = br.readLine();
			}

		}
		

		catch(Exception e) {
			System.exit(1);
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
		while(s.charAt(k)>=48 && s.charAt(k)<=57 && k!=s.length()) {
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



}
