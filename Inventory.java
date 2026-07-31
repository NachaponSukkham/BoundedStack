import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Inventory — ADT แทนช่องเก็บของของผู้เล่น RPG
 * 
 * Abstraction Function:
 * items = ช่องเก็บของของผู้เล่น
 * แต่ละ Item แทนไอเท็มหนึ่งชนิด มีได้สูงสุด 20 ช่อง
 * quantity แทนจำนวนของไอเท็มนั้น
 * 
 * หน้าที่ของมัน: 
 * คอยจัดเก็บว่าผู้เล่นมีไอเทมอะไรบ้าง และมีจำนวนอย่างละกี่ชิ้น
 * - ไอเทมทุกชิ้นต้องมีชื่อชัดเจน จะเป็นของไร้ชื่อไม่ได้
 * - ของในกระเป๋าต้องมีจำนวนตั้งแต่ 1 ชิ้นขึ้นไป ถ้าเป็น 0 คือต้องเอาออกไปเลย
 * - กระเป๋ามีช่องเก็บของจำกัด ห้ามเก็บของเกินจำนวนช่อง
 * - ของชนิดเดียวกันต้องไม่ถูกแยกเป็นหลายช่อง ชื่อไอเทมในแต่ละช่องห้ามซ้ำกัน
 * 
 * ระบบรักษาความปลอดภัย (Safety from Rep Exposure): 
 * กระเป๋าใบนี้ถูกล็อคไว้อย่างแน่นหนา ระบบภายนอกจะไม่สามารถเข้ามาล้วงหรือแอบแก้ไขข้อมูล
 * ไอเทมได้โดยตรง หากมีคนอื่นอยากรู้ว่าในกระเป๋ามีอะไร ระบบจะทำแค่ "ถ่ายเอกสารสำเนา" 
 * รายชื่อของไปให้ดูเท่านั้น เพื่อป้องกันของหายหรือถูกโกง
 */
public class Inventory {

    public static final int MAX_SLOTS = 20;

    private final List<Item> items;

    private static class Item {
        private final String name;
        private int quantity;

        Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    /** 
     * สร้าง Inventory ว่างๆ ขึ้นมา
     */
    public Inventory() {
        this.items = new ArrayList<>();
        checkRep();
    }

    /** 
     * สร้าง Inventory จากรายชื่อไอเทมเริ่มต้น พร้อม Validation และ Defensive Copy
     */
    public Inventory(List<String> initialItems) {
        // 1. ตรวจสอบว่า List เป็น null หรือไม่
        if (initialItems == null) {
            throw new IllegalArgumentException("initialItems must not be null");
        }
        
        // 2. ตรวจสอบว่าจำนวนเกิน MAX_SLOTS หรือไม่
        if (initialItems.size() > MAX_SLOTS) {
            throw new IllegalArgumentException("initialItems exceeds max slots");
        }

        Set<String> seenNames = new HashSet<>();
        
        // วนตรวจสอบข้อมูลนำเข้าแต่ละตัวตามกฎ
        for (String itemName : initialItems) {
            // 3. ตรวจสอบว่า Item เป็น null หรือไม่
            if (itemName == null) {
                throw new IllegalArgumentException("item name must not be null");
            }
            // 4. ตรวจสอบว่าชื่อ Item เป็นสตริงว่างหรือไม่
            if (itemName.isEmpty()) {
                throw new IllegalArgumentException("item name must not be empty");
            }
            // 5. ตรวจสอบชื่อซ้ำ (รองรับทั้งแบบติดกันและไม่ติดกัน)
            if (!seenNames.add(itemName)) {
                throw new IllegalArgumentException("duplicate item name: " + itemName);
            }
        }

        // 6. ผ่านการตรวจสอบทั้งหมดแล้ว ค่อยสร้างรายการจริง (Defensive Copy)
        this.items = new ArrayList<>();
        for (String itemName : initialItems) {
            this.items.add(new Item(itemName, 1));
        }
        
        checkRep();
    }

    /**
     * Observer คืนจำนวนชนิดของไอเท็มใน Inventory
     */
    public int size() {
        return items.size();
    }

    /**
     * Observer ตรวจสอบว่ามีไอเทมชื่อนี้อยู่ใน Inventory หรือไม่
     */
    public boolean contains(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Representation Invariant คอยตรวจสอบว่ากฎเหล็กของกระเป๋ายังเป็นจริงอยู่หรือไม่
     */
    private void checkRep() {
        assert items != null : "items must not be null";
        assert items.size() <= MAX_SLOTS : "inventory exceeds max slots";

        Set<String> names = new HashSet<>();

        for (Item item : items) {
            assert item != null : "item must not be null";
            assert item.getName() != null : "item name must not be null";
            assert !item.getName().isEmpty() : "item name must not be empty";
            assert item.getQuantity() > 0 : "quantity must be positive";
            assert names.add(item.getName()) : "duplicate item name";
        }
    }
}