package a4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

/** @author Amy Huang, Maya Leong */
public class PhDTreeTest {
    private static final Professor prof1 = new Professor("Amy", "Huang");
    private static final Professor prof2 = new Professor("Maya", "Leong");
    private static final Professor prof3 = new Professor("Matthew", "Hui");
    private static final Professor prof4 = new Professor("Arianna", "Curillo");
    private static final Professor prof5 = new Professor("Michelle", "Gao");
    private static final Professor prof6 = new Professor("Isa", "Siu");

    //extra people for the test cases
    private static final Professor prof7 = new Professor("Mary", "Shelley");
    private static final Professor prof8 = new Professor("Marie", "Curie");
    private static final Professor prof9 = new Professor("Huiwen", "Zhang");
    private static final Professor prof10 = new Professor("Lana", "Sheng");
    private static final Professor prof11 = new Professor("Yasuan", "Yang");

    private static final Professor obiwan = new Professor("Obi-wan", "Kenobi");
    private static final Professor yoda = new Professor("Yoda", "LastName");
    private static final Professor qui = new Professor("Qui-Gon", "Jinn");
    private static final Professor ashoka = new Professor("Ashoka", "Tano");
    private static final Professor count = new Professor("Count", "Dooku");
    private static final Professor anakin = new Professor("Anakin", "Skywalker");
    private static final Professor luke = new Professor("Luke", "Skywalker");
    private static final Professor leia = new Professor("Leia", "Organa");
    private static final Professor ben = new Professor("Ben", "Solo");

    private static PhDTree tree1() {
        return new PhDTree(prof1, 2023);
    }
    private static PhDTree tree2() {
        return new PhDTree(prof4, 2019);
    }

    private static PhDTree tree3() {
        PhDTree t = new PhDTree(prof1, 1950);
        t.insert(prof1, prof2, 1960);
        t.insert(prof2, prof3, 1970);
        return t;
    }

    @Test
    public void constructorTests() {
        assertEquals("Amy Huang", tree1().toString());
        assertEquals("Arianna Curillo", tree2().toString());
    }

    @Test
    public void getterTests() {
        assertEquals(prof1, tree1().prof());
        // we have not inserted anything into the tree yet
        PhDTree t = new PhDTree(prof1, 2000);
        assertEquals(0, t.numAdvisees());
    }

    @Test
    public void insertTests() {
        // add professor 2 as a child of professor 1
        PhDTree t = new PhDTree(prof1, 1950);
        t.insert(prof1, prof2, 1960);
        t.insert(prof2, prof3, 1970);
        assertEquals(1, t.numAdvisees());
        assertEquals(3, t.size());
        assertEquals("Amy Huang[Maya Leong[Matthew Hui]]", t.toString());

        PhDTree t2 = new PhDTree(prof1, 1950);
        t2.insert(prof1, prof2, 1989);
        assertEquals(2, t2.size());
        assertEquals(1, t2.numAdvisees());
        assertEquals("Amy Huang[Maya Leong]", t2.toString());

        //case: one advisor, many advisees. adding leaves to one root.
        PhDTree t3 = new PhDTree(prof1, 1950);
        t3.insert(prof1, prof3, 2000);
        t3.insert(prof1, prof4, 2020);
        t3.insert(prof1, prof5, 1999);
        assertEquals(4, t3.size());
        assertEquals(3, t3.numAdvisees());
        assertEquals("Amy Huang[Arianna Curillo, Michelle Gao, Matthew Hui]", t3.toString());

        //inserting a leaf to a root that already has an advisee
        PhDTree t4 = new PhDTree(anakin, 2000);
        t4.insert(anakin, luke, 2001);
        t4.insert(anakin, leia, 2001);
        assertEquals(3, t4.size());
        assertEquals(2, t4.numAdvisees());
        assertTrue(t4.contains(leia));
        assertFalse(t4.contains(obiwan));
        assertEquals("Anakin Skywalker[Leia Organa, Luke Skywalker]", t4.toString());

        //inserting a leaf to an advisor that is not the root
        PhDTree t5 = new PhDTree(obiwan, 2002);
        t5.insert(obiwan, anakin, 2003);
        t5.insert(obiwan, ashoka, 2004);
        t5.insert(anakin, luke, 2006);
        assertTrue(t5.contains(luke));
        assertFalse(t5.contains(leia));
        assertEquals(2, t5.numAdvisees());
        assertEquals(4, t5.size());
        assertEquals(2, t5.maxDepth());

    }

