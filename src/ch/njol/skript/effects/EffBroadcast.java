/*
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * Copyright 2011, 2012 Peter Güttinger
 * 
 */

package ch.njol.skript.effects;

import java.util.regex.Matcher;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.api.Effect;
import ch.njol.skript.api.intern.Variable;
import ch.njol.skript.util.VariableString;

/**
 * 
 * @author Peter Güttinger
 * 
 */
public class EffBroadcast extends Effect {
	
	static {
		Skript.addEffect(EffBroadcast.class, "broadcast %variablestring%( (to|in) %-world%)?");
	}
	
	private Variable<VariableString> messages;
	private Variable<World> worlds;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(final Variable<?>[] vars, final int matchedPattern, final Matcher matcher) {
		messages = (Variable<VariableString>) vars[0];
		worlds = (Variable<World>) vars[1];
	}
	
	@Override
	public void execute(final Event e) {
		for (final VariableString mVS : messages.get(e, false)) {
			final String m = mVS.get(e);
			if (m == null)
				continue;
			if (worlds == null) {
				Bukkit.broadcastMessage(m);
			} else {
				for (final World w : worlds.get(e, false)) {
					for (final Player p : w.getPlayers()) {
						p.sendMessage(m);
					}
				}
			}
		}
	}
	
	@Override
	public String getDebugMessage(final Event e) {
		return "broadcast " + messages.getDebugMessage(e) + (worlds == null ? "" : " to " + worlds.getDebugMessage(e));
	}
	
}
