package automata;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Implémentation d'un automate non déterministe
 * 
 * @author Bruno.Bogaert (at) univ-lille1.fr
 *
 */
public class NDAutomaton extends AbstractAutomaton implements Recognizer, AutomatonBuilder {

	protected Set<State> initialStates;
	protected HashMap<Key, Set<State>> delta;

	public NDAutomaton() {
		super();
		initialStates = new PrintSet<State>();
		delta = new HashMap<Key, Set<State>>();
	}
	@Override
	public boolean isInitial(String name) throws StateException {
		return isInitial(states.get(name));
	}

	@Override
	public boolean isInitial(Integer id) throws StateException {
		return isInitial(states.get(id));
	}

	@Override
	public Set<State> getTransitionSet(State from, char letter) {
		return Collections.unmodifiableSet(delta.get(new Key(from, letter)));
	}

	@Override
	public Set<State> getTransitionSet(String from, char letter) {
		return getTransitionSet(states.get(from), letter);
	}

	@Override
	public Set<State> getTransitionSet(Integer from, char letter) {
		return getTransitionSet(states.get(from), letter);
	}

	@Override
	public Set<State> getInitialStates() {
		return Collections.unmodifiableSet(this.initialStates);
	}

	@Override
	public void setInitial(State s) {
		initialStates.add(s);
	}

	@Override
	public boolean isInitial(State s) {
		return initialStates.contains(s);
	}

	@Override
	public void addTransition(State from, Character letter, State to) {
		alphabet.add(letter);
		Key k = new Key(from, letter);
		Set<State> arrival = delta.get(k);
		if (arrival == null) {
			arrival = new PrintSet<State>();
		}
		if (!arrival.contains(to)) {
			arrival.add(to);
			delta.put(k, arrival);
		}
	}
	
	protected boolean look(String word,int cpt) throws StateException {
		if (cpt > word.length()) {
			return false; 
		}
		String tmp = word.substring(0,cpt);
		if (word.equals(tmp)) {
			return true && this.isAccepting(word);
		}
		else {
			for (State state : this.getTransitionSet(tmp, word.charAt(cpt))) {
				return look(word,cpt +1);
			}
		}
		return false;
		
	}

	
	public boolean accept(String word) throws StateException {
		String tmp = word.substring(0, 1);
		Set<State> initial = this.getInitialStates();
		if (!initial.contains(tmp)) {
			return false;
		}
		if (word.length() == 1) {
			return this.isAccepting(word);
		}
		 return this.look(word, 2);
	}
	
	protected void determinate(State state, NDAutomaton dAutomaton) throws StateException {
		for (Character letter : this.usedAlphabet()) {
			Set<State> nextLetters = this.getTransitionSet(state,letter);
			for (State s : nextLetters) {
				if (dAutomaton.getTransitionSet(s, letter).isEmpty()) {
					dAutomaton.addTransition(state, letter,s);
				}
				else {
					determinate(s,dAutomaton);
				}
				if (this.isAccepting(s)) {
					dAutomaton.setAccepting(s);
				}
			}
		}
	}

	public NDAutomaton deterministic() throws StateException {
		NDAutomaton dAutomaton = new NDAutomaton();
		for (State s : initialStates) {
			if (!dAutomaton.accept(s.getName())) {
			dAutomaton.setInitial(s);
			}
			determinate(s,dAutomaton);
		}
		return dAutomaton;
	}


	public Writer writeGraphvizTransitions(Writer buff) {
		PrintWriter out = new PrintWriter(buff);
		for (Map.Entry<Key, Set<State>> entry : delta.entrySet()) {
			for (State dest : entry.getValue()) {
				out.print("  " + entry.getKey().from.getId() + " -> " + dest.getId());
				out.println(" [label = \"" + entry.getKey().letter + "\" ]");
			}
		}
		return buff;
	}

	public Writer writeGraphvizInner(Writer buff) {
		writeGraphvizStates(buff, true);
		writeGraphvizInitials(buff);
		writeGraphvizTransitions(buff);
		return buff;
	}


}
