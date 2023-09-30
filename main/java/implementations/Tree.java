package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private final E element;
    private Tree<E> parent;
    private final List<Tree<E>> children;


    public Tree(E element) {
        this.element = element;
        this.children = new ArrayList<>();
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }


    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.element;
    }

    @Override
    public String getAsString() {

        StringBuilder sb = new StringBuilder();
        List<Tree<E>> trees = dfsTrees(new ArrayList<>());

        for (Tree<E> tree : trees) {
            int i = checkIndent(tree);
            sb.append(" ".repeat(i))
                    .append(tree.element)
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    private int checkIndent(Tree<E> tree) {
        return measurePath(tree) * 2;
    }

    private int measurePath(Tree<E> tree) {

        if (tree.parent != null) {
            Deque<Tree<E>> queue = new ArrayDeque<>();
            queue.offer(tree);
            int path = 0;

            while (!queue.isEmpty()) {
                Tree<E> currentTree = queue.poll();
                if (currentTree.parent != null) {
                    queue.offer(currentTree.parent);
                    path++;
                } else {
                    return path;
                }
            }
        }

        return 0;
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> leafs = new ArrayList<>();
        Deque<Tree<E>> trees = new ArrayDeque<>();
        trees.offer(this);

        while (!trees.isEmpty()) {
            Tree<E> currentTree = trees.poll();

            if (!currentTree.children.isEmpty()) {

                for (Tree<E> child : currentTree.children) {
                    trees.offer(child);
                }
            } else {
                leafs.add(currentTree.element);
            }
        }

        return leafs;
    }

    public List<Tree<E>> getLeafTrees() {
        List<Tree<E>> leafs = new ArrayList<>();
        Deque<Tree<E>> trees = new ArrayDeque<>();
        trees.offer(this);

        while (!trees.isEmpty()) {
            Tree<E> currentTree = trees.poll();

            if (!currentTree.children.isEmpty()) {

                for (Tree<E> child : currentTree.children) {
                    trees.offer(child);
                }
            } else {
                leafs.add(currentTree);
            }
        }

        return leafs;
    }

    public List<E> dfsElements(List<E> elements) {

        elements.add(this.element);
        if (!children.isEmpty()) {

            for (Tree<E> child : children) {
                child.dfsElements(elements);
            }

        }

        return elements;

    }

    public List<Tree<E>> dfsTrees(List<Tree<E>> trees) {

        trees.add(this);
        if (!children.isEmpty()) {

            for (Tree<E> child : children) {
                child.dfsTrees(trees);
            }

        }

        return trees;

    }

    public List<E> bfs(List<E> elements) {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            elements.add(tree.element);

            if (!tree.children.isEmpty()) {
                for (Tree<E> child : tree.children) {
                    queue.offer(child);
                }
            }
        }

        return elements;
    }


    @Override
    public List<E> getMiddleKeys() {

        List<E> middleKeys = new ArrayList<>();
        Deque<Tree<E>> trees = new ArrayDeque<>();
        trees.offer(this);

        while (!trees.isEmpty()) {

            Tree<E> tree = trees.poll();

            if (!tree.children.isEmpty()) {
                for (Tree<E> child : tree.children) {
                    trees.offer(child);
                }
                if (tree.parent != null) {
                    middleKeys.add(tree.element);
                }
            }

        }

        return middleKeys;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {

        int path = 0;
        Tree<E> deepestLeftMost = this;

        List<Tree<E>> leafTrees = getLeafTrees();

        for (Tree<E> tree : leafTrees) {
            int i = measurePath(tree);
            if (path < i) {
                path = i;
                deepestLeftMost = tree;
            }
        }

        return deepestLeftMost;
    }

    @Override
    public List<E> getLongestPath() {
        int path = 0;
        List<E> longestPath = new ArrayList<>();

        List<Tree<E>> leafTrees = getLeafTrees();

        for (Tree<E> tree : leafTrees) {
            int i = measurePath(tree);
            if (path < i) {
                path = i;
                List<E> list = getElementsOfPath(tree);
                Collections.reverse(list);
                longestPath = list;
            }
        }

        return longestPath;
    }

    private List<E> getElementsOfPath(Tree<E> tree) {
        Tree<E> current = tree;
        List<E> list = new ArrayList<>();

        while (current.parent != null) {
            list.add(current.element);
            current = current.parent;
        }
        list.add(current.element);
        return list;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {

        List<List<E>> paths = new ArrayList<>();
        List<Tree<E>> leafTrees = getLeafTrees();
        for (Tree<E> tree : leafTrees) {

            List<E> elementsOfPath = getElementsOfPath(tree);
            List<Integer> intList = (List<Integer>) elementsOfPath;
            Integer currentSum = intList.stream().reduce(Integer::sum).get();
            if (sum == currentSum) {
                Collections.reverse(elementsOfPath);
                paths.add(elementsOfPath);
            }

        }
        return paths;
    }


    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {

        List<List<Tree<E>>> allSubTrees = getAllSubTrees(this);
        for (var listOfTrees : allSubTrees) {
            List<Integer> elements = (List<Integer>) listOfTrees.stream().map(Tree::getKey).collect(Collectors.toList());
            Integer currentSum = elements.stream().reduce(Integer::sum).get();
            if(sum == currentSum){
                return listOfTrees;
            }
        }


        return null;
    }

    private List<List<Tree<E>>> getAllSubTrees(Tree<E> tree) {
        List<List<Tree<E>>> listOfSubTrees = new ArrayList<>();


        Deque<Tree<E>> subTrees = new ArrayDeque<>();
        subTrees.offer(tree);

        while (!subTrees.isEmpty()) {
            Tree<E> current = subTrees.poll();
            List<Tree<E>> trees = current.dfsTrees(new ArrayList<>());
            listOfSubTrees.add(trees);

            for (Tree<E> child : current.children) {
                subTrees.offer(child);
            }
        }


        return listOfSubTrees;
    }


}



