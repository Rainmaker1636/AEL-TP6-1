package automata;

import java.util.Set;


/**
 * Méthodes d'utlisation d'un automate
 * @author Bruno.Bogaert (at) univ-lille1.fr
 *
 */
public interface Automaton extends Recognizer {

	/**
	 * @return états de l'automate
	 */
	Iterable<State> getStates();

	/**
	 * @return états initiaux
	 */
	Iterable<State> getInitialStates();

	/**
	 * @return ensemble des lettres utilisées par l'automate
	 */
	Set<Character> usedAlphabet();
	/**
	 * @param s
	 *            état de l'automate
	 * @return indique si l'état est acceptant
	 * @throws StateException
	 *             si l'état n'appartient pas à l'automate
	 */
	boolean isAccepting(State s) throws StateException;

	/**
	 * @param name
	 *            nom d'état
	 * @return indique si l'état est acceptant
	 * @throws StateException
	 *             si ce nom d'état est invalide
	 */
	boolean isAccepting(String name) throws StateException;

	/**
	 * @param id
	 *            rang d'un état de l'automate
	 * @return indique si l'état est acceptant
	 * @throws StateException
	 *             si le rang est invalide
	 */
	boolean isAccepting(Integer id) throws StateException;

	/**
	 * @param s
	 *            état de l'automate
	 * @return indique si l'état est initial
	 * @throws StateException
	 *             si l'état n'appartient pas à l'automate
	 */
	boolean isInitial(State s) throws StateException;

	/**
	 * @param name
	 *            nom d'état
	 * @return indique si l'état est initial
	 * @throws StateException
	 *             si ce nom d'état est invalide
	 */
	boolean isInitial(String name) throws StateException;

	/**
	 * @param id
	 *            rang d'état
	 * @return indique si l'état est initial
	 * @throws StateException
	 *             si ce rang est invalide
	 */
	boolean isInitial(Integer id) throws StateException;

	/**
	 * @param from
	 *            état de départ
	 * @param letter
	 *            lettre
	 * @return transition delta(from,letter). null si la transition est
	 *         indéfinie
	 * @throws StateException
	 *             si l'état n'appartient pas à l'automate
	 */
	Iterable<State> getTransitionSet(State from, char letter) throws StateException;

	/**
	 * @param from
	 *            état de départ
	 * @param letter
	 *            lettre
	 * @return transition delta(from,letter). null si la transition est
	 *         indéfinie
	 * @throws StateException
	 *             si ce nom d'état est invalide
	 */
	Iterable<State> getTransitionSet(String from, char letter) throws StateException;

	/**
	 * @param from
	 *            état de départ
	 * @param letter
	 *            lettre
	 * @return transition delta(from,letter). null si la transition est
	 *         indéfinie
	 * @throws StateException
	 *             si ce rang est invalide
	 */
	Iterable<State> getTransitionSet(Integer from, char letter) throws StateException;

	/**
	 * @return représentation de l'automate en langage Graphviz
	 */
	public String toGraphviz();

}
