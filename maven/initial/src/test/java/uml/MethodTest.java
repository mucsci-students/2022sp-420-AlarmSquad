package uml;

import junit.framework.TestCase;

public class MethodTest extends TestCase {

    Method setName = new Method("setName", "void");
    Method getName = new Method("getName", "String");
    Parameter newName = new Parameter("newName", "String");
    Parameter approval = new Parameter("approval", "bool");
    Parameter denial = new Parameter("denial", "bool");

    public void testGetReturnType() {
        assertEquals("void", setName.getReturnType());
        assertEquals("String", getName.getReturnType());
    }

    public void testGetParamList() {
        setName.addParameter(newName);
        setName.addParameter(approval);
        assertEquals("newName, approval", setName.getParamList().get(0).getAttName() + ", " +
                setName.getParamList().get(1).getAttName());
    }

    public void testAddParameter() {
        setName.addParameter(newName);
        assertEquals("newName", setName.getParamList().get(0).getAttName());
    }

    public void testDeleteParameter() {
        setName.addParameter(newName);
        setName.addParameter(approval);
        assertEquals("approval", setName.findParameter("approval").getAttName());
        setName.deleteParameter(approval);
        assertEquals(null, setName.findParameter("approval"));
    }

    public void testFindParameter() {
        setName.addParameter(newName);
        assertEquals("newName", setName.findParameter("newName").getAttName());
        assertEquals(null, setName.findParameter("approval"));
    }

    public void testChangeParameter() {
        setName.addParameter(newName);
        setName.addParameter(approval);
        assertEquals("approval", setName.findParameter("approval").getAttName());
        assertEquals(null, setName.findParameter("denial"));
        setName.changeParameter("approval", denial);
        assertEquals(null, setName.findParameter("approval"));
        assertEquals("denial", setName.findParameter("denial").getAttName());
        assertEquals(null, setName.findParameter("approval"));
    }
}