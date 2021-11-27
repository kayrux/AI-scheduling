package OrModel;

import java.util.ArrayList;
import com.Model.*;
import java.lang.Math;

public class Populate {
//ArrayList<ArrayList<Slot>> facts = new ArrayList<>();
Node facts;

    public ArrayList<ArrayList<Slot>> PopulateNoTree (ArrayList<Slot> slotList,
	ArrayList<CourseLab>,
	ArrayList<Pair<CourseLab, CourseLab>> notCompatibleList,
	ArrayList<Slot> initFact,
	int factCount) {
		ArrayList<Slot> fact = new ArrayList<Slot>();
		ArrayList<ArrayList<Slot>> factList = new ArrayList<ArrayList<Slot>>();
		for(int i = 0; i < factCount; i++) {
			for(int j = 0; j < 3; j++) {
				makeFact(slotList, initFact);
			}
			factList.add(fact);
		}
		return factList;
    }
	
	public ArrayList<Slot> makeFact(ArrayList<Slot> slotList, ArrayList<Slot> initFact) {
		if(initFact.size() == 0) {
			ArrayList<Slot> fact = new ArrayList<Slot>();
		} else {
			ArrayList<Slot> fact = new ArrayList<Slot>(initFact);
		}
		ArrayList<Slot> fact = new ArrayList<Slot>();
		int randSlot = (int)(Math.random() * slotList.size());
		fact.add(slotList.get(randSlot));
		if(constraint(fact) == True) {
			return fact;
		} else {
			makeFact(slotList, initFact);
		}
	}
	
	////////////////////
	//Using an Or Tree
	
	public ArrayList<ArrayList<Slot>> PopulateOrTree () {
		
	}
}

public class Node {
    Slot value;
    ArrayList<Node> children = new ArrayList<Node>();
	
	
	
	
	
	
	
	
	
	

    Node(Slot value) {
        this.value = value;
        children = null;
    }

    public Node addChild(Node parent, Slot child) {
        parent.children.add(Node(child));
		return Node;
    }
	
	public checkConstraints() {
		
	}
	
	public buildTree(Node root) {
		int first = 0;
		while(first != 1) {
			addChild(root, );
			checkConstraints(Node);
		}
	}
	
}

public class TreeOr {
    ArrayList<TreeOr> tree = new ArrayList<TreeOr>();
}