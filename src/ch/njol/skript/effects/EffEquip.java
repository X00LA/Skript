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

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.api.Effect;
import ch.njol.skript.api.Testable;
import ch.njol.skript.api.exception.InitException;
import ch.njol.skript.api.exception.ParseException;
import ch.njol.skript.api.intern.Variable;
import ch.njol.skript.util.ItemType;

/**
 * @author Peter Güttinger
 * 
 */
public class EffEquip extends Effect implements Testable {
	
	static {
		Skript.addEffect(EffEquip.class,
				"equip( %player%)? with %itemtype%",
				"make %player% wear %itemtype%");
	}
	
	private Variable<Player> players;
	private Variable<ItemType> types;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(final Variable<?>[] vars, final int matchedPattern, final Matcher matcher) throws InitException, ParseException {
		players = (Variable<Player>) vars[0];
		types = (Variable<ItemType>) vars[1];
	}
	
	@Override
	public String getDebugMessage(final Event e) {
		return "equip " + players.getDebugMessage(e) + " with " + types.getDebugMessage(e);
	}
	
	@Override
	protected void execute(final Event e) {
		final Iterable<Player> ps = players.get(e, false);
		for (final ItemType t : types.get(e, false)) {
			for (final ItemStack item : t.getAll()) {
				switch (item.getType()) {
					case LEATHER_BOOTS:
					case IRON_BOOTS:
					case GOLD_BOOTS:
					case DIAMOND_BOOTS:
						for (final Player p : ps) {
							p.getInventory().setBoots(item);
						}
					break;
					case LEATHER_LEGGINGS:
					case IRON_LEGGINGS:
					case GOLD_LEGGINGS:
					case DIAMOND_LEGGINGS:
						for (final Player p : ps) {
							p.getInventory().setLeggings(item);
						}
					break;
					case LEATHER_CHESTPLATE:
					case IRON_CHESTPLATE:
					case GOLD_CHESTPLATE:
					case DIAMOND_CHESTPLATE:
						for (final Player p : ps) {
							p.getInventory().setChestplate(item);
						}
					break;
					default:
						if (!item.getType().isBlock())
							continue;
						//$FALL-THROUGH$
					case LEATHER_HELMET:
					case IRON_HELMET:
					case GOLD_HELMET:
					case DIAMOND_HELMET:
						for (final Player p : ps) {
							p.getInventory().setHelmet(item);
						}
				}
			}
		}
	}
	
	@Override
	public boolean test(final Event e) {
		//		final Iterable<Player> ps = players.get(e, false);
		//		for (final ItemType t : types.get(e, false)) {
		//			for (final Player p : ps) {
		//				//TODO this + think...
		//			}
		//		}
		return false;
	}
	
}
