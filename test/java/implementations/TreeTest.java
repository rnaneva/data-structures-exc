package implementations;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TreeTest {

    Tree<Integer> TREE;

    @Before
    public void init() {
        String[] input = {
                "7 19",
                "7 21",
                "7 14",
                "19 1",
                "19 12",
                "19 31",
                "14 23",
                "14 6"
        };
        TreeFactory treeFactory = new TreeFactory();
        TREE = treeFactory.createTreeFromStrings(input);
    }

    @Test
    public void testTreeCreation() {

        assertEquals(Integer.valueOf(7), TREE.getKey());
    }


    @Test
    public void testDFS() {

        List<Integer> elements = new ArrayList<>();
        List<Integer> trees = TREE.dfsElements(elements);

        assertEquals(List.of(7, 19, 1, 12, 31, 21, 14, 23, 6), trees);

    }



    @Test
    public void testBFS() {

        List<Integer> elements = new ArrayList<>();
        List<Integer> trees = TREE.bfs(elements);

        assertEquals(List.of(7, 19, 21, 14, 1, 12, 31, 23, 6), trees);

    }


    @Test
    public void testTreeAsString() {

        assertEquals("7\r\n" +
                "  19\r\n" +
                "    1\r\n" +
                "    12\r\n" +
                "    31\r\n" +
                "  21\r\n" +
                "  14\r\n" +
                "    23\r\n" +
                "    6", TREE.getAsString());
    }

    @Test
    public void testLeafKeys() {

        List<Integer> leafKeys = TREE.getLeafKeys();
        Collections.sort(leafKeys);

        assertEquals(List.of(1, 6, 12, 21, 23, 31), leafKeys);
    }

    @Test
    public void testMiddleNodes() {

        List<Integer> leafKeys = TREE.getMiddleKeys();
        Collections.sort(leafKeys);

        assertEquals(List.of(14, 19), leafKeys);
    }

    @Test
    public void testDeepestLeftmostNode() {

        Tree<Integer> deepestLeftmostNode = TREE.getDeepestLeftmostNode();

        assertEquals(Integer.valueOf(1), deepestLeftmostNode.getKey());
    }

    @Test
    public void testLongestPath() {

        List<Integer> longestPath = TREE.getLongestPath();

        assertEquals(List.of(7, 19, 1), longestPath);
    }

    @Test
    public void testPathsWithGivenSum() {

        List<List<Integer>> lists = TREE.pathsWithGivenSum(27);

        List<List<Integer>> expected =
                List.of(List.of(7, 19, 1), List.of(7, 14, 6));

        for (int i = 0; i < lists.size(); i++) {
            assertEquals(expected.get(i), lists.get(i));
        }
    }

    @Test
    public void testTreesWithGivenSum() {

        List<Tree<Integer>> trees = TREE.subTreesWithGivenSum(43);
        String asString = trees.get(0).getAsString();
        assertTrue(asString.contains("14"));
        assertTrue(asString.contains("23"));
        assertTrue(asString.contains("6"));
    }
}