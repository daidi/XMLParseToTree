package org.abego.treelayout.util;

import static org.abego.treelayout.internal.util.Contract.checkArg;

import org.abego.treelayout.Configuration;

/**
 * Specify a {@link Configuration} through configurable parameters, or falling
 * back to some frequently used defaults.
 * 
 * @author Udo Borkowski (ub@abego.org)
 * 
 * 
 * @param <TreeNode>
 */
public class DefaultConfiguration<TreeNode> implements
                Configuration<TreeNode> {

        /**
         * Specifies the constants to be used for this Configuration.
         * 
         * @param gapBetweenLevels
         * @param gapBetweenNodes
         * @param location
         *            [default: {@link org.abego.treelayout.Configuration.Location#Top Top}]
         * @param alignmentInLevel
         *            [default: {@link org.abego.treelayout.Configuration.AlignmentInLevel#Center Center}]
         */
        public DefaultConfiguration(double gapBetweenLevels,
                        double gapBetweenNodes, Location location,
                        AlignmentInLevel alignmentInLevel) {
                checkArg(gapBetweenLevels >= 0, "gapBetweenLevels must be >= 0");
                checkArg(gapBetweenNodes >= 0, "gapBetweenNodes must be >= 0");

                this.gapBetweenLevels = gapBetweenLevels;
                this.gapBetweenNodes = gapBetweenNodes;
                this.location = location;
                this.alignmentInLevel = alignmentInLevel;
        }

        /**
         * Convenience constructor, using a default for the alignmentInLevel.
         * <p>
         * see
         * {@link #DefaultConfiguration(double, double, org.abego.treelayout.Configuration.Location, org.abego.treelayout.Configuration.AlignmentInLevel)}
         */
        public DefaultConfiguration(double gapBetweenLevels,
                        double gapBetweenNodes, Location location) {
                this(gapBetweenLevels, gapBetweenNodes, location,
                                AlignmentInLevel.Center);
        }

        /**
         * Convenience constructor, using a default for the rootLocation and the
         * alignmentInLevel.
         * <p>
         * see
         * {@link #DefaultConfiguration(double, double,  org.abego.treelayout.Configuration.Location, org.abego.treelayout.Configuration.AlignmentInLevel)}
         */
        public DefaultConfiguration(double gapBetweenLevels,
                        double gapBetweenNodes) {
                this(gapBetweenLevels, gapBetweenNodes, Location.Top,
                                AlignmentInLevel.Center);
        }

        // -----------------------------------------------------------------------
        // gapBetweenLevels

        private final double gapBetweenLevels;

        @Override
        public double getGapBetweenLevels(int nextLevel) {
                return gapBetweenLevels;
        }

        // -----------------------------------------------------------------------
        // gapBetweenNodes

        private final double gapBetweenNodes;

        @Override
        public double getGapBetweenNodes(TreeNode node1, TreeNode node2) {
                return gapBetweenNodes;
        }

        // -----------------------------------------------------------------------
        // location

        private final Location location;

        @Override
        public Location getRootLocation() {
                return location;
        }

        // -----------------------------------------------------------------------
        // alignmentInLevel

        private AlignmentInLevel alignmentInLevel;

        @Override
        public AlignmentInLevel getAlignmentInLevel() {
                return alignmentInLevel;
        }

}