package org.danRtech.spaceshipdata.model;

/**
 * Enumeration of the spaceship type.
 * <p> NOTE: "Type-safe enum" design pattern used. <p/>
 */
public enum SpaceshipType {
    Scout("Scout"),
    Transport("Transport"),
    Battleship("Battleship"),
    Varies("Varies");

    private String tag;

    private SpaceshipType(String tag){
        this.tag = tag;
    }

    public static SpaceshipType findByTag(String theTag){
        for(SpaceshipType shipType : SpaceshipType.values()){
            if(shipType.tag.equalsIgnoreCase(theTag)){
                return shipType;
            }
        }
        return null;
    }
}
