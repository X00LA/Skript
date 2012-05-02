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

package ch.njol.skript.loops;

import java.util.Iterator;
import java.util.regex.Matcher;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.api.Changer.ChangeMode;
import ch.njol.skript.api.LoopVar;
import ch.njol.skript.api.intern.Variable;
import ch.njol.skript.data.DefaultChangers;
import ch.njol.skript.util.BlockSphereIterator;

/**
 * @author Peter Güttinger
 * 
 */
public class LoopVarBlockSphere extends LoopVar<Block> {
	
	static {
		Skript.addLoop(LoopVarBlockSphere.class, Block.class, "blocks in (radius|radii|radiuses) %float%( around %location%)?");
	}
	
	private Variable<Float> radii;
	private Variable<Location> centers;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(final Variable<?>[] vars, final int matchedPattern, final Matcher matcher) {
		radii = (Variable<Float>) vars[0];
		centers = (Variable<Location>) vars[1];
	}
	
	@Override
	protected Iterator<Block> iterator(final Event e) {
		return new BlockSphereIterator(centers.getFirst(e), radii.getFirst(e));
	}
	
	@Override
	public Class<?> acceptChange(final ChangeMode mode) {
		return DefaultChangers.blockChanger.acceptChange(mode);
	}
	
	@Override
	public void change(final Event e, final Variable<?> delta, final ChangeMode mode) {
		DefaultChangers.blockChanger.change(e, this, delta, mode);
	}
	
	@Override
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}
	
	@Override
	public String getLoopDebugMessage(final Event e) {
		return "blocks in radius " + radii.getDebugMessage(e) + " around " + centers.getDebugMessage(e);
	}
	
	@Override
	public String toString() {
		return "the loop-block";
	}
	
	@Override
	public boolean isLoopOf(final String s) {
		return s.equalsIgnoreCase("block");
	}
	
}
