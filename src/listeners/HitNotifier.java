package listeners;
/**
 * A HitNotifier object can be hit and can notify registered HitListeners about hit events.
 * Classes implementing this interface allow other objects to listen for and respond to hits.
 */
public interface HitNotifier {
    /**
     * Adds the given HitListener to the list of listeners that will be notified of hit events.
     * @param hl the HitListener to add
     */
    void addHitListener(HitListener hl);
    /**
     * Removes the given HitListener from the list of listeners to hit events.
     * @param hl the HitListener to remove
     */
    void removeHitListener(HitListener hl);
}