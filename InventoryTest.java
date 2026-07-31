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

        Inventory inv = new Inventory();

        check("new inventory created", inv != null);

        System.out.println();
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }
}