    @Test
    public void findTreeTests() {
        PhDTree tree1 = tree1();
        tree1.insert(prof1, prof2, 1950);
        tree1.insert(prof2, prof3, 1960);
        PhDTree tree4 = new PhDTree(prof2, 1950);
        tree4.insert(prof2, prof3, 1980);
        try {
            assertEquals(tree4.prof(), tree1.findTree(prof2).prof());
            assertEquals("Maya Leong[Matthew Hui]", tree1.findTree(prof2).toString());
        } catch (NotFound e) { fail("Not found"); }

        assertThrows(NotFound.class, () -> tree2().findTree(prof5));
        assertThrows(NotFound.class, () -> tree1.findTree(prof4));
        try {
            assertEquals(1, tree1.findTree(prof3).size());
        } catch (NotFound e) {
            fail("Not found");
        }

        //p is the root and the leaf; no children nodes.
        PhDTree starWar = new PhDTree(yoda, 200);
        try {assertEquals("Yoda LastName", starWar.findTree(yoda).toString());}
        catch(NotFound e){fail("Not found");}
        //making sure it's throwing NotFound
        assertThrows(NotFound.class, () -> starWar.findTree(anakin));

        //p is the root with one advisee.
        starWar.insert(yoda, count, 400);
        starWar.insert(count, qui, 300);
        try{
            assertEquals("Yoda LastName[Count Dooku[Qui-Gon Jinn]]",
                    starWar.findTree(yoda).toString());
        }
        catch(NotFound e){
            fail("Not found");
        }
        //making sure it's throwing NotFound
        assertThrows(NotFound.class, () -> starWar.findTree(obiwan));

        //case: one advisor with two advisees, p is the root
        PhDTree simpleBiFurTree = new PhDTree(obiwan, 200);
        simpleBiFurTree.insert(obiwan, anakin, 250);
        simpleBiFurTree.insert(obiwan, luke, 300);
        assertTrue(simpleBiFurTree.contains(anakin));
        try{
            assertEquals("Obi-wan Kenobi[Anakin Skywalker, Luke Skywalker]",
                    simpleBiFurTree.findTree(obiwan).toString());
            //case: p is leaf
            assertEquals("Luke Skywalker", simpleBiFurTree.findTree(luke).toString());
        }
        catch(NotFound e){
            fail("Not found");
        }
        //making sure it's throwing NotFound
        assertThrows(NotFound.class, () -> simpleBiFurTree.findTree(leia));

        //case: p is not a leaf nor a root. has a parent and at least one child node.
        PhDTree complexBiFur = new PhDTree(yoda, 200);
        complexBiFur.insert(yoda, obiwan, 210);
        complexBiFur.insert(obiwan, anakin, 230);
        complexBiFur.insert(obiwan, luke, 380);
        complexBiFur.insert(anakin, leia, 380);
        complexBiFur.insert(anakin, ashoka, 260);
        try{
            assertEquals("Obi-wan Kenobi[Anakin Skywalker[Leia Organa, Ashoka Tano], Luke Skywalker]",
                    complexBiFur.findTree(obiwan).toString());
            assertEquals("Anakin Skywalker[Leia Organa, Ashoka Tano]",
                    complexBiFur.findTree(anakin).toString());
        }
        catch(NotFound e){
            fail("Not found");
        }
        assertThrows(NotFound.class, () -> complexBiFur.findTree(count));

    }

