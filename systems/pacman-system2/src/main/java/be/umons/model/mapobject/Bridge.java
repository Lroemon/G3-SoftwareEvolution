package be.umons.model.mapobject;

import be.umons.model.Map;
import be.umons.model.Position;

/**
 * A bridge is a structure carrying a road, path, railway, etc. across a river, road, or other obstacle.
 * In this pacman game it carries a road accross another road
 */
public class Bridge extends Boxes {

    public enum Type {
        NORTH_SOUTH,
        WEST_EAST
    }

    private final Type type;

    public Bridge(Position position, Type type) {
        this.setPosition(position);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return "Bridge [" + position + "]";
    }

    @Override
    public void action(final Pacman pacman) {
        setBrigdeState(pacman);
    }

    @Override
    public void action(final Ghost ghost) {
        setBrigdeState(ghost);
    }

    private void setBrigdeState(DynamicTarget dt) {
        if (this.type == Type.NORTH_SOUTH &&
                dt.getBridgeState() != BridgeState.UNDER_NS_BRIDGE && dt.getBridgeState() != BridgeState.ON_NS_BRIDGE) {
            if (dt.getHeadingTo() == Map.Direction.EAST || dt.getHeadingTo() == Map.Direction.WEST){
                dt.changeBridgeState(BridgeState.UNDER_NS_BRIDGE);
            } else if (dt.getHeadingTo() == Map.Direction.NORTH || dt.getHeadingTo() == Map.Direction.SOUTH){
                dt.changeBridgeState(BridgeState.ON_NS_BRIDGE);
            }
        } else if (this.type == Type.WEST_EAST &&
                dt.getBridgeState() != BridgeState.UNDER_WE_BRIDGE && dt.getBridgeState() !=BridgeState.ON_WE_BRIDGE) {
            if (dt.getHeadingTo() == Map.Direction.EAST || dt.getHeadingTo() == Map.Direction.WEST){
                dt.changeBridgeState(BridgeState.ON_WE_BRIDGE);
            } else if (dt.getHeadingTo() == Map.Direction.NORTH || dt.getHeadingTo() == Map.Direction.SOUTH){
                dt.changeBridgeState(BridgeState.UNDER_WE_BRIDGE);
            }
        }
    }

    public enum BridgeState{
        /**
         * The object is on a west-east bridge.
         */
        ON_WE_BRIDGE(true),

        /**
         * The object is on a north-south bridge.
         */
        ON_NS_BRIDGE(true),

        /**
         * The object is under a west-east bridge.
         */
        UNDER_WE_BRIDGE(false),

        /**
         * The object is under a north-south bridge.
         */
        UNDER_NS_BRIDGE(false),

        /**
         * The object is not on a bridge.
         */
        NOT_ON_BRIDGE(true);

        private final boolean isVisible;
        BridgeState(final boolean isVisible) { this.isVisible = isVisible; }
        public boolean isVisible() { return this.isVisible; }
    }
}
