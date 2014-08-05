/*
 * Author: 598Johnn897
 * 
 * Date: Aug 5, 2014
 * Package: com.jkush321.autowalls.team
 *
 */
package com.jkush321.autowalls.team;

/**
 * Created: Aug 5, 2014 <br>
 * Time: 7:26:47 PM <br>
 * Year: 2014 <p>
 *
 * By: 598Johnn897 <p>
 * 
 * Project: AutoWalls <br>
 * File: TeamList.java <br>
 * Package: com.jkush321.autowalls.team <p>
 * 
 * @author 598Johnn897
 */
public enum TeamList {

	BLUE(new TeamBlue()),
	GREEN(new TeamGreen()),
	RED(new TeamRed()),
	YELLOW(new TeamYellow());
	
	private Team team;
	
	TeamList(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}
	
}
