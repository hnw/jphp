package ru.regenix.jphp.runtime.memory;

import ru.regenix.jphp.runtime.type.HashTable;

public class ReferenceMemory extends Memory {

    public Memory value;

    public ReferenceMemory(Memory value) {
        super(Type.REFERENCE);
        this.value = value == null ? Memory.NULL : value;
    }

    public static Memory valueOf(Memory value){
        return new ReferenceMemory(value);
    }

    public ReferenceMemory() {
        super(Type.REFERENCE);
        this.value = Memory.NULL;
    }

    @Override
    public long toLong() {
        return value.toLong();
    }

    @Override
    public double toDouble() {
        return value.toDouble();
    }

    @Override
    public boolean toBoolean() {
        return value.toBoolean();
    }

    @Override
    public Memory toNumeric() {
        return value.toNumeric();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Memory inc() {
        return value.inc();
    }

    @Override
    public Memory dec() {
        return value.dec();
    }

    @Override
    public Memory negative() {
        return value.negative();
    }

    @Override
    public Memory plus(Memory memory) {
        return value.plus(memory);
    }

    @Override
    public Memory minus(Memory memory) {
        return value.minus(memory);
    }

    @Override
    public Memory mul(Memory memory) {
        return value.mul(memory);
    }

    @Override
    public Memory div(Memory memory) {
        return value.div(memory);
    }

    @Override
    public Memory mod(Memory memory) {
        return value.mod(memory);
    }

    @Override
    public boolean equal(Memory memory) {
        return value.equal(memory);
    }

    @Override
    public boolean notEqual(Memory memory) {
        return value.notEqual(memory);
    }

    @Override
    public String concat(Memory memory) {
        return value.concat(memory);
    }

    @Override
    public boolean smaller(Memory memory) {
        return value.smaller(memory);
    }

    @Override
    public boolean smallerEq(Memory memory) {
        return value.smallerEq(memory);
    }

    @Override
    public boolean greater(Memory memory) {
        return value.greater(memory);
    }

    @Override
    public boolean greaterEq(Memory memory) {
        return value.greaterEq(memory);
    }

    @Override
    public Memory toImmutable() {
        if (value instanceof ReferenceMemory)
            return value.toImmutable();
        else
            return value;
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public Memory assign(Memory memory) {
        if (value.type == Type.REFERENCE)
            return value.assign(memory);
        else
            return value = memory.toImmutable();
    }

    @Override
    public Memory assign(long value) {
        if (this.value.type == Type.REFERENCE)
            return this.value.assign(value);
        else
            return this.value = LongMemory.valueOf(value);
    }

    @Override
    public Memory assign(String value) {
        if (this.value.type == Type.REFERENCE)
            return this.value.assign(value);
        else
            return this.value = new StringMemory(value);
    }

    @Override
    public Memory assign(boolean value) {
        if (this.value.type == Type.REFERENCE)
            return this.value.assign(value);
        else
            return this.value = value ? TRUE : FALSE;
    }

    @Override
    public Memory assign(double value) {
        if (this.value.type == Type.REFERENCE)
            return this.value.assign(value);
        else
            return this.value = new DoubleMemory(value);
    }

    @Override
    public void assignRef(Memory memory) {
        if (memory instanceof ReferenceMemory){
            ReferenceMemory reference = (ReferenceMemory)memory;
            if (reference.value instanceof ReferenceMemory)
                value = reference.value;
            else
                value = reference;
        } else
            value = memory;
    }

    @Override
    public Memory minus(long value) {
        return this.value.minus(value);
    }

    private StringMemory typeString(){
        if (toImmutable().type != Type.STRING){
            assign(new StringMemory(value.toString()));
        }

        return (StringMemory)toImmutable();
    }

    @Override
    public void concatAssign(Memory memory) {
        typeString().concatAssign(memory);
    }

    @Override
    public void concatAssign(String value) {
        typeString().append(value);
    }

    @Override
    public void concatAssign(long value) {
        typeString().append(value);
    }

    @Override
    public void concatAssign(double value) {
        typeString().append(value);
    }

    @Override
    public void concatAssign(boolean value) {
        typeString().append(value);
    }

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    @Override
    public void unset() {
        this.value = NULL;
    }

    @Override
    public Memory valueOfIndex(Memory index) {
        switch (value.type){
            case STRING: return CharMemory.valueOf(this, (StringMemory)this.value, (int)index.toNumeric().toLong());
            case ARRAY:
            case REFERENCE: return value.valueOfIndex(index);
            default:
                return new ArrayItemMemory(HashTable.toKey(index), this);
        }
    }

    @Override
    public Memory valueOfIndex(long index) {
        switch (value.type){
            case STRING: return CharMemory.valueOf(this, (StringMemory)this.value, (int)index);
            case ARRAY:
            case REFERENCE: return value.valueOfIndex(index);
            default:
                return new ArrayItemMemory(index, this);
        }
    }

    @Override
    public Memory valueOfIndex(double index) {
        switch (value.type){
            case STRING: return CharMemory.valueOf(this, (StringMemory)this.value, (int)index);
            case ARRAY:
            case REFERENCE: return value.valueOfIndex(index);
            default:
                return new ArrayItemMemory((long)index, this);
        }
    }

    @Override
    public Memory valueOfIndex(String index) {
        switch (value.type){
            case STRING: return CharMemory.valueOf(this, (StringMemory)this.value, (int)value.toNumeric().toLong());
            case ARRAY:
            case REFERENCE: return value.valueOfIndex(index);
            default:
                return new ArrayItemMemory(index, this);
        }
    }

    @Override
    public Memory valueOfIndex(boolean index) {
        switch (value.type){
            case STRING: return CharMemory.valueOf(this, (StringMemory)this.value, index ? 1 : 0);
            case ARRAY:
            case REFERENCE: return value.valueOfIndex(index);
            default:
                return new ArrayItemMemory(index ? 0L : 1L, this);
        }
    }
}