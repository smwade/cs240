package spell;

public class Trie implements ITrie {

	public int nodeCount;
	public int totalWordCount;
	public int uniqueWordCount;
	Node root;

	public Trie() {
		nodeCount = 1;
		totalWordCount = 0;
		uniqueWordCount = 0;
		Node head = new Node();
		root = head;
	}

	@Override
	public String toString() {
		StringBuilder word = new StringBuilder();
		StringBuilder output = new StringBuilder();
		toStringHelper(root, word, output);
		return output.toString();
	}

	// helper method for toString
	private void toStringHelper(Node node, StringBuilder word,
			StringBuilder output) {
		if (node == null) {
			return;
		}
		if (node.getValue() > 0) {
			output.append(word.toString() + '\n');
		}
		for (int i = 0; i < 26; i++) {
			int x = i + 'a';
			char toApp = (char) x;
			word.append(toApp);
			toStringHelper(node.getChild(i), word, output);
			word.setLength(word.length() - 1);
		}
	}

	@Override
	public int hashCode() {
		return nodeCount * totalWordCount * 18059;
	}

	@Override
	public boolean equals(Object o) {
		// null refference
		if (o == null) {
			return false;
		}
		// same address
		if (this == o) {
			return true;
		}
		// same class
		if (getClass() != o.getClass()) {
			return false;
		}
		// now quick check on the counts
		Trie other = (Trie) o;
		if ((this.nodeCount != other.nodeCount)
				|| (this.totalWordCount != other.totalWordCount)
				|| (this.uniqueWordCount != other.uniqueWordCount)) {
			return false;
		}
		// recurse through the whole Trie
		return equalsHelper(this.root, other.root);

	}

	private boolean equalsHelper(Node thisNode, Node thatNode) {
		if (!thisNode.equals(thatNode)) {
			return false;
		}
		for (int i = 0; i < 26; i++) {
			if (thisNode.nodeArray[i] != null) {
				// if equalsHelper returns false
				if (!equalsHelper(thisNode.getChild(i), thatNode.getChild(i))) {
					return false;
				}
			}
		}
		return true;
	}

	public void add(String word) {
		int curIndex = 0;
		Node curNode = root;
		while (curIndex < word.length()) {
			if (curNode.nodeArray[word.charAt(curIndex) - 'a'] == null) {
				curNode.addConnection(word.charAt(curIndex));
				nodeCount++;
			}
			curNode = curNode.getChild(word.charAt(curIndex));
			curIndex++;
		}
		if (curNode.count == 0) {
			uniqueWordCount++;
		}
		curNode.count++;
		totalWordCount++;
	}

	public INode find(String word) {
		int curIndex = 0;
		Node curNode = root;
		while (curIndex < word.length()) {
			if (curNode == null) {
				return null;
			}
			if (curNode.nodeArray[word.charAt(curIndex) - 'a'] == null) {
				return null;
			}
			curNode = curNode.getChild(word.charAt(curIndex));
			curIndex++;
		}
		if (curNode.count == 0) {
			return null;
		}
		return curNode;
	}

	public int getWordCount() {
		return uniqueWordCount;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public class Node implements INode {
		public int count;
		public Node[] nodeArray;

		public Node() {
			this.nodeArray = new Node[26];
			this.count = 0;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o == this) {
				return true;
			}
			if (getClass() != o.getClass()) {
				return false;
			}

			Node other = (Node) o;
			if (this.count != other.count) {
				return false;
			}

			for (int i = 0; i < 26; i++) {
				if ((this.nodeArray[i] == null) && (other.nodeArray[i] != null)) {
					return false;
				}
				if ((this.nodeArray[i] != null) && (other.nodeArray[i] == null)) {
					return false;
				}
			}
			return true;
		}

		public void incrementCount() {
			count++;
		}

		public int getValue() {
			return count;
		}

		public void addConnection(char letter) {
			int index = letter - 'a';
			nodeArray[index] = new Node();
		}

		public Node getChild(char charVal) {
			int index = charVal - 'a';
			return nodeArray[index];
		}

		public Node getChild(int index) {
			return nodeArray[index];
		}

	}
}