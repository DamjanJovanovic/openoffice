/**************************************************************
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 *************************************************************/



package mod._sw;

import java.io.PrintWriter;

import lib.StatusException;
import lib.TestCase;
import lib.TestEnvironment;
import lib.TestParameters;
import util.SOfficeFactory;

import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.style.XStyleFamiliesSupplier;
import com.sun.star.text.ControlCharacter;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.Type;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;

/**
 * Test for object which is represented by service
 * <code>com.sun.star.text.NumberingRules</code>.<p>
 * Object implements the following interfaces :
 * <ul>
 *  <li> <code>com::sun::star::beans::XPropertySet</code></li>
 *  <li> <code>com::sun::star::container::XIndexAccess</code></li>
 *  <li> <code>com::sun::star::container::XElementAccess</code></li>
 *  <li> <code>com::sun::star::text::NumberingRules</code></li>
 * </ul> <p>
 * This object test <b> is NOT </b> designed to be run in several
 * threads concurently.
 * @see com.sun.star.beans.XPropertySet
 * @see com.sun.star.container.XIndexAccess
 * @see com.sun.star.container.XElementAccess
 * @see com.sun.star.text.NumberingRules
 * @see ifc.beans._XPropertySet
 * @see ifc.container._XIndexAccess
 * @see ifc.container._XElementAccess
 * @see ifc.text._NumberingRules
 */
public class SwXNumberingRules extends TestCase {
    XTextDocument xTextDoc;

    /**
    * Creates text document.
    */
    protected void initialize( TestParameters tParam, PrintWriter log ) {
        SOfficeFactory SOF = SOfficeFactory.getFactory( (XMultiServiceFactory)tParam.getMSF() );
        try {
            log.println( "creating a textdocument" );
            xTextDoc = SOF.createTextDoc( null );
        } catch ( com.sun.star.uno.Exception e ) {
            e.printStackTrace( log );
            throw new StatusException( "Couldn't create document", e );
        }
    }

    /**
    * Disposes text document.
    */
    protected void cleanup( TestParameters tParam, PrintWriter log ) {
        log.println( "    disposing xTextDoc " );
        util.DesktopTools.closeDoc(xTextDoc);
    }

    /**
    * Creating a Testenvironment for the interfaces to be tested.
    * At first some strings are inserted to a text document, then style families
    * are gotten from text document using <code>XStyleFamiliesSupplier</code>.
    * Then style family 'NuberingRules' is retrieved, then style indexed '0'
    * is obtained. At the end property value 'NumberingRules' is obtained from
    * given style.
    */
    protected TestEnvironment createTestEnvironment(TestParameters tParam, PrintWriter log) {
        XInterface oObj = null;
        XIndexAccess NumStyleI = null;

        XText oText = xTextDoc.getText();
        XTextCursor oCursor = oText.createTextCursor();

        try {
            for (int i=0; i<5; i++) {
                oText.insertString(oCursor, "The quick brown fox jumps "+
                    "over the lazy Dog", false);
                oText.insertControlCharacter(oCursor,
                    ControlCharacter.PARAGRAPH_BREAK, false);
            }
        } catch ( com.sun.star.lang.IllegalArgumentException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        }

        XStyleFamiliesSupplier oStyleFamiliesSupplier = (XStyleFamiliesSupplier)
            UnoRuntime.queryInterface(XStyleFamiliesSupplier.class, xTextDoc);

        try {
            XNameAccess oStyleFamilies = oStyleFamiliesSupplier.getStyleFamilies();
            XNameContainer NumStyles = (XNameContainer) AnyConverter.toObject(
                new Type(XNameContainer.class),
                    oStyleFamilies.getByName("NumberingStyles"));
            NumStyleI = (XIndexAccess)
                UnoRuntime.queryInterface(XIndexAccess.class,NumStyles);
        } catch ( com.sun.star.lang.WrappedTargetException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        } catch ( com.sun.star.container.NoSuchElementException e ) {
            log.println("Error, no such style family...");
            e.printStackTrace(log);
        } catch ( com.sun.star.lang.IllegalArgumentException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        }
        
        Object instance1 = null;

        try {
            oObj = (XInterface) AnyConverter.toObject(
                    new Type(XInterface.class),NumStyleI.getByIndex(0));
            XPropertySet props = (XPropertySet)
                UnoRuntime.queryInterface(XPropertySet.class, oObj);
            oObj = (XInterface) AnyConverter.toObject(
                new Type(XInterface.class),props.getPropertyValue("NumberingRules"));
            XIndexAccess nRules = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class, props.getPropertyValue("NumberingRules"));
            instance1 = nRules.getByIndex(0);
        } catch ( com.sun.star.lang.WrappedTargetException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        } catch ( com.sun.star.lang.IndexOutOfBoundsException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        } catch ( com.sun.star.beans.UnknownPropertyException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        } catch ( com.sun.star.lang.IllegalArgumentException e ) {
            log.println("Error, exception occurred...");
            e.printStackTrace(log);
        }        
        
        TestEnvironment tEnv = new TestEnvironment( oObj );
        
        tEnv.addObjRelation("INSTANCE1", instance1);
        return tEnv;
    }

}    // finish class SwXNumberingRules