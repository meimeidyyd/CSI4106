package robot.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.Grid;
import model.Node;
import model.Orientation;
import model.Position;
import model.SearchMethods;
import model.SearchSolution;

public class RobotApp {

	public static void main(String[] args) {

		Grid grid = generateGrid(50, 50, getPositions("2,2/2,3/3,2"), getPositions("1,2/2,1/3,3/2,4"),
				new Position(4, 3), Orientation.WEST);
//		Grid grid2 = generateGrid(4, 4, getPositions("2,2/2,3/3,2"), getPositions("1,2/2,1/3,3/4,2"),
//				new Position(3, 4), Orientation.WEST);

		for(int i=1;i<=3;i++){
			SearchSolution solution = search(i, grid);
			printSolution(solution);
			
		}

	}

	/**
	 * Receives a solution for a search problem and prints the states of that
	 * solution starting from the root node, and total cost, depth and time of
	 * that solution. If node solution is null prints "No Solution".
	 * 
	 * @param solution
	 *            The solution solution of the search problem.
	 */
	private static void printSolution(SearchSolution solution) {
		if (solution != null) {
			System.out.println("------ " + solution.getSearchMethod() + " ------");
			Stack<Node> allNodes = new Stack<>();
			allNodes.add(solution.getSolutionNode());
			Node node = solution.getSolutionNode();
			int totalCost=solution.getSolutionNode().getState().getAction().getEngery();
			while (node.getParent() != null) {
				allNodes.add(node.getParent());
				node = node.getParent();
				totalCost+=node.getState().getAction().getEngery();
			}
			while (!allNodes.isEmpty()) {
				System.out.println(allNodes.pop().getState().toString());
			}
			System.out.println("Total cost: " + totalCost);
			System.out.println("Depth: " + solution.getSolutionNode().getDepth());
			System.out.println("Branch factors: " + solution.getBranchFactor());
			System.out.println("Time: " + solution.getDuration() + "ms");
			System.out.println();
		} else {
			System.out.println("No solution was found.");
			System.out.println();
		}
	}

	/**
	 * Generates a search for the specified grid and the specified search
	 * method.
	 * 
	 * @param method
	 *            The search method integer. 1 for Depth-first search, 2 for
	 *            Breath-first search and 3 for A* search.
	 * @param grid
	 *            The grid where the search will be applied.
	 * @return The node solution for that specified search.
	 */
	private static SearchSolution search(int method, Grid grid) {
		SearchMethods search = new SearchMethods(grid,method);
		SearchSolution solution = null;
		if (method == 1) {
			solution = search.DFS();
		} else if (method == 2) {
			solution = search.BFS();
		} else if (method == 3) {

			solution = search.AStar();
			
		}
		return solution;
	}

	/**
	 * Generates a grid with columns, lines, obstacle and dirt positions, a
	 * start position and orientation.
	 * 
	 * @param columns
	 * @param lines
	 * @param obstacles
	 * @param dirt
	 * @param startPosition
	 * @param orientation
	 * @return
	 */
	private static Grid generateGrid(int columns, int lines, List<Position> obstacles, List<Position> dirt,
			Position startPosition, Orientation orientation) {
		Grid grid = new Grid(columns, lines, obstacles, dirt, startPosition, orientation);
		return grid;
	}

	/**
	 * Returns an array of positions from a string with positions separated by /
	 * and each position separated by commas. Example of string: "1,2/3,2/5,6"
	 * 
	 * @param positionsString
	 *            The string with the positions separated by / and each position
	 *            separated with commas.
	 * @return The array of positions or null if no positions were identified in
	 *         the string.
	 */
	private static List<Position> getPositions(String positionsString) {
		List<Position> positions = new ArrayList<>();
		if (positionsString.length() > 0) {
			String[] pos = positionsString.split("/");
			for (int i = 0; i < pos.length; i++) {
				try {
					int x = Integer.parseInt(pos[i].split(",")[0]);
					int y = Integer.parseInt(pos[i].split(",")[1]);
					positions.add(new Position(x, y));
				} catch (Exception e) {
				}
			}
		}
		return positions;
	}

}
