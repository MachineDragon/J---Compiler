// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

/**
 * A representation of an interface declaration.
 */
class JInterfaceDeclaration extends JAST implements JTypeDecl {
    // Interface modifiers.
    private ArrayList<String> mods;

    // Interface name.
    private String name;

    // Interface block.
    private ArrayList<JMember> interfaceBlock;

    // This interface type.
    private Type thisType;

    // Super interface types.
    private ArrayList<TypeName> superTypes;

    // Context for this interface.
    private ClassContext context;

    /**
     * Constructs an AST node for an interface declaration.
     *
     * @param line           line in which the interface declaration occurs in the source file.
     * @param mods           class modifiers.
     * @param name           class name.
     * @param superTypes     super class types.
     * @param interfaceBlock interface block.
     */
    public JInterfaceDeclaration(int line, ArrayList<String> mods, String name,
                                 ArrayList<TypeName> superTypes,
                                 ArrayList<JMember> interfaceBlock) {
        super(line);
        this.mods = mods;
        this.name = name;
        this.superTypes = superTypes;
        this.interfaceBlock = interfaceBlock;
    }

    /**
     * {@inheritDoc}
     */
    public void declareThisType(Context context) {
    }

    /**
     * {@inheritDoc}
     */
    public void preAnalyze(Context context) {
    }

    /**
     * {@inheritDoc}
     */
    public String name() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public Type superType() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Type thisType() {
        return thisType;
    }

    /**
     * {@inheritDoc}
     */
    public JAST analyze(Context context) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JInterfaceDeclaration:" + line, e);
        if (mods != null) {
            ArrayList<String> value = new ArrayList<String>();
            for (String mod : mods) {
                value.add(String.format("\"%s\"", mod));
            }
            e.addAttribute("modifiers", value);
        }
        e.addAttribute("name", name);
        if (superTypes != null) {
            ArrayList<String> value = new ArrayList<String>();
            for (TypeName typeName : superTypes) {
                value.add(String.format("\"%s\"", typeName.toString()));
            }
            e.addAttribute("super", value);
        }
        if (context != null) {
            context.toJSON(e);
        }
        if (interfaceBlock != null) {
            for (JMember member : interfaceBlock) {
                ((JAST) member).toJSON(e);
            }
        }
    }
}
