package net.itsrelizc.mcserver.LanguageManager;

public class LexerObject {
    public LexerType getType() {
        return type;
    }

    public void setType(LexerType type) {
        this.type = type;
    }

    private LexerType type;

    public LexerInnerObjectType getValue() {
        return value;
    }

    public void setValue(LexerInnerObjectType value) {
        this.value = value;
    }

    private LexerInnerObjectType value;

    public Object getInner_Val() {
        return inner_Val;
    }

    public void setInner_Val(Object inner_Val) {
        this.inner_Val = inner_Val;
    }

    private Object inner_Val = null;
    public LexerObject(LexerType type, LexerInnerObjectType inner){
        this.type = type;
        this.value = inner;
    }
    public LexerObject(LexerType type, Object inner){
        this.type = type;
        this.value = null;
        this.inner_Val = inner;
    }
}