    @Test
    public void numAdviseesTest(){
        //edge case: root with no advisees
        PhDTree t2 = new PhDTree(prof1, 1900);
        assertEquals(0, t2.numAdvisees());
        //adivsee to root with one respective advisee
        PhDTree t3 = new PhDTree(prof1, 1900);
        t3.insert(prof1, prof2, 1960);
        t3.insert(prof2, prof3,  1970);
        assertEquals(1, t3.numAdvisees());

        //non-root node with multiple advisees
        PhDTree t4 = new PhDTree(prof1, 1900);
        t4.insert(prof1, prof2, 1960);
        t4.insert(prof1, prof3,1970);
        assertEquals(2, t4.numAdvisees());

        //one advisee with multiple advisors, children of root have many children.
        PhDTree t5 = new PhDTree(prof1, 1900);
        t5.insert(prof1, prof2, 1960);
        t5.insert(prof1, prof3,1970);
        t5.insert(prof1, prof11, 2010);
        t5.insert(prof2, prof4, 1969);
        t5.insert(prof2, obiwan,2010);
        t5.insert(prof3, prof5, 2000);
        t5.insert(prof5, prof7, 2001);
        t5.insert(prof3, prof9, 2010);
        t5.insert(prof7, prof8, 2001);
        assertEquals(3, t5.numAdvisees());
    }

    @Test
    public void sizeTest() {
        PhDTree t = new PhDTree(prof1, 1900);
        t.insert(prof1, prof2, 1920);
        t.insert(prof2, prof3, 1930);
        assertEquals(3, t.size());

        //edge case: tree with root and no children
        PhDTree t2 = new PhDTree(prof1, 1900);
        assertEquals(1, t2.size());
        //root with more than one advisee, advisees each have one child.
        PhDTree t3 = new PhDTree(prof1, 1900);
        t3.insert(prof1, prof2, 1990);
        t3.insert(prof2, prof3, 2000);
        t3.insert(prof1, prof4, 2001);
        t3.insert(prof4, prof5, 2005);
        assertEquals(5, t3.size());
        //double digit value. root has many advisees, advisees have multiple children nodes.
        PhDTree t4 = new PhDTree(prof1, 1900);
        t4.insert(prof1, prof2, 1960);
        t4.insert(prof1, prof3,1970);
        t4.insert(prof1, prof11, 2010);
        t4.insert(prof2, prof4, 1969);
        t4.insert(prof3, prof5, 2000);
        t4.insert(prof5, prof7, 2001);
        t4.insert(prof3, prof9, 2010);
        t4.insert(prof7, prof8, 2001);
        t4.insert(prof9, prof10, 2023);
        assertEquals(10, t4.size());
    }

    @Test
    public void containsTest() {
        PhDTree t = new PhDTree(prof1, 1900);
        t.insert(prof1, prof2, 1920);
        t.insert(prof2, prof3, 1930);
        assertTrue(t.contains(new Professor("Amy", "Huang")));
        assertFalse(t.contains(prof6));
    }

