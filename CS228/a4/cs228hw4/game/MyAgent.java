/**
 * @author jakegude
 */
package cs228hw4.game;

import java.awt.Color;
import java.io.File;
import edu.iastate.cs228.game.*;

//edu.iastate.cs228.game.simulation.agent.RandomActionAgent

public class MyAgent implements Agent {

	private Color mycolor = null;
	private Color opcolor = null;
	
	/**
	    * Function to retrieve your image file (assuming it is present at the base
	    * level of your project).
	    * @return the agent's image file (if you wish to make a 128x128 icon for your
	    *         agent) or null (to use the default image)
	    */
	public File getAgentImage() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	    * This method must return your agent's name (of your choosing). 
	    * @return the agent's name
	    */
	public String getAgentName() {
		return "212121";
	}

	/**
	    * Function used to generate command turns from your agent.  Use the passed
	    * GalaxyState to decide 3 actions for your agent.  This version
	    * will only be used if this agent is run during grading.
	    * @param g a scan of the current state of the system
	    * @param energyLevel energy level corresponding to the amount of energy the agent has
	    * @return An array of 3 AgentActions that indicates the agent's next 3
	    *         actions if in an exhibition/grading situation. If fewer than 3  actions are 
	    *         specified or there are null actions, these agent moves are lost.
	    */
	public AgentAction[] getCommandTurnGrading(GalaxyState galaxy, int energy) {
		AgentAction [] actions = new AgentAction[3];
		SystemState curstate = galaxy.getCurrentSystemFor(mycolor);
		SystemState [] neighbors = curstate.getNeighbors();
		int cost = neighbors[0].getCostToCapture();
		int index = 0;
		String [] str = new String [neighbors.length];
		
		for (int i = 0; i < str.length; i++) {
			str[i] = neighbors[i].toString();
		}
		
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i].getCostToCapture() < cost && !neighbors[i].getOwner().equals(mycolor)) {
				cost = neighbors[i].getCostToCapture();
				index = i;
			}
		}

		actions[0] = new Agent.Move(neighbors[index].getName());
		actions[1] = new Agent.Capture(cost);
		actions[2] = new Agent.Refuel();
		
		
		return actions;
	}

	/**
	    * Function used to generate command turns from your agent.  Use the passed
	    * GalaxyState to decide 3 actions for your agent.  This version
	    * will only be used if this agent is run during a tournament.
	    * @param g a scan of the current state of the system
	    * @param energyLevel energy level corresponding to the amount of energy the agent has
	    * @return an array of 3 AgentActions that indicates the agent's next 3
	    *         actions if in a tournament situation. If fewer than 3 actions are
	    *         specified or there are null actions, these agent moves are lost.
	    */
	public AgentAction[] getCommandTurnTournament(GalaxyState arg0, int arg1) {
		AgentAction [] actions = new AgentAction[3];
		SystemState curstate = arg0.getCurrentSystemFor(mycolor);
		SystemState [] neighbors = curstate.getNeighbors();
		int cost = neighbors[0].getCostToCapture();
		int index = 0;
		String [] str = new String [neighbors.length];
		
		for (int i = 0; i < str.length; i++) {
			str[i] = neighbors[i].toString();
		}
		
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i].getCostToCapture() < cost && !neighbors[i].getOwner().equals(mycolor)) {
				cost = neighbors[i].getCostToCapture();
				index = i;
			}
		}

		actions[0] = new Agent.Move(neighbors[index].getName());
		actions[1] = new Agent.Capture(cost);
		actions[2] = new Agent.Refuel();
		
		
		return actions;
	}

	/**
	    * This method must return your first name.
	    * @return student's first name
	    */
	public String getFirstName() {
		return "Jake";
	}

	/**
	    * This method must return your last name. 
	    * @return student's last name
	    */
	public String getLastName() {
		return "Gudenkauf";
	}

	/** 
	    * This method must return your student ID. 
	    * @return the student's ISU ID
	    */
	public String getStuID() {
		return "613032445";
	}

	/** 
	    * This method must return your username.
	    * @return the students ISU username
	    */
	public String getUsername() {
		return "jakegude";
	}

	/**
	    * Whether your agent is a candidate for the tournament. Return true if this
	    * agent is submitted for the tournament, false otherwise.
	    * @return true iff you wish to enter this agent in the tournament
	    */
	public boolean inTournament() {
		return false;
//		return true; // change once ready to turn in assignment
	}

	/**
	    * Allow the simulation to set the color of your agent. It's up to you to
	    * save this information somehow.  This method is called only once, at the
	    * start of each simulation.
	    * @param c color your agent will appear as
	    */
	public void setColor(Color arg0) {
		mycolor = arg0;
	}

	 /**
	    * Allow the simulation to set the color of your agent's opponent. It's up to you to
	    * save this information if you need it.  This method is called only once, at the
	    * start of each simulation.
	    * @param c color of your agent's opponent
	    */
	public void setOpponentColor(Color arg0) {
		opcolor = arg0;
	}


}
