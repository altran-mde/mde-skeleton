package com.altran.ec.mde.skeleton.espilce.polvi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Command;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Event;
import org.eclipse.xtext.example.fowlerdsl.statemachine.State;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine;
import org.eclipse.xtext.example.fowlerdsl.statemachine.Transition;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.espilce.polvi.generator.api.fsa.IFileSystemAccess;

@SuppressWarnings("all")
public class CodeGenerator {
  public void generate(final URI modelURI, final String outputPath) {
    final IFileSystemAccess fsa = new IFileSystemAccess() {
      @Override
      public void deleteFile(final String fileName) {
        throw new UnsupportedOperationException("Not implemented");
      }
      
      @Override
      public void generateFile(final String fileName, final CharSequence contents) {
        try {
          final File out = new File(fileName);
          out.getParentFile().mkdirs();
          out.createNewFile();
          FileOutputStream _fileOutputStream = new FileOutputStream(out);
          final BufferedOutputStream stream = new BufferedOutputStream(_fileOutputStream);
          try {
            IOUtils.write(contents, stream, Charset.defaultCharset());
          } finally {
            stream.close();
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
      
      @Override
      public void generateFile(final String fileName, final String outputConfigurationName, final CharSequence contents) {
        throw new UnsupportedOperationException("Not implemented");
      }
    };
    final Statemachine model = this.loadModelInstance(modelURI);
    final CharSequence contents = this.toJavaCode(model);
    fsa.generateFile(outputPath, contents);
  }
  
  public Statemachine loadModelInstance(final URI modelUri) {
    Statemachine _xblockexpression = null;
    {
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final Resource resource = resourceSet.getResource(modelUri, true);
      EObject _head = IterableExtensions.<EObject>head(resource.getContents());
      _xblockexpression = ((Statemachine) _head);
    }
    return _xblockexpression;
  }
  
  public CharSequence toJavaCode(final Statemachine sm) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.io.BufferedReader;");
    _builder.newLine();
    _builder.append("import java.io.IOException;");
    _builder.newLine();
    _builder.append("import java.io.InputStreamReader;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _className = this.className(sm.eResource());
    _builder.append(_className);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public static void main(String[] args) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("new ");
    String _className_1 = this.className(sm.eResource());
    _builder.append(_className_1, "        ");
    _builder.append("().run();");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    {
      EList<Command> _commands = sm.getCommands();
      for(final Command c : _commands) {
        _builder.append("    ");
        CharSequence _declareCommand = this.declareCommand(c);
        _builder.append(_declareCommand, "    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("void run() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("boolean executeActions = true;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("String currentState = \"");
    State _head = IterableExtensions.<State>head(sm.getStates());
    String _name = null;
    if (_head!=null) {
      _name=_head.getName();
    }
    _builder.append(_name, "        ");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("String lastEvent = null;");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("while (true) {");
    _builder.newLine();
    {
      EList<State> _states = sm.getStates();
      for(final State state : _states) {
        _builder.append("            ");
        CharSequence _generateCode = this.generateCode(state);
        _builder.append(_generateCode, "            ");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Event> _resetEvents = sm.getResetEvents();
      for(final Event resetEvent : _resetEvents) {
        _builder.append("            ");
        _builder.append("if (\"");
        String _name_1 = resetEvent.getName();
        _builder.append(_name_1, "            ");
        _builder.append("\".equals(lastEvent)) {");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("    ");
        _builder.append("System.out.println(\"Resetting state machine.\");");
        _builder.newLine();
        _builder.append("            ");
        _builder.append("    ");
        _builder.append("currentState = \"");
        State _head_1 = IterableExtensions.<State>head(sm.getStates());
        String _name_2 = null;
        if (_head_1!=null) {
          _name_2=_head_1.getName();
        }
        _builder.append(_name_2, "                ");
        _builder.append("\";");
        _builder.newLineIfNotEmpty();
        _builder.append("            ");
        _builder.append("    ");
        _builder.append("executeActions = true;");
        _builder.newLine();
        _builder.append("            ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("            ");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("private String receiveEvent() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("System.out.flush();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("BufferedReader br = new BufferedReader(new InputStreamReader(System.in));");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("try {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return br.readLine();");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("} catch (IOException ioe) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("System.out.println(\"Problem reading input\");");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return \"\";");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence declareCommand(final Command command) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void do");
    String _firstUpper = StringExtensions.toFirstUpper(command.getName());
    _builder.append(_firstUpper);
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("System.out.println(\"Executing command ");
    String _name = command.getName();
    _builder.append(_name, "    ");
    _builder.append(" (");
    String _code = command.getCode();
    _builder.append(_code, "    ");
    _builder.append(")\");");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateCode(final State state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (currentState.equals(\"");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append("\")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("if (executeActions) {");
    _builder.newLine();
    {
      EList<Command> _actions = state.getActions();
      for(final Command c : _actions) {
        _builder.append("        ");
        _builder.append("do");
        String _firstUpper = StringExtensions.toFirstUpper(c.getName());
        _builder.append(_firstUpper, "        ");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("        ");
    _builder.append("executeActions = false;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("System.out.println(\"Your are now in state \'");
    String _name_1 = state.getName();
    _builder.append(_name_1, "    ");
    _builder.append("\'. Possible events are [");
    final Function1<Transition, String> _function = (Transition t) -> {
      return t.getEvent().getName();
    };
    String _join = IterableExtensions.join(ListExtensions.<Transition, String>map(state.getTransitions(), _function), ", ");
    _builder.append(_join, "    ");
    _builder.append("].\");");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("lastEvent = receiveEvent();");
    _builder.newLine();
    {
      EList<Transition> _transitions = state.getTransitions();
      for(final Transition t : _transitions) {
        _builder.append("    ");
        _builder.append("if (\"");
        String _name_2 = t.getEvent().getName();
        _builder.append(_name_2, "    ");
        _builder.append("\".equals(lastEvent)) {");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("currentState = \"");
        String _name_3 = t.getState().getName();
        _builder.append(_name_3, "        ");
        _builder.append("\";");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("executeActions = true;");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public String className(final Resource res) {
    String name = res.getURI().lastSegment();
    return StringExtensions.toFirstUpper(name.substring(0, name.indexOf(".")));
  }
}
