public class InventoryTest {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {

        testCreator();

        System.out.println();
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    private static void testCreator() {

        System.out.println("-- Creator --");

        Inventory inv = new Inventory();

        check("new inventory created", inv != null);

        check("new inventory size = 0",
                inv.size() == 0);
    }
}