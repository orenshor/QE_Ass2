package system;

public class LeafStub extends Leaf{
    public int size;
    /** Array of blocks containing system.Leaf data */
    public int[] allocations;/**
     * Ctor - create leaf.
     *
     * @param name Name of the leaf.
     * @param size Size, in KB, of the leaf.
     * @throws OutOfSpaceException Allocating space failed.
     */
    public LeafStub(String name, int size) throws OutOfSpaceException {
        super(name,size);
    }

}
