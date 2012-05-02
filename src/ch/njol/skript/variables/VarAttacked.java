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

package ch.njol.skript.variables;

import java.lang.reflect.Array;
import java.util.regex.Matcher;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.api.exception.ParseException;
import ch.njol.skript.api.intern.Variable;
import ch.njol.skript.util.EntityType;
import ch.njol.skript.util.Utils;

/**
 * @author Peter Güttinger
 * 
 */
public class VarAttacked extends Variable<Entity> {
	
	static {
		Skript.addVariable(VarAttacked.class, Entity.class, "(attacked|damaged|victim)( (.+))?");
	}
	
	private EntityType type;
	private Entity[] array;
	
	@Override
	public void init(final Variable<?>[] vars, final int matchedPattern, final Matcher matcher) throws ParseException {
		final String type = matcher.group(3) == null ? null : matcher.group(3);
		if (type == null) {
			this.type = new EntityType(Entity.class, 1);
		} else {
			this.type = Utils.getEntityType(type);
			if (this.type == null)
				throw new ParseException("'" + type + "' is not an entity type");
		}
		array = (Entity[]) Array.newInstance(this.type.c, 1);
	}
	
	@Override
	protected Entity[] getAll(final Event e) {
		final Entity entity = Skript.getEventValue(e, Entity.class);
		if (type.isInstance(entity)) {
			array[0] = entity;
			return array;
		}
		return null;
	}
	
	@Override
	public Class<? extends Entity> getReturnType() {
		return type.c;
	}
	
	@Override
	public String getDebugMessage(final Event e) {
		if (e == null)
			return "attacked " + type;
		return Skript.toString(getFirst(e), true);
	}
	
	@Override
	public String toString() {
		return "the attacked " + type;
	}
	
}
