package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {

    private static final class Node<T>   // en indre nodeklasse
    {
        private final T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private final Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }
    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        //Jeg har brukt programkode 5.2.3 a) fra kompendiet

        Node<T> p = rot, q = null;              //p starter i roten
        int cmp = 0;                            //hjelpevariabel

        while (p != null){                      //Fortsetter til p er ute av treet
            q = p;                              //q er forelder til p
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
                                       //p er nå null, dvs ute av treet, q er den siste vi passerte

        p = new Node <>(verdi, q);     //Oppretter en ny node som tar inn verdi og forelder
        if (q == null)
            rot = p;                   //p er rotnode hvis forelder er null
        else if (cmp < 0)
            q.venstre = p;             //venstre barn til q
        else
            q.høyre = p;               //høyre barn til q

        antall++;                      //én verdi mer i treet
        return true;
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        //Har brukt kode fra løsningen på oppgave 2 i avsnitt 5.2.6 i kompendiet
        Node<T> p = rot;
        int antallVerdi = 0;

        while (p != null){
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else {
                if (cmp == 0) antallVerdi++;
                p = p.høyre;
            }
        }
        return antallVerdi;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        //Har brukt programkode 5.1.7 h)
        while(true){
            if (p.venstre != null)
                p = p.venstre;
            else if(p.høyre != null)
                p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
            if (p.forelder == null)             //Hvis p er rotnoden så er p den siste i postorden
                return null;
            else if (p == p.forelder.høyre)     //Hvis p er høyre barn, er forelderen neste i postorden
                return p.forelder;
            else if (p.forelder.høyre == null)  //Hvis p er venstre barn og enebarn, er forelderen neste
                return p.forelder;

            else {                                  //Hvis p er venstre barn, men ikke enebarn
                Node<T> denne = p.forelder.høyre;      //så er neste i postorden den bladnoden
                while (denne.venstre != null) {     //som er lengst mot venstre av forelderens etterfølgere
                    denne = denne.venstre;
                }
                return denne;
            }
    }

    public void postorden(Oppgave<? super T> oppgave) {
        //Kode fra oppgave 1 fra 5.1.15 i kompendiet
        Node<T> p = rot;

        while (true){                   //flytter p til den første i postorden
            if (p.venstre != null)
                p = p.venstre;
            else if (p.høyre != null)
                p = p.høyre;
            else break;
        }
        oppgave.utførOppgave(p.verdi);

        while (p != rot) {
            p = nestePostorden(p);      //finner alle nodene i postorden ved hjelp av metoren nestePostorden

            assert p != null;
            oppgave.utførOppgave(p.verdi);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        // Kode fra løsningen på oppgave 7 i kapittel 5.1.7 i kompendiet
        if (p.venstre != null)
            postordenRecursive(p.venstre, oppgave);
        if (p.høyre != null)
            postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
