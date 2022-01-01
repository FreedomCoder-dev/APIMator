package com.freedomcoder.apigen.parser.apilang.compiler;

import com.freedomcoder.apigen.iface.*;
import com.freedomcoder.apigen.iface.containers.ArrayContainer;
import com.freedomcoder.apigen.iface.containers.ObjectContainer;
import com.freedomcoder.apigen.parser.ContainerFactory;
import com.freedomcoder.apigen.parser.apilang.ApilangBaseVisitor;
import com.freedomcoder.apigen.parser.apilang.ApilangParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ApiParsingVisitor extends ApilangBaseVisitor {

    ApiParseProcessor proc;
    SchemaDom dom;

    public ApiParsingVisitor(ApiParseProcessor proc, SchemaDom dom) {
        this.proc = proc;
        this.dom = dom;
    }

    @Override
    public Object visitImportDeclaration(ApilangParser.ImportDeclarationContext ctx) {
        String text = ctx.STRING().getText();
        try {
            proc.parseLocalFile(text.substring(1, text.length() - 1), dom);
        } catch (IOException e) {
            e.printStackTrace();
            proc.identlog--;
            proc.log("Failed to import file " + text);

        }
        return super.visitImportDeclaration(ctx);
    }

    @Override
    public Object visitApiDeclaration(ApilangParser.ApiDeclarationContext ctx) {

        visitApiGroupDeclaration("", ctx.apiTypeDeclaration());
        return null;
    }

    public Object visitApiMethodDeclaration(String basename, ApilangParser.ApiMethodDeclarationContext ctx) {
        basename += ctx.ID().getText();
        MethodDef def = new MethodDef(dom, basename);

        HashMap<String, ContainerDef> ret = new HashMap<String, ContainerDef>();

        List<ApilangParser.FieldDeclarationContext> fieldDeclaration = ctx.fieldDeclaration();
        for (ApilangParser.FieldDeclarationContext get : fieldDeclaration) {
            String type = get.ID().toString();
            int arrayDepth = 0;
            if (get.ARRBRACK() != null) {
                arrayDepth = get.ARRBRACK().size();
            }
            TerminalNode dESCRIPTION = get.DESCRIPTION();
            String description = dESCRIPTION == null ? "" : dESCRIPTION.getText();
            List<ApilangParser.VariableDeclaratorContext> variableDeclarator = get.variableDeclaratorList().variableDeclarator();
            for (ApilangParser.VariableDeclaratorContext get1 : variableDeclarator) {
                String name = get1.ID().getText();
                get1.atom();
                ContainerDef container = ContainerFactory.makeContainer(dom, type);
                if (arrayDepth > 0) {
                    ArrayContainer cont = new ArrayContainer(dom);
                    cont.setTypes(container);
                    container = cont;
                }
                container.setRequired(true);
                container.setDescription(description);
                ret.put(name, container);
            }
        }

        def.setParams(ret);

        List<ApilangParser.ResponseDeclarationContext> responseDeclaration = ctx.responseDeclaration();
        for (ApilangParser.ResponseDeclarationContext res : responseDeclaration) {
            def.addResponse(ctx.ID().getText(), makeref(visitResponseDeclaration(basename, res)));
        }
        dom.getMethods().put(def.getName(), def);
        return def;
    }

    public ResponseRef makeref(ResponseDef def) {
        ResponseRef ref = new ResponseRef(dom);
        ref.setName("responses.json#/definitions/" + def.getName());
        return ref;
    }

    public ObjectRef makeref(ObjectDef def) {
        ObjectRef ref = new ObjectRef(dom, "");
        ref.setName("objects.json#/definitions/" + def.getName());
        return ref;
    }

    public ResponseDef visitResponseDeclaration(String basename, ApilangParser.ResponseDeclarationContext ctx) {
        String text = basename.replace('.', '_') + '_' + ctx.ID().getText();
        ResponseDef res = new ResponseDef(dom, text);
        ObjectContainer inner = new ObjectContainer(dom);
        List<ApilangParser.ExtFieldDeclarationContext> extFieldDeclaration = ctx.extFieldDeclaration();
        inner.setProperties(parseContainerList(text, extFieldDeclaration));
        res.setParams(inner);
        dom.getResponses().put(text, res);
        return res;
    }

    public HashMap<String, ContainerDef> parseContainerList(String basename, List<ApilangParser.ExtFieldDeclarationContext> list) {
        HashMap<String, ContainerDef> ret = new HashMap<String, ContainerDef>();
        for (ApilangParser.ExtFieldDeclarationContext get0 : list) {
            if (get0.fieldDeclaration() != null) {
                ApilangParser.FieldDeclarationContext get = get0.fieldDeclaration();
                String type = get.ID().toString();
                int arrayDepth = 0;
                if (get.ARRBRACK() != null) {
                    arrayDepth = get.ARRBRACK().size();
                }
                TerminalNode descriptionNode = get.DESCRIPTION();
                String description = descriptionNode == null ? "" : descriptionNode.getText();
                List<ApilangParser.VariableDeclaratorContext> variableDeclarator = get.variableDeclaratorList().variableDeclarator();
                for (ApilangParser.VariableDeclaratorContext get1 : variableDeclarator) {
                    String name = get1.ID().getText();
                    get1.atom();
                    ContainerDef container = ContainerFactory.makeContainer(dom, type);
                    for (int i = 0; i < arrayDepth; i++) {
                        ArrayContainer cont = new ArrayContainer(dom);
                        cont.setTypes(container);
                        container = cont;
                    }
                    container.setRequired(true);
                    container.setDescription(description);
                    ret.put(name, container);
                }
            } else if (get0.inlineObjectDeclaration() != null) {
                ApilangParser.InlineObjectDeclarationContext ctx = get0.inlineObjectDeclaration();
                String name;
                if (ctx.inlineTypeName() != null) {
                    name = ctx.inlineTypeName().ID().getText();
                } else {
                    name = basename + "_" + ctx.ID().getText();
                }
                int arrayDepth = 0;
                if (ctx.ARRBRACK() != null) {
                    arrayDepth = ctx.ARRBRACK().size();
                }

                ObjectContainer cont = new ObjectContainer(dom);
                cont.setRequired(true);
                cont.setName(name);
                cont.setProperties(parseContainerList(name, ctx.extFieldDeclaration()));
                ContainerDef makeContainer = ContainerFactory.makeContainer(dom, name);
                for (int i = 0; i < arrayDepth; i++) {
                    ArrayContainer conta = new ArrayContainer(dom);
                    conta.setTypes(makeContainer);
                    makeContainer = conta;
                }

                ret.put(ctx.ID().getText(), makeContainer);
                ObjectDef def = new ObjectDef(dom, name);
                def.setParams(cont);
                dom.getObjects().put(name, def);
            }
        }
        return ret;
    }

    public void visitApiGroupDeclaration(String basename, List<ApilangParser.ApiTypeDeclarationContext> apielem) {

        for (ApilangParser.ApiTypeDeclarationContext get : apielem) {
            ApilangParser.ApiGroupDeclarationContext apiGroupDeclaration = get.apiGroupDeclaration();
            if (apiGroupDeclaration != null) {
                visitApiGroupDeclaration(basename + apiGroupDeclaration.ID().getText() + '.', apiGroupDeclaration.apiTypeDeclaration());
            } else {
                ApilangParser.ApiMethodDeclarationContext apiMethodDeclaration = get.apiMethodDeclaration();
                if (apiMethodDeclaration != null)
                    visitApiMethodDeclaration(basename, apiMethodDeclaration);
                else {
                    ApilangParser.ObjectDeclarationContext ctx = get.objectDeclaration();
                    if (ctx == null) return;
                    processObject(basename, ctx);
                }
            }
        }
    }


    public void processObject(String basename, ApilangParser.ObjectDeclarationContext ctx) {
        String name;
        if (ctx.ID() != null) {
            name = ctx.ID().getText();
        } else {
            name = basename + "_" + ctx.ID().getText();
        }

        ObjectContainer cont = new ObjectContainer(dom);
        cont.setRequired(true);
        cont.setName(name);
        cont.setProperties(parseContainerList(name, ctx.extFieldDeclaration()));
        ObjectDef def = new ObjectDef(dom, name);
        def.setParams(cont);
        dom.getObjects().put(name, def);
    }

    @Override
    public Object visitFieldDeclaration(ApilangParser.FieldDeclarationContext ctx) {
        return super.visitFieldDeclaration(ctx);
    }

    @Override
    public Object visitObjectDeclaration(ApilangParser.ObjectDeclarationContext ctx) {
        return super.visitObjectDeclaration(ctx);
    }

    @Override
    public Object visitInlineObjectDeclaration(ApilangParser.InlineObjectDeclarationContext ctx) {
        return super.visitInlineObjectDeclaration(ctx);
    }

    @Override
    public Object visitVariableDeclarator(ApilangParser.VariableDeclaratorContext ctx) {
        return super.visitVariableDeclarator(ctx);
    }

}
