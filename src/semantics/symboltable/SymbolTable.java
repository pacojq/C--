package semantics.symboltable;

import java.util.*;
import syntax.Definition;

public class SymbolTable {
	
	private int scope = 0;
	private List<Map<String, Definition>> table;
	
	
	public SymbolTable()  {
		scope = 0;
		table = new ArrayList<Map<String, Definition>>();
		table.add(new HashMap<String, Definition>());
	}

	public void set() {
		scope ++;
		if (table.size() >= scope)
			table.add(new HashMap<String, Definition>());
	}
	
	public void reset() {
		if (scope == 0)
			throw new RuntimeException("Don't you fool dare to set the scope to -1.");
		table.get(scope).clear();
		scope --;
	}
	
	public boolean insert(Definition definition) {
		String key = definition.getName();
		if (currentMap().containsKey(key))
			return false;
		
		currentMap().put(key, definition);
		definition.setScope(scope);
		return true;
	}
	
	
	public Definition find(String id) {
		Definition def;
		for (int s = scope; s >= 0; s --) {
			def = findInScope(s, id);
			if (def != null)
				return def;
		}
		return null;
	}
	

	public Definition findInCurrentScope(String id) {
		return findInScope(scope, id);
	}
	
	
	
	private Map<String, Definition> currentMap() {
		return table.get(scope);
	}
	
	private Definition findInScope(int scope, String id) {
		return table.get(scope).get(id);
	}
	
	
	
}
