package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;

public class HeadApplicationTest {
	static HeadApplication headApp;
	static InputStream is;
	static OutputStream os;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		headApp = new HeadApplication();
	}

	@Before
	public void setUp() throws Exception {
		is = null;
		os = new ByteArrayOutputStream();
	}

	@Test
	public void testHeadOptionAndFile() {
		String args[] = "-n 3 test.txt".split(" ");
		String expected = "line 1"+System.lineSeparator()+"line 2"+System.lineSeparator()+"line 3"+System.lineSeparator()+"";
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testHeadOptionExceedFileLines() {
		String args[] = "-n 1000 test.txt".split(" ");
		// 4 lines in test.txt, should print all 4 lines
		String expected = "line 1"+System.lineSeparator()+"line 2"+System.lineSeparator()+"line 3"+System.lineSeparator()+"line 4"+System.lineSeparator()+"";
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testHeadFileOnly() {
		String args[] = "test2.txt".split(" ");
		// 12 lines in test2.txt, should only print 10 lines
		String expected = "line 1"+System.lineSeparator()+"line 2"+System.lineSeparator()+"line 3"+System.lineSeparator()+"line 4"+System.lineSeparator()+"line 5"+System.lineSeparator()+"line 6"+System.lineSeparator()+"line 7"+System.lineSeparator()+"line 8"+System.lineSeparator()+"line 9"+System.lineSeparator()+"line 10"+System.lineSeparator()+"";
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testEmptyFile() {
		String args[] = "fileTestEmpty.txt".split(" ");
		String expected = ""; // newline is not added
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testOnlySpacesFile() {
		String args[] = "fileTest_onlyspaces.txt".split(" ");
		String expected = "     "+System.lineSeparator()+"";
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testOnlyNewlinesFile() {
		String args[] = { "fileTest only newlines.txt" };
		String expected = ""+System.lineSeparator()+""+System.lineSeparator()+""+System.lineSeparator()+""+System.lineSeparator()+"";
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testManyNewlinesFile() {
		String args[] = { "fileTest manynewlines.txt" };
		String expected = System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = os.toString();
		assertEquals(expected, output);
	}

	@Test
	public void testHeadEmptyInput() {
		String args[] = "".split(" ");
		Exception exc = new Exception();
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			exc = e;
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		assertEquals("head: File not found", exc.getMessage());
	}

	@Test
	public void testHeadInvalidFile() {
		String args[] = "sdfsd".split(" ");
		Exception exc = new Exception();
		try {
			headApp.run(args, is, os);
		} catch (AbstractApplicationException e) {
			exc = e;
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		assertEquals("head: File not found", exc.getMessage());
	}

	@Test
	// Integrated Test
	public void testOverAllFromStdin() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		String args = "cat test.txt | head -n 2";
		String expected = "line 1"+System.lineSeparator()+"line 2"+System.lineSeparator();
		shell.parseAndEvaluate(args, os);
		assertEquals(expected, os.toString());

	}

	@Test
	public void testOverAllFromFile() throws AbstractApplicationException, ShellException {
		os = new ByteArrayOutputStream();
		ShellImpl shell = new ShellImpl();
		String args = "head -n 2 test.txt";
		String expected = "line 1" + System.lineSeparator() +"line 2"+System.lineSeparator();
		shell.parseAndEvaluate(args, os);
		assertEquals(expected, os.toString());
	}

}
