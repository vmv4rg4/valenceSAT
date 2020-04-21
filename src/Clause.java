/*
	Une clause est un tableau de litteraux 
*/
import java.lang.Math;
public class Clause {
	
	// Le tableau des litteraux de la clause
	public int [] litteraux;
	// Le nombre de litteraux de la clause
	public int taille;


	public Clause(int [] t) {
		this.litteraux=t;
		setTaille(t);
	}

	public void setTaille(int [] t) {
		int i=0;
		while(i+1!=t.length && t[i+1]!=0) {
			i++;
		}
		this.taille=i;
	}

	/* renvoi vrai si le litteral est dans la clause */
	public boolean estDansClause(int lit) {
		int i=0;
		while(i!=this.taille && lit!=this.litteraux[i]) {
			i++;
		}
		return i!=this.taille;
	}

	/* renvoi vrai si la variable apparait negativement dans la clause */
	public boolean apparaitNegativement(int var) {
		return estDansClause(-var);
	}

	/* renvoi vrai si la variable apparait positivement dans la clause */
	public boolean apparaitPositivement(int var) {
		return estDansClause(var);
	}

	public void afficherClause() {
		int i=0;
		while(i!=this.taille+1) {
			System.out.print(litteraux[i]+" ");
			i++;
		}
		System.out.println();
	}


}