    @Test
    public void findAcademicLineageTest() {
        PhDTree t = new PhDTree(prof1, 1900);
        t.insert(prof1, prof2, 1920);
        t.insert(prof2, prof3, 1930);
        List<Professor> lineage1 = new LinkedList<>();
        lineage1.add(prof1);
        lineage1.add(prof2);
        lineage1.add(prof3);
        try {
            assertEquals(lineage1, t.findAcademicLineage(prof3));
        } catch (NotFound e) {
            fail("Not found");
        }
        //--
        PhDTree starwars = new PhDTree(yoda, 200);
        starwars.insert(yoda, count, 300);
        starwars.insert(yoda, leia, 380);
        starwars.insert(count, qui, 400);
        starwars.insert(qui, obiwan, 450);
        starwars.insert(obiwan, anakin, 470);
        starwars.insert(obiwan, luke, 500);
        starwars.insert(anakin, ashoka, 480);

        //case: p is not a leaf, and is not a direct advisee to the root.
        List<Professor> starwarsLinObi = new LinkedList<>();
        starwarsLinObi.add(yoda);
        starwarsLinObi.add(count);
        starwarsLinObi.add(qui);
        starwarsLinObi.add(obiwan);
        try {
            assertEquals(starwarsLinObi, starwars.findAcademicLineage(obiwan));
            assertFalse(yoda.equals(starwars.findAdvisor(obiwan)));
        }
        catch(NotFound e){
            fail("Not found");
        }
        //case: p is not a descendant of the root.
        assertThrows(NotFound.class, () -> starwars.findAcademicLineage(prof1));

        //case: p is the root.
        List<Professor> starwarsLinYoda = new LinkedList<>();
        starwarsLinYoda.add(yoda);
        try {
            assertEquals(starwarsLinYoda, starwars.findAcademicLineage(yoda));
        }
        catch(NotFound e){
            fail("Not found");
        }
        //case: p is not a leaf, is a direct advisee of the root.
        List<Professor> starwarsLinCount = new LinkedList<>();
        starwarsLinCount.add(yoda);
        starwarsLinCount.add(count);
        try{
            assertEquals(starwarsLinCount, starwars.findAcademicLineage(count));
            assertTrue(yoda.equals(starwars.findAdvisor(count)));
        }
        catch(NotFound e){
            fail("Not found");
        }
        //case: p is a leaf, is an indirect ancestor of the root
        List<Professor> starwarsLinLuke = new LinkedList<>();
        starwarsLinLuke.add(yoda);
        starwarsLinLuke.add(count);
        starwarsLinLuke.add(qui);
        starwarsLinLuke.add(obiwan);
        starwarsLinLuke.add(luke);
        try{
            assertEquals(starwarsLinLuke, starwars.findAcademicLineage(luke));
            assertFalse(yoda.equals(starwars.findAdvisor(luke)));
        }
        catch(NotFound e){
            fail("Not found");
        }
        //case: p is a direct advisee and a leaf.
        List<Professor> starwarsLinLeia = new LinkedList<>();
        starwarsLinLeia.add(yoda);
        starwarsLinLeia.add(leia);
        try{
            assertEquals(starwarsLinLeia, starwars.findAcademicLineage(leia));
            assertTrue(yoda.equals(starwars.findAdvisor(leia)));
        }
        catch(NotFound e){
            fail("Not found");
        }

        //extra tests
        List<Professor> aLin = new LinkedList<>();
        aLin.add(yoda);
        aLin.add(count);
        aLin.add(qui);
        aLin.add(obiwan);
        aLin.add(anakin);
        aLin.add(ashoka);
        try {
            assertEquals(aLin, starwars.findAcademicLineage(ashoka));
        }
        catch(NotFound e){
            fail("Not found");
        }

        PhDTree simpleStar = new PhDTree(obiwan, 1900);
        simpleStar.insert(obiwan, anakin, 20);
        simpleStar.insert(anakin, luke, 50);
        List<Professor> simpleLin = new LinkedList<>();
        simpleLin.add(obiwan);
        simpleLin.add(anakin);
        assertTrue(simpleStar.contains(luke));
        try {
            assertEquals(simpleLin, simpleStar.findAcademicLineage(anakin));
        }
        catch(NotFound e){
            fail("Not found");
        }
        PhDTree twins = new PhDTree(anakin, 2000);
        twins.insert(anakin, leia, 2001);
        twins.insert(anakin, luke, 2001);
        List<Professor> twinList = new LinkedList<>();
        twinList.add(anakin);
        twinList.add(leia);
        try{
            assertEquals(twinList, twins.findAcademicLineage(leia));
        }
        catch(NotFound e){
            fail("Not found");
        }
    }

    @Test
    public void commonAncestorTest() {
        PhDTree t = tree3();
        try {
            assertEquals(prof2, t.commonAncestor(prof2, prof3));
            assertEquals(prof1, t.commonAncestor(prof1, prof3));
        } catch (NotFound exc) {
            fail("Not found");
        }
        assertThrows(NotFound.class, () -> t.commonAncestor(prof5, prof3));

        PhDTree skywalker = new PhDTree(anakin, 2000);
        skywalker.insert(anakin, luke, 2001);
        skywalker.insert(anakin, leia, 2002);
        skywalker.insert(leia, ben, 2003);
        try{
            //case: prof1 and prof2 are both leaves and direct advisees of the root.
            assertEquals(anakin, skywalker.commonAncestor(luke, leia));
            assertTrue(skywalker.findAdvisor(luke).equals(skywalker.findAdvisor(leia)));
            //case: one of the professors is the root of the tree, the other is a leaf and direct advisee.
            assertEquals(anakin, skywalker.commonAncestor(luke, anakin));
        }
        catch(NotFound e){
            fail("Not found");
        }
        assertThrows(NotFound.class, () -> skywalker.commonAncestor(luke, obiwan));


        PhDTree jediOrder = new PhDTree(yoda, 1000);
        jediOrder.insert(yoda, count, 1110);
        jediOrder.insert(yoda, leia, 2000);
        jediOrder.insert(leia, ben, 2025);
        jediOrder.insert(count, qui, 1150);
        jediOrder.insert(qui, obiwan, 1180);
        jediOrder.insert(obiwan, anakin, 2000);
        jediOrder.insert(anakin, ashoka, 2010);
        jediOrder.insert(anakin, luke, 2015);

        try{
            //case: prof1 and prof2 are not leaves, and they are direct advisees of the root.
            assertEquals(yoda, jediOrder.commonAncestor(count, leia));
            assertTrue(jediOrder.findAdvisor(leia).equals(jediOrder.findAdvisor(count)));
            //case: prof1 and prof2 are leaves and they are not direct advisees of the root.
            assertEquals(yoda, jediOrder.commonAncestor(ben, luke));
            assertFalse(jediOrder.findAdvisor(luke).equals(jediOrder.findAdvisor(ben)));
            //case: prof1 and prof2 are not leaves, neither one is a root, and they are both not
            // direct advisees of the root.
            assertEquals(yoda, jediOrder.commonAncestor(leia, anakin));
            assertEquals(obiwan, jediOrder.commonAncestor(obiwan, anakin));
        }
        catch(NotFound e){
            fail("Not found");
        }

    }

