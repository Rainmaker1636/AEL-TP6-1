package automata;

public interface DeterministicAutomaton extends Automaton {

	/**
	 * @return état initial de l'automate
	 */
	State getInitialState();

	/**
	 * @param s
	 * @param letter
	 * @return transition delta(s,letter)
	 * @throws StateException
	 *             si s est invalide
	 */
	State getTransition(State s, char letter) throws StateException;

	/**
	 * @param name nom d'un état s de l'automate
	 * @param letter
	 * @return transition delta(s,letter)
	 * @throws StateException
	 */
	State getTransition(String name, char letter) throws StateException;

	/**
	 * @param id : rang d'un état s de l'automate
	 * @param letter
	 * @return transition delta(s,letter)
	 * @throws StateException
	 */
	State getTransition(Integer id, char letter) throws StateException;

}
