package sectionManager;

import java.util.ArrayList;
import java.util.Hashtable;

import objectManager.GameObject;
import objectManager.ObjectChangeEvent;
import objectManager.ObjectType;
import objectManager.ObjectChangeEvent.ObjectChangeType;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import factionManager.Faction;

import physicsManager.PhysicalObject;
import utilityManager.MathBox;

public abstract class SectionObject extends PhysicalObject {
	protected ArrayList<Section> sections;
	protected ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<ArrayList<Integer>>();

	protected boolean sectionChange = false;

	public SectionObject(ObjectType objectType, GameObject source, Hashtable<String, Double> values, ArrayList<Section> sections, ArrayList<ArrayList<Integer>> adjacencyList, Faction faction) {
		super(objectType, source, values, faction);
		this.sections = sections;
		this.adjacencyList = adjacencyList;
	}

	public void updateSections() {
		for (Section section : sections) {
			if (section.getHealth() <= 0) {
				section.setExploding(true);
				section.setExplosionTimer(section.getExplosionTimer() - 1);

				if (section.getHealth() <= 0) {
					section.setAlive(false);
				}
			}
		}
		removeDeadSections();
	}

	public Section getClosestSection(Vector2D point) {
		if (sections.isEmpty()) {
			return null;
		}

		Section closest = sections.iterator().next();
		
		Vector2D absolutePosition = MathBox.rotatePoint(closest.getSectionPosition(), orientation).add(objectPosition);
		double min = absolutePosition.distance(point);
		for (Section section : sections) {
			absolutePosition = MathBox.rotatePoint(section.getSectionPosition(), orientation).add(objectPosition);
			double distance = absolutePosition.distance(point);
			if (distance < min && !section.isExploding() && section.isAlive()) {
				closest = section;
				min = distance;
			}
		}
		return closest;
	}

	private void removeDeadSections() {
		ArrayList<Section> toRemove = new ArrayList<Section>();
		for (Section section : sections) {
			if (!section.isAlive() && section.getExplosionTimer() <= 0) {
				toRemove.add(section);
			}
		}

		for (Section section : toRemove) {
			// register change to sections for updating the view of this
			sectionChange = true;

			// get the indices of the sections connected to the section being removed
			ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();
			for (Integer connected : adjacencyList.get(section.getIndex())) {
				indicesToRemove.add(connected);
			}

			// clear edges of section being removed
			adjacencyList.get(section.getIndex()).clear();

			for (Integer indexToRemove : indicesToRemove) {
				adjacencyList.get(indexToRemove).remove((Integer) section.getIndex());
			}

			sections.remove(section);

			updateObservers(new ObjectChangeEvent(this, ObjectChangeType.CREATION, getFragments()));
		}
	}

	private ArrayList<PhysicalObject> getFragments() {
		ArrayList<PhysicalObject> fragments = new ArrayList<PhysicalObject>();

		ArrayList<Section> connected = getSections(getConnected(new ArrayList<Integer>(), 0));
		if (connected.size() < sections.size()) {
			// get unconnected sections
			ArrayList<Section> unconnected = new ArrayList<Section>();
			for (Section section : sections) {
				if (!connected.contains(section)) {
					unconnected.add(section);
				}
			}

			while (!unconnected.isEmpty()) {
				// get sections connected to unconnected section
				ArrayList<Section> newGroup = getSections(getConnected(new ArrayList<Integer>(), unconnected.get(0).getIndex()));

				// get adjacency list for above sections
				ArrayList<ArrayList<Integer>> newAdjacencyList = new ArrayList<ArrayList<Integer>>();

				for (Section section : newGroup) {
					newAdjacencyList.add(adjacencyList.get(section.getIndex()));
				}

				// remove sections in newGroup from unconnected
				for (Section section : newGroup) {
					unconnected.remove(section);
				}

				// remove fragment sections from sections
				for (Section section : newGroup) {
					sections.remove(section);
				}

				// create new fragment
				fragments.add(createFragment(newGroup, newAdjacencyList));
			}
		}

		return fragments;
	}

	private ArrayList<Integer> getConnected(ArrayList<Integer> connected, int start) {
		if (!connected.contains(start)) {
			connected.add(start);
		}
		for (Integer i : adjacencyList.get(start)) {
			if (!connected.contains(i)) {
				connected.add(i);
				getConnected(connected, i);
			}
		}

		return connected;
	}

	public Section getSectionFromIndex(int index) {
		for (Section section : sections) {
			if (section.getIndex() == index) {
				return section;
			}
		}
		return null;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}
	
	private ArrayList<Section> getSections(ArrayList<Integer> indices) {
		ArrayList<Section> matches = new ArrayList<Section>();
		for (Section section : sections) {
			if (indices.contains(section.getIndex())) {
				matches.add(section);
			}
		}
		return matches;
	}

	public ArrayList<Section> getAdjacentSections(int index) {
		ArrayList<Section> adjacentSections = new ArrayList<Section>();
		ArrayList<Integer> indices = adjacencyList.get(index);
		for (Section section : sections) {
			if (indices.contains(section.getIndex())) {
				adjacentSections.add(section);
			}
		}
		return adjacentSections;
	}

	@Override
	public ArrayList<double[]> getLines() {
		ArrayList<double[]> lines = new ArrayList<double[]>();

		// position of corners of square section
		for (Section section : sections) {
			// get edges of current section, each edge is made up of 4 points
			ArrayList<double[]> sectionLines = section.getLines();

			// rotate edges by object orientation
			for (int i = 0; i < sectionLines.size(); i++) {
				double[] line = new double[] { sectionLines.get(i)[0],// +
																	// position.getX(),
						sectionLines.get(i)[1],// + position.getY(),
						sectionLines.get(i)[2],// + position.getX(),
						sectionLines.get(i)[3],// + position.getY()
				};

				double[] p1 = MathBox.rotatePoint(new double[] { line[0], line[1] }, orientation);
				double[] p2 = MathBox.rotatePoint(new double[] { line[2], line[3] }, orientation);

				p1[0] += objectPosition.getX();
				p1[1] += objectPosition.getY();
				p2[0] += objectPosition.getX();
				p2[1] += objectPosition.getY();

				// double[] p1 = MathBox.rotatePoint(new double[] {line[0],
				// line[1]}, new double[] {position.getX(), position.getY()},
				// orientation);
				// double[] p2 = MathBox.rotatePoint(new double[] {line[2],
				// line[3]}, new double[] {position.getX(), position.getY()},
				// orientation);
				sectionLines.set(i, new double[] { p1[0], p1[1], p2[0], p2[1] });
			}

			// add lines to line storage
			lines.addAll(sectionLines);
		}

		return lines;
	}

	protected abstract PhysicalObject createFragment(ArrayList<Section> fragmentSections, ArrayList<ArrayList<Integer>> adjacencyList);
}
