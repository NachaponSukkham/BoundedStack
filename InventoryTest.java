import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        testInventoryFromList();

        System.out.println();
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    private static void testCreator() {
        System.out.println("Creator");
        Inventory inv = new Inventory();
        check("new inventory created", inv != null);
        check("new inventory size = 0", inv.size() == 0);
    }

    private static void testInventoryFromList() {
        System.out.println("Inventory From List");

        // Test 1: สร้างด้วย Potion และ Sword (size = 2)
        try {
            Inventory inv1 = new Inventory(Arrays.asList("Potion", "Sword"));
            check("Test 1: size = 2", inv1.size() == 2);
        } catch (Exception e) {
            check("Test 1: size = 2", false);
        }

        // Test 2: สร้างด้วย List เปล่า (size = 0)
        try {
            Inventory inv2 = new Inventory(new ArrayList<>());
            check("Test 2: size = 0", inv2.size() == 0);
        } catch (Exception e) {
            check("Test 2: size = 0", false);
        }

        // Test 3: ส่ง null ต้อง throw IllegalArgumentException
        boolean test3Passed = false;
        try {
            new Inventory(null);
        } catch (IllegalArgumentException e) {
            test3Passed = true;
        }
        check("Test 3: null throws exception", test3Passed);

        // Test 4: มีค่า null ใน List ต้อง throw
        boolean test4Passed = false;
        try {
            new Inventory(Arrays.asList("Potion", null));
        } catch (IllegalArgumentException e) {
            test4Passed = true;
        }
        check("Test 4: list with null item throws exception", test4Passed);

        // Test 5: มีสตริงว่างใน List ต้อง throw
        boolean test5Passed = false;
        try {
            new Inventory(Arrays.asList("Potion", ""));
        } catch (IllegalArgumentException e) {
            test5Passed = true;
        }
        check("Test 5: list with empty string throws exception", test5Passed);

        // Test 6: ส่ง List ที่มี 21 รายการ เกิน MAX_SLOTS ต้อง throw
        boolean test6Passed = false;
        try {
            List<String> tooManyItems = new ArrayList<>();
            for (int i = 1; i <= 21; i++) {
                tooManyItems.add("Item" + i);
            }
            new Inventory(tooManyItems);
        } catch (IllegalArgumentException e) {
            test6Passed = true;
        }
        check("Test 6: 21 items exceeds max slots throws exception", test6Passed);
    }
}