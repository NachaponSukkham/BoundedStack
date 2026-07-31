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
        testContains();
        testCount();

        System.out.println();
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    private static void testCreator() {
        System.out.println("-- Creator --");
        Inventory inv = new Inventory();
        check("new inventory created", inv != null);
        check("new inventory size = 0", inv.size() == 0);
    }

    private static void testInventoryFromList() {
        System.out.println("-- Inventory From List --");

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

        // Test 7: ชื่อไอเทมซ้ำกัน (ติดกัน) ต้อง throw IllegalArgumentException
        boolean testDuplicatePassed = false;
        try {
            new Inventory(
                Arrays.asList(
                    "Potion",
                    "Potion"
                )
            );
        } catch (IllegalArgumentException e) {
            testDuplicatePassed = true;
        }
        check(
            "duplicate item names throw exception",
            testDuplicatePassed
        );

        // Test 8: Single item (1 item valid minimum non-empty)
        try {
            Inventory invSingle = new Inventory(Arrays.asList("Potion"));
            check("Test 8: single item inventory size = 1", invSingle.size() == 1);
        } catch (Exception e) {
            check("Test 8: single item inventory size = 1", false);
        }

        // Test 9: 20 items พอดี (Boundary valid MAX_SLOTS)
        try {
            List<String> maxItems = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                maxItems.add("Item" + i);
            }
            Inventory invMax = new Inventory(maxItems);
            check("Test 9: 20 items allowed", invMax.size() == 20);
        } catch (Exception e) {
            check("Test 9: 20 items allowed", false);
        }

        // Test 10: Duplicate แบบไม่ติดกัน ต้อง throw
        boolean testNonAdjacentDuplicatePassed = false;
        try {
            new Inventory(
                Arrays.asList(
                    "Potion",
                    "Sword",
                    "Potion"
                )
            );
        } catch (IllegalArgumentException e) {
            testNonAdjacentDuplicatePassed = true;
        }
        check("Test 10: non-adjacent duplicate throws exception", testNonAdjacentDuplicatePassed);

        // Test 11: Defensive Copy (modify original list after constructor call)
        try {
            List<String> source =
                new ArrayList<>(
                    Arrays.asList(
                        "Potion",
                        "Sword"
                    )
                );

            Inventory inv =
                new Inventory(source);

            source.add("Apple");

            check(
                "Test 11: modifying source list does not affect inventory",
                inv.size() == 2
            );

        } catch (Exception e) {
            check(
                "Test 11: modifying source list does not affect inventory",
                false
            );
        }
    }

    private static void testContains() {
        System.out.println("-- Contains Observer --");

        Inventory inv = new Inventory(Arrays.asList("Potion", "Sword"));

        // Test 12: contains existing item
        check("Test 12: contains existing item (Potion)", inv.contains("Potion"));

        // Test 13: does not contain missing item
        check("Test 13: does not contain missing item (Shield)", !inv.contains("Shield"));

        // Test 14: contains(null) throws IllegalArgumentException
        boolean nullPassed = false;
        try {
            inv.contains(null);
        } catch (IllegalArgumentException e) {
            nullPassed = true;
        }
        check("Test 14: contains null throws exception", nullPassed);

        // Test 15: contains("") throws IllegalArgumentException
        boolean emptyPassed = false;
        try {
            inv.contains("");
        } catch (IllegalArgumentException e) {
            emptyPassed = true;
        }
        check("Test 15: contains empty string throws exception", emptyPassed);
    }

    private static void testCount() {
        System.out.println("-- Count Observer --");

        Inventory inv =
            new Inventory(
                Arrays.asList(
                    "Potion",
                    "Sword"
                )
            );

        check(
            "Test 16: count existing item",
            inv.count("Potion") == 1
        );

        check(
            "Test 17: count missing item",
            inv.count("Shield") == 0
        );

        boolean nullPassed = false;

        try {
            inv.count(null);
        } catch (IllegalArgumentException e) {
            nullPassed = true;
        }

        check(
            "Test 18: count null throws exception",
            nullPassed
        );

        boolean emptyPassed = false;

        try {
            inv.count("");
        } catch (IllegalArgumentException e) {
            emptyPassed = true;
        }

        check(
            "Test 19: count empty string throws exception",
            emptyPassed
        );
    }
}