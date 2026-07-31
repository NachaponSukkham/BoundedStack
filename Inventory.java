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
 * Safety from Rep Exposure ระบบรักษาความปลอดภัย: 
 * กระเป๋าใบนี้ถูกล็อคไว้ ระบบภายนอกจะไม่สามารถเข้ามาล้วงหรือแอบแก้ไขข้อมูล
 * ไอเทมได้โดยตรง หากมีคนอื่นอยากรู้ว่าในกระเป๋ามีอะไร ระบบจะทำแค่ "ถ่ายเอกสารสำเนา" 
 * รายชื่อของไปให้ดูเท่านั้น เพื่อป้องกันของหายหรือถูกโกง
 */
public class Inventory {

    public static final int MAX_SLOTS = 20;

    // เก็บไอเทมไว้เป็น private final เพื่อป้องกันคนนอกมาแก้ List
    private final List<Item> items;

    // คลาสย่อยทำเป็น private เพื่อซ่อนไม่ให้คนนอกเห็นโครงสร้างภายใน
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
     * Creator: สร้าง Inventory ว่างๆ ขึ้นมา
     */
    public Inventory() {
        this.items = new ArrayList<>();
        checkRep();
    }

    /**
     * Representation Invariant คอยตรวจสอบว่ากฎของกระเป๋ายังเป็นจริงอยู่หรือไม่ 
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