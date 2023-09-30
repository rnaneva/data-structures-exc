package implementations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeFactory {
    private final Map<Integer, Tree<Integer>> nodesByKeys;


    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {

        for (String elements : input) {
            List<Integer> pair =
                    Arrays.stream(elements.split("\\s+")).map(Integer::parseInt)
                            .collect(Collectors.toList());

            int parent = pair.get(0);
            int child = pair.get(1);

            nodesByKeys.putIfAbsent(parent, createNodeByKey(parent) );
            nodesByKeys.put(child, createNodeByKey(child));
            addEdge(parent, child);
        }

        return getRoot();
    }

    private Tree<Integer> getRoot() {

        for (Tree<Integer> tree : nodesByKeys.values()) {

            Tree<Integer> parent = tree.getParent();
            if(parent == null){
                return tree;
            }
        }

        return null;
    }

    public Tree<Integer> createNodeByKey(int key) {

        return new Tree<>(key);
    }

    public void addEdge(int parent, int child) {
        Tree<Integer> parentTree = nodesByKeys.get(parent);
        Tree<Integer> childTree = nodesByKeys.get(child);

        parentTree.addChild(childTree);
        childTree.setParent(parentTree);

    }
}



