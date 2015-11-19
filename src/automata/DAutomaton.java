package automata;

import java.util.HashMap;
import java.util.Set;

public class DAutomaton extends AbstractAutomaton implements DeterministicAutomaton  {

	protected State initialState;
	protected HashMap<Key,State> delta;
	
	public DAutomaton() {
		super();
		this.delta = new HashMap<Key,State>();
	}
	
	public DAutomaton(State s) {
		super();
		this.initialState = s;
		this.delta = new HashMap<Key,State>();
	}

	@Override
	public boolean isInitial(State s) {
		return s.getName() == this.initialState.getName() && s.getId() == this.initialState.getId();
	}

	@Override
	public boolean isInitial(String name) throws StateException {
		return this.isInitial(this.states.get(name));
	}

	@Override
	public boolean isInitial(Integer id) throws StateException {
		return this.isInitial(this.states.get(id));
	}


	@Override
	public Set<State> getTransitionSet(String from, char letter) {
		return null;
	}

	@Override
	public Iterable<State> getTransitionSet(Integer from, char letter) throws StateException {
		return null;
	}

	@Override
	public boolean accept(String word) throws StateException {
		if (word.length() <= 1) {
			return this.isAccepting(word) && this.isInitial(word);
		}
		String tmp = word.substring(0, 1);
		if (this.isInitial(tmp)) {
			return look(word,2);
		}
		else {
			return false;
		}
	}

	private boolean look(String word, int cpt) throws StateException {
		if (cpt > word.length()) {
			return false;
		}
		String tmp = word.substring(0, cpt);
		if (word.equals(tmp)) {
			return true && this.isAccepting(word);
		}
		else {
			if (this.getTransition(tmp,word.charAt(cpt)) == null) {
				return false;
			}
			else {
				return look(word,cpt+1);
			}
		}
	}

	@Override
	public State getInitialState() {
		return this.initialState;
	}

	@Override
	public State getTransition(State s, char letter) throws StateException {
		return this.delta.get(new Key(s,letter));
	}

	@Override
	public State getTransition(String name, char letter) throws StateException {
		return this.getTransition(this.states.get(name), letter);
	}

	@Override
	public State getTransition(Integer id, char letter) throws StateException {
		return this.getTransition(this.states.get(id), letter);
	}


	@Override
	public void setInitial(State s) {
		if (this.initialState == null) {
			this.initialState = s;
		}
	}

	@Override
	public void addTransition(State from, Character letter, State to) {
		this.alphabet.add(letter);
		State state = this.delta.get(new Key(from,letter));
		if (state == null) {
			this.delta.put(new Key(from,letter),to);
		}
		
	}

	@Override
	public Iterable<State> getTransitionSet(State from, char letter) throws StateException {
		return null;
	}

	@Override
	public Iterable<State> getInitialStates() {
		return null;
	}

}