    @Test
    public void maxDepthTest() {
        PhDTree t = tree3();
        assertEquals(2, t.maxDepth());

        //edge case: no advisees, one leaf (root)
        PhDTree t2 = new PhDTree(prof1, 1900);
        assertEquals(0, t2.maxDepth());

        //multiple advisees, with their own advisees. Lineages have varying depths.
        PhDTree t4 = new PhDTree(prof1, 1900);
        t4.insert(prof1, prof2, 1960);
        t4.insert(prof1, prof3,1970);
        t4.insert(prof1, prof11, 2010);
        t4.insert(prof2, prof4, 1969);
        t4.insert(prof3, prof5, 2000);
        t4.insert(prof5, prof7, 2001);
        t4.insert(prof3, prof9, 2010);
        t4.insert(prof7, prof8, 2001);
        t4.insert(prof9, prof10, 2023);
        assertEquals(4, t4.maxDepth());

        //multiple advisees, all with the same depth.
        PhDTree t5 = new PhDTree(prof1, 1900);
        t5.insert(prof1, prof2, 1960);
        t5.insert(prof1, prof3, 1960);
        t5.insert(prof1, prof4, 1960);
        t5.insert(prof1, prof5, 1960);
        t5.insert(prof1, prof6, 1960);
        assertEquals(1, t5.maxDepth());

        //one path through the tree
        PhDTree t6 = new PhDTree(prof1, 1900);
        t6.insert(prof1, prof2, 1960);
        t6.insert(prof2, prof3, 1980);
        t6.insert(prof3, prof4, 2001);
        t6.insert(prof4, prof5, 2002);
        assertEquals(4, t6.maxDepth());

    }

