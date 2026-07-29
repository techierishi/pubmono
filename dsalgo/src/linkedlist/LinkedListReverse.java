class Node
{
	int data;
	Node next;

	Node(int data, Node next) {
		this.data = data;
		this.next = next;
	}
};

public class LinkedListReverse
{
	// Helper function to print given linked list
	public static void printList(Node head)
	{
		Node ptr = head;
		while (ptr != null)
		{
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		}

		System.out.println("null");
	}

	// Iterate through the list and move/insert each node to the front of
	// the result list like a Push of the node
	public static Node reverse(Node head)
	{
		Node result = null;
		Node current = head;

		// traverse the list
		while (current != null)
		{
			// tricky: note the next node
			Node next = current.next; // 2 , 3 , 4
			System.out.println("Next Local :"+next.data);

			// move the current node onto the result
			current.next = result; // null , 1 , 2
			result = current; // 1 , 2 , 3
			System.out.println("result :"+result.data);

			// process next node
			current = next; // 2 , 3
			System.out.println("current :"+current.data);

		}

		// fix head
		return result;
	}

	// reverse linked list iteratively
	public static void main(){
		// input keys
		int[] keys = { 1, 2, 3, 4, 5, 6 };

		Node head = null;
		for (int i = keys.length - 1; i >= 0; i--) {
			head = new Node(keys[i], head);
		}

		head = reverse(head);
		printList(head);
	}
}
