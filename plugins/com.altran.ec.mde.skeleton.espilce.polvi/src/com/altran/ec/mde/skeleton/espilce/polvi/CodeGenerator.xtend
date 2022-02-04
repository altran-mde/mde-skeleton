package com.altran.ec.mde.skeleton.espilce.polvi

import java.io.File
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import org.apache.commons.io.IOUtils
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.espilce.polvi.generator.api.fsa.IFileSystemAccess
import org.eclipse.xtext.example.fowlerdsl.statemachine.Statemachine
import java.nio.charset.Charset
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.example.fowlerdsl.statemachine.Command
import org.eclipse.xtext.example.fowlerdsl.statemachine.State

class CodeGenerator {

    def void generate(URI modelURI, String outputPath) {
        val IFileSystemAccess fsa = new IFileSystemAccess() {
            override deleteFile(String fileName) {
                throw new UnsupportedOperationException("Not implemented")
            }

            override generateFile(String fileName, CharSequence contents) {
                val out = new File(fileName)
                out.parentFile.mkdirs
                out.createNewFile
                
                val stream = new BufferedOutputStream(new FileOutputStream(out))
                try {
                    IOUtils.write(contents, stream, Charset.defaultCharset())
                } finally {
                    stream.close()
                }
            }
            
            override generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
                throw new UnsupportedOperationException("Not implemented")
            }
        }
        
        val model = modelURI.loadModelInstance
        val contents = model.toJavaCode
        fsa.generateFile(outputPath, contents)
    }
    
    def Statemachine loadModelInstance(URI modelUri) {
        val resourceSet = new ResourceSetImpl()
        val resource = resourceSet.getResource(modelUri, true)
        resource.getContents().head as Statemachine
    }

    def toJavaCode(Statemachine sm) '''
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        
        public class «sm.eResource.className» {
            
            public static void main(String[] args) {
                new «sm.eResource.className»().run();
            }
            
            «FOR c : sm.commands»
                «c.declareCommand»
            «ENDFOR»
            
            void run() {
                boolean executeActions = true;
                String currentState = "«sm.states.head?.name»";
                String lastEvent = null;
                while (true) {
                    «FOR state : sm.states»
                        «state.generateCode»
                    «ENDFOR»
                    «FOR resetEvent : sm.resetEvents»
                        if ("«resetEvent.name»".equals(lastEvent)) {
                            System.out.println("Resetting state machine.");
                            currentState = "«sm.states.head?.name»";
                            executeActions = true;
                        }
                    «ENDFOR»
                    
                }
            }
            
            private String receiveEvent() {
                System.out.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    return br.readLine();
                } catch (IOException ioe) {
                    System.out.println("Problem reading input");
                    return "";
                }
            }
        }
    '''

    def declareCommand(Command command) '''
        void do«command.name.toFirstUpper»() {
            System.out.println("Executing command «command.name» («command.code»)");
        }
    '''

    def generateCode(State state) '''
        if (currentState.equals("«state.name»")) {
            if (executeActions) {
                «FOR c : state.actions»
                    do«c.name.toFirstUpper»();
                «ENDFOR»
                executeActions = false;
            }
            System.out.println("Your are now in state '«state.name»'. Possible events are [«
                state.transitions.map(t | t.event.name).join(', ')»].");
            lastEvent = receiveEvent();
            «FOR t : state.transitions»
                if ("«t.event.name»".equals(lastEvent)) {
                    currentState = "«t.state.name»";
                    executeActions = true;
                }
            «ENDFOR»
        }
    '''

    def className(Resource res) {
        var name = res.URI.lastSegment
        return name.substring(0, name.indexOf('.')).toFirstUpper
    }

}