    @Test
    public void getAdvisorTest() {
        PhDTree t = tree3();
        try {
            assertEquals(prof2.toString(), t.findAdvisor(prof3).toString());
        } catch (NotFound e) {
            fail("Not found");
        }
        assertThrows(NotFound.class, () -> t.findAdvisor(prof1));
        //---
        PhDTree skywalker = new PhDTree(obiwan, 2001);
        skywalker.insert(obiwan, anakin, 2002);
        skywalker.insert(obiwan, luke, 2005);
        skywalker.insert(anakin, ashoka, 2010);
        try{
            //case: p is leaf, advisor is root. advisor has multiple advisees.
            assertEquals(obiwan.toString(), skywalker.findAdvisor(anakin).toString());
            assertEquals(obiwan.toString(), skywalker.findAdvisor(luke).toString());
            //case: p is a leaf, advisor is not a root.
            assertEquals(anakin.toString(), skywalker.findAdvisor(ashoka).toString());
        }
        catch(NotFound e){
            fail("Not found");
        }
        //case: p is not a descendant of the root node of the tree (not in the tree).
        assertThrows(NotFound.class, () -> skywalker.findAdvisor(leia));

        //case: one advisor with lots of advisees (leaves)
        PhDTree manyAdv = new PhDTree(yoda, 1001);
        manyAdv.insert(yoda, count, 1050);
        manyAdv.insert(yoda, qui, 2000);
        manyAdv.insert(yoda, obiwan, 2001);
        manyAdv.insert(yoda, anakin, 2002);
        try{
            assertEquals(yoda.toString(), manyAdv.findAdvisor(anakin).toString());
            assertEquals(yoda.toString(), manyAdv.findAdvisor(count).toString());
            assertEquals(yoda.toString(), manyAdv.findAdvisor(obiwan).toString());
            assertEquals(yoda.toString(), manyAdv.findAdvisor(qui).toString());
        }
        catch(NotFound e){
            fail("Not found");
        }
        assertThrows(NotFound.class, () -> manyAdv.findAdvisor(luke));

        //case: p is the root, it has no advisors in the tree.
        PhDTree noAdv = new PhDTree(yoda, 1180);
        assertThrows(NotFound.class, () -> noAdv.findAdvisor(yoda));

        //case: p is not a leaf, and p's advisor is not a root.
        PhDTree complex = new PhDTree(yoda, 1180);
        complex.insert(yoda, count, 1190);
        complex.insert(yoda, qui, 2000);
        complex.insert(qui, obiwan, 2010);
        complex.insert(obiwan, anakin, 2008);
        complex.insert(obiwan, ashoka, 2010);
        complex.insert(anakin, luke, 2016);
        complex.insert(anakin, leia, 2016);
        try{
            assertEquals(obiwan.toString(), complex.findAdvisor(anakin).toString());
            assertEquals(qui.toString(), complex.findAdvisor(obiwan).toString());
        }
        catch(NotFound e){
            fail("Not found");
        }
        assertThrows(NotFound.class, () -> complex.findAdvisor(yoda));
    }

    @Test
    public void toStringVerbose() {
        PhDTree t = tree3();
        String[] lines = t.toStringVerbose().split("\n");
        String[] expected = {
            "Amy Huang - 1950",
            "Maya Leong - 1960",
            "Matthew Hui - 1970"
        };
        Arrays.sort(lines);
        Arrays.sort(expected);
        assertTrue(Arrays.equals(lines, expected));

        //case: root has multiple direct advisees
        PhDTree starWars = new PhDTree(yoda, 2000);
        starWars.insert(yoda, count, 2005);
        starWars.insert(yoda, leia, 2010);
        starWars.insert(count, qui, 2010);
        starWars.insert(qui, obiwan, 2015);
        starWars.insert(obiwan, anakin, 2020);
        starWars.insert(obiwan, luke, 2025);
        String[] lines2 = starWars.toStringVerbose().split("\n");
        String[] expected2 = {
                "Yoda LastName - 2000",
                "Count Dooku - 2005",
                "Leia Organa - 2010",
                "Qui-Gon Jinn - 2010",
                "Obi-wan Kenobi - 2015",
                "Anakin Skywalker - 2020",
                "Luke Skywalker - 2025",
        };
        Arrays.sort(lines2);
        Arrays.sort(expected2);
        assertTrue(Arrays.equals(lines2, expected2));

        //case: tree consisting of one root/leaf
        PhDTree rootOnly = new PhDTree(prof9, 2020);
        String[] lines3 = rootOnly.toStringVerbose().split("\n");
        String[] expected3 = {
                "Huiwen Zhang - 2020"
        };
        Arrays.sort(lines3);
        Arrays.sort(expected3);
        assertTrue(Arrays.equals(lines3, expected3));

        //case: each advisor only has one advisee-- one path from the leaf to the root all on the left.
        PhDTree starwarsLeft = new PhDTree(yoda, 2000);
        starwarsLeft.insert(yoda, count, 2001);
        starwarsLeft.insert(count, leia, 2002);
        starwarsLeft.insert(leia, qui, 2003);
        String[] lines4 = starwarsLeft.toStringVerbose().split("\n");
        String[] expected4 = {
                "Yoda LastName - 2000",
                "Count Dooku - 2001",
                "Leia Organa - 2002",
                "Qui-Gon Jinn - 2003"
        };
        Arrays.sort(lines4);
        Arrays.sort(expected4);
        assertTrue(Arrays.equals(lines4, expected4));
    }
}
