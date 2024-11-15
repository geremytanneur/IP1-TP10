import java.util.*;
import java.io.*;

public class Labyrinthe {
    //Cette fonction prend en argument le nom d'un fichier
    //contenant la description d'un labyrinthe
    //et renvoie la liste de liste d'entiers correspondante
    public static int[][]chargeLabyrinthe(String nomFichier) {
		int[][]labyrinthe={};
		try{
			Scanner sc=new Scanner(new File(nomFichier)).useDelimiter("\n");
			int c=0;
			//On compte le nombre de lignes
			while(sc.hasNext()){
				c=c+1;
				String tmp=sc.next();
			}
			labyrinthe=new int[c][];
			sc=new Scanner(new File(nomFichier)).useDelimiter("\n");
			int i=0;
			while(sc.hasNext()){
				String ligne=sc.next();
				String[] splitLigne=ligne.split(",");
				labyrinthe[i]=new int[splitLigne.length];
				for(int j=0;j<splitLigne.length;j=j+1){
					labyrinthe[i][j]=Integer.parseInt(splitLigne[j]);
				}
				i=i+1;
			}
			
		}
		catch(Exception e){
			System.out.println("Probleme dans la lecture du fichier "+nomFichier);
			e.printStackTrace();
		}
		return labyrinthe;
    }

    public static Random rand = new Random();

    public static int randRange(int a, int b) {
        return rand.nextInt(b - a + 1) + a;
    }

	public static void afficheLab(int[][] lab) {
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				char c = lab[i][j] == 0 ? 'X' : ' ';
				System.out.print(c);
			}
			System.out.println();
		}
	}

	public static int[][] copieLab = {{}};

	public static int caseHaut(int[][] lab, int l, int c) {
		return l == 0 ? -1 : lab[l - 1][c];
	}

	public static int caseBas(int[][] lab, int l, int c) {
		return l == lab.length - 1 ? -1 : lab[l + 1][c];
	}
	
	public static int caseGauche(int[][] lab, int l, int c) {
		return c == 0 ? -1 : lab[l][c - 1];
	}
	
	public static int caseDroite(int[][] lab, int l, int c) {
		return c == lab[l].length - 1 ? -1 : lab[l][c + 1];
	}

	public static int[][] voisinsLibres(int[][] lab, int l, int c) {
		int n = 0;
		if (caseHaut(lab, l, c) == 1) {
			n++;
		}
		if (caseBas(lab, l, c) == 1) {
			n++;
		}
		if (caseGauche(lab, l, c) == 1) {
			n++;
		}
		if (caseDroite(lab, l, c) == 1) {
			n++;
		}
		int[][] voisinsLibres = new int[n][2];
		n = 0;
		if (caseHaut(lab, l, c) == 1) {
			voisinsLibres[n][0] = l - 1;
			voisinsLibres[n][1] = c;
			n++;
		}
		if (caseBas(lab, l, c) == 1) {
			voisinsLibres[n][0] = l + 1;
			voisinsLibres[n][1] = c;
			n++;
		}
		if (caseGauche(lab, l, c) == 1) {
			voisinsLibres[n][0] = l;
			voisinsLibres[n][1] = c - 1;
			n++;
		}
		if (caseDroite(lab, l, c) == 1) {
			voisinsLibres[n][0] = l;
			voisinsLibres[n][1] = c + 1;
			n++;
		}
		return voisinsLibres;

	}

	public static void printArray(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static void changeVoisins(int[][] lab, int l, int c, int i) {
		int[][] voisins = voisinsLibres(lab, l, c);
		for (int k = 0; k < voisins.length; k++) {
			lab[voisins[k][0]][voisins[k][1]] = i + 1;
		}
	}
	
	public static void etapeParcours(int[][] lab, int i) {
		for (int j = 0; j < lab.length; j++) {
			for (int j2 = 0; j2 < lab[j].length; j2++) {
				if (lab[j][j2] == i) {changeVoisins(lab, j, j2, i);}
			}
		}
	}

	public static boolean finParcours(int[][] lab) {
		boolean pasDeVoisinLibre = true;
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab.length; j++) {
				if (lab[i][j] == 1 && voisinsLibres(lab, j, i).length != 0 || (i == lab.length - 1 && j == lab[lab.length - 1].length - 1)) {
					pasDeVoisinLibre = false;
				}
			}
		}
		return lab[lab.length-1][lab[lab.length-1].length-1] > 1 || lab[lab.length-1][lab[lab.length-1].length-1] == 0 || pasDeVoisinLibre;
	}
	
	public static boolean parcours(int[][] lab) {
		copieLab = lab;
		copieLab[0][0] = 2;
		int i = 1;
		do {
			i++;
			etapeParcours(copieLab, i);
		} while (!finParcours(copieLab));
		if (copieLab[copieLab.length-1][copieLab[copieLab.length-1].length-1] > 1) {
			System.out.println("\n"+copieLab[copieLab.length-1][copieLab[copieLab.length-1].length-1]+" pas\n");
			return true;
		}
		return false;
	}

        public static void main(String[] args) {
		int[][]lab=chargeLabyrinthe("labyrinthe1.csv");
		parcours(lab);
    }

}
