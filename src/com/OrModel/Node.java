package com.OrModel;
import java.util.ArrayList;
import com.DataStructures.Slot;

class NodeTemp extends Populate {
    ArrayList<NodeTemp> children = new ArrayList<NodeTemp>();
	public Slot value;
	public Slot parent;
	
	
	NodeTemp() {
		this.value = null;
        children = null;
	}
	
	NodeTemp(Slot v, Slot p) {
		this.parent = p;
        this.value = v;
        children = null;
    }
	
	public NodeTemp addChild(Slot c) {
        this.children.add(new NodeTemp(c, this.value));
		return this;
    }
	
	public Slot backTrack() {
		if(this.parent == null) {
			return null;
		} else {
			return this.parent;
		}
	}
	
	public NodeTemp setRoot(Slot rootSlot) {
        this.value = rootSlot;
		return this;
    }
	
	public void checkConstraints() {
		
	}
	
	public void buildTree(NodeTemp root) {
		int first = 0;
		while(first != 1) {
			addChild(root.value);
			//checkConstraints(this);
		}
	}
	
}