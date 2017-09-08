// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.neo4j.data;

import java.util.ArrayList;
import java.util.List;

public class Relationship implements Cloneable {

    private String type;

    private Direction direction;

    private Index index = new Index();

    public Relationship() {
        direction = Direction.OUTGOING;
    }

    private Relationship(String type, Direction direction, Index index) {
        this.type = type;
        this.direction = direction;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + ((index == null) ? 0 : index.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Relationship other = (Relationship) obj;
        if (direction != other.direction) {
            return false;
        }
        if (index == null) {
            if (other.index != null) {
                return false;
            }
        } else if (!index.equals(other.index)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public Relationship clone() {
        return new Relationship(type, direction, index.clone());
    }

    public static enum Direction {
        OUTGOING("Outgoing"),
        INCOMING("Incoming");

        private Direction(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public static Direction getFromName(String value) {
            if (value == null || value.isEmpty()) {
                return null;
            }
            for (Direction direction : Direction.values()) {
                if (value.equals(direction.name)) {
                    return direction;
                }
            }
            return null;
        }

        public static String[] getNames() {
            List<String> names = new ArrayList<String>(values().length);
            for (Direction direction : values()) {
                names.add(direction.name);
            }
            return names.toArray(new String[names.size()]);
        }
    }
}
