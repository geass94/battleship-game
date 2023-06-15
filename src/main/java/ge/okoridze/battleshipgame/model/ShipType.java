package ge.okoridze.battleshipgame.model;

public enum ShipType {
    TWO_SLOT(2, "TWO_SLOT"),
    FOUR_SLOT(4, "FOUR_SLOT");

    private int size;
    private String name;

    ShipType(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public static ShipType getFromName(String name){
        for (ShipType ShipType: ShipType.values()){
            if(ShipType.getName().equals(name)){
                return ShipType;
            }
        }
        throw new RuntimeException("ShipType with provided name[" + name + "] not found");
    }

    public static ShipType getFromSize(int size){
        for (ShipType ShipType: ShipType.values()){
            if(ShipType.size == size){
                return ShipType;
            }
        }
        throw new RuntimeException("ShipType with provided size[" + size + "] not found");
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}