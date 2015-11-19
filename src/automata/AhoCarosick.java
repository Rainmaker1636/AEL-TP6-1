package automata;

import java.util.HashMap;
import java.util.Map;

public class AhoCarosick extends DAutomaton {

	protected String[] words;
	protected int maxLongueur;
	protected Map<String,State> finalStates = new HashMap<String,State>();
	protected Map<Key,State> replis;
	
	public AhoCarosick(String[] words) {
		this.words = words; 
		this.replis = new HashMap<Key,State>();
	}
	
	protected void maxLength() {
		int tmp =0;
		for (int i =0 ; i <words.length ; i++) {
			int length = this.words[i].length();
			if (length > tmp) {
				tmp = length;
			}
		}
		this.maxLongueur = tmp;
	}
	
	public void createAutomaton() throws StateException {
		State root = this.addNewState();
		this.setInitial(root);
		for (int i=0 ; i <this.words.length ; i++) {
			this.finalStates.put(this.words[i],root);
		}
		for (int lg = 0 ; lg <this.maxLongueur;lg++) {
			for (int i = 0 ; i < this.words.length ; i++) {
				if (lg < this.words[i].length()) {
					Character letter = this.words[i].charAt(lg);
					State from = this.finalStates.get(this.words[i]);
					State q = this.createNewState(from, letter);
					this.finalStates.put(this.words[i].substring(0,lg),q);
					// on enleve la valeur deja existante pour la remplacer par le nouvel etat final
					this.finalStates.remove(this.words[i]);
					if (lg + 1 == this.words[i].length()) {
						this.setAccepting(this.finalStates.get(this.words[i].substring(0,lg)));
					}
				}
			}
		}
		
	}
	
	protected void auxRepli(State state) throws StateException {
		for (Character letter : this.usedAlphabet()) {
			State son = this.getTransition(state, letter);
			if (son != null) {
				this.replis.put(new Key(son,letter),state);
			}
		}
	}
	
	public void repli() throws StateException {
		for (State state : this.states) {
			auxRepli(state);
		}
	}
	
	public State createNewState(State father, Character letter) throws StateException {
		State tmp = this.addNewState();
		this.repli();
		this.addTransition(father,letter,tmp);
		if (this.isInitial(father)) {
			this.replis.put(new Key(tmp,letter),this.initialState);
		}
		else {
			State s = father;
			State e = this.getTransition(s, letter);
			while (e != null && this.isInitial(s)) {
				s = this.replis.get(new Key(s,letter));
				e = this.getTransition(s, letter);
			}
			if (e != null) {
				e = this.replis.get(new Key(tmp,letter));
				if (this.isAccepting(e)) {
					this.setAccepting(tmp);
				}
			}
			else {
				this.replis.remove(new Key(tmp,letter));
				this.replis.put(new Key(tmp,letter),this.initialState );
			}
			
		}
		return tmp;
	}
	
	public void completerAutomate() throws StateException {
		for (State s : this.states) {
			for (Character letter : this.usedAlphabet()) {
				State tmp =this.getTransition(s, letter);
				if (tmp == null) {
					if (this.isInitial(tmp)) {
						this.addTransition(tmp, letter, this.initialState);
					}
					else {
						State repli = this.replis.get(new Key(tmp,letter));
						this.addTransition(tmp, letter,this.getTransition(repli, letter));
					}
				}
			}
		}
	}
}
