/* 


*/
import java.io.*;

public class CNF {
	
	private Clause [] gamma;
	private int [] longueurDesClauses;
	private boolean [] assignation;

	private int NB_VARS;
	public int NB_CLAUSES;



	public CNF(String nomFic) {
		lectureDimacs(nomFic);
		System.out.println(this.NB_CLAUSES);
	}


	public void lectureDimacs(String nomFic) {
		try {
			Reader r = new FileReader(nomFic);
			BufferedReader br = new BufferedReader(r);
			String l = br.readLine();
			System.out.println(l);
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
				System.out.println(l);
				gamma[k] = new Clause(lectureLigne(l));
				k++;
				System.out.println("ok");
				l = br.readLine();
			}
			System.out.println(this.NB_VARS);

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
			System.out.println("okkkkk");
			k++;
		}
		return sig*val;
	}




	public void lectureLignePCNF(String l) {	
		int j=6;
		this.NB_VARS=lireEntier(l,j);
		System.out.println(this.NB_VARS);		
		while(l.charAt(j)!=' ') {
			j++;
		}
		j++;
		System.out.println(j);
		this.NB_CLAUSES=lireEntier(l,j);
		System.out.println(this.NB_CLAUSES);
	}

	public int [] lectureLigne(String l) {
		System.out.println(l.length());
		int t [] = new int[l.length()-2];
		int k=0;
		int i=0;
		while(l.charAt(i)!='0') {
			System.out.println(l.charAt(i)+" "+k);
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